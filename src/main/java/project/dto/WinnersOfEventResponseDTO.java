package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WinnersOfEventResponseDTO {
    private List<Winners> winners;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Winners {
        private long applicationId;
        private long eventId;
        private long member;
        private String surname;
        private String name;
        private String patronymic;
        private long birthday;
        private String phone;
        private String email;
    }
}
