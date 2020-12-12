
package projekti.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projekti.Objects.PictureLike;


public interface PictureLikeRepository extends JpaRepository<PictureLike, Long> {
    @Query(value = "SELECT * FROM PICTURE_LIKE pl WHERE pl.PICTURE_ID = :pictureId AND pl.LIKER_ID = :likerId", nativeQuery = true)
    PictureLike findSpecificLikeByPictureIdAndAccountId(@Param("pictureId") Long pictureId, @Param("likerId") Long likerId);
    
    @Query(value = "SELECT PICTURE_ID FROM PICTURE_LIKE pl WHERE pl.LIKER_ID = :likerId", nativeQuery = true)
    List<Long> findAllLikesByAccountId(@Param("likerId") Long likerId);
}
