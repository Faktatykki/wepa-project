
package projekti.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projekti.Objects.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query(value = "SELECT * FROM FRIEND fr WHERE fr.RECEIVER_ID=:id OR REQUESTER_ID=:id", nativeQuery = true)
    List<Friend> findAllFriendsById(@Param("id")Long id);
}
