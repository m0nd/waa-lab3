package waa.labs.lab3.services;

import waa.labs.lab3.domain.Post;
import waa.labs.lab3.domain.dtos.CommentDto;
import waa.labs.lab3.domain.dtos.PostDto;

import java.util.List;

public interface IPostService {
    List<PostDto> getAllPosts();

    PostDto getPostById(long postId);

    List<PostDto> getPostsWithTitleMatching(String title);

    List<CommentDto> getAllPostComments(long postId);

    void savePost(PostDto postDto);

    void saveCommentForPost(long postId, CommentDto commentDto);

    void updatePost(long postId, PostDto postDto);

    void deletePostById(long postId);

}
