package com.smartspend.backend.module;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {

    // getters & setters
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String name;

    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;// ✅ REQUIRED

    @Getter @Setter                     // ✅ THIS WAS MISSING
    @Column(nullable = false)
    private String role; // ROLE_USER or ROLE_ADMIN

    // ✅ No-arg constructor (JPA needs this)
    public User() {}

    // ✅ Constructor used in UserService
    public User(String name, String email, String password,String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }


}
