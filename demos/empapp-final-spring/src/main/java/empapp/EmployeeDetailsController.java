package empapp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Component
@RequestScope
public class EmployeeDetailsController {

    @Inject
    private EmployeesService employeesService;

    @Getter
    @Setter
    private String id;

    @Getter
    private EmployeeDto employee;

    public void findEmployee() {
        employee  = employeesService.findEmployeeById(id).orElseThrow(() ->
                new IllegalArgumentException("Employee not found"));
    }
}
