package org.clinica.parcial.controllers;

import jakarta.validation.Valid;
import org.clinica.parcial.domain.dtos.res.GeneralResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.clinica.parcial.domain.dtos.ToggleRoleDTO;
import org.clinica.parcial.domain.entities.Role;
import org.clinica.parcial.domain.entities.User;
import org.clinica.parcial.services.RoleService;
import org.clinica.parcial.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class AdminController {
final UserService userService;
final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/user-role")
    public ResponseEntity<GeneralResponse> updateUserRole(@RequestBody @Valid ToggleRoleDTO req, BindingResult err) {
        try {
            if (err.hasErrors()) {
                return GeneralResponse.builder().status(HttpStatus.BAD_REQUEST).data(err.getAllErrors()).getResponse();
            }
            User user = userService.findOneByIdentifier(req.getIdentifier());
            if (user == null) {
                return GeneralResponse.builder().status(HttpStatus.NOT_FOUND).message("User not found").getResponse();
            }

            Role role = roleService.findByName(req.getRole()).orElse(null);
            if (role == null) {
                return GeneralResponse.builder().status(HttpStatus.NOT_FOUND).message("Role not found").getResponse();
            }
            userService.toggleRole(role, user);
            return GeneralResponse.builder().status(HttpStatus.OK).message("Roles updated!").getResponse();

        } catch (Exception e) {
            return GeneralResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
        }
    }

    // Nuevo método para eliminar rol específico
    @DeleteMapping("/user-role")
    public ResponseEntity<GeneralResponse> removeUserRole(@RequestBody @Valid ToggleRoleDTO req, BindingResult err) {
        try {
            if (err.hasErrors()) {
                return GeneralResponse.builder().status(HttpStatus.BAD_REQUEST).data(err.getAllErrors()).getResponse();
            }
            User user = userService.findOneByIdentifier(req.getIdentifier());
            if (user == null) {
                return GeneralResponse.builder().status(HttpStatus.NOT_FOUND).message("User not found").getResponse();
            }

            Role role = roleService.findByName(req.getRole()).orElse(null);
            if (role == null) {
                return GeneralResponse.builder().status(HttpStatus.NOT_FOUND).message("Role not found").getResponse();
            }

            user.getRoles().remove(role);
            userService.saveUser(user);

            return GeneralResponse.builder().status(HttpStatus.OK).message("Role removed!").getResponse();
        } catch (Exception e) {
            return GeneralResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).getResponse();
        }
    }
}
