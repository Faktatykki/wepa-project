
package projekti.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projekti.Objects.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
   @Query(value = "SELECT * FROM MESSAGE ms WHERE ms.MESS_RECEIVER_ID=:id ORDER BY timestamp DESC LIMIT 25", nativeQuery = true)
   List<Message> findAllMessagesReceivedById(@Param("id")Long id);
}
