package unsw.comp9900.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class CourseDTO {
    private Integer csid;
    private Integer cid;
    private String cname;
    private Integer tid;
    private String tFName;
    private String tMName;
    private String tLName;
    private Date startTime;
    private Date finishTime;
    private String location;
    private Boolean isSelect;
    private Boolean isDelete;
}
