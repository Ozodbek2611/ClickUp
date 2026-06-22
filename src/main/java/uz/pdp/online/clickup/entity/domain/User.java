package uz.pdp.online.clickup.entity.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.online.clickup.entity.enums.SystemRoleName;
import uz.pdp.online.clickup.entity.template.AbsUUIDEntity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User extends AbsUUIDEntity implements UserDetails {

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String color;

    private String initialLetter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id")
    private Attachment avatar;

    @Enumerated(EnumType.STRING)
    private SystemRoleName systemRole;

    private String emailCode;

    @PrePersist
    @PreUpdate
    public void setInitialLetter() {
        this.initialLetter = fullName.substring(0, 1).toUpperCase();
    }

    private Timestamp lastActive;

    private boolean enabled;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (systemRole == null) return List.of();
        return List.of(new SimpleGrantedAuthority("ROLE_" + systemRole));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


}
