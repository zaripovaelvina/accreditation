package project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonModel {
    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private long birthday;
    private String phone;
    private String email;
    private long citizenshipId;
    private long countryId;
    private String gender;
    private boolean removed;
    private long created;
}
