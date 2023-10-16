package dev.mxtheuz.todolist.task;

import dev.mxtheuz.todolist.utils.HttpResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public ResponseEntity<HttpResponse<List<TaskModel>>> GetAll(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok(new HttpResponse<>(200, "success", taskRepository.findByUserId(userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse<TaskModel>> GetOne(@PathVariable("id") String id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Optional<TaskModel> task = taskRepository.findById(id);
        if(task.isPresent()) {
            if(task.get().getUserId().equals(userId)) {
                return ResponseEntity.ok(new HttpResponse<>(200, "success", task.get()));
            }
        }
        return ResponseEntity.status(404).body(new HttpResponse<>(404, "not_found", null));
    }

    @PostMapping
    public ResponseEntity<HttpResponse<TaskModel>> Create(@RequestBody TaskModel model, HttpServletRequest request) {
        model.setUserId((String) request.getAttribute("userId"));
        model.setFinished(false);
        return ResponseEntity.status(201).body(new HttpResponse<>(201, "created", taskRepository.save(model)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse<TaskModel>> Update(@RequestBody TaskModel model, @PathVariable("id") String id, HttpServletRequest request) {
        Optional<TaskModel> task = taskRepository.findById(id);
        String userId = (String) request.getAttribute("userId");
        if(task.isPresent()) {
            if(Objects.equals(task.get().getUserId(), userId)) {
                if(model.getName() != null) {
                    task.get().setName(model.getName());
                }
                if(model.getCreatedAt() != null) {
                    task.get().setCreatedAt(model.getCreatedAt());
                }
                if(model.getFinishAt() != null) {
                    task.get().setFinishAt(model.getFinishAt());
                }

                return ResponseEntity.status(200).body(new HttpResponse<>(200, "updated",
                        taskRepository.save(task.get())
                ));
            }
        }
        return ResponseEntity.status(404).body(new HttpResponse<>(404, "not_found", null));
    }

    @PutMapping("/finished/{id}")
    public ResponseEntity<HttpResponse<TaskModel>> Finished(@PathVariable("id") String id, HttpServletRequest request) {
        Optional<TaskModel> task = taskRepository.findById(id);
        String userId = (String) request.getAttribute("userId");
        if(task.isPresent()) {
            if(Objects.equals(task.get().getUserId(), userId)) {
                task.get().setFinished(!task.get().isFinished());
                return ResponseEntity.status(200).body(new HttpResponse<>(200, "updated",
                        taskRepository.save(task.get())
                ));
            }
        }
        return ResponseEntity.status(404).body(new HttpResponse<>(404, "not_found", null));
    }

}
