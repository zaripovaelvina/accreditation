package project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Disciplines {
    private long id;
    private String name;
    private int status;
    private int weaponStatus;
}
