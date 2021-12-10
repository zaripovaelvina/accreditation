package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.domain.WeaponModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WeaponFullRowMapper implements RowMapper<WeaponModel> {

    @Override
    public WeaponModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WeaponModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image"),
                rs.getBoolean("removed")
        );
    }
}
