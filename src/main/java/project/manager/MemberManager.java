package project.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import project.domain.MemberFullModel;
import project.domain.WinnerFullModel;
import project.dto.*;
import project.exception.MemberNotFoundException;
import project.rowmapper.MemberFullRowMapper;
import project.rowmapper.WinnerFullRowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MemberManager {
    private final NamedParameterJdbcTemplate template;
    private final MemberFullRowMapper memberFullRowMapper;
    private final WinnerFullRowMapper winnerFullRowMapper;
    private final String defaultImage = "noimage.png";

    public MembersOfEventResponseDTO membersOfEvent(long eventId) {
       try {
            final List<MemberFullModel> memberLists = template.query(
                    // language=PostgreSQL
                    """
                            SELECT application_id, event_id, member, surname, name, patronymic,
                            EXTRACT(EPOCH FROM birthday) AS birthday, phone, email, winner, status
                            FROM members
                            WHERE status = 1 AND event_id = :eventId
                            ORDER BY event_id
                            LIMIT 100
                            """,
                    Map.of("eventId", eventId),
                    memberFullRowMapper
            );

            final MembersOfEventResponseDTO responseDTO = new MembersOfEventResponseDTO(
                    new ArrayList<>(memberLists.size()));
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
                        memberList.getWinner(),
                        memberList.getStatus()
                ));
            }

            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new MemberNotFoundException(e);
        }
    }

    public WinnersOfEventResponseDTO getWinners(long eventId, int winner) {
        try {
            final List<WinnerFullModel> winnerLists = template.query(
                    // language=PostgreSQL
                    """
                            SELECT application_id, event_id, member, surname, name, patronymic,
                            EXTRACT(EPOCH FROM birthday) AS birthday, phone, email, winner
                            FROM members
                            WHERE event_id = :eventId AND winner = :winner
                            ORDER BY event_id
                            LIMIT 100
                            """,
                    Map.of("eventId", eventId,
                            "winner", winner),
                    winnerFullRowMapper
            );

            final WinnersOfEventResponseDTO responseDTO = new WinnersOfEventResponseDTO(
                    new ArrayList<>(winnerLists.size()));
            for (WinnerFullModel winnerList : winnerLists) {
                responseDTO.getWinners().add(new WinnersOfEventResponseDTO.Winners(
                        winnerList.getApplicationId(),
                        winnerList.getEventId(),
                        winnerList.getMember(),
                        winnerList.getSurname(),
                        winnerList.getName(),
                        winnerList.getPatronymic(),
                        winnerList.getBirthday(),
                        winnerList.getPhone(),
                        winnerList.getEmail()
                ));
            }
            return responseDTO;

        } catch (EmptyResultDataAccessException e) {
            throw new MemberNotFoundException(e);
        }
    }

    public MemberCountResponseDTO memberCount(long eventId) {
        try {
            final long member = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT count(member) from members WHERE status = 1 AND event_id = :eventId
                            """,
                    Map.of("eventId", eventId),
                    Long.class
            );

            return new MemberCountResponseDTO(member);

        } catch (EmptyResultDataAccessException e) {
            throw new MemberNotFoundException(e);
        }
    }
}
