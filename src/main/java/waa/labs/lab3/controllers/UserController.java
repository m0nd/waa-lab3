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
    public List<UserDto> getAllUsers(@RequestParam(name = "has-multiple-posts", required = false) boolean hasMultiplePosts) {
        return hasMultiplePosts ? userService.getUsersWithMultiplePosts() : userService.getAllUsers();
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
    public List<CommentDto> getCommentsByUser(@PathVariable long userId, @PathVariable long postId) {
        return userService.getCommentsByUser(userId,postId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void saveUser(@RequestBody UserDto newUserDto) {
        userService.saveUser(newUserDto);
    }

    @PostMapping("/{userId}/posts")
    public void addPostByUser(@PathVariable long userId, @RequestBody PostDto postDto) {
        userService.savePostByUser(userId, postDto);
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
