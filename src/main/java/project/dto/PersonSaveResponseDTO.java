package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonSaveResponseDTO {
    private Person person;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Person {
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
        private String image;
    }
}
