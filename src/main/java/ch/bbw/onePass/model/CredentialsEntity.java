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
    public CredentialsEntity(String username, String email, String password, String url, String notice) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.url = url;
        this.notice = notice;
    }

    @Column(name = "id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

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
}