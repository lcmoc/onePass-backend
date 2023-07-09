package ch.bbw.onePass.JsonReturnModels;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CredentialsReturn {
    public CredentialsReturn(Long id, String username, String email, String password, String url, String notice, String name, Long userId, Long categoryId) {
        this.id = id;
        this.categoryId = categoryId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.url = url;
        this.notice = notice;
        this.name = name;
        this.userId = userId;
    }

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("url")
    private String url;

    @JsonProperty("notice")
    private String notice;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("user_id")
    private Long userId;
}
