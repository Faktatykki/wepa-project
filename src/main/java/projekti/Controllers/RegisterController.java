
package projekti.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Repositories.AccountRepository;
import projekti.Services.RegisterService;

@Controller
public class RegisterController {
    
    @Autowired
    private AccountRepository acRep;
    
    @Autowired
    private RegisterService regSer;
    
    @GetMapping("/register")
    public String frontPage() {
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String name, 
                           @RequestParam String userURL,
                           @RequestParam String password,
                           @RequestParam String passwordAgain) {
        
       if(regSer.register(name, userURL, password, passwordAgain)) {
           return "redirect:/successReg";
       }
        
        return "redirect:/failreg";
    }
    
    @GetMapping("/successReg")
    public String succesfulRegister() {
        return "successfulregister";
    }
    
    @GetMapping("/failreg")
    public String failedRegister() {
        return "regfail";
    } 
}