package com.example.sparta.post.controller;


import com.example.sparta.post.domain.PasswordDto;
import com.example.sparta.post.domain.Post;
import com.example.sparta.post.domain.PostRepository;
import com.example.sparta.post.domain.PostRequestDto;
import com.example.sparta.post.service.PostService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@ResponseBody


public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/api/post")
    public List<Post> getPost() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @GetMapping("/api/post/{id}")
    public Optional<Post> getPost(@PathVariable Long id){
        return postRepository.findById(id);
    }


    @PostMapping("/api/createPost")
    public Post cratePost(@RequestBody PostRequestDto requestDto){
        Post post = new Post(requestDto);
        return postRepository.save(post);
    }

    @PostMapping("/api/checkPost/{id}")
    Boolean checkPassword(@PathVariable Long id, @RequestParam("password") String password) {
        try {
            Optional<Post> post = postRepository.findByIdAndPassword(id, password);
            if(post.isPresent()){
                return true;
            }else {
                return false;
            } } catch (Exception e) {
            return false;
        }
    }

    @PutMapping("/api/updatePost/{id}")
    public String updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto,@RequestBody PasswordDto passwordDto) {
        Optional<Post> post  = postRepository.findById(id);
        if(post.get().getPassword().equals(passwordDto.getPassword())){
            postRepository.deleteById(id);
            return "삭제";
        }
        else {
            return "비밀번호 틀림";
        }

    }

    @DeleteMapping("api/deletePost/{id}")
    public String deletePost(@PathVariable Long id, @RequestBody PasswordDto passwordDto) {
        Optional<Post> post = postRepository.findById(id);
        if (post.get().getPassword().equals(passwordDto.getPassword())){
            postRepository.deleteById(id);
            return "삭제합니다";
        }
        else {
            return "비밀번호틀림";
        }
    }
}