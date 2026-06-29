package com.amit.service;

import com.amit.modal.Issue;
import com.amit.modal.Project;
import com.amit.modal.User;
import com.amit.repository.IssueRepository;
import com.amit.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl  implements IssueService {

    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @Override
    public Issue getIssueById(Long issueId) throws Exception {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if(issue.isPresent()){
            return issue.get();
        }
        throw new Exception("Issue Not Found"+issueId);

    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {

        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issue, User user) throws Exception {
        System.out.println(issue);
        System.out.println(issue.getProjectID());
        Project project=projectService.getProjectById(issue.getProjectID());


        Issue issue1=new Issue();
        issue1.setTitle(issue.getTitle());
        issue1.setDescription(issue.getDescription());
        issue1.setStatus(issue.getStatus());
        issue1.setProjectID(issue.getProjectID());
        issue1.setPriority(issue.getPriority());
        issue1.setDueDate(issue.getDueDate());

        issue1.setProject(project);
        return issueRepository.save(issue1);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        getIssueById(issueId);
        issueRepository.deleteById(issueId);

    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user=userService.findUserById(userId);
        Issue issue=getIssueById(issueId);
        issue.setAssignee(user);


        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue=getIssueById(issueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
