package exercise.controller;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.TaskMapper;
import exercise.repository.TaskRepository;
import exercise.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        var resultFromDb = taskRepository.findAll();
        return resultFromDb.stream().map(t -> taskMapper.map(t)).toList();
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable long id) {
        var resultFromDb = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task with id " + id + " not found")
        );

        return taskMapper.map(resultFromDb);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@RequestBody @Valid TaskCreateDTO body) {
        var taskEntity = taskMapper.map(body);
        taskRepository.save(taskEntity);

        return taskMapper.map(taskEntity);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTaskById(@RequestBody @Valid TaskUpdateDTO updateDTO, @PathVariable long id) {
        var taskEntity = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task with id " + id + " not found")
        );

        var user = userRepository.findById(updateDTO.getAssigneeId()).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found")
        );

        taskMapper.update(updateDTO, taskEntity);
        taskEntity.setAssignee(user);
        taskRepository.save(taskEntity);

        return taskMapper.map(taskEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskById(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
}
