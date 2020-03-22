package xyz.coolestme.community.service;

import org.springframework.stereotype.Service;
import xyz.coolestme.community.model.Comment;

@Service
public class CommentService {

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){
            return;
        }
    }
}
