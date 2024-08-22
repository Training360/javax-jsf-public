package empapp;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface EmployeeMapper {

    Employee toEntity(CreateEmployeeCommand command);

    EmployeeDto toDto(Employee employee);

    UpdateEmployeeCommand dtoToUpdateCommand(EmployeeDto employeeDto);
}
