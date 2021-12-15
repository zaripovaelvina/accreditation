package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DisciplineSaveResponseDTO {
    private Disciplines Discipline;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Disciplines {
        private long id;
        private String name;
        private int status;
    }
}
