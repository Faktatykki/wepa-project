
package projekti.Objects;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Friend extends AbstractPersistable<Long> {
    
    @NotNull
    @ManyToOne
    private Account receiver;
    
    @NotNull
    @ManyToOne
    private Account requester;
    
}
