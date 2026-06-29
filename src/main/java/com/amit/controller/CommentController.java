package com.amit.controller;

import com.amit.modal.Comment;
import com.amit.modal.User;
import com.amit.request.CreateCommentRequest;
import com.amit.response.MessageResponse;
import com.amit.service.CommentService;
import com.amit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest createCommentRequest,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
            User user=userService.findUserProfileByJwt(jwt);
            Comment createComment=commentService.createComment(createCommentRequest.getIssueId(), user.getId(), createCommentRequest.getComment());

            return new ResponseEntity<>(createComment, HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String jwt
    )throws Exception {
        User  user=userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());

        return new ResponseEntity<>(new MessageResponse("Comment deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssueId(
            @PathVariable Long issueId)throws Exception {
        List<Comment> comments=commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
