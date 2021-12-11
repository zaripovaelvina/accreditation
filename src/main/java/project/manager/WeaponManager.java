package project.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import project.domain.WeaponModel;
import project.dto.*;
import project.exception.WeaponNotFoundException;
import project.rowmapper.WeaponBasicRowMapper;
import project.rowmapper.WeaponFullRowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WeaponManager {
    private final NamedParameterJdbcTemplate template;
    private final WeaponBasicRowMapper weaponBasicRowMapper;
    private final WeaponFullRowMapper weaponFullRowMapper;
    private final String defaultImage = "noimage.png";

    public WeaponGetAllResponseDTO getAll() {
        final List<WeaponModel> weaponLists = template.query(
                // language=PostgreSQL
                """
                        SELECT id, name, image FROM weapon
                        WHERE removed = FALSE
                        ORDER BY id
                        LIMIT 500
                        """,
                weaponBasicRowMapper
        );

        final WeaponGetAllResponseDTO responseDTO = new WeaponGetAllResponseDTO(new ArrayList<>(weaponLists.size()));
        for (WeaponModel weaponList : weaponLists) {
            responseDTO.getWeapon().add(new WeaponGetAllResponseDTO.Weapons(
                    weaponList.getId(),
                    weaponList.getName(),
                    weaponList.getImage()
            ));
        }
        return responseDTO;
    }

    public WeaponGetByIdResponseDTO getById(long id) {
        try {
            final WeaponModel weaponOne = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, name, image FROM weapon
                            WHERE id = :id AND removed = FALSE
                            """,
                    Map.of("id", id),
                    weaponFullRowMapper
            );

            final WeaponGetByIdResponseDTO responseDTO = new WeaponGetByIdResponseDTO(
                    new WeaponGetByIdResponseDTO.Weapons(
                            weaponOne.getId(),
                            weaponOne.getName(),
                            weaponOne.getImage()
                            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new WeaponNotFoundException(e);
        }
    }

    public WeaponSaveResponseDTO save(WeaponSaveRequestDTO requestDTO) {
        if (requestDTO.getId() == 0) {
            return create(requestDTO);
        }
        return update(requestDTO);
    }

    private WeaponSaveResponseDTO create(WeaponSaveRequestDTO requestDTO) {
        final WeaponModel weaponOne = template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO weapon(name, image) VALUES (:name, :image)
                        RETURNING id, name, image
                        """,
                Map.of(
                        "name", requestDTO.getName(),
                        "image", getImage(requestDTO.getImage())
                ),
                weaponFullRowMapper
        );

        final WeaponSaveResponseDTO responseDTO = new WeaponSaveResponseDTO(new WeaponSaveResponseDTO.Weapons(
                weaponOne.getId(),
                weaponOne.getName(),
                weaponOne.getImage()

        ));

        return responseDTO;
    }

    public void removeById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE weapon SET removed = TRUE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new WeaponNotFoundException("weapon with id" + id + " not found");
        }
    }

    public void restoreById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE weapon SET removed = FALSE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new WeaponNotFoundException("weapon with id" + id + " restored");
        }
    }

    private WeaponSaveResponseDTO update(WeaponSaveRequestDTO requestDTO) {
        try {
            final WeaponModel weaponOne = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE weapon SET name = :name, image = :image
                            WHERE id = :id AND removed = FALSE
                            RETURNING id, name, image
                            """,
                    Map.of(
                            "name", requestDTO.getName(),
                            "image", getImage(requestDTO.getImage())
                    ),
                    weaponFullRowMapper
            );

            final WeaponSaveResponseDTO responseDTO = new WeaponSaveResponseDTO(new WeaponSaveResponseDTO.Weapons(
                    weaponOne.getId(),
                    weaponOne.getName(),
                    weaponOne.getImage()
            ));

            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new WeaponNotFoundException(e);
        }
    }

    private String getImage(String image) {
        return image == null ? defaultImage : image;
    }
}