package jsf.training.empappspring;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;

@Component
@RequestScope
public class HelloController {

    public String getMessage() {
        return "Hello Spring JSF!" + LocalDateTime.now();
    }
}
