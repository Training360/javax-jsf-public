package empapp;

import lombok.Data;

@Data
public class UpdateEmployeeCommand {

    private String id;

    private String name;
}
