package cloud.vlix.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    private int id;
    private String billCode;
    private String productName;
    private String productDesc;
    private String productUnit;
    private String productCount;
    private String totalPrice;
    private int isPayment;
    private int createdBy;
    private Date creationDate;
    private int modifyBy;
    private Date modifyDate;
    private int providerId;
    private String providerName;
}
