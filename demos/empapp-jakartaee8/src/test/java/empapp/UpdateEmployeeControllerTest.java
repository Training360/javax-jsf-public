package empapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateEmployeeControllerTest {

    @Mock
    EmployeesService service;

    @Mock
    MessageContext messageContext;

    @Spy
    EmployeeMapper employeeMapper = new EmployeeMapperImpl();

    @InjectMocks
    UpdateEmployeeController controller;


    @Test
    void testFillForm() {
        when(service.findEmployeeById(any()))
                .thenReturn(Optional.of(new EmployeeDto("1", "John Doe")));

        controller.setId("1");
        controller.findEmployee();

        assertEquals("John Doe", controller.getCommand().getName());
    }

    @Test
    void testUpdate() {
        when(service.findEmployeeById(any()))
                .thenReturn(Optional.of(new EmployeeDto("1", "John Doe")));

        controller.setId("1");
        controller.findEmployee();

        controller.getCommand().setName("Joe Doe");
        controller.update();

        verify(service).updateEmployee(argThat(c -> c.getName().equals("Joe Doe")));
        verify(messageContext).setFlashMessage(eq("Employee has update"));
    }
}
