package com.bjw.board.model;

import lombok.Data;

@Data
public class Post {
    private Long id;
    private String title;
    private String content;
}
