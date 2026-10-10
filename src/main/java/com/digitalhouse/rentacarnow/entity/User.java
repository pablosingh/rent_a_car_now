package com.digitalhouse.rentacarnow.entity;

import java.util.List;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Role role;

    @Transient
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String roleNameInput;

    @Column(nullable = false)
    private Boolean verified = false;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String photoPath;

    @Column(length = 20)
    private String phone;

    @Column(name = "reset_token", unique = true)
    @JsonIgnore
    private String resetToken;

    @Column(name = "reset_token_expiry")
    @JsonIgnore
    private java.time.Instant resetTokenExpiry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Car> ownedCars;

    @JsonIgnore
    public Role getRole() {
        return role;
    }

    @JsonIgnore
    public void setRole(Role role) {
        this.role = role;
        this.roleNameInput = null;
    }

    @JsonProperty("role")
    public String getRoleName() {
        if (roleNameInput != null) return roleNameInput;
        return role != null ? role.getName() : null;
    }

    @JsonProperty("role")
    public void setRoleName(String roleName) {
        this.roleNameInput = roleName;
    }

    @JsonIgnore
    public String getPendingRoleName() {
        return roleNameInput;
    }

    public boolean hasRole(String name) {
        return name != null && role != null && name.equals(role.getName());
    }
}
