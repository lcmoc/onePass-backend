package ch.bbw.onePass.JsonReturnModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryReturn {
    public CategoryReturn(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("user_id")
    Long userId;
}
