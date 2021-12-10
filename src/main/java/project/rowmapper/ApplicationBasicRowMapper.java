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
                rs.getLong("personId"),
                rs.getLong("eventId"),
                rs.getLong("disciplinesId"),
                rs.getLong("organizationId"),
                rs.getLong("weaponId"),
                rs.getString("weaponManufacturer"),
                rs.getString("permitSerial"),
                rs.getString("permitNum"),
                rs.getLong("permitDate"),
                rs.getString("permitManufacturer"),
                rs.getString("image")
        );
    }
}
