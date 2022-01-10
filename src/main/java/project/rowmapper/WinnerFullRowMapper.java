package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.domain.WinnerFullModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WinnerFullRowMapper implements RowMapper<WinnerFullModel> {

    @Override
    public WinnerFullModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WinnerFullModel(
                rs.getLong("application_id"),
                rs.getLong("event_id"),
                rs.getLong("member"),
                rs.getString("surname"),
                rs.getString("name"),
                rs.getString("patronymic"),
                rs.getLong("birthday"),
                rs.getString("phone"),
                rs.getString("email")
        );
    }
}
