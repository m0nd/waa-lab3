package waa.labs.lab3.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waa.labs.lab3.domain.Comment;
import waa.labs.lab3.domain.Post;
import waa.labs.lab3.domain.dtos.CommentDto;
import waa.labs.lab3.domain.dtos.PostDto;
import waa.labs.lab3.helpers.ListMapper;
import waa.labs.lab3.repositories.IPostRepo;
import waa.labs.lab3.services.IPostService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService implements IPostService {
    IPostRepo postRepo;

    ModelMapper modelMapper;
    ListMapper<Post, PostDto> postListToDtoMapper;
    ListMapper<Comment, CommentDto> commentListToDtoMapper;

    @Autowired
    public PostService(
            IPostRepo postRepo,
            ModelMapper modelMapper,
            ListMapper<Post, PostDto> postListMapper,
            ListMapper<Comment, CommentDto> commentListMapper
    ) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
        this.postListToDtoMapper = postListMapper;
        this.commentListToDtoMapper = commentListMapper;
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postListToDtoMapper.mapList(postRepo.findAll(), PostDto.class);
    }

    @Override
    public PostDto getPostById(long postId) {
        var desiredPost = postRepo.findById(postId).orElse(null);
        return (desiredPost == null) ? null : modelMapper.map(desiredPost, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsWithTitleMatching(String title) {
        return postListToDtoMapper.mapList(postRepo.findByTitle(title), PostDto.class);
    }

    @Override
    public List<CommentDto> getAllPostComments(long postId) {
        var desiredPost = postRepo.findById(postId).orElse(null);
        if (desiredPost == null) return new ArrayList<>();
        return commentListToDtoMapper.mapList(desiredPost.getComments(), CommentDto.class);
    }

    @Override
    public void savePost(PostDto postDto) {
        Post newPost = new Post();
        newPost.setTitle(postDto.getTitle());
        newPost.setContent(postDto.getContent());
        postRepo.save(newPost);
    }

    @Override
    public void saveCommentForPost(long postId, CommentDto commentDto) {
        Post desiredPost = postRepo.findById(postId).orElse(null);
        if (desiredPost != null) {
            Comment newComment = new Comment();
            newComment.setName(commentDto.getName());
            desiredPost.addComment(newComment);
            postRepo.save(desiredPost);
        }
    }

    @Override
    public void updatePost(long postId, PostDto postDto) {
        Post desiredPost = postRepo.findById(postId).orElse(null);
        if (desiredPost != null) {
            desiredPost.setTitle(postDto.getTitle());
            desiredPost.setContent(postDto.getContent());
            postRepo.save(desiredPost);
        }
    }

    @Override
    public void deletePostById(long postId) {
        postRepo.deleteById(postId);
    }
}
