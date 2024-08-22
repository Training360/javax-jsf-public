package empapp;

import lombok.extern.slf4j.Slf4j;

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

@Named
@ApplicationScoped
@Slf4j
public class EmployeesService {

    @Inject
    private EmployeeMapper mapper;

    private final List<Employee> employees = new CopyOnWriteArrayList<>();

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
        log.info("Create employee {}", command.getName());
        Employee employee = mapper.toEntity(command);
        saveFile(command, employee);
        employee.setId(UUID.randomUUID().toString());
        employees.add(employee);
        return mapper.toDto(employee);
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
        return employees.stream().map(mapper::toDto).toList();
    }

    public List<EmployeeDto> listEmployees(String query) {
        return employees.stream()
                .filter(e -> query == null || e.getName().startsWith(query))
                .map(mapper::toDto).toList();
    }

    public Optional<EmployeeDto> updateEmployee(UpdateEmployeeCommand command) {
        return employees.stream()
                .filter(e -> e.getId().equals(command.getId()))
                .peek(e -> e.setName(command.getName()))
                .map(mapper::toDto)
                .findFirst();
    }

    public void deleteEmployee(String id) {
        employees.removeIf(e -> e.getId().equals(id));
    }

    public Optional<EmployeeDto> findEmployeeById(String id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .map(mapper::toDto)
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
        return employees.stream().anyMatch(e -> e.getCardNumber() != null && !e.getCardNumber().isBlank() && e.getCardNumber().equals(cardNumber));
    }


}
