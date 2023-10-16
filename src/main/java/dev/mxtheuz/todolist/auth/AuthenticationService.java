package dev.mxtheuz.todolist.auth;


import dev.mxtheuz.todolist.user.UserModel;
import dev.mxtheuz.todolist.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashService hashService;

    public boolean login(String email, String password) {
        UserModel user = this.userRepository.findByEmail(email);
        if(user == null) return false;
        return hashService.verify(password, user.getPassword());
    }

    public UserModel register(UserModel user) {
        user.setPassword(hashService.hash(user.getPassword()));
        return userRepository.save(user);
    }
}
