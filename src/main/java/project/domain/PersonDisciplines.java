package project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonDisciplines {
    private long id;
    private long personId;
    private long eventId;
    private long disciplinesId;
    private long organizationId;
}
