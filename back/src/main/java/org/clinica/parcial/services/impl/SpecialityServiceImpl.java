package org.clinica.parcial.services.impl;

import org.clinica.parcial.domain.entities.Speciality;
import org.clinica.parcial.repositories.SpecialityRepository;
import org.clinica.parcial.services.SpecialityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialityServiceImpl implements SpecialityService {
    private final SpecialityRepository specialityRepository;

    public SpecialityServiceImpl(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public List<Speciality> findByNameIn(List<String> names) {
        return specialityRepository.findByNameIn(names);
    }

    @Override
    public List<Speciality> findAllSpecialities() {
        return specialityRepository.findAll();
    }

    @Override
    public Optional<Speciality> findByName(String name) {
        return specialityRepository.findByName(name);
    }
}