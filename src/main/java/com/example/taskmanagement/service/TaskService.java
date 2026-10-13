package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.TaskRequest;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.exception.TaskNotFoundException;
import com.example.taskmanagement.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private static final Logger logger =
            LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskRequest request) {

        logger.info("Creating new task with title: {}", request.getTitle());

        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                request.getStatus()
        );

        Task savedTask = taskRepository.save(task);

        logger.info("Task created successfully with id: {}", savedTask.getId());

        return savedTask;
    }

    public List<Task> getAllTasks() {

        logger.info("Fetching all tasks");

        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {

        logger.info("Fetching task with id: {}", id);

        return taskRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Task not found with id: {}", id);
                    return new TaskNotFoundException(id);
                });
    }

    public Task updateTask(Long id, TaskRequest request) {

        logger.info("Updating task with id: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cannot update. Task not found with id: {}", id);
                    return new TaskNotFoundException(id);
                });

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());

        Task updatedTask = taskRepository.save(task);

        logger.info("Task updated successfully with id: {}", id);

        return updatedTask;
    }

    public void deleteTask(Long id) {

        logger.info("Deleting task with id: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cannot delete. Task not found with id: {}", id);
                    return new TaskNotFoundException(id);
                });

        taskRepository.delete(task);

        logger.info("Task deleted successfully with id: {}", id);
    }
}