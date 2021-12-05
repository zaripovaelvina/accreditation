package project.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import project.domain.PersonModel;
import project.dto.PersonGetAllResponseDTO;
import project.rowmapper.PersonRowMapper;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonManager {
    private final NamedParameterJdbcTemplate template;
    private final PersonRowMapper personRowMapper;

    public PersonGetAllResponseDTO getAll() {
        final List<PersonModel> personLists = template.query(
                // language=PostgreSQL
                """
                        SELECT id, name, surname, patronymic, birthday, phone, email, citizenship_id, country_id, gender
                        FROM person
                        WHERE removed = FALSE
                        ORDER BY id
                        LIMIT 500
                        """,
                personRowMapper
        );

        final PersonGetAllResponseDTO responseDTO = new PersonGetAllResponseDTO(new ArrayList<>(personLists.size()));
        for (PersonModel personList : personLists) {
            responseDTO.getPersons().add(new PersonGetAllResponseDTO.Person(
                    personList.getId(),
                    personList.getName(),
                    personList.getSurname(),
                    personList.getPatronymic(),
                    personList.getBirthday(),
                    personList.getPhone(),
                    personList.getEmail(),
                    personList.getCitizenshipId(),
                    personList.getCountryId(),
                    personList.getGender()
            ));

        }

        return responseDTO;
    }
}
