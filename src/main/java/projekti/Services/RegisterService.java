
package projekti.Services;

import projekti.Repositories.AccountRepository;
import projekti.Objects.Account;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    
    @Autowired
    private AccountRepository acRep;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @Transactional
    public boolean register(String name, String userUrl, String password, String passwordAgain) {
         
        if(registeringOk(name, userUrl, password, passwordAgain)) {
            Account newAccount = new Account();

            newAccount.setName(name);
            newAccount.setPassword(passwordEncoder.encode(password));
            newAccount.setUserUrl(userUrl);
            
            List<String> auth = new ArrayList<>();
            auth.add("USER");
            auth.add(name);
            
            newAccount.setAuthorities(auth);
            
            acRep.save(newAccount);
            
            return true;
        }
        
        return false;
    }
    
    //I did this in the very beginning (the whole password check etc.) and I know this is not the most efficient way
    private boolean registeringOk(String name, String userUrl, String password, String passwordAgain) {
        
        if(!checkForEmptyParameters(name, userUrl, password, passwordAgain)) {
            return false;
        }
        if(!availableName(name)) {
            return false;
        }
        if(!availableUserUrl(userUrl)) {
            return false;
        }
        if(!passwordsMatch(password, passwordAgain)) {
            return false;
        }
        
        return true;
    }
    
    private boolean availableUserUrl(String userUrl) {
        
        Account account = acRep.findByUserUrl(userUrl);
        
        if(account != null) {
            return false;
        }
        return true;
    }
    
    private boolean checkForEmptyParameters(String name, String userUrl, String password, String passwordAgain) {
        
        if(name.isEmpty() || userUrl.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()) {
            return false;
        }
        return true;
    }
    
    private boolean passwordsMatch(String password, String passwordAgain) {
        
        if(password.equals(passwordAgain)) {
            return true;
        }
        
        return false;
    }
    
    private boolean availableName(String name) {
        
        Account account = acRep.findByName(name);
        
        if(account != null) {
            return false;
        }
        
        return true;
    }
}
