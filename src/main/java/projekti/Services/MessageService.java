
package projekti.Services;

import projekti.Repositories.AccountRepository;
import projekti.Objects.Account;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projekti.Objects.Message;
import projekti.Objects.MessageComment;
import projekti.Objects.MessageLike;
import projekti.Repositories.MessageCommentRepository;
import projekti.Repositories.MessageLikeRepository;
import projekti.Repositories.MessageRepository;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messRep;
    
    @Autowired
    private AccountRepository accRep;
    
    @Autowired
    private MessageCommentRepository messCommRep;
    
    @Autowired
    private MessageLikeRepository messLikeRep;
    
    public void newLike(Long messageId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account acc = accRep.findByName(username);
        
        Message likedMessage = messRep.getOne(messageId);
        
        MessageLike messLike = new MessageLike();
        
        messLike.setLiker(acc);
        messLike.setMessage(likedMessage);
        
        if(checkIfLiked(messageId)) {
            return;
        }
        
        messLikeRep.save(messLike);
    }
    
    public void newMessage(String userUrl, String content) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account receiver = accRep.findByUserUrl(userUrl);
        Account sender = accRep.findByName(username);
        
        Message newMessage = new Message();
        newMessage.setMessReceiver(receiver);
        newMessage.setMessSender(sender);
        newMessage.setContent(content);
        
        Date date = java.util.Calendar.getInstance().getTime();
        newMessage.setTimestamp(date);
        
        messRep.save(newMessage);
    }
    
    public void homeNewMessage(String content) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account receiver = accRep.findByName(username);
        Account sender = accRep.findByName(username);
        
        Message newMessage = new Message();
        newMessage.setMessReceiver(receiver);
        newMessage.setMessSender(sender);
        newMessage.setContent(content);
        
        Date date = java.util.Calendar.getInstance().getTime();
        newMessage.setTimestamp(date);
        
        messRep.save(newMessage);
    }
    
    public List<Message> getAllReceivedMessagesByName(String user) {
        Long id = accRep.findByName(user).getId();
        return messRep.findAllMessagesReceivedById(id);
    }
    
    public List<Message> getAllReceivedMessagesByUserUrl(String userUrl) {
        Long id = accRep.findByUserUrl(userUrl).getId();
        return messRep.findAllMessagesReceivedById(id);
    }
    
    public void newCommentOnMessage(Long id, String content, String sender) {  
        Message commentedMessage = messRep.getOne(id);
        
        if(commentedMessage.getComments().size() >= 10) {
            MessageComment mc = commentedMessage.getComments().get(0);
            messCommRep.delete(mc);
        }
        
        MessageComment messComm = new MessageComment();
        
        messComm.setSender(accRep.findByName(sender));
        messComm.setMessage(commentedMessage);
        messComm.setContent(content);
        
        Date timestamp = java.util.Calendar.getInstance().getTime();
        messComm.setTimestamp(timestamp);
        
        messCommRep.save(messComm);
    }
    
    public boolean checkIfLiked(Long messageId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Long userId = accRep.findByName(username).getId();
        
        if(messLikeRep.findSpecificLikeByMessageIdAndAccountId(messageId, userId) == null) {
            return false;
        }
        
        return true;
    }
    
    
}
