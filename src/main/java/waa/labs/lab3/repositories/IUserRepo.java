package waa.labs.lab3.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import waa.labs.lab3.domain.User;

import java.util.List;

public interface IUserRepo extends CrudRepository<User, Long> {
    @Override
    List<User> findAll();

    @Query("select u from User u where size(u.posts) > :minNumPosts")
    List<User> findHavingPostsMoreThan(int minNumPosts);

    @Query("select u from User u join u.posts as p where p.title like :postTitle%")
    List<User> findHavingPostTitleMatching(String postTitle);
}
