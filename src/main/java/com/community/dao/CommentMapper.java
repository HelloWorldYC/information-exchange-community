package com.community.dao;

import com.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    //通过评论的类型（对帖子评论或者对帖子评论的评论）和评论的对象（帖子或评论）筛选出评论
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    //获得帖子评论的总数或者对评论的评论总数
    int selectCountByEntity(int entityType, int entityId);

    //添加评论
    int insertComment(Comment comment);

    Comment selectCommentById(int id);
}
