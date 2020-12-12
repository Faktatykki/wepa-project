
package projekti.Repositories;


import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projekti.Objects.PictureFile;

public interface PictureRepository extends JpaRepository<PictureFile, Long> {
    @Query(value = "SELECT * FROM PICTURE_FILE pf WHERE pf.OWNER_ID=:id", nativeQuery = true)
    List<PictureFile> findPicturesById(@Param("id")Long id);
}
