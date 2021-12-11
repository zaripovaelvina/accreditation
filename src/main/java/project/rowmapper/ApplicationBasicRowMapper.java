package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.domain.ApplicationBasicModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ApplicationBasicRowMapper implements RowMapper<ApplicationBasicModel> {

    @Override
    public ApplicationBasicModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ApplicationBasicModel(
                rs.getLong("id"),
                rs.getLong("person_id"),
                rs.getLong("event_id"),
                rs.getLong("disciplines_id"),
                rs.getLong("organization_id"),
                rs.getLong("weapon_id"),
                rs.getString("weapon_manufacturer"),
                rs.getString("permit_serial"),
                rs.getString("permit_num"),
                rs.getLong("permit_date"),
                rs.getString("permit_manufacturer"),
                rs.getString("image")
        );
    }
}
