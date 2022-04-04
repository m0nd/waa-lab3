package waa.labs.lab3.services;

import waa.labs.lab3.domain.dtos.CommentDto;
import waa.labs.lab3.domain.dtos.PostDto;
import waa.labs.lab3.domain.dtos.UserDto;

import java.util.List;

public interface IUserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(long userId);

    void saveUser(UserDto userDto);

    void savePostByUser(long userId, PostDto postDto);

    void saveCommentByUser(long userId, long postId, CommentDto commentDto);

    void updateUser(long userId, UserDto userDto);

    void deleteUserById(long userId);

    List<PostDto> getPostsByUser(long userId);

    List<UserDto> getUsersWithPostsMoreThan(int minNumPosts);

    List<UserDto> getUsersWithPostTitleMatching(String title);

    List<CommentDto> getAllUserComments(long userId, long postId);

    CommentDto getUserCommentById(long userId, long postId, long commentId);
}
