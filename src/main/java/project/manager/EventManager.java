package project.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import project.domain.EventModel;
import project.dto.*;
import project.exception.EventNotFoundException;
import project.rowmapper.EventBasicRowMapper;
import project.rowmapper.EventFullRowMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EventManager {
    private final NamedParameterJdbcTemplate template;
    private final EventBasicRowMapper eventBasicRowMapper;
    private final EventFullRowMapper eventFullRowMapper;
    private final String defaultImage = "noimage.png";

    public EventGetAllResponseDTO getAll() {
        final List<EventModel> eventLists = template.query(
                // language=PostgreSQL
                """
                        SELECT id, name, image FROM events
                        WHERE completed = FALSE
                        ORDER BY id
                        LIMIT 500
                        """,
                eventBasicRowMapper
        );

        final EventGetAllResponseDTO responseDTO = new EventGetAllResponseDTO(new ArrayList<>(eventLists.size()));
        for (EventModel eventList : eventLists) {
            responseDTO.getEvents().add(new EventGetAllResponseDTO.Events(
                    eventList.getId(),
                    eventList.getName(),
                    eventList.getImage()
            ));
        }
        return responseDTO;
    }

    public EventGetByIdResponseDTO getById(long id) {
        try {
            final EventModel eventOne = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, name, image FROM events
                            WHERE id = :id AND completed = FALSE
                            """,
                    Map.of("id", id),
                    eventFullRowMapper
            );

            final EventGetByIdResponseDTO responseDTO = new EventGetByIdResponseDTO(
                    new EventGetByIdResponseDTO.Events(
                            eventOne.getId(),
                            eventOne.getName(),
                            eventOne.getImage()
                    ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new EventNotFoundException(e);
        }
    }

    public EventSaveResponseDTO save(EventSaveRequestDTO requestDTO) {
        if (requestDTO.getId() == 0) {
            return create(requestDTO);
        }
        return update(requestDTO);
    }

    private EventSaveResponseDTO create(EventSaveRequestDTO requestDTO) {
        final EventModel eventOne = template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO events(name, image) VALUES (:name, :image)
                        RETURNING name, image
                        """,
                Map.of(
                        "name", requestDTO.getName(),
                        "image", getImage(requestDTO.getImage())
                ),
                eventFullRowMapper
        );

        final EventSaveResponseDTO responseDTO = new EventSaveResponseDTO(new EventSaveResponseDTO.Event(
                eventOne.getId(),
                eventOne.getName(),
                eventOne.getImage(),
                eventOne.isCompleted()
        ));

        return responseDTO;
    }

    public void removeById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE events SET completed = TRUE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new EventNotFoundException("event with id" + id + " not found");
        }
    }

    public void restoreById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE events SET completed = FALSE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new EventNotFoundException("event with id" + id + " restored");
        }
    }

    private EventSaveResponseDTO update(EventSaveRequestDTO requestDTO) {
        try {
            final EventModel eventOne = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE events SET name = :name, image = :image
                            WHERE id = :id AND completed = FALSE
                            RETURNING name, image
                            """,
                    Map.of(
                            "name", requestDTO.getName(),
                            "image", getImage(requestDTO.getImage())
                    ),
                    eventFullRowMapper
            );

            final EventSaveResponseDTO responseDTO = new EventSaveResponseDTO(new EventSaveResponseDTO.Event(
                    eventOne.getId(),
                    eventOne.getName(),
                    eventOne.getImage(),
                    eventOne.isCompleted()
            ));

            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new EventNotFoundException(e);
        }
    }

    private String getImage(String image) {
        return image == null ? defaultImage : image;
    }
}