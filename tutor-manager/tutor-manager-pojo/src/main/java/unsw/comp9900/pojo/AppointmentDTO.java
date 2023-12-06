package unsw.comp9900.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class AppointmentDTO {
    private  Integer aid;
    private Integer csid;
    private String cname;
    private Integer tid;
    private String location;
    private String tFName;
    private String tMName;
    private String tLName;
    private Integer sid;
    private String sFName;
    private String sMName;
    private String sLName;
    private Date startTime;
    private Date finishTime;
    private Boolean approve;
    private Boolean isDelete;

}
