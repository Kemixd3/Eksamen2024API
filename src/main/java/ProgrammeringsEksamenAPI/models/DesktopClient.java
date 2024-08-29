package ProgrammeringsEksamenAPI.models;

import ProgrammeringsEksamenAPI.security.entity.UserWithRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class DesktopClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 60)  // Assuming password is encoded
    private String password;

    @Column(nullable = false, length = 100)
    private String desktopName;

    @Column(nullable = false, length = 45)
    private String ip;

    @ManyToOne
    @JoinColumn(name = "user_username", nullable = false)
    private UserWithRoles user;

    public DesktopClient() {}

    public DesktopClient(String username, String password, String desktopName, String ip, UserWithRoles user) {
        this.username = username;
        this.password = password;
        this.desktopName = desktopName;
        this.ip = ip;
        this.user = user;
    }
}

