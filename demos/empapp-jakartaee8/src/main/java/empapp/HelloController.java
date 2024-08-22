package empapp;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

//@Model
@Named
@RequestScoped
@Getter @Setter
public class HelloController {

//    private String message;

    private String name;

    public String sayHello() {
        String message = "Hello " + name;

        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("message", message);

        return "hello.xhtml?faces-redirect=true";
    }
}
