package cloud.vlix.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private int id;
    private String contact;
    private String addressDesc;
    private String postCode;
    private String tel;
    private int createdBy;
    private String creationDate;
    private int modifyBy;
    private String modifyDate;
    private int userId;
}
