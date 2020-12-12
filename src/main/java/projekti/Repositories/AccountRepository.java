
package projekti.Repositories;

import projekti.Objects.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByName(String name);
    Account findByUserUrl(String userUrl);
    List<Account> findByNameContainingIgnoreCase(String name);
}
