package xyz.coolestme.community.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import xyz.coolestme.community.dto.QuestionQueryDTO;
import xyz.coolestme.community.model.Question;
import xyz.coolestme.community.model.QuestionExample;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question question);
    int inCommentCount(Question question);
    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}