package com.socialmedia.fitness.controller;

import com.socialmedia.fitness.model.Post;
import com.socialmedia.fitness.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    // Get all posts
    @GetMapping("/getall")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Get post by ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postRepository.findById(id)
                .orElse(null);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new post
    @PostMapping("/")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postRepository.save(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // Update an existing post
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        if (!postRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedPost.setId(id);
        Post savedPost = postRepository.save(updatedPost);
        return new ResponseEntity<>(savedPost, HttpStatus.OK);
    }

    // Delete a post
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

