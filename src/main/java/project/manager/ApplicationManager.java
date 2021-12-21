package project.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import project.domain.ApplicationBasicModel;
import project.domain.ApplicationFullModel;
import project.domain.MemberFullModel;
import project.domain.PersonBasicModel;
import project.dto.*;
import project.exception.ApplicationNotFoundException;
import project.exception.MemberNotFoundException;
import project.exception.PersonNotFoundException;
import project.rowmapper.ApplicationBasicRowMapper;
import project.rowmapper.ApplicationFullRowMapper;
import project.rowmapper.MemberFullRowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApplicationManager {
    private final NamedParameterJdbcTemplate template;
    private final ApplicationBasicRowMapper applicationBasicRowMapper;
    private final ApplicationFullRowMapper applicationFullRowMapper;
    private final MemberFullRowMapper memberFullRowMapper;
    private final String defaultImage = "noimage.png";

    public ApplicationGetAllResponseDTO getAll() {
        final List<ApplicationBasicModel> applicationLists = template.query(
                // language=PostgreSQL
                """
                        SELECT id, person_id, event_id, disciplines_id, organization_id, weapon_id, weapon_manufacturer,
                               permit_serial, permit_num, EXTRACT(EPOCH FROM permit_date) AS permit_date, 
                               permit_manufacturer, image
                        FROM applications
                        WHERE removed = FALSE
                        ORDER BY id
                        LIMIT 500
                        """,
                applicationBasicRowMapper
        );

        final ApplicationGetAllResponseDTO responseDTO = new ApplicationGetAllResponseDTO(new ArrayList<>(applicationLists.size()));
        for (ApplicationBasicModel applicationList : applicationLists) {
            responseDTO.getApplications().add(new ApplicationGetAllResponseDTO.Applications(
                    applicationList.getId(),
                    applicationList.getPersonId(),
                    applicationList.getEventId(),
                    applicationList.getDisciplinesId(),
                    applicationList.getOrganizationId(),
                    applicationList.getWeaponId(),
                    applicationList.getWeaponManufacturer(),
                    applicationList.getPermitSerial(),
                    applicationList.getPermitNum(),
                    applicationList.getPermitDate(),
                    applicationList.getPermitManufacturer(),
                    applicationList.getImage()
            ));
        }

        return responseDTO;
    }

    public ApplicationGetByIdResponseDTO getById(long id) {
        try {
            final ApplicationFullModel applicationList = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, person_id, event_id, disciplines_id, organization_id, weapon_id,
                            weapon_manufacturer, permit_serial, permit_num, EXTRACT(EPOCH FROM permit_date) AS permit_date,
                            permit_manufacturer, image                            
                            FROM applications 
                            WHERE id = :id AND removed = FALSE
                            """,
                    Map.of("id", id),
                    applicationFullRowMapper
            );

            final ApplicationGetByIdResponseDTO responseDTO = new ApplicationGetByIdResponseDTO(
                    new ApplicationGetByIdResponseDTO.Applications(
                            applicationList.getId(),
                            applicationList.getPersonId(),
                            applicationList.getEventId(),
                            applicationList.getDisciplinesId(),
                            applicationList.getOrganizationId(),
                            applicationList.getWeaponId(),
                            applicationList.getWeaponManufacturer(),
                            applicationList.getPermitSerial(),
                            applicationList.getPermitNum(),
                            applicationList.getPermitDate(),
                            applicationList.getPermitManufacturer(),
                            applicationList.getImage()
                    ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ApplicationNotFoundException(e);
        }
    }

    public ApplicationSaveResponseDTO save(ApplicationSaveRequestDTO requestDTO) {
        if (requestDTO.getId() == 0) {
            return create(requestDTO);
        }
        return update(requestDTO);
    }

    private ApplicationSaveResponseDTO create(ApplicationSaveRequestDTO requestDTO) {
        final ApplicationFullModel applicationList = template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO applications (person_id, event_id, disciplines_id, organization_id, weapon_id, 
                        weapon_manufacturer, permit_serial, permit_num, permit_date, permit_manufacturer, image) 
                        VALUES (:person_id, :event_id, :disciplines_id, :organization_id, :weapon_id, 
                               :weapon_manufacturer, :permit_serial, :permit_num, to_date(:permit_date, 'dd.mm.yyyy'),
                               :permit_manufacturer, :image)
                        RETURNING id, person_id, event_id, disciplines_id, organization_id, weapon_id, weapon_manufacturer,
                               permit_serial, permit_num, EXTRACT(EPOCH FROM permit_date) AS permit_date, 
                               permit_manufacturer, image
                            """,
                Map.ofEntries(
                        Map.entry("person_id", requestDTO.getPersonId()),
                        Map.entry("event_id", requestDTO.getEventId()),
                        Map.entry("disciplines_id", requestDTO.getDisciplinesId()),
                        Map.entry("organization_id", requestDTO.getOrganizationId()),
                        Map.entry("weapon_id", requestDTO.getWeaponId()),
                        Map.entry("weapon_manufacturer", requestDTO.getWeaponManufacturer()),
                        Map.entry("permit_serial", requestDTO.getPermitSerial()),
                        Map.entry("permit_num", requestDTO.getPermitNum()),
                        Map.entry("permit_date", requestDTO.getPermitDate()),
                        Map.entry("permit_manufacturer", requestDTO.getPermitManufacturer()),
                        Map.entry("image", requestDTO.getImage())
                ),
                applicationFullRowMapper
        );

        final ApplicationSaveResponseDTO responseDTO = new ApplicationSaveResponseDTO(
                new ApplicationSaveResponseDTO.Applications(
                        applicationList.getId(),
                        applicationList.getPersonId(),
                        applicationList.getEventId(),
                        applicationList.getDisciplinesId(),
                        applicationList.getOrganizationId(),
                        applicationList.getWeaponId(),
                        applicationList.getWeaponManufacturer(),
                        applicationList.getPermitSerial(),
                        applicationList.getPermitNum(),
                        applicationList.getPermitDate(),
                        applicationList.getPermitManufacturer(),
                        applicationList.getImage()
                ));

        return responseDTO;
    }

    public void removeById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE applications SET removed = TRUE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new ApplicationNotFoundException("Application with id" + id + " not found");
        }
    }

    public void restoreById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE applications SET removed = FALSE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new ApplicationNotFoundException("Application with id" + id + " restored");
        }
    }

    private ApplicationSaveResponseDTO update(ApplicationSaveRequestDTO requestDTO) {
        try {
            final ApplicationFullModel applicationList = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE applications SET person_id = :person_id, event_id = :event_id, 
                            disciplines_id = :disciplines_id, organization_id = :organization_id, 
                            weapon_id = :weapon_id, weapon_manufacturer = :weapon_manufacturer, 
                            permit_serial = :permit_serial, permit_num = :permit_num, permit_date = :permit_date, 
                            permit_manufacturer =:permit_manufacturer, image = :image
                            WHERE id = :id AND removed = FALSE
                            RETURNING person_id, event_id, disciplines_id, organization_id, weapon_id,
                            weapon_manufacturer, permit_serial, permit_num, permit_date, permit_manufacturer, image
                            """,
                    Map.of(
                            "person_id", requestDTO.getPersonId(),
                            "event_id", requestDTO.getEventId(),
                            "disciplines_id", requestDTO.getDisciplinesId(),
                            "organization_id", requestDTO.getOrganizationId(),
                            "weapon_id", requestDTO.getWeaponId(),
                            "permit_serial", requestDTO.getWeaponManufacturer(),
                            "permit_num", requestDTO.getPermitSerial(),
                            "permit_date", requestDTO.getPermitDate(),
                            "permit_manufacturer", requestDTO.getPermitManufacturer(),
                            "image", getImage(requestDTO.getImage())
                    ),
                    applicationFullRowMapper
            );

            final ApplicationSaveResponseDTO responseDTO = new ApplicationSaveResponseDTO(
                    new ApplicationSaveResponseDTO.Applications(
                            applicationList.getId(),
                            applicationList.getPersonId(),
                            applicationList.getEventId(),
                            applicationList.getDisciplinesId(),
                            applicationList.getOrganizationId(),
                            applicationList.getWeaponId(),
                            applicationList.getWeaponManufacturer(),
                            applicationList.getPermitSerial(),
                            applicationList.getPermitNum(),
                            applicationList.getPermitDate(),
                            applicationList.getPermitManufacturer(),
                            applicationList.getImage()
                    ));

            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ApplicationNotFoundException(e);
        }
    }

    private String getImage(String image) {
        return image == null ? defaultImage : image;
    }

    public void changeStatus(long id, int status) {
        final PersonBasicModel basicEmail = new PersonBasicModel();
        final SimpleMailMessage message = new SimpleMailMessage();

        try {
            final long personId = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE applications SET status = :status
                            WHERE id = :id AND removed = FALSE
                            RETURNING person_id
                            """,
                    Map.of("id", id, "status", status),
                    Long.class

            );
        } catch (EmptyResultDataAccessException e) {
            throw new PersonNotFoundException("Person with id" + id + " not found");
        }
    }

    public void setWinner(long personId, long eventId) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE applications SET winner = 1 WHERE person_id = :personId AND event_id = :eventId
                        """,
                Map.of("personId", personId,
                        "eventId", eventId)
        );
        if (affected == 0) {
            throw new ApplicationNotFoundException("Application with id" + personId + " NotFound");
        }
    }

    public MembersOfEventResponseDTO membersOfEvent(long eventId) {
        try {
                final List<MemberFullModel> memberLists = template.query(
                // language=PostgreSQL
                """
                        SELECT application_id, event_id, member, surname, name, patronymic,
                        EXTRACT(EPOCH FROM birthday) AS birthday, phone, email, winner
                        FROM members
                        WHERE event_id = :eventId
                        ORDER BY event_id
                        LIMIT 100
                        """,
                Map.of("eventId", eventId),
                memberFullRowMapper
        );

        final MembersOfEventResponseDTO responseDTO = new MembersOfEventResponseDTO(new ArrayList<>(memberLists.size()));
        for (MemberFullModel memberList : memberLists) {
            responseDTO.getMember().add(new MembersOfEventResponseDTO.Members(
                    memberList.getApplicationId(),
                    memberList.getEventId(),
                    memberList.getMember(),
                    memberList.getSurname(),
                    memberList.getName(),
                    memberList.getPatronymic(),
                    memberList.getBirthday(),
                    memberList.getPhone(),
                    memberList.getEmail(),
                    memberList.getWinner()
            ));
        }

        return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new MemberNotFoundException(e);
        }
    }
}
