package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApplicationGetAllResponseDTO {
    private List<Applications> applications;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Applications {
        private long id;
        private long personId;
        private long eventId;
        private long disciplinesId;
        private long organizationId;
        private long weaponId;
        private String weaponManufacturer;
        private String permitSerial;
        private String permitNum;
        private long permitDate;
        private String permitManufacturer;
        private String image;
    }
}

