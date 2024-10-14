package org.clinica.parcial.repositories;


import org.clinica.parcial.domain.entities.Token;
import org.clinica.parcial.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    List<Token> findByUserAndActive(User user, Boolean active);

}
