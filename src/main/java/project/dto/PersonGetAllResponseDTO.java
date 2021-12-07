package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonGetAllResponseDTO {
    private List<Person> persons;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Person {
        private long id;
        private String name;
        private String surname;
        private String phone;
        private String email;
        private long citizenshipId;
        private long countryId;
        private String gender;
    }
}
