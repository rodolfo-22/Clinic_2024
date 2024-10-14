package org.clinica.parcial.services;

import org.clinica.parcial.domain.entities.Speciality;

import java.util.List;
import java.util.Optional;

public interface SpecialityService {
    Optional<Speciality> findByName(String role);
    List<Speciality> findByNameIn(List<String> names);
    List<Speciality> findAllSpecialities();

}
