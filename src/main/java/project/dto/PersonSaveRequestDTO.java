package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonSaveRequestDTO {
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
    private String image;

}
