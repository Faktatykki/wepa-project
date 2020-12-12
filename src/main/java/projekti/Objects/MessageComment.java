
package projekti.Objects;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class MessageComment extends AbstractPersistable<Long> {
    
    @NotNull
    @ManyToOne
    private Account sender;
    
    @NotNull
    @ManyToOne
    private Message message;
    
    @NotNull
    private String content;
    
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
}
