package dev.mxtheuz.todolist.auth;


import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class HashService {

    public String hash(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public boolean verify(String password, String hash) {
        return BCrypt.verifyer().verify(password.toCharArray(), hash).verified;
    }
}
