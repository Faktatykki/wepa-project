
package projekti.Objects;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account extends AbstractPersistable<Long> {


    @Size(min = 4, max = 30)
    private String name;
    

    @Size(min = 8, max = 80)
    private String password;
    

    @Size(min = 2, max = 15)
    private String userUrl;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "messReceiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();
     
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PictureFile> pictures = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities = new ArrayList<>();
    
    //Tämä, koska tämä korjasi haussa tapahtuvan 'stackOverFlow'-virheen
    @Override
    public String toString() {
        return "Account [name="+name+", userUrl="+userUrl+"]";
    }
}
