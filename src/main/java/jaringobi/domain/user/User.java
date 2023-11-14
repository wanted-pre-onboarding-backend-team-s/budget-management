package jaringobi.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jaringobi.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(255)")
    private String username;

    @NotEmpty
    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted = false;

    @Builder
    public User(String username, String password) {
        Assert.hasText(username, "username must not be empty");
        Assert.hasText(password, "password must not be empty");
        this.username = username;
        this.password = password;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder 암호화는 필수 입니다.");
        Assert.hasText(password, "password must not be empty");
        password = passwordEncoder.encode(password);
    }

    public boolean matchesPassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }

    public Long getId() {
        return id;
    }
}
