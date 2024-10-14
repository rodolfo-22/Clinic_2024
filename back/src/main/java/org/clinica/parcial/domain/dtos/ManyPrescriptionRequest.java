package org.clinica.parcial.domain.dtos;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ManyPrescriptionRequest
{
    List<@Valid PrescripcionRequest> prescriptions;
}
