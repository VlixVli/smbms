package cloud.vlix.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String userCode;
    private String userName;
    private String userPassword;
    private int gender;
    private Date birthday;
    private String phone;
    private String address;
    private int userRole;
    private int createdBy;
    private String creationDate;
    private int modifyBy;
    private String modifyDate;
    private String userRoleName;
    private int age;

    public void setAge() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(birthday);
        int birthdayYear = calendar.get(Calendar.YEAR);
        int birthdayMonth = calendar.get(Calendar.MONTH);
        int birthdayDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (nowMonth > birthdayMonth) {
            age = nowYear - birthdayYear;
        } else if (nowMonth == birthdayMonth && nowDay > birthdayDay) {
            age = nowYear - birthdayYear;
        } else {
            age = nowYear - birthdayYear - 1;
        }
    }
}
