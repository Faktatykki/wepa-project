package projekti.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String helloWorld() {
        return "redirect:/welcome";
    }
    
    @GetMapping("/welcome")
    public String firstPage() {
        return "firstpage";
    }
}
