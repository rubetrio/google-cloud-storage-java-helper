package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @RequestMapping("/test1")
    public String index() {
        return "Greetings from Spring Boot!2";
    }
    
}
