package empapp;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
@Slf4j
public class EmployeesService {

    private List<Employee> employees = new CopyOnWriteArrayList<>();

    @Inject
    private ModelMapper mapper;

    private Path tempDir;

    @PostConstruct
    public void initEmployees() {
        createEmployee(new CreateEmployeeCommand("John Doe"));
        createEmployee(new CreateEmployeeCommand("Jane Doe"));

        try {
            tempDir = Files.createTempDirectory("empapp");
            log.info(tempDir.toString());
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Can not create dir", ioe);
        }
    }

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        log.info("Create employee " + command.getName());
        Employee employee = mapper.map(command, Employee.class);
        saveFile(command, employee);
        employee.setId(UUID.randomUUID().toString());
        employees.add(employee);
        return mapper.map(employee, EmployeeDto.class);
    }

    private void saveFile(CreateEmployeeCommand command, Employee employee) {
        if (command.getFile() != null) {
            String filename = command.getFile().getSubmittedFileName();
            try {
                Files.copy(command.getFile().getInputStream(), tempDir.resolve(filename));
            } catch (IOException ioe) {
                throw new IllegalStateException("Can not copy to temp dir", ioe);
            }
            employee.setFileName(filename);
        }
    }


    public List<EmployeeDto> listEmployees() {
        return employees.stream().map(e -> mapper.map(e, EmployeeDto.class)).collect(Collectors.toList());
    }

    public List<EmployeeDto> listEmployees(String query) {
        return employees.stream()
                .filter(e -> query == null || e.getName().startsWith(query))
                .map(e -> mapper.map(e, EmployeeDto.class)).collect(Collectors.toList());
    }

    public Optional<EmployeeDto> updateEmployee(UpdateEmployeeCommand command) {
        return employees.stream()
                .filter(e -> e.getId().equals(command.getId()))
                .peek(e -> mapper.map(command, e))
                .map(e -> mapper.map(e, EmployeeDto.class))
                .findFirst();
    }

    public void deleteEmployee(String id) {
        Optional<Employee> employee = employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        if (employee.isPresent()) {
            employees.remove(employee.get());
        }
    }

    public Optional<EmployeeDto> findEmployeeById(String id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .map(e -> mapper.map(e, EmployeeDto.class))
                .findFirst();
    }

    public void deleteAllEmployees() {
        employees.clear();
    }

    public List<String> listTypes() {
        return List.of("full-time", "part-time");
    }

    public List<Skill> listSkills() {
        return List.of(new Skill("java", "Java programming"), new Skill("jsf", "Java ServerFaces"));
    }

    public Path getFile(String filename) {
        return tempDir.resolve(filename);
    }

    public boolean isEmployeeWithCardNumber(String cardNumber) {
        return employees.stream().anyMatch(e -> e.getCardNumber() != null && e.getCardNumber().equals(cardNumber));
    }


}
