package com.cdcrane.stockflowbackend.users;

import com.cdcrane.stockflowbackend.users.dto.RegisterUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> registerNewUser(@RequestBody RegisterUserDTO registerUserDTO) {

        userUseCase.registerNewUser(registerUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}
