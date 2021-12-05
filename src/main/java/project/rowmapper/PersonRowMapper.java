package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.domain.PersonModel;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonRowMapper<T> implements RowMapper<PersonModel> {

    @Override
    public PersonModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PersonModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("patronymic"),
                rs.getLong("birthday"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getLong("citizenshipId"),
                rs.getLong("countryId"),
                rs.getString("gender"),
                rs.getBoolean("removed"),
                rs.getLong("created")
        );
    }
}
