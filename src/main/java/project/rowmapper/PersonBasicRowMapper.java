package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.domain.PersonBasicModel;
import project.domain.PersonFullModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonBasicRowMapper implements RowMapper<PersonBasicModel> {

    @Override
    public PersonBasicModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PersonBasicModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getLong("citizenship_id"),
                rs.getLong("country_id"),
                rs.getString("gender"),
                rs.getString("image")
        );
    }
}
