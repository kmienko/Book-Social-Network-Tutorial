package com.kmienko.Book_Social_Network_Tutorial.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user") //reserved in SQL so we need to use different name
@EntityListeners(AuditingEntityListener.class) //@EnableJpaAuditing is needed in main class
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue
    private Integer userId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private boolean enabled;
    private boolean accountLocked;

    //TODO:
    //private List<Role> roles

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(updatable = false)
    private LocalDateTime lastModifiedDate;

    @Override
    public String getName(){
        return email;
    }

    public String getFullName() {
        return firstName +" "+lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        //do not store plain text in DB!
        //hashing + salt i.e.
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
