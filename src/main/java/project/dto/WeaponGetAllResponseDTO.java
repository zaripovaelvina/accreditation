package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeaponGetAllResponseDTO {
    private List<Weapons> weapon;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Weapons {
        private long id;
        private String name;
        private String image;
    }
}

