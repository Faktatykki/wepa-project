
package projekti.Repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projekti.Objects.MessageLike;


public interface MessageLikeRepository extends JpaRepository<MessageLike, Long> {
    @Query(value = "SELECT * FROM MESSAGE_LIKE ml WHERE ml.MESSAGE_ID = :messageId AND ml.LIKER_ID = :likerId", nativeQuery = true)
    MessageLike findSpecificLikeByMessageIdAndAccountId(@Param("messageId")Long messageId, @Param("likerId") Long likerId);

    @Query(value = "SELECT MESSAGE_ID FROM MESSAGE_LIKE ml WHERE ml.LIKER_ID = :likerId", nativeQuery = true)
    List<Long> findAllLikesByAccountId(@Param("likerId") Long likerId);
}
