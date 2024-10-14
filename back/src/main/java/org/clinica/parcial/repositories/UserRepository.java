package org.clinica.parcial.repositories;


import org.clinica.parcial.domain.dtos.DoctorDTO;
import org.clinica.parcial.domain.entities.Role;
import org.clinica.parcial.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findOneByUsernameOrEmail(String username, String email);
    Optional<User> findOneByActiveAndUsernameOrEmail(Boolean active, String username, String email);

    List<User> findAllByRolesContaining(Role role);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'Doctor' AND NOT EXISTS (" +
            "SELECT 1 FROM MedicalAppointment a WHERE a.doctor.id = u.id AND a.state IN ('approved', 'pending') AND " +
            "((:startTime BETWEEN a.realizacionDate AND a.finalizationDate) OR " +
            "(:endTime BETWEEN a.realizacionDate AND a.finalizationDate) OR " +
            "(a.realizacionDate BETWEEN :startTime AND :endTime)))")
    List<User> findAvailableDoctors(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
