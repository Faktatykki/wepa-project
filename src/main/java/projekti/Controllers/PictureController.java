
package projekti.Controllers;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import projekti.Objects.Account;
import projekti.Objects.PictureFile;
import projekti.Services.PictureService;
import projekti.Services.UserService;

@Controller
public class PictureController {
    
    @Autowired
    private UserService userSer;
    
    @Autowired
    private PictureService picSer;
    
    @PostMapping("/home/pictures/upload")
    public String addPicture(@RequestParam("desc")String desc, @RequestParam("file") MultipartFile file) throws IOException {
        picSer.save(desc, file);
        
        return "redirect:/home/pictures"; 
    }
    
    @GetMapping("/home/pictures")
    public String picturesView(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        List<PictureFile> fos = picSer.getPicturesByUsername(username);
        
        model.addAttribute("pictures", fos);
        
        return "pictures";
    }
    
    @PostMapping("/home/pictures/commentpicture/{imageId}")
    public String commentPicture(HttpServletRequest request, @PathVariable Long imageId, @RequestParam (value = "send", required = true)String content) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        //with this able to return to page viewed right before
        String referer = request.getHeader("Referer");
        
        picSer.newCommentOnPicture(imageId, username, content);
        
        return "redirect:" + referer;
    }
    
    @GetMapping("/Users/pictures/{userUrl}")
    public String userPictureAlbumView(@PathVariable String userUrl, Model model) {
        Account user = userSer.findByUserUrl(userUrl);
        
        List<PictureFile> fos = picSer.getPicturesByUsername(user.getName());
        
        model.addAttribute("user", user);
        model.addAttribute("pictures", fos);
        
        return "userpictures";
    }
    
    @PostMapping("/home/pictures/removepicture/{imageId}")
    public String removePicture(@PathVariable Long imageId) {
        picSer.removePicture(imageId);
        
        return "redirect:/home/pictures";
    }
    
    @PostMapping("/home/pictures/makeprofilepicture/{imageId}")
    public String setProfilePicture(@PathVariable Long imageId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        picSer.changeProfilePicture(imageId, username);
        
        return "redirect:/home/pictures";
    }
    
    @PostMapping("/Users/pictures/likepicture/{pictureId}")
    public String likePicture(HttpServletRequest request, @PathVariable Long pictureId) {
        String referer = request.getHeader("Referer");
        
        picSer.newLikeOnPicture(pictureId);
        
        return "redirect:" + referer;
    }
}
