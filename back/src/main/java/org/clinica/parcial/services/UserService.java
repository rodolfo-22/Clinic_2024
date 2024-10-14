package org.clinica.parcial.services;



import org.clinica.parcial.domain.dtos.DoctorDTO;
import org.clinica.parcial.domain.dtos.UserRegisterDTO;
import org.clinica.parcial.domain.entities.Role;
import org.clinica.parcial.domain.entities.Token;
import org.clinica.parcial.domain.entities.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface UserService {
    Token registerToken(User user) throws Exception;
    Boolean isTokenValid(User user, String token);
    void cleanTokens(User user) throws Exception;

    User findUserAuthenticated() throws Exception;

    List<User> findAllUser();

    User findByUsernameOrEmail(UserRegisterDTO info);
    void createUser(UserRegisterDTO info);

    User findOneByIdentifier(String identifier);
    boolean checkPassword(User user, String password);

    User findOneById(UUID userId);
    void saveUser(User user);

    void toggleRole(Role role, User user);
    User findById(UUID id);

    List<User> findAllUsersByRole(Role role);
    List<User> findAvailableDoctors(Date startTime, int duration);


    //List<DoctorDTO> getAllDoctors();
}
