package unsw.comp9900.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Verify {
    private Integer vid;

    private String email;

    private Integer code;

    private Date time;
}