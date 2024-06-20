package ProgrammeringsEksamenAPI.security.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Role")
public class Role {
   @Id
   private String roleName;

   public Role(String roleName) {
      this.roleName = roleName;
   }

   @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
   Set<UserWithRoles> users;

   public void addUser(UserWithRoles user) {
      if(users == null) users = new HashSet<>();
      users.add(user);
   }
}
