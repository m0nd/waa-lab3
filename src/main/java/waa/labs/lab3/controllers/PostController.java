package waa.labs.lab3.controllers;

import org.springframework.web.bind.annotation.*;
import waa.labs.lab3.domain.Comment;
import waa.labs.lab3.domain.dtos.CommentDto;
import waa.labs.lab3.domain.dtos.PostDto;
import waa.labs.lab3.services.IPostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostDto> getAllPosts(@RequestParam(name = "title", required = false) String postTitle) {
        System.out.println("In PostController: title = " + postTitle);
        return postTitle != null ? postService.getPostsWithTitleMatching(postTitle) : postService.getAllPosts();
    }


    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable long postId) {
        return postService.getPostById(postId);
    }


    @GetMapping("/{postId}/comments")
    public List<CommentDto> getAllPostComments(@PathVariable long postId) {
        return postService.getAllPostComments(postId);
    }

    @PostMapping
    public void savePost(@RequestBody PostDto postDto) {
        postService.savePost(postDto);
    }


    @PostMapping("/{postId}/comments")
    public void saveCommentForPost(@PathVariable long postId, @RequestBody CommentDto commentDto) {
        postService.saveCommentForPost(postId, commentDto);
    }


    @PutMapping("/{postId}")
    public void updatePost(@PathVariable long postId, @RequestBody PostDto postDto) {
        postService.updatePost(postId, postDto);
    }


    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable long postId) {
        postService.deletePostById(postId);
    }
}
