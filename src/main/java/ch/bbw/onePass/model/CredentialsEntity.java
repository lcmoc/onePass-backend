package ch.bbw.onePass.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "CREDENTIALS")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"user"})
public class CredentialsEntity {
    public CredentialsEntity(CategoryEntity category, String username, String email, String password, String url, String notice, String name) {
        this.category = category;
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
}
