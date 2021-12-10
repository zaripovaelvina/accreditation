package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.domain.ApplicationFullModel;
import project.domain.EventModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ApplicationFullRowMapper implements RowMapper<ApplicationFullModel> {

    @Override
    public ApplicationFullModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ApplicationFullModel(
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
                rs.getString("image"),
                rs.getBoolean("removed"),
                rs.getLong("created")
        );
    }
}
