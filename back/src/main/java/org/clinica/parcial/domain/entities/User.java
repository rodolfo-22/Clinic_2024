package org.clinica.parcial.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;

    @Column(insertable = false)
    @ColumnDefault(value = "true")
    private Boolean active;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MedicalAppointment> appointments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Token> tokens;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "permitions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
    public boolean isAvailable(Date startTime, int duration) {
        Date endTime = new Date(startTime.getTime() + duration * 60000L);
        for (MedicalAppointment appointment : appointments) {
            if (appointment.getState().equals("approved") || appointment.getState().equals("pending")) {
                Date appointmentEndTime = new Date(appointment.getRealizacionDate().getTime() + appointment.getDuration() * 60000L);
                if (startTime.before(appointmentEndTime) && endTime.after(appointment.getRealizacionDate())) {
                    return false; // Solapamiento detectado, no está disponible
                }
            }
        }
        return true; // No hay solapamiento, está disponible
    }


    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


}