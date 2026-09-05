package com.worksphere.backend.mapper;

import org.springframework.stereotype.Component;

import com.worksphere.backend.dto.TaskRequest;
import com.worksphere.backend.dto.TaskResponse;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.entity.Project;
import com.worksphere.backend.entity.Task;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequest request, Project project, Employee assignee) {
        return new Task(
                request.getTitle(),
                request.getDescription(),
                request.getStatus(),
                request.getDueDate(),
                project,
                assignee
        );
    }

    public TaskResponse toResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setDueDate(task.getDueDate());
        
        if (task.getProject() != null) {
            response.setProjectId(task.getProject().getId());
            response.setProjectName(task.getProject().getName());
        }
        
        if (task.getAssignee() != null) {
            response.setAssigneeId(task.getAssignee().getId());
            response.setAssigneeName(task.getAssignee().getFirstName() + " " + task.getAssignee().getLastName());
        }
        
        return response;
    }

    public void updateEntity(Task task, TaskRequest request, Project project, Employee assignee) {
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        task.setProject(project);
        task.setAssignee(assignee);
    }
}
