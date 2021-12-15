package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DisciplineSaveRequestDTO {
    private long id;
    private String name;
    private int status;
}
