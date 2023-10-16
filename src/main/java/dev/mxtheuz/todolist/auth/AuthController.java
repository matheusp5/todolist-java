package dev.mxtheuz.todolist.auth;

import dev.mxtheuz.todolist.user.UserModel;
import dev.mxtheuz.todolist.user.UserRepository;
import dev.mxtheuz.todolist.utils.HttpResponse;
import dev.mxtheuz.todolist.utils.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<HttpResponse<String>> Login(@RequestBody LoginUserDTO dto) {
        if(authService.login(dto.email(), dto.password())) {
            UserModel user = userRepository.findByEmail(dto.email());
            String token = jwtTokenService.CreateToken(user.getId());
            return ResponseEntity.ok(new HttpResponse<>(200, "authorized", token));
        } else {
            return ResponseEntity.ok(new HttpResponse<>(401, "unauthorized", ""));
        }
    }


    @PostMapping("/register")
    public ResponseEntity<HttpResponse<Object>> Register(@RequestBody UserModel dto) {
        if(userRepository.findByEmail(dto.getEmail()) == null) {
            UserModel user = authService.register(dto);
            return ResponseEntity.status(201).body(new HttpResponse<>(201, "created", user));
        } else {
            return ResponseEntity.status(400).body(new HttpResponse<>(400, "email_already_exists", ""));
        }
    }
}
