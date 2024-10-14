package org.clinica.parcial.controllers;

import org.clinica.parcial.domain.entities.Speciality;
import org.clinica.parcial.services.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SpecialityController {

    private final SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @GetMapping("/specialities")
    @PreAuthorize("hasAnyAuthority('Asistente')")
    public ResponseEntity<List<Speciality>> getAllSpecialities() {
        List<Speciality> specialities = specialityService.findAllSpecialities();
        return new ResponseEntity<>(specialities, HttpStatus.OK);
    }
}
