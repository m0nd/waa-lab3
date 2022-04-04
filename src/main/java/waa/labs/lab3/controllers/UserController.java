package waa.labs.lab3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import waa.labs.lab3.domain.dtos.CommentDto;
import waa.labs.lab3.domain.dtos.PostDto;
import waa.labs.lab3.domain.dtos.UserDto;
import waa.labs.lab3.services.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    IUserService userService;

    @Autowired
    UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers(
            @RequestParam(name = "minPosts", required = false) Integer minPosts,
            @RequestParam(name = "postTitle", required = false) String postTitle
    ) {
        if (minPosts != null) return userService.getUsersWithPostsMoreThan(minPosts);
        if (postTitle != null) return userService.getUsersWithPostTitleMatching(postTitle);

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/{userId}/posts")
    public List<PostDto> getPostsByUser(@PathVariable long userId) {
        return userService.getPostsByUser(userId);
    }

    @GetMapping("/{userId}/posts/{postId}/comments")
    public List<CommentDto> getAllUserComments(@PathVariable long userId, @PathVariable long postId) {
        return userService.getAllUserComments(userId,postId);
    }

    @GetMapping("/{userId}/posts/{postId}/comments/{commentId}")
    public CommentDto getUserCommentById(@PathVariable long userId, @PathVariable long postId, @PathVariable long commentId) {
        return userService.getUserCommentById(userId, postId, commentId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void saveUser(@RequestBody UserDto newUserDto) {
        userService.saveUser(newUserDto);
    }

    @PostMapping("/{userId}/posts")
    public void savePostByUser(@PathVariable long userId, @RequestBody PostDto postDto) {
        userService.savePostByUser(userId, postDto);
    }

    @PostMapping("/{userId}/posts/{postId}/comments")
    public void saveCommentByUser(@PathVariable long userId, @PathVariable long postId, @RequestBody CommentDto commentDto) {
        userService.saveCommentByUser(userId, postId, commentDto);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable long userId, @RequestBody UserDto userDto) {
        userService.updateUser(userId, userDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long userId) {
        userService.deleteUserById(userId);
    }


}
