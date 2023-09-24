package ru.practicum.ewm.repository;

import ru.practicum.ewm.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /* @Query("select c from Comment c " +
             "where :text is null or (lower(c.text) like lower(concat('%', :text, '%'))) " +
             "order by c.created"
     )*/
    List<Comment> findAllByTextOrderByCreated(String text, Pageable pageable);

    List<Comment> findAllByEventOrderByCreated(Long eventId, Pageable pageable);

    List<Comment> findAllByAuthorOrderByCreated(Long authorId, Pageable pageable);

}
