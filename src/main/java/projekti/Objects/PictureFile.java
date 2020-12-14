
package projekti.Objects;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PictureFile extends AbstractPersistable<Long> {
    
    @NotNull
    @ManyToOne
    private Account owner;
    
    @NotNull
    private String name;
    
    @NotNull
    private String mediaType;
    
    @NotNull
    private Long size;
    
    @NotNull
    private boolean ProfilePicture = false;
    
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String imageToDeploy;
    
    private String description;
    
    //cascadet tänne jos kusee
    @OneToMany(mappedBy = "fo", cascade = CascadeType.ALL)
    List<PictureComment> comments = new ArrayList<>();
    
    @OneToMany(mappedBy = "picture", cascade = CascadeType.ALL)
    List<PictureLike> likes = new ArrayList<>();
    
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    
   // @NotNull
   // @Lob
   // @Type(type = "org.hibernate.type.BinaryType")
   // private byte[] content;
}
