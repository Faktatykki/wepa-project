
package projekti.Services;

import projekti.Repositories.AccountRepository;
import projekti.Objects.Account;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projekti.Objects.Friend;
import projekti.Objects.FriendRequest;
import projekti.Repositories.FriendRequestRepository;

@Service
public class FriendRequestService {
    
    @Autowired
    private FriendRequestRepository frRep;
    
    @Autowired
    private AccountRepository accRep;
    
    
    public List<FriendRequest> findAllFriendRequestsByUserUrl(String url) {
        Long id = accRep.findByUserUrl(url).getId();
        
        return frRep.findAllRequestsdById(id);
    }
    
    public List<FriendRequest> findAllFriendRequestsByName(String name) {
        Long id = accRep.findByName(name).getId();
        
        return frRep.findAllRequestsdById(id);
    }
    
    public boolean checkIfFriendRequestOnDb(Long idRec, Long idReq) {
        if(frRep.findSpecificFriendRequestByIds(idRec, idReq) == null && frRep.findSpecificFriendRequestByIds(idReq, idRec) == null) {
            return false;
        }
        
        return true;
    }
    
    public void newFriendRequest(String userUrl) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account receiver = accRep.findByUserUrl(userUrl);
        Account sender = accRep.findByName(username);
        
        FriendRequest fR = new FriendRequest();
        fR.setReceiver(receiver);
        fR.setRequester(sender);
        
        Date date = java.util.Calendar.getInstance().getTime();
        
        fR.setTimestamp(date);
        
        frRep.save(fR);
    }
    
    public void acceptFriendRequest(Account receiver, Account requester) {
        Long idRec = receiver.getId();
        Long idReq = requester.getId();
        
        if(checkIfFriendRequestOnDb(idRec, idReq)) {
            Account rec = accRep.getOne(idRec);
            Account req = accRep.getOne(idReq);
            
            Friend recFriend = makeNewFriend(rec, req);
            Friend reqFriend = makeNewFriend(req, rec);
            
            rec.getFriends().add(recFriend);
            req.getFriends().add(reqFriend);
            
            accRep.save(rec);
            accRep.save(req);
           
            FriendRequest frToDelete = frRep.findSpecificFriendRequestByIds(idRec, idReq);
            frRep.delete(frToDelete);
        }
    }
    
    public void declineFriendRequest(Account receiver, Account requester) {
        Long idRec = receiver.getId();
        Long idReq = requester.getId();
        
        FriendRequest frToDelete = frRep.findSpecificFriendRequestByIds(idRec, idReq);
        
        frRep.delete(frToDelete);
    }
    
    public Friend makeNewFriend(Account rec, Account req) {
        Friend friend = new Friend();
        
        friend.setReceiver(rec);
        friend.setRequester(req);
        
        return friend;
    }
    
    public boolean checkIfFriendRequests() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account acc = accRep.findByName(username);
        
        List<FriendRequest> fRs = frRep.findAllRequestsdById(acc.getId());
        
        if(!fRs.isEmpty()) {
            return true;
        }
        
        return false;
    }
    
    public boolean checkIfFriends() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account acc = accRep.findByName(username);
        
        if(!acc.getFriends().isEmpty()) {
            return true;
        }
        
        return false;
    }
}
