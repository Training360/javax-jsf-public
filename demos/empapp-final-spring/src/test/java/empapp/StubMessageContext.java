package empapp;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Named;

@ApplicationScoped
@Specializes
public class StubMessageContext extends MessageContext {

    @Override
    public void setFlashMessage(String message) {

    }
}
