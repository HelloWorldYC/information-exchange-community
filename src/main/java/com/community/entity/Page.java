package com.community.entity;

/**
 * 封装分页相关的信息
 */
public class Page {

    //当前页码，从页面返回
    private int current = 1;

    //每页显示条数，从页面返回
    private int limit = 10;

    //数据总数（用于计算总页数），从数据库返回
    private int rows;

    //查询路径（用于复用分页链接），从数据库返回
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        //仅当要设置的值合理才允许设置
        if(current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        //仅当要设置的值合理才允许设置
        if(limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        //仅当要设置的值合理才允许设置
        if(rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取当前页的起始行
     * @return
     */
    public int getOffset(){
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     * @return
     */
    public int getTotalPage(){
//        return rows % limit == 0 ? rows / limit : rows / limit + 1;
        if (rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

    /**
     * 获得起始页码
     * @return
     */
    public int getFrom(){
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    /**
     * 获取结束页码
     * @return
     */
    public int getTo(){
        int to = current + 2;
        int total = getTotalPage();
        return to > total ? total : to;
    }
}
