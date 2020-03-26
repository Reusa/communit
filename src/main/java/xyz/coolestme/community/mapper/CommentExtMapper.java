package xyz.coolestme.community.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import xyz.coolestme.community.model.Comment;
import xyz.coolestme.community.model.CommentExample;

import java.util.List;

public interface CommentExtMapper {
    int inCommentCount(Comment comment);
}