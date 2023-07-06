package ch.bbw.onePass.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "CATEGORY")
public class CategoryEntity {

    public CategoryEntity(String name, UserEntity user) {
        this.name = name;
        this.user = user;
    }

    @Column(name = "id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    public Long getUser_id() {
        return user.getId();
    }

}
