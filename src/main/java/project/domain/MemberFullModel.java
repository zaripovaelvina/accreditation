package project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberFullModel {
    private long applicationId;
    private long eventId;
    private long member;
    private String surname;
    private String name;
    private String patronymic;
    private long birthday;
    private String phone;
    private String email;
    private Integer winner;
    private Integer status;
}

