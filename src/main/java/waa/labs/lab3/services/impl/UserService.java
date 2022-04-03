package waa.labs.lab3.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waa.labs.lab3.domain.Comment;
import waa.labs.lab3.domain.Post;
import waa.labs.lab3.domain.User;
import waa.labs.lab3.domain.dtos.CommentDto;
import waa.labs.lab3.domain.dtos.PostDto;
import waa.labs.lab3.domain.dtos.UserDto;
import waa.labs.lab3.helpers.ListMapper;
import waa.labs.lab3.repositories.IPostRepo;
import waa.labs.lab3.repositories.IUserRepo;
import waa.labs.lab3.services.IUserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService implements IUserService {
    IUserRepo userRepo;
    IPostRepo postRepo;

    private final ModelMapper modelMapper;

    private final ListMapper<User, UserDto> userListToDtoMapper;
    private final ListMapper<Post, PostDto> postListToDtoMapper;
    private final ListMapper<Comment, CommentDto> commentListToDtoMapper;

    @Autowired
    UserService(
            IUserRepo userRepo,
            IPostRepo postRepo,
            ModelMapper modelMapper,
            ListMapper<User, UserDto> userListMapper,
            ListMapper<Post, PostDto> postListMapper,
            ListMapper<Comment, CommentDto> commentListMapper
    ) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
        this.userListToDtoMapper = userListMapper;
        this.postListToDtoMapper = postListMapper;
        this.commentListToDtoMapper = commentListMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userListToDtoMapper.mapList(userRepo.findAll(), UserDto.class);
    }

    @Override
    public UserDto getUserById(long userId) {
        return modelMapper.map(userRepo.findById(userId).orElse(null), UserDto.class);
    }

    @Override
    public void saveUser(UserDto userDto) {
        User newUser = new User();
        newUser.setName(userDto.getName());
        userRepo.save(newUser);
    }

    @Override
    public void savePostByUser(long userId, PostDto postDto) {
        User desiredUser = userRepo.findById(userId).orElse(null);
        if (desiredUser != null) {
            Post newPost = new Post();
            newPost.setContent(postDto.getContent());
            newPost.setTitle(postDto.getTitle());
            desiredUser.addPost(newPost);
        }
    }

    @Override
    public void saveCommentByUser(long userId, long postId, CommentDto commentDto) {
        User desiredUser = userRepo.findById(userId).orElse(null);
        Post desiredPost = postRepo.findById(postId).orElse(null);
        if (desiredUser != null && desiredPost != null) {
            Comment newComment = new Comment();
            newComment.setName(commentDto.getName());
//            desiredUser.getPosts().stream().filter(post -> post.getId() == desiredPost.getId()).findFirst().orElse(null)
            desiredPost.addComment(newComment);
        }
    }

    @Override
    public void updateUser(long userId, UserDto userDto) {
        var userToUpdate = userRepo.findById(userId).orElse(null);

        if (userToUpdate != null) {
            userToUpdate.setName(userDto.getName());
            userRepo.save(userToUpdate);
        }
    }

    @Override
    public void deleteUserById(long userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public List<PostDto> getPostsByUser(long userId) {
        var user = userRepo.findById(userId).orElse(null);
        if (user == null)
            return new ArrayList<>();
        return postListToDtoMapper.mapList(user.getPosts(), PostDto.class);
    }

    @Override
    public List<UserDto> getUsersWithMultiplePosts() {
        return userListToDtoMapper.mapList(userRepo.findHavingMultiplePosts(), UserDto.class);
    }

    @Override
    public List<CommentDto> getCommentsByUser(long userId, long postId) {
        var user = userRepo.findById(userId).orElse(null);
        if (user == null) return new ArrayList<>();

        var desiredPost = user.getPosts().stream().filter(post -> post.getId() == postId).findFirst().orElse(null);
        if (desiredPost == null) return new ArrayList<>();

        return commentListToDtoMapper.mapList(desiredPost.getComments(), CommentDto.class);
    }
}
