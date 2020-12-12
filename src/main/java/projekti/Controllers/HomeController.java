
package projekti.Controllers;

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
public class HomeController {
    
    @Autowired
    private FriendRequestService frSer;
    
    @Autowired
    private UserService userSer;
    
    @Autowired
    private MessageService messSer;
    
    @Autowired
    private PictureService picSer;
    
    @GetMapping("/home")
    public String homeView(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account userAcc = userSer.findByName(username);
        
        //check if any pending friendrequests
        model.addAttribute("checkIfRequests", frSer.checkIfFriendRequests());
        
        //check if any friends
        model.addAttribute("checkIfFriends", frSer.checkIfFriends());
        
        model.addAttribute("user", username);
        model.addAttribute("name", "You're logged in as '" + username + "'");
        
        model.addAttribute("profilepicture", picSer.getProfilePicture(userAcc));
        
        model.addAttribute("requests", frSer.findAllFriendRequestsByName(username));
        model.addAttribute("friends", userSer.findAllFriends(username));
        
        model.addAttribute("messages", messSer.getAllReceivedMessagesByName(username));
        
        return "home";
    }  
    
    //userUrl = who requested the friendship
    @PostMapping("/home/acceptrequest/{userUrl}")
    public String acceptFriendRequest(@PathVariable String userUrl) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account receiverMe = userSer.findByName(username);
        Account requester = userSer.findByUserUrl(userUrl);
        
        frSer.acceptFriendRequest(receiverMe, requester);
        
        return "redirect:/home";
    }
    
    @PostMapping("/home/declinerequest/{userUrl}")
    public String declineFriendRequest(@PathVariable String userUrl) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account receiverMe = userSer.findByName(username);
        Account requester = userSer.findByUserUrl(userUrl);
        
        
        frSer.declineFriendRequest(receiverMe, requester);
        
        return "redirect:/home";
    }
    
    @PostMapping("/home")
    public String sendMessage(@RequestParam (value = "send", required = true)String content, Model model) {
        messSer.homeNewMessage(content);
        
        return "redirect:/home";
    }
    
    @PostMapping("/home/commentmessage/{messageId}")
    public String commentMessage(@PathVariable Long messageId, @RequestParam (value = "send", required = true)String content) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        messSer.newCommentOnMessage(messageId, content, username);
        
        return "redirect:/home";
    }
    
    @PostMapping("/home/likemessage/{messageId}")
    public String likeMessage(@PathVariable Long messageId) {
        messSer.newLike(messageId);
        
        return "redirect:/home";
    }
}
