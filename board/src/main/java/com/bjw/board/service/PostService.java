package com.bjw.board.service;

import com.bjw.board.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(Long id);
    void savePost(Post post);
    void updatePost(Post post);
    void deletePost(Long id);
}
