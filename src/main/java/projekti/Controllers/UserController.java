
package projekti.Controllers;

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
import projekti.Objects.Account;
import projekti.Services.FriendRequestService;
import projekti.Services.MessageService;
import projekti.Services.PictureService;
import projekti.Services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userSer;
    
    @Autowired
    private FriendRequestService frSer;
    
    @Autowired
    private MessageService messSer;
    
    @Autowired
    private PictureService picSer;
    
    @GetMapping("/Users")
    public String searchUsers(@RequestParam (value = "search", required = false)String name, Model model) throws Exception {
        model.addAttribute("search", userSer.searchForName(name));
        return "search";
    }
    
    @GetMapping("/Users/{userUrl}")
    public String getUser(@PathVariable String userUrl, Model model) {
        Account profileYoureWatching = userSer.findByUserUrl(userUrl);
        String profilename = profileYoureWatching.getName();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if(profilename.equals(username)) {
            return "redirect:/home";
        }
        
        model.addAttribute("name", "You're viewing profile of '" + profilename + "'");
        
        model.addAttribute("profilepicture", picSer.getProfilePicture(profileYoureWatching));
        
        //check if there's pending friendrequest
        model.addAttribute("checkIfRequest", frSer.checkIfFriendRequestOnDb(userSer.findByName(username).getId(), userSer.findByName(profilename).getId()));
        
        //check if already friends
        model.addAttribute("checkIfFriends", userSer.checkIfFriends(userSer.findByName(username).getId(), userSer.findByName(profilename).getId()));
              
        model.addAttribute("messages", messSer.getAllReceivedMessagesByUserUrl(userUrl));
        
        
        return "profile";
    }
    
    @PostMapping("/Users/{userUrl}")
    public String addFriend(@PathVariable String userUrl, Model model) {
        frSer.newFriendRequest(userUrl);
        
        return "redirect:/Users/{userUrl}";
    }
    
    @PostMapping("/Users/sendmessage/{userUrl}")
    public String sendMessage(@PathVariable String userUrl, @RequestParam (value = "send", required = true)String content, Model model) {
        messSer.newMessage(userUrl, content);
        
        return "redirect:/Users/{userUrl}";
    }
    
    @PostMapping("/Users/commentmessage/{messageId}")
    public String commentMessage(HttpServletRequest request, @PathVariable Long messageId, @RequestParam (value = "send", required = true)String content) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        //with this able to return to page viewed right before
        String referer = request.getHeader("Referer");
        
        messSer.newCommentOnMessage(messageId, content, username);
        
        return "redirect:" + referer;
    }
    
    @PostMapping("/Users/likemessage/{messageId}")
    public String likeMessage(HttpServletRequest request, @PathVariable Long messageId) {
        String referer = request.getHeader("Referer");
        
        messSer.newLike(messageId);
        
        return "redirect:" + referer;
    }
}