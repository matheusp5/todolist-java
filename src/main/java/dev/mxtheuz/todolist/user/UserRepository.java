package dev.mxtheuz.todolist.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, String> {
    UserModel findByEmail(String email);
}
