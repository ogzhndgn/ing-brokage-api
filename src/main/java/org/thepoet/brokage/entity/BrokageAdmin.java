package org.thepoet.brokage.entity;


import jakarta.persistence.*;
import lombok.*;
import org.thepoet.brokage.model.enums.UserStatus;

@Entity
@Table(name = "brokage_admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrokageAdmin extends Base {
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "surname", nullable = false, length = 100)
    private String surname;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @Column(name = "admin_token", nullable = false)
    private String adminToken;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25)
    private UserStatus status = UserStatus.ACTIVE;
}
