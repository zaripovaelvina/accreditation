package project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Application {
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
    private long personDisciplinesId;
    private String file;
}
