package com.capstone.tele_ticketing_backend_1.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_signup_requests")
@Data
@NoArgsConstructor
public class UserSignupRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(nullable = false)
    private String fullName;

    // Dr. X's Note: We store the *hashed* password, never plaintext.
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String preferredRole; // Stores the simple string, e.g., "l1_engineer"

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public UserSignupRequest(String username, String fullName, String password, String preferredRole) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.preferredRole = preferredRole;
    }
}