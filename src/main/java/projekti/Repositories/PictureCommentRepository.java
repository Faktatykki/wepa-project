
package projekti.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.Objects.PictureComment;

public interface PictureCommentRepository extends JpaRepository<PictureComment, Long> {
    
}
