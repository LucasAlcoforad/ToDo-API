package github.com.LucasAlcoforad.TO_DO.dto.user;

import java.sql.Timestamp;

public record ResponseUserDTO(String email, String username, Timestamp createTimeStamp, Timestamp updateTimeStamp){
}
