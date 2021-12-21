package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MembersOfEventResponseDTO {
    private List<Members> member;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Members {
        private long applicationId;
        private long eventId;
        private long member;
        private String surname;
        private String name;
        private String patronymic;
        private long birthday;
        private String phone;
        private String email;
        private Integer winner;
    }
}
