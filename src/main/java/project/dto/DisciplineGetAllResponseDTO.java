package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DisciplineGetAllResponseDTO {
    private List<Disciplines> disciplines;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Disciplines {
        private long id;
        private String name;
        private int status;
    }
}

