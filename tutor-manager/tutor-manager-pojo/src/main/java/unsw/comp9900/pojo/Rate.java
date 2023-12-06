package unsw.comp9900.pojo;

import lombok.Data;

@Data
public class Rate {
    private Integer aid;
    private Integer rate;
    private String feedback;
    private Boolean isDelete;
}