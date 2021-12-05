package project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonDisciplinesWeapon {
    private long id;
    private long personId;
    private long weaponId;
    private String weaponManufacturer;
    private String permitSerial;
    private String permitNum;
    private long permitDate;
    private long personDisciplinesId;
    private String file;
}
