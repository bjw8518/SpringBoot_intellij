package com.bjw.board.controller;


import com.bjw.board.model.Post;
import com.bjw.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

//    @RequestMapping("/")
//    public String hello() {
//        return "hello";
//    }

    @GetMapping("/list")
    public String listPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "/list";
    }

    @GetMapping("/view/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "/view";
    }

    @GetMapping("/create")
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "/create";
    }

    @PostMapping("/create")
    public String createPostSubmit(@ModelAttribute Post post) {
        postService.savePost(post);
        return "redirect:/posts/list";
    }

    @GetMapping("/update/{id}")
    public String updatePostForm(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "/update";
    }

    @PostMapping("/update")
    public String updatePostSubmit(@ModelAttribute Post post) {
        postService.updatePost(post);
        return "redirect:/posts/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return "redirect:/posts/list";
    }
}
