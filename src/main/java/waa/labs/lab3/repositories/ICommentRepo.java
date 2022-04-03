package waa.labs.lab3.repositories;

import org.springframework.data.repository.CrudRepository;
import waa.labs.lab3.domain.Comment;

import java.util.List;

public interface ICommentRepo extends CrudRepository<Comment, Long> {
    @Override
    List<Comment> findAll();
}
