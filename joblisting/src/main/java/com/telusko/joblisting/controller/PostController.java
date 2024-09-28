package com.telusko.joblisting.controller;


import javax.servlet.http.HttpServletResponse;

import com.telusko.joblisting.repository.PostRepository;
import com.telusko.joblisting.model.Post;
import com.telusko.joblisting.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostRepository repo;
    @Autowired
    SearchRepository sRepo;



    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
    @GetMapping("/posts/{text}")
    public List<Post> search(@PathVariable String text){
        return  sRepo.findByText(text);

    }
    @GetMapping("/allPosts")
    public List<Post> getAllPosts(){
        return repo.findAll();
    }
    @PostMapping("/post")
    public  Post addpost(@RequestBody Post posts){
        return repo.save(posts);
    }
}
