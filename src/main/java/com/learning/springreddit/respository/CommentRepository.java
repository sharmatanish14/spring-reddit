package com.learning.springreddit.respository;

import com.learning.springreddit.model.Comment;
import com.learning.springreddit.model.Post;
import com.learning.springreddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);

}
