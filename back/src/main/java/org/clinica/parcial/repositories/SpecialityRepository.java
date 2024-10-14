package org.clinica.parcial.repositories;

import org.clinica.parcial.domain.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, UUID> {
    List<Speciality> findByNameIn(List<String> names);
    Optional<Speciality> findByName(String name);

}
