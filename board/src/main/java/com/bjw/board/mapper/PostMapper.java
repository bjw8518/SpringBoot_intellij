package com.bjw.board.mapper;

import com.bjw.board.model.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("SELECT * FROM posts")
    List<Post> getAllPosts();

    @Select("SELECT * FROM posts WHERE id = #{id}")
    Post getPostById(Long id);

    @Insert("INSERT INTO posts (title, content) VALUES (#{title}, #{content})")
    void insertPost(Post post);

    @Update("UPDATE posts SET title = #{title}, content = #{content} WHERE id = #{id}")
    void updatePost(Post post);

    @Delete("DELETE FROM posts WHERE id = #{id}")
    void deletePost(Long id);
}
