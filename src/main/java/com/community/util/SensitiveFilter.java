package com.community.util;

import org.apache.commons.lang3.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换符
    private static final String  REPLACEMENT = "***";

    //定义根节点
    private TrieNode rootNode = new TrieNode();

    //构造前缀树
    @PostConstruct
    private void init(){
        //读取敏感词文件
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while((keyword = reader.readLine()) != null){
                this.addKeyword(keyword);
            }
        } catch (Exception e) {
            logger.error("加载敏感词文件失败：" + e.getMessage());
        }
    }

    //添加单个敏感词到前缀树中
    private void addKeyword(String keyword){
        TrieNode tempNode = rootNode;
        for(int i = 0; i < keyword.length(); i++){
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if(subNode == null){
                //初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNodes(c, subNode);
            }
            //当前节点指向子节点，进入下一轮循环
            tempNode = subNode;
            if(i == keyword.length()-1){
                tempNode.setIsKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     * @param text 待过滤的文本
     * @return  过滤后的文本
     */
    public String filter(String text){
        //定义前缀树中的指针
        TrieNode tempNode = rootNode;
        //定义文本中的起始指针
        int start = 0;
        //定义文本中的终止指针
        int position = 0;
        //定义结果的文本
        StringBuilder sb = new StringBuilder();

        //空值处理
        if(text == null){
            return null;
        }

        //这里用终止指针是因为终止指针能够更快判断完文本
        while(position < text.length()){
            char c = text.charAt(position);

            //跳过符号
            if(isSymbol(c)){
                //如果前缀树中的指针指向根节点说明这个符号没有夹杂在敏感词中间，可以作为结果
                if(tempNode == rootNode){
                    sb.append(c);
                    start++;
                }
                position++;
                continue;
            }

            TrieNode subNode = tempNode.getSubNode(c);
            //如果判定中途子节点为空，说明当前start指针指向的字符不满足条件，start可以++
            if(subNode == null){
                sb.append(text.charAt(start));
                position = ++start;
                tempNode = rootNode;
                continue;
            } else if (subNode.getIsKeywordEnd()){
                //如果节点判定到了最终节点，说明是敏感词，直接替换，并从position的下一个位置继续循环
                sb.append(REPLACEMENT);
                start = ++position;
                tempNode = rootNode;
                continue;
            }
            //这里说明判定还没有结束，可以继续判定，将子节点设为当前节点，并将position后移更新
            tempNode = subNode;
            position++;
        }
        //待检测的字符串最终没满足敏感词条件，虽然判定完了，但还未加到结果上，因此需要再添加上
        sb.append(text.substring(start));
        return sb.toString();
    }

    //判断是否为符号
    private boolean isSymbol(Character c){
        // 0x2E80~0x9FFF 是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    //前缀树节点类
    private class TrieNode{
        //标识是否为尾节点，关键词结束标识
        private boolean isKeywordEnd = false;
        //表示当前节点的子节点，不止一个，(key是下级字符,value是下级节点)
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean getIsKeywordEnd() {
            return isKeywordEnd;
        }

        public void setIsKeywordEnd(boolean isKeywordEnd) {
            this.isKeywordEnd = isKeywordEnd;
        }

        //添加子节点
        public void addSubNodes(Character c, TrieNode subNode){
            subNodes.put(c, subNode);
        }

        //获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }
}
