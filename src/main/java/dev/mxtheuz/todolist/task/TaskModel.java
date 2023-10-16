package dev.mxtheuz.todolist.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    private boolean finished;

    private String userId;

    private LocalDateTime startsAt;
    private LocalDateTime finishAt;

    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;
}
