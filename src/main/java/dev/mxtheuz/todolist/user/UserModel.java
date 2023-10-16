package dev.mxtheuz.todolist.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String email;
    private String password;

    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;
}

