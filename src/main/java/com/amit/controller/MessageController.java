package com.amit.controller;

import com.amit.modal.Chat;
import com.amit.modal.Message;
import com.amit.modal.User;
import com.amit.request.CreateMeassageRequest;
import com.amit.service.MessageService;
import com.amit.service.ProjectService;
import com.amit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @RequestBody CreateMeassageRequest  request) throws  Exception {
        User user=userService.findUserById(request.getSenderId());

        Chat chat=projectService.getChatByProjectId(request.getProjectId());
        if(chat==null){
            throw new Exception("Chat not found");
        }
        Message sentMessage=messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByProjectId(@PathVariable("projectId") Long projectId) throws Exception{
        List<Message> messages=messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
