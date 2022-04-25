package com.example.springcodeup.controllers;

import com.example.springcodeup.Post;
import com.example.springcodeup.PostRepository;
import com.example.springcodeup.User;
import com.example.springcodeup.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

/*    public ArrayList<Post> example(){
        ArrayList<Post> ex = new ArrayList<>();
        Post one =new Post("hello!","First post");
        Post two = new Post("hi..", "second post");
        ex.add(one);
        ex.add(two);
        return ex;
    }*/

    @GetMapping("/posts")
    public String posts(Model model){
//        ArrayList<Post> allPosts = new ArrayList<>();
//                allPosts.add(new Post("looking for a home for dog", "dog, dont want to leave alone, looking for a good home"));
//                allPosts.add(new Post("cat", "trying to find a new cat. would love a kitten for our kids to grow up with"));

                        ArrayList<Post> allPosts = (ArrayList<Post>) postDao.findAll();
        model.addAttribute("Posts", allPosts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postIds(@PathVariable long id, Model model){
        Post post = postDao.getById(id);
        model.addAttribute("post",post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String startCreatePost(Model model) {
        model.addAttribute("newPost",new Post());
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam(name = "title") String title, @RequestParam(name ="body") String body){
//        System.out.println("your title is "+title+" and the body was :"+ body);
        User user = userDao.getById(1L);
        Post post =new Post(title,body);
        post.setOwner(user);
        postDao.save(post);

        return "redirect:/posts";
    }


}
