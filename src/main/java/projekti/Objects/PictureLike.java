

package projekti.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PictureLike extends AbstractPersistable<Long> {
    
   @NotNull
   @OneToOne
   private Account liker;
   
   @NotNull
   @ManyToOne
   private PictureFile picture;
   
}