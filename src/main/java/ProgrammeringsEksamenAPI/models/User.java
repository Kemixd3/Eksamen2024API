package ProgrammeringsEksamenAPI.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
    @Table(name = "User")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Getter @Setter
        private Long userId;

        @Column(nullable = false, unique = true)
        @Getter @Setter
        private String username;

        @Column(nullable = false)
        @Getter
        @Setter
        private String password;

        @Column(nullable = false, unique = true)
        @Getter @Setter
        private String email;

        @Column(nullable = false)
        @Getter @Setter
        private String name;

        // Getters and setters
    }

