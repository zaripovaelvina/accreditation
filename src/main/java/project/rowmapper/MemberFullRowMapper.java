package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.domain.ApplicationFullModel;
import project.domain.MemberFullModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MemberFullRowMapper implements RowMapper<MemberFullModel> {

    @Override
    public MemberFullModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MemberFullModel(
                rs.getLong("application_id"),
                rs.getLong("event_id"),
                rs.getLong("member"),
                rs.getString("surname"),
                rs.getString("name"),
                rs.getString("patronymic"),
                rs.getLong("birthday"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getInt("winner")
        );
    }
}
