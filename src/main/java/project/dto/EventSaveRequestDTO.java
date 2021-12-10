package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventSaveRequestDTO {
    private long id;
    private String name;
    private boolean completed;
    private String image;

}
