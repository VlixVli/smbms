package cloud.vlix.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private int id;
    private String roleCode;
    private String roleName;
    private int createdBy;
    private String creationDate;
    private int modifyBy;
    private String modifyDate;
}
