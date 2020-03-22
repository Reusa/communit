package xyz.coolestme.community.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import xyz.coolestme.community.model.Question;
import xyz.coolestme.community.model.QuestionExample;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question question);

}