
package projekti.Services;

import projekti.Repositories.AccountRepository;
import projekti.Objects.Account;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.Objects.Friend;


@Service
public class UserService {

    @Autowired
    private AccountRepository accRep;
    
    public List<Account> searchForName(String name) {
        if(name != null) {
            List<Account> result = accRep.findByNameContainingIgnoreCase(name);
            
            return result;
        }
        
        return null;
    }
    
    public Account searchById(Long id) {
        if(accRep.getOne(id) == null) {
            return null;
        } 
        
        return accRep.getOne(id);
    }
    
    public Account findByName(String name) {
        if(name != null) {
            return accRep.findByName(name);
        }
        
        return null;
    }
    
    public Account findByUserUrl(String userUrl) {
        if(userUrl != null) {
            return accRep.findByUserUrl(userUrl);
        }
        
        return null;
    }

    public List<Friend> findAllFriends(String name) {
        if(name != null) {
            Account account = accRep.findByName(name);
            
            return account.getFriends();
        }
        
        return null;
    }
    
    public boolean checkIfFriends(Long myAccountId, Long profileWatchingId) {
        Account myAccount = accRep.getOne(myAccountId);
        Account profileWatching = accRep.getOne(profileWatchingId);
        
        for(Friend friends: myAccount.getFriends()) {
            if((friends.getReceiver().equals(myAccount) && friends.getRequester().equals(profileWatching))) {
                return true;
            }
            if((friends.getReceiver().equals(profileWatching) && friends.getRequester().equals(myAccount))) {
                return true;
            }
        }
        
        return false;
    }
}   
