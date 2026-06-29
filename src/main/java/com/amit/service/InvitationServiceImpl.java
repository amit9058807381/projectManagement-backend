package com.amit.service;

import com.amit.modal.Invitation;
import com.amit.repository.InvitationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {

        String token = UUID.randomUUID().toString();
        Invitation invitation = new Invitation();
        invitation.setToken(token);
        invitation.setEmail(email);
        invitation.setProjectId(projectId);

        invitationRepository.save(invitation);

        String invitationLink="http://localhost:5173/accept_invitation?token="+token;
        emailService.sendEmailWithToken(email,invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {
        Invitation invitation = invitationRepository.findByToken(token);
        if(invitation==null){
            throw new Exception("Invalid Invitation token");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
                Invitation invitation = invitationRepository.findByEmail(userEmail);


        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepository.findByToken(token);
        invitationRepository.delete(invitation);
    }
}
