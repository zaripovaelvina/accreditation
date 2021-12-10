package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventGetAllResponseDTO {
    private List<Events> events;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Events {
        private long id;
        private String name;
        private String image;
    }
}

