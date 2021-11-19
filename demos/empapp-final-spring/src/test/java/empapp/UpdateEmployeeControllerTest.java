package empapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateEmployeeControllerTest {

    @Mock
    private EmployeesService service;

    @Mock
    private MessageContext messageContext;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private UpdateEmployeeController controller;

    @Test
    public void testFillForm() {
        when(service.findEmployeeById(any()))
                .thenReturn(Optional.of(new EmployeeDto("1", "John Doe")));

        controller.setId("1");
        controller.findEmployee();

        assertEquals("John Doe", controller.getCommand().getName());
    }

    @Test
    public void testUpdate() {
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
