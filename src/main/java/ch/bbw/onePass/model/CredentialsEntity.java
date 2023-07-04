package ch.bbw.onePass.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "CREDENTIALS")
public class CredentialsEntity {
    public CredentialsEntity(UserEntity user, CategoryEntity category, String username, String email, String password, String url, String notice, String name) {
        setUser(user);
        this.category = category;
        this.user = user;
        this.username = username;
        this.email = email;
        this.password = password;
        this.url = url;
        this.notice = notice;
        this.name = name;
    }

    @Column(name = "id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "url")
    private String url;

    @Column(name = "notice")
    private String notice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    public Long getUser_id() {
        return user.getId();
    }

}
