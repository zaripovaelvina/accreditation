package project.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import project.domain.PersonBasicModel;
import project.domain.PersonFullModel;
import project.dto.PersonGetAllResponseDTO;
import project.dto.PersonGetByIdResponseDTO;
import project.exception.PersonNotFoundException;
import project.rowmapper.PersonBasicRowMapper;
import project.rowmapper.PersonFullRowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PersonManager {
    private final NamedParameterJdbcTemplate template;
    private final PersonBasicRowMapper personBasicRowMapper;
    private final PersonFullRowMapper personFullRowMapper;

    public PersonGetAllResponseDTO getAll() {
        final List<PersonBasicModel> personLists = template.query(
                // language=PostgreSQL
                """
                        SELECT id, name, surname, phone, email, citizenship_id, country_id, gender
                        FROM person
                        WHERE removed = FALSE
                        ORDER BY id
                        LIMIT 500
                        """,
                personBasicRowMapper
        );

        final PersonGetAllResponseDTO responseDTO = new PersonGetAllResponseDTO(new ArrayList<>(personLists.size()));
        for (PersonBasicModel personList : personLists) {
            responseDTO.getPersons().add(new PersonGetAllResponseDTO.Person(
                    personList.getId(),
                    personList.getName(),
                    personList.getSurname(),
                    personList.getPhone(),
                    personList.getEmail(),
                    personList.getCitizenshipId(),
                    personList.getCountryId(),
                    personList.getGender()
            ));
        }
        return responseDTO;
    }

    public PersonGetByIdResponseDTO getById(long id) {
        try {
            final PersonFullModel personList = (PersonFullModel) template.queryForObject(
                    // language=PostgreSQL
                    """
                        SELECT id, name, surname, patronymic, birthday, phone, email, citizenship_id, country_id, gender
                        FROM person
                        WHERE id = :id AND removed = FALSE
                        """,
                Map.of("id", id),
                personFullRowMapper
        );

        final PersonGetByIdResponseDTO responseDTO = new PersonGetByIdResponseDTO(new PersonGetByIdResponseDTO.Person(
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
        return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new PersonNotFoundException(e);
        }
    }
}