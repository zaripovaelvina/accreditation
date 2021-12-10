package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.domain.EventModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EventBasicRowMapper implements RowMapper<EventModel> {

    @Override
    public EventModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EventModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image"),
                rs.getBoolean("completed")
        );
    }
}
