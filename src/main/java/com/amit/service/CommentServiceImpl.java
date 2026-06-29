package com.amit.service;

import com.amit.modal.Comment;
import com.amit.modal.Issue;
import com.amit.modal.User;
import com.amit.repository.CommentRepository;
import com.amit.repository.IssueRepository;
import com.amit.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;

    @Override
    public Comment createComment(Long issueId, Long userId, String comment) throws Exception {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);
        if(issueOptional.isEmpty()){
            throw new Exception("Issue with id " + issueId + " not found");
        }
        if(userOptional.isEmpty()){
            throw new Exception("User with id " + userId + " not found");
        }

        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment comment1 = new Comment();
        comment1.setIssue(issue);
        comment1.setUser(user);
        comment1.setCreatedDateTime(LocalDateTime.now());
        comment1.setContent(comment);

        Comment savedComment = commentRepository.save(comment1);
        issue.getComments().add(savedComment);
        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);
        if(commentOptional.isEmpty()){
            throw new Exception("Comment with id " + commentId + " not found");
        }
        if(userOptional.isEmpty()){
            throw new Exception("User with id " + userId + " not found");
        }
        Comment comment = commentOptional.get();
        User user = userOptional.get();
        if(comment.getUser().equals(user)){
            commentRepository.delete(comment);
        }else {
            throw new Exception("User doesn't belong to this comment");
        }
    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
