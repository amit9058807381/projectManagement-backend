package com.amit.service;

import com.amit.modal.Chat;
import com.amit.modal.Message;
import com.amit.modal.User;
import com.amit.repository.MessageRepository;
import com.amit.repository.ProjectRepository;
import com.amit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long chatId, String content) throws Exception {
        User user = userRepository.findById(senderId).orElseThrow(() -> new Exception("User not found"));
        Chat chat=projectService.getProjectById(chatId).getChat();

        Message message=new Message();
        message.setContent(content);
        message.setSender(user);
        message.setChat(chat);
        message.setCreatedAt(LocalDateTime.now());
        Message savedmessage=messageRepository.save(message);

        chat.getMessages().add(savedmessage);

        return savedmessage ;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat=projectService.getChatByProjectId(projectId);
        List<Message> findByChatIdOrderByCreatedAtAsc=messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        return findByChatIdOrderByCreatedAtAsc;
    }
}
