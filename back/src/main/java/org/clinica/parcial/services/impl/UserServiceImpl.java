package org.clinica.parcial.services.impl;

import jakarta.transaction.Transactional;
import org.clinica.parcial.domain.dtos.DoctorDTO;
import org.clinica.parcial.domain.dtos.UserRegisterDTO;
import org.clinica.parcial.domain.entities.Role;
import org.clinica.parcial.domain.entities.Token;
import org.clinica.parcial.domain.entities.User;
import org.clinica.parcial.repositories.TokenRepository;
import org.clinica.parcial.repositories.UserRepository;
import org.clinica.parcial.services.UserService;
import org.clinica.parcial.utils.JWTTools;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JWTTools jwtTools;
    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepository;

    public UserServiceImpl(JWTTools jwtTools, TokenRepository tokenRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtTools = jwtTools;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Token registerToken(User user) throws Exception {
        cleanTokens(user);

        String tokenString = jwtTools.generateToken(user);
        Token token = new Token(tokenString, user);

        tokenRepository.save(token);

        return token;
    }

    @Override
    public Boolean isTokenValid(User user, String token) {
        try {
            cleanTokens(user);
            List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

            tokens.stream()
                    .filter(tk -> tk.getContent().equals(token))
                    .findAny()
                    .orElseThrow(Exception::new);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cleanTokens(User user) throws Exception {
        List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

        tokens.forEach(token -> {
            if (!jwtTools.verifyToken(token.getContent())) {
                token.setActive(false);
                tokenRepository.save(token);
            }
        });
    }

    @Override
    public User findUserAuthenticated() {
        String identifier = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findOneByUsernameOrEmail(identifier, identifier).orElse(null);
    }

    @Override
    public User findOneByIdentifier(String identifier) {
        return userRepository.findOneByActiveAndUsernameOrEmail(true, identifier, identifier).orElse(null);
    }

    @Override
    public User findByUsernameOrEmail(UserRegisterDTO info) {
        return userRepository.findOneByUsernameOrEmail(info.getUsername(), info.getEmail()).orElse(null);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createUser(UserRegisterDTO info) {
        User user = new User();

        user.setUsername(info.getUsername());
        user.setEmail(info.getEmail());
        String encryptedPassword = passwordEncoder.encode(info.getPassword());
        user.setPassword(encryptedPassword);

        userRepository.save(user);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User findOneById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void toggleRole(Role role, User user) {
        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
        } else {
            user.getRoles().add(role);
        }

        userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAllUsersByRole(Role role) {
        return userRepository.findAllByRolesContaining(role);
    }

    @Override
    public List<User> findAvailableDoctors(Date startTime, int duration) {
        Date endTime = new Date(startTime.getTime() + duration * 60000L);

        // Añadir 15 minutos al tiempo de finalización
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        calendar.add(Calendar.MINUTE, 15);
        Date endTimeWithBuffer = calendar.getTime();

        return userRepository.findAvailableDoctors(startTime, endTimeWithBuffer);
    }



}