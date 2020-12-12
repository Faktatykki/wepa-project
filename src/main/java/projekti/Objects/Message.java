
package projekti.Objects;

import java.util.Date;
import java.util.*;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message extends AbstractPersistable<Long> {
       
    @NotNull
    @ManyToOne
    private Account messReceiver;
    
    @NotNull
    @ManyToOne
    private Account messSender;
    
    @NotNull
    private String content;
    
    @OneToMany(mappedBy = "message")
    List<MessageComment> comments = new ArrayList<>();
    
    @OneToMany(mappedBy = "message")
    List<MessageLike> likes = new ArrayList<>();
    
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    
}
