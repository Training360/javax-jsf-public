package empapp;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class UpdateEmployeeController {

    @Getter
    private UpdateEmployeeCommand command = new UpdateEmployeeCommand();

    @Getter @Setter
    private String id;

    @Inject
    private EmployeesService employeesService;

    @Inject
    private MessageContext messageContext;

    @Inject
    private EmployeeMapper mapper;

    public void findEmployee() {
        EmployeeDto employeeDto = employeesService.findEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        command = mapper.dtoToUpdateCommand(employeeDto);
    }

    public String update() {
        employeesService.updateEmployee(command);
        messageContext.setFlashMessage("Employee has update");
        return "employees.xhtml?faces-redirect=true";
    }
}
