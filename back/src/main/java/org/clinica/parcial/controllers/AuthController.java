package org.clinica.parcial.controllers;

import jakarta.validation.Valid;
import org.clinica.parcial.domain.dtos.res.GeneralResponse;
import org.clinica.parcial.domain.dtos.res.TokenDTO;
import org.clinica.parcial.domain.dtos.UserLoginDTO;
import org.clinica.parcial.domain.dtos.UserRegisterDTO;
import org.clinica.parcial.domain.entities.Token;
import org.clinica.parcial.domain.entities.Role;
import org.clinica.parcial.domain.entities.User;
import org.clinica.parcial.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> login(@RequestBody @Valid UserLoginDTO info) {
        User user = userService.findOneByIdentifier(info.getIdentifier());

        if (user == null) {
            return GeneralResponse.builder().status(HttpStatus.NOT_FOUND).getResponse();
        }

        if (!userService.checkPassword(user, info.getPassword())) {
            return GeneralResponse.builder().status(HttpStatus.NOT_FOUND).message("Incorrect password").getResponse();
        }

        try {
            Token token = userService.registerToken(user);
            String role = user.getRoles().stream()
                    .map(Role::getAuthority) // Convertir Role a String usando getAuthority()
                    .findFirst() // o .collect(Collectors.joining()) si hay múltiples roles
                    .orElse("User");
            TokenDTO tokenDTO = new TokenDTO(token, role);
            return GeneralResponse.builder().status(HttpStatus.OK).data(tokenDTO).getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return GeneralResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
        }
    }


    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> register(@RequestBody @Valid UserRegisterDTO info) {
        User user = userService.findByUsernameOrEmail(info);

        if(user != null)
            return GeneralResponse.builder().status(HttpStatus.CONFLICT).getResponse();

        userService.createUser(info);

        return GeneralResponse.builder().status(HttpStatus.OK).message("User registered succesfuly").getResponse();
    }

    @GetMapping("/me")
    public ResponseEntity<GeneralResponse> getAuthenticatedUser() {
        try {
            User user = userService.findUserAuthenticated();
            return GeneralResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Me")
                    .data(user)
                    .getResponse();
        } catch (Exception e) {
            return GeneralResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Could not fetch the authenticated user")
                    .getResponse();
        }
    }
}
