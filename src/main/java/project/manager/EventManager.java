package project.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventManager {
    private final NamedParameterJdbcTemplate template;

    /* public EventGetByIdResponseDTO getById(long id) {
        try {
            final EventFullModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, name FROM events
                            WHERE id = :id AND status = FALSE
                            """,
                    Map.of("id", id),
                    productFullRowMapper
            );

            final EventGetByIdResponseDTO responseDTO = new EventGetByIdResponseDTO(new EventGetByIdResponseDTO.Events(
                    item.getId(),
                    item.getName(),
                    item.getStatus(),
                    ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new EventNotFoundException(e);
        } */

    }
