package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventSaveResponseDTO {
    private Event events;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Event {
        private long id;
        private String name;
        private String image;
        private boolean completed;
    }
}
