package project.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import project.domain.DisciplineModel;
import project.dto.*;
import project.exception.DisciplineNotFoundException;
import project.rowmapper.DisciplineRowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DisciplineManager {
    private final NamedParameterJdbcTemplate template;
    private final DisciplineRowMapper disciplineRowMapper;
    private final String defaultImage = "noimage.png";

    public DisciplineGetAllResponseDTO getAll() {
        final List<DisciplineModel> disciplineLists = template.query(
                // language=PostgreSQL
                """
                        SELECT id, name, status FROM disciplines
                        ORDER BY id
                        LIMIT 500
                        """,
                disciplineRowMapper
        );

        final DisciplineGetAllResponseDTO responseDTO = new DisciplineGetAllResponseDTO(new ArrayList<>(disciplineLists.size()));
        for (DisciplineModel disciplineList : disciplineLists) {
            responseDTO.getDisciplines().add(new DisciplineGetAllResponseDTO.Disciplines(
                    disciplineList.getId(),
                    disciplineList.getName(),
                    disciplineList.getStatus()
            ));
        }
        return responseDTO;
    }

    public DisciplineGetByIdResponseDTO getById(long id) {
        try {
            final DisciplineModel disciplineOne = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, name, status FROM disciplines
                            WHERE id = :id 
                            """,
                    Map.of("id", id),
                    disciplineRowMapper
            );

            final DisciplineGetByIdResponseDTO responseDTO = new DisciplineGetByIdResponseDTO(
                    new DisciplineGetByIdResponseDTO.Disciplines(
                            disciplineOne.getId(),
                            disciplineOne.getName(),
                            disciplineOne.getStatus()
                    ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new DisciplineNotFoundException(e);
        }
    }

    public DisciplineSaveResponseDTO save(DisciplineSaveRequestDTO requestDTO) {
        if (requestDTO.getId() == 0) {
            return create(requestDTO);
        }
        return update(requestDTO);
    }

    private DisciplineSaveResponseDTO create(DisciplineSaveRequestDTO requestDTO) {
        final DisciplineModel disciplineOne = template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO disciplines (name, status) VALUES (:name, :status)
                        RETURNING id, name, status
                        """,
                Map.of(
                        "name", requestDTO.getName(),
                        "status", requestDTO.getStatus()
                ),
                disciplineRowMapper
        );

        final DisciplineSaveResponseDTO responseDTO = new DisciplineSaveResponseDTO(new DisciplineSaveResponseDTO.Disciplines(
                disciplineOne.getId(),
                disciplineOne.getName(),
                disciplineOne.getStatus()
        ));

        return responseDTO;
    }

    public void removeById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE disciplines SET deleted = TRUE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new DisciplineNotFoundException("discipline with id" + id + " not found");
        }
    }

    public void restoreById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE disciplines SET deleted = FALSE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new DisciplineNotFoundException("discipline with id" + id + " restored");
        }
    }

    private DisciplineSaveResponseDTO update(DisciplineSaveRequestDTO requestDTO) {
        try {
            final DisciplineModel disciplineOne = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE disciplines SET name = :name, status = :status
                            WHERE id = :id AND deleted = FALSE
                            RETURNING id, name, status
                            """,
                    Map.of(
                            "name", requestDTO.getName(),
                            "status", requestDTO.getStatus()
                    ),
                    disciplineRowMapper
            );

            final DisciplineSaveResponseDTO responseDTO = new DisciplineSaveResponseDTO(new DisciplineSaveResponseDTO.Disciplines(
                    disciplineOne.getId(),
                    disciplineOne.getName(),
                    disciplineOne.getStatus()
            ));

            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new DisciplineNotFoundException(e);
        }
    }

    private String getImage(String image) {
        return image == null ? defaultImage :image;
    }
}