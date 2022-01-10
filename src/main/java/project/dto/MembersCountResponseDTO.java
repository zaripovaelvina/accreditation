package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MembersCountResponseDTO {
    private List<Members> member;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Members {
        private long eventId;
    }
}
