package org.clinica.parcial.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "appointments")
public class MedicalAppointment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "start_timestamp")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date solicitadaDate;

    @Column(name = "end_timestamp")
   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date estimadaFinalizacionDate;

    @Column(name = "realization_timestamp")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date realizacionDate;

    @Column(name = "finalization_timestamp")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date finalizationDate;

    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SpecialAppointment> userAppointment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private User doctor;


    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Prescription> prescription;

    @Column(name = "approved")
    @ColumnDefault(value = "false")
    @JsonIgnore
    private Boolean approved;

    @Column(name = "state")
    private String state;

    @Column(name = "reason")
    private String reason;

    @Column(name = "duration")
    private Integer duration; // Cambiado a Integer

}
