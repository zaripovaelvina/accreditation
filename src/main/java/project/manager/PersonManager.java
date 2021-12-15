package project.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import project.domain.PersonBasicModel;
import project.domain.PersonFullModel;
import project.dto.*;
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
    private final JavaMailSender mailSender;
    private final String defaultImage = "noimage.png";

    public PersonGetAllResponseDTO getAll() {
        final List<PersonBasicModel> memberLists = template.query(
                // language=PostgreSQL
                """
                        SELECT id, name, surname, phone, email, citizenship_id, country_id, gender, image
                        FROM persons
                        WHERE removed = FALSE
                        ORDER BY id
                        LIMIT 500
                        """,
                personBasicRowMapper
        );

        final PersonGetAllResponseDTO responseDTO = new PersonGetAllResponseDTO(new ArrayList<>(memberLists.size()));
        for (PersonBasicModel memberList : memberLists) {
            responseDTO.getPersons().add(new PersonGetAllResponseDTO.Person(
                    memberList.getId(),
                    memberList.getName(),
                    memberList.getSurname(),
                    memberList.getPhone(),
                    memberList.getEmail(),
                    memberList.getCitizenshipId(),
                    memberList.getCountryId(),
                    memberList.getGender(),
                    memberList.getImage()
            ));
        }
        return responseDTO;
    }

    public PersonGetByIdResponseDTO getById(long id) {
        try {
            final PersonFullModel member = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, name, surname, patronymic, EXTRACT(EPOCH FROM birthday) AS birthday, phone, 
                            email, citizenship_id, country_id, gender, image
                            FROM persons
                            WHERE id = :id AND removed = FALSE
                            """,
                    Map.of("id", id),
                    personFullRowMapper
            );

            final PersonGetByIdResponseDTO responseDTO = new PersonGetByIdResponseDTO(
                    new PersonGetByIdResponseDTO.Person(
                            member.getId(),
                            member.getName(),
                            member.getSurname(),
                            member.getPatronymic(),
                            member.getBirthday(),
                            member.getPhone(),
                            member.getEmail(),
                            member.getCitizenshipId(),
                            member.getCountryId(),
                            member.getGender(),
                            member.getImage()
                    ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new PersonNotFoundException(e);
        }
    }

    public PersonSaveResponseDTO save(PersonSaveRequestDTO requestDTO) {
        if (requestDTO.getId() == 0) {
            return create(requestDTO);
        }
        return update(requestDTO);
    }

    private PersonSaveResponseDTO create(PersonSaveRequestDTO requestDTO) {
        final PersonFullModel member = template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO persons (name, surname, patronymic, birthday, phone, email, 
                                           citizenship_id, country_id, gender, image) 
                        VALUES (:name, :surname, :patronymic, to_date(:birthday, 'dd.mm.yyyy'), :phone, :email, 
                               :citizenship_id, :country_id, :gender, :image)
                        RETURNING id, name, surname, patronymic, EXTRACT(EPOCH FROM birthday) AS birthday, phone, email, 
                                  citizenship_id, country_id, gender, image
                        """,
                Map.of(
                        "name", requestDTO.getName(),
                        "surname", requestDTO.getSurname(),
                        "patronymic", requestDTO.getPatronymic(),
                        "birthday", requestDTO.getBirthday(),
                        "phone", requestDTO.getPhone(),
                        "email", requestDTO.getEmail(),
                        "citizenship_id", requestDTO.getCitizenshipId(),
                        "country_id", requestDTO.getCountryId(),
                        "gender", requestDTO.getGender(),
                        "image", getImage(requestDTO.getImage())
                ),
                personFullRowMapper
        );

        final PersonSaveResponseDTO responseDTO = new PersonSaveResponseDTO(new PersonSaveResponseDTO.Person(
                member.getId(),
                member.getName(),
                member.getSurname(),
                member.getPatronymic(),
                member.getBirthday(),
                member.getPhone(),
                member.getEmail(),
                member.getCitizenshipId(),
                member.getCountryId(),
                member.getGender(),
                member.getImage()
        ));

        return responseDTO;
    }

    public void removeById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE persons SET removed = TRUE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new PersonNotFoundException("person with id" + id + " not found");
        }
    }

    public void restoreById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE persons SET removed = FALSE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new PersonNotFoundException("person with id" + id + " restored");
        }
    }

    private PersonSaveResponseDTO update(PersonSaveRequestDTO requestDTO) {
        try {
            final PersonFullModel member = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE persons SET name = :name, surname = :surname, patronymic = :patronymic, 
                            birthday = :birthday, phone = :phone, email = :email, citizenship_id = :citizenship_id,
                            country_id = :country_id, gender = :gender, image = :image
                            WHERE id = :id AND removed = FALSE
                            RETURNING id, name, surname, patronymic, birthday, phone, email, 
                            citizenship_id, country_id, gender, image
                            """,
                    Map.of(
                            "name", requestDTO.getName(),
                            "surname", requestDTO.getSurname(),
                            "patronymic", requestDTO.getPatronymic(),
                            "birthday", requestDTO.getBirthday(),
                            "phone", requestDTO.getPhone(),
                            "email", requestDTO.getEmail(),
                            "citizenship_id", requestDTO.getCitizenshipId(),
                            "country_id", requestDTO.getCountryId(),
                            "gender", requestDTO.getGender(),
                            "image", getImage(requestDTO.getImage())
                    ),
                    personFullRowMapper
            );

            final PersonSaveResponseDTO responseDTO = new PersonSaveResponseDTO(new PersonSaveResponseDTO.Person(
                    member.getId(),
                    member.getName(),
                    member.getSurname(),
                    member.getPatronymic(),
                    member.getBirthday(),
                    member.getPhone(),
                    member.getEmail(),
                    member.getCitizenshipId(),
                    member.getCountryId(),
                    member.getGender(),
                    member.getImage()
            ));

            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new PersonNotFoundException(e);
        }
    }

    private String getImage(String image) {
        return image == null ? defaultImage : image;
    }

    public void sandMailById(long id, int status) {
        try {
            final String personEmail = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT p.email FROM persons p, applications a
                            WHERE p.id = a.person_id AND a.status = :status and  p.id = :id
                            """,
                    Map.of("id", id, "status", status),
                    String.class
            );

            final SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("test999java@gmail.com"); // логин
            message.setTo(personEmail);

            if (status == 1) {
                message.setSubject("Вы можете участвовать на соревновании");
                message.setText("Уважаемый участник! Благодарим Вас за участие на данном мероприятии. Желаем Вам успехов!");
                mailSender.send(message);
                System.out.println("Письмо успешно отправлено");
            } else {
                message.setSubject("Вы не можете участвовать на соревновании");
                message.setText("Уважаемый участник! К сожалению, Вы не допущены к соревнованию." +
                        "Благодарим Вас за желание участвовать на данном мероприятии. Ждем Вас в следующем году!");
                mailSender.send(message);
                System.out.println("Письмо успешно отправлено");

            }
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Письмо не отправилось");
            throw new PersonNotFoundException(e);
        }
    }
}

