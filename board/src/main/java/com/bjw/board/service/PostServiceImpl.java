
package com.bjw.board.service;

import com.bjw.board.PostMapper;
import com.bjw.board.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public List<Post> getAllPosts() {
        return postMapper.getAllPosts();
    }

    @Override
    public Post getPostById(Long id) {
        return postMapper.getPostById(id);
    }

    @Override
    public void savePost(Post post) {
        postMapper.insertPost(post);
    }

    @Override
    public void updatePost(Post post) {
        postMapper.updatePost(post);
    }

    @Override
    public void deletePost(Long id) {
        postMapper.deletePost(id);
    }
}
