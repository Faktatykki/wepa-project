
package projekti.Objects;


import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"RECEIVER_ID", "REQUESTER_ID"})})
public class FriendRequest extends AbstractPersistable<Long> {
    
    @NotNull
    @ManyToOne
    private Account requester;
    
    @NotNull
    @ManyToOne
    private Account receiver;
    
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
}
