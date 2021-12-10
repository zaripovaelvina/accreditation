package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeaponGetByIdResponseDTO {
    private Weapons weapon;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Weapons {
        private long id;
        private String name;
        private String image;
    }
}
