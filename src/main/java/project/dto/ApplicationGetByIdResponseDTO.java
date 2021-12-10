package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApplicationGetByIdResponseDTO {
    private Applications application;

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
        private Boolean removed;
        private long created;
    }
}
