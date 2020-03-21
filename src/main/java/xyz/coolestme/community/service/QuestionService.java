package xyz.coolestme.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.coolestme.community.dto.PageinationDTO;
import xyz.coolestme.community.dto.QuestionDTO;
import xyz.coolestme.community.mapper.QuestionMapper;
import xyz.coolestme.community.mapper.UserMapper;
import xyz.coolestme.community.model.Question;
import xyz.coolestme.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PageinationDTO list(Integer page, Integer size) {

        PageinationDTO pageinationDTO = new PageinationDTO();
        Integer totalCount = questionMapper.count();
        pageinationDTO.setPageination(totalCount,page,size);
        if (page < 1){
            page = 1;
        }
        if (page > pageinationDTO.getTotalPage()){
            page = pageinationDTO.getTotalPage();
        }

        //size*(page-1)
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();



        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        pageinationDTO.setQuestions(questionDTOList);

        return pageinationDTO;
    }

    public PageinationDTO list(Integer userId, Integer page, Integer size) {
        PageinationDTO pageinationDTO = new PageinationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        pageinationDTO.setPageination(totalCount,page,size);
        if (page < 1){
            page = 1;
        }
        if (page > pageinationDTO.getTotalPage()){
            page = pageinationDTO.getTotalPage();
        }

        //size*(page-1)
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();



        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        pageinationDTO.setQuestions(questionDTOList);

        return pageinationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question =questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
