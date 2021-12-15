package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.domain.DisciplineModel;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DisciplineRowMapper implements RowMapper<DisciplineModel> {

    @Override
    public DisciplineModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DisciplineModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("status")
        );
    }
}
