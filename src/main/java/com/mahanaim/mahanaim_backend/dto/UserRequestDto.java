package com.mahanaim.mahanaim_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class UserRequestDto {
    private String email;
    private String name;
    private Integer age;
    private String position;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
