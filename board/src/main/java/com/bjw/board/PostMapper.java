package com.bjw.board;

import com.bjw.board.model.Post;

import java.util.List;

public interface PostMapper {
    List<Post> getAllPosts();
    Post getPostById(Long id);
    void insertPost(Post post);
    void updatePost(Post post);
    void deletePost(Long id);
}
