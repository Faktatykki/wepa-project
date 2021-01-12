
package projekti.Services;

import java.io.IOException;
import projekti.Repositories.AccountRepository;
import projekti.Objects.Account;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projekti.Objects.PictureComment;
import projekti.Objects.PictureFile;
import projekti.Objects.PictureLike;
import projekti.Repositories.PictureCommentRepository;
import projekti.Repositories.PictureLikeRepository;
import projekti.Repositories.PictureRepository;

@Service
public class PictureService {
    
    @Autowired
    private PictureRepository picRep;
    
    @Autowired 
    private AccountRepository accRep;
    
    @Autowired
    private PictureCommentRepository picCommRep;
    
    @Autowired
    private PictureLikeRepository picLikeRep;
    
    
    public void save(String desc, MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account userAcc = accRep.findByName(username);
        
        if(picRep.findPicturesById(userAcc.getId()).size() >= 10) {
            return;
        }
        
        PictureFile fo = new PictureFile();
        
        fo.setOwner(userAcc);
        fo.setName(file.getOriginalFilename());
        fo.setMediaType(file.getContentType());
        fo.setSize(file.getSize());
        fo.setImageToDeploy(Base64.getEncoder().encodeToString(file.getBytes()));
        fo.setDescription(desc);
        
        Date timestamp = java.util.Calendar.getInstance().getTime();
        fo.setTimestamp(timestamp);
        
        picRep.save(fo);
    }
    
    public void removePicture(Long id) {
        picRep.deleteById(id);
    }
    
    public void userSave(String userUrl, String desc, MultipartFile file) throws IOException {
        Account userAcc = accRep.findByUserUrl(userUrl);
        
        PictureFile fo = new PictureFile();
        
        fo.setOwner(userAcc);
        fo.setName(file.getOriginalFilename());
        fo.setMediaType(file.getContentType());
        fo.setSize(file.getSize());
        fo.setImageToDeploy(Base64.getEncoder().encodeToString(file.getBytes()));
        fo.setDescription(desc);
        
        picRep.save(fo);
    }
    
    public List<PictureFile> getPicturesByUsername(String username) {
        Long id = accRep.findByName(username).getId();
        
        return picRep.findPicturesById(id);
    }
    
    public Account getOwnerById(Long id) {
        return picRep.getOne(id).getOwner();
    }
    
    public void newCommentOnPicture(Long id, String sender, String content)  {
        PictureFile picture = picRep.getOne(id);
        
        if(picture.getComments().size() >= 10) {
            PictureComment pc = picture.getComments().get(0);
            picCommRep.delete(pc);
        }
        
        PictureComment picComm = new PictureComment();
        
        picComm.setSender(accRep.findByName(sender));
        picComm.setFo(picture);
        picComm.setContent(content);
        
        Date timestamp = java.util.Calendar.getInstance().getTime();
        picComm.setTimestamp(timestamp);
        
        picCommRep.save(picComm);
    }
    
    public void newLikeOnPicture(Long pictureId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account acc = accRep.findByName(username);
        
        PictureFile likedPicture = picRep.getOne(pictureId);
        
        PictureLike picLike = new PictureLike();
        
        picLike.setLiker(acc);
        picLike.setPicture(likedPicture);
        
        if(checkIfLiked(pictureId)) {
            return;
        }
        
        picLikeRep.save(picLike);
        
    }
    
    public void changeProfilePicture(Long imageId, String username) {
        Account acc = accRep.findByName(username);
        
        for(PictureFile picture: picRep.findPicturesById(acc.getId())) {
            if(picture.getId() == imageId && picture.isProfilePicture()) {
                return;
            }
            if(picture.getId() == imageId) {
                picture.setProfilePicture(true);
                picRep.save(picture);
                continue;
            }
           
            picture.setProfilePicture(false);
            picRep.save(picture);
        }
    }
    
    public String getProfilePicture(Account user) {
     
        for(PictureFile picture: picRep.findPicturesById(user.getId())) {
            if(picture.isProfilePicture()) {
                return picture.getImageToDeploy();
            }
        }
        
        return null;
    }
    
    public boolean checkIfLiked(Long pictureId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Long userId = accRep.findByName(username).getId();
        
        if(picLikeRep.findSpecificLikeByPictureIdAndAccountId(pictureId, userId) == null) {
            return false;
        }
        
        return true;
    }
}
