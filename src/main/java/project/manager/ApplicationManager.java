package project.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import project.domain.ApplicationBasicModel;
import project.domain.ApplicationFullModel;
import project.dto.ApplicationGetAllResponseDTO;
import project.dto.ApplicationGetByIdResponseDTO;
import project.dto.ApplicationSaveRequestDTO;
import project.dto.ApplicationSaveResponseDTO;
import project.exception.ApplicationNotFoundException;
import project.rowmapper.ApplicationBasicRowMapper;
import project.rowmapper.ApplicationFullRowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApplicationManager {
    private final NamedParameterJdbcTemplate template;
    private final ApplicationBasicRowMapper ApplicationBasicRowMapper;
    private final ApplicationFullRowMapper ApplicationFullRowMapper;
    private final String defaultImage = "noimage.png";

    public ApplicationGetAllResponseDTO getAll() {
        final List<ApplicationBasicModel> applicationLists = template.query(
                // language=PostgreSQL
                """
                        SELECT id, person_id, event_id, disciplines_id, organization_id, weapon_id, weapon_manufacturer,
                               permit_serial, permit_num, permit_date, permit_manufacturer, image
                        FROM application
                        WHERE removed = FALSE
                        ORDER BY id
                        LIMIT 500
                        """,
                ApplicationBasicRowMapper
        );

        final ApplicationGetAllResponseDTO responseDTO = new ApplicationGetAllResponseDTO(new ArrayList<>(applicationLists.size()));
        for (ApplicationBasicModel applicationList : applicationLists) {
            responseDTO.getApplication().add(new ApplicationGetAllResponseDTO.Applications(
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
                            SELECT id, person_id, event_id, disciplines_id, organization_id, weapon_id, weapon_manufacturer,
                               permit_serial, permit_num, permit_date, permit_manufacturer, image, removed, created
                            FROM Application
                            WHERE id = :id AND removed = FALSE
                            """,
                    Map.of("id", id),
                    ApplicationFullRowMapper
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
                            applicationList.getImage(),
                            applicationList.getRemoved(),
                            applicationList.getCreated()
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
                        INSERT INTO application (id, person_id, event_id, disciplines_id, organization_id, weapon_id, weapon_manufacturer,
                               permit_serial, permit_num, permit_date, permit_manufacturer, image, removed, created) 
                        VALUES (:id, :person_id, :event_id, :disciplines_id, :organization_id, :weapon_id, :weapon_manufacturer,
                               :permit_serial, :permit_num, :permit_date, :permit_manufacturer, :image, :removed, :created)
                        RETURNING id, person_id, event_id, disciplines_id, organization_id, weapon_id, weapon_manufacturer,
                               permit_serial, permit_num, permit_date, permit_manufacturer, image, removed, created
                            """,
                Map.ofEntries(
                        Map.entry("id", requestDTO.getId()),
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
                        Map.entry("image", requestDTO.getImage()),
                        Map.entry("removed", requestDTO.getRemoved()),
                        Map.entry("created", requestDTO.getCreated())
                ),
                ApplicationFullRowMapper
        );

        final ApplicationSaveResponseDTO responseDTO = new ApplicationSaveResponseDTO(new ApplicationSaveResponseDTO.Applications(
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
                applicationList.getImage(),
                applicationList.getRemoved(),
                applicationList.getCreated()
        ));

        return responseDTO;
    }

    public void removeById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE application SET removed = TRUE WHERE id = :id
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
                        UPDATE Application SET removed = FALSE WHERE id = :id
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
                            UPDATE application SET person_id = :person_id, event_id = :event_id, disciplines_id = :disciplines_id, 
                            organization_id = :organization_id, weapon_id = :weapon_id, weapon_manufacturer = :weapon_manufacturer, permit_serial = :permit_serial,
                            permit_num = :permit_num, permit_date = :permit_date, permit_manufacturer =:permit_manufacturer, image = :image
                            WHERE id = :id AND removed = FALSE
                            RETURNING person_id, event_id, disciplines_id, organization_id, weapon_id, weapon_manufacturer, 
                            permit_serial, permit_num, permit_date, permit_manufacturer, image
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
                    ApplicationFullRowMapper
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
                            applicationList.getImage(),
                            applicationList.getRemoved(),
                            applicationList.getCreated()
                    ));

            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ApplicationNotFoundException(e);
        }
    }

    private String getImage(String image) {
        return image == null ? defaultImage : image;
    }

}