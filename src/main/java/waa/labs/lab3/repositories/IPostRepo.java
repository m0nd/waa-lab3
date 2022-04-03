package waa.labs.lab3.repositories;

import org.springframework.data.repository.CrudRepository;
import waa.labs.lab3.domain.Post;

import java.util.List;

public interface IPostRepo extends CrudRepository<Post, Long> {
    @Override
    List<Post> findAll();
}
