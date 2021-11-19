package empapp;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class CreateEmployeeIT {
    @Inject
    private EmployeesService employeesService;

    @Inject
    private CreateEmployeeController createEmployeeController;

    @Inject
    private EmployeesController employeesController;

    @Inject
    private UpdateEmployeeController updateEmployeeController;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(CreateEmployeeController.class.getPackage())
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/faces-config.xml"), "faces-config.xml")
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsLibraries(Maven.configureResolver().loadPomFromFile("pom.xml")
                        .resolve("org.modelmapper:modelmapper").withTransitivity().asSingleFile())

                ;
    }


    @Test
    public void testCreateEmployee() {
        employeesService.deleteAllEmployees();

        createEmployeeController.getCommand().setName("John Doe");
        createEmployeeController.saveEmployee();

        assertEquals(List.of("John Doe"),
                employeesController.getEmployees().stream().map(EmployeeDto::getName).collect(Collectors.toList()));
    }

    @Test
    public void testUpdateEmployee() {
        employeesService.deleteAllEmployees();

        createEmployeeController.getCommand().setName("John Doe");
        createEmployeeController.saveEmployee();

        employeesController.initEmployees();
        String id = employeesController.getEmployees().stream().findFirst().get().getId();
        System.out.println(id);

        updateEmployeeController.setId(id);
        updateEmployeeController.findEmployee();

        updateEmployeeController.getCommand().setName("Joe Doe");
        updateEmployeeController.update();

        employeesController.initEmployees();

        assertEquals(List.of("Joe Doe"),
                employeesController.getEmployees().stream().map(EmployeeDto::getName).collect(Collectors.toList()));
    }

}
