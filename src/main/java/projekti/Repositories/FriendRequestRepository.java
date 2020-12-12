
package projekti.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projekti.Objects.FriendRequest;


public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    @Query(value = "SELECT * FROM FRIEND_REQUEST fr WHERE fr.RECEIVER_ID=:id", nativeQuery = true)
    List<FriendRequest> findAllRequestsdById(@Param("id")Long id);
    
    @Query(value = "SELECT * FROM FRIEND_REQUEST fr WHERE fr.RECEIVER_ID = :receiverId AND REQUESTER_ID = :requesterId", nativeQuery = true)
    FriendRequest findSpecificFriendRequestByIds(@Param("receiverId")Long receiverId, @Param("requesterId")Long requesterId);
}
