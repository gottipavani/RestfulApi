package com.restfulApi.payload;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.List;


@Data
public class WebtoonDto {
    private Long id;
    @Size(min = 2, message = "Should be more than 2 characters")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotEmpty(message = "At least one character is required")
    @Size(min = 1, message = "Each character must have at least 1 character")
    private List<String> characters;
}

