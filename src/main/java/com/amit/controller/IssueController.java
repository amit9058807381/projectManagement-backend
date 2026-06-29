package com.amit.controller;

import com.amit.modal.Issue;
import com.amit.modal.IssueDto;
import com.amit.modal.User;
import com.amit.request.IssueRequest;
import com.amit.response.MessageResponse;
import com.amit.service.IssueService;
import com.amit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;
    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable("issueId") Long issueId) throws Exception{
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable("projectId") Long projectId) throws Exception{
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDto> createIssue(
            @RequestBody IssueRequest issue,
            @RequestHeader("Authorization") String token
            ) throws Exception{
            User tokenUse=userService.findUserProfileByJwt(token);
            User user=userService.findUserById(tokenUse.getId());

            Issue createdIssue=issueService.createIssue(issue,tokenUse);
            IssueDto issueDto=new IssueDto();

            issueDto.setId(createdIssue.getId());
            issueDto.setTitle(createdIssue.getTitle());
            issueDto.setDescription(createdIssue.getDescription());
            issueDto.setStatus(createdIssue.getStatus());
            issueDto.setTags(createdIssue.getTags());
            issueDto.setProjectID(createdIssue.getProjectID());
            issueDto.setPriority(createdIssue.getPriority());
            issueDto.setProject(createdIssue.getProject());
            issueDto.setDueDate(createdIssue.getDueDate());
            issueDto.setAssignee(createdIssue.getAssignee());


            return ResponseEntity.ok(issueDto);

    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable("issueId") Long issueId,
            @RequestHeader("Authorization") String token
    ) throws Exception{
        User user=userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId,user.getId());

        MessageResponse messageResponse=new MessageResponse();
        messageResponse.setMessage("Issue has been deleted");
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
            @PathVariable Long issueId,
            @PathVariable Long userId
    )throws Exception{
        Issue issue=issueService.addUserToIssue(issueId,userId);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
            @PathVariable Long issueId,
            @PathVariable String status
    )throws Exception{
        Issue issue=issueService.updateStatus(issueId,status);
        return ResponseEntity.ok(issue);
    }
}

