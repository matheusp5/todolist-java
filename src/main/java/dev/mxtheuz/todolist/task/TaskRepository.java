package dev.mxtheuz.todolist.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel, String> {
    List<TaskModel> findByUserId(String userId);
}
