package com.scm.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;

    @Column(nullable = false)
    @NotBlank(message = "User name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Lob
    @Size(max = 1000, message = "About section can't exceed 1000 characters")
    private String about;

    @Column(length = 1000)
    @Size(max = 1000, message = "Profile picture URL can't exceed 1000 characters")
    @URL(message = "Profile picture must be a valid URL")
    private String profilePic;

    @Pattern(
            regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",
            message = "Phone number must be valid (10 digits, optionally with country code)"
    )
    private String phoneNumber;

    private boolean enabled = true;

    private boolean emailVerified = false;

    private boolean phoneNumberVerified = false;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Authentication provider is required")
    private Providers provider = Providers.SELF;

    private String emailToken;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Contact> contacts = new LinkedHashSet<>();

}
