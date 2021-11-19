package empapp;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
//@FlowScoped("create-employee")
@SessionScoped
@Slf4j
public class CreateEmployeeController implements Serializable {

    @Getter
    private CreateEmployeeCommand command = new CreateEmployeeCommand();

    @Getter
    private List<String> types;

    @Getter
    private List<Skill> skills;

    @Inject
    private EmployeesService employeesService;

    @Inject
    private MessageContext messageContext;

    @Getter
    private String monogram;

    @PostConstruct
    public void init() {
        log.info("Create employee controller init");
        types = employeesService.listTypes();
        skills = employeesService.listSkills();
    }

    private void clear() {
        command = new CreateEmployeeCommand();
        monogram = null;
    }

    @PreDestroy
    public void destroy() {
        log.info("Create employee controller destroy");
    }

    public String saveEmployeeFirst() {
        return "create-employee-details.xhtml?faces-redirect=true";
    }

    public String saveEmployee() {
        if (employeesService.isEmployeeWithCardNumber(command.getCardNumber())) {
            FacesContext.getCurrentInstance()
                    .addMessage("create-form:card-number-input",
                            new FacesMessage("Card number is already used"));
            return null;
        }

        employeesService.createEmployee(command);

        ResourceBundle resourceBundle = FacesContext.getCurrentInstance().getApplication()
                .evaluateExpressionGet(
                        FacesContext.getCurrentInstance(),
                        "#{msgs}",
                        ResourceBundle.class);

        String message = resourceBundle.getString("employee-created");

        messageContext.setFlashMessage(message);
        clear();
        return "employees.xhtml?faces-redirect=true";
    }

    public void calculateMonogram() {
        monogram = Arrays.stream(command.getName().split(" "))
                .map(s -> Character.toString(s.charAt(0)))
                .collect(Collectors.joining());
    }
}
