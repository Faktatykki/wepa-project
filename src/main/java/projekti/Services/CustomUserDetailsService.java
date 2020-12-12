
package projekti.Services;

import projekti.Repositories.AccountRepository;
import projekti.Objects.Account;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
        
    @Autowired
    private AccountRepository acRep;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = acRep.findByName(username);
        if(account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        
        return new org.springframework.security.core.userdetails.User(
                account.getName(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority(username)));
    }
    
}
