package empapp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class Employee {

    private String id;

    private String name;

    private String cardNumber;

    private String type;

    private List<String> skills;

    private String fileName;

    private LocalDateTime startedAt;

}
