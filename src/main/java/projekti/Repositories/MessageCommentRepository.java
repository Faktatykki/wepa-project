
package projekti.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import projekti.Objects.MessageComment;

public interface MessageCommentRepository extends JpaRepository<MessageComment, Long> {
    
}
