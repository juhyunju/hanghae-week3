package com.example.sparta.post.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PostRequestDto {
    private String username;
    private String title;
    private String content;
    private String password;
}