package ProgrammeringsEksamenAPI.configuration;

import ProgrammeringsEksamenAPI.security.entity.Role;
import ProgrammeringsEksamenAPI.security.entity.UserWithRoles;
import ProgrammeringsEksamenAPI.security.repository.RoleRepository;
import ProgrammeringsEksamenAPI.security.repository.UserWithRolesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class SetupDevUsers implements ApplicationRunner {

    UserWithRolesRepository userWithRolesRepository;
    RoleRepository roleRepository;
    PasswordEncoder pwEncoder;

    @Value("${devPassword}")
    private String passwordUsedByAll;

    public SetupDevUsers(UserWithRolesRepository userWithRolesRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userWithRolesRepository = userWithRolesRepository;
        this.roleRepository = roleRepository;
        this.pwEncoder = passwordEncoder;
    }

    public void run(ApplicationArguments args) {
        setupAllowedRoles();
        setupUserWithRoleUsers();
    }

    private void setupAllowedRoles(){
        roleRepository.save(new Role("ADMIN"));
    }

    private void setupUserWithRoleUsers() {
        Role roleAdmin = roleRepository.findById("ADMIN").orElseThrow(()-> new NoSuchElementException("Role 'admin' not found"));

        UserWithRoles user1 = new UserWithRoles("Admin", pwEncoder.encode(passwordUsedByAll), "admin@example.com");
        UserWithRoles user2 = new UserWithRoles("Admin2", pwEncoder.encode(passwordUsedByAll), "admin2@example.com");

        user1.addRole(roleAdmin);
        user2.addRole(roleAdmin);

        userWithRolesRepository.save(user1);
        userWithRolesRepository.save(user2);

    }
}
