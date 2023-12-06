package unsw.comp9900.pojo;

import lombok.Data;

@Data
public class RateDTO {
    private Integer tid;
    private String tFName;
    private String tMName;
    private String tLName;
    private Integer csid;
    private String cname;
    private Integer sid;
    private String sFName;
    private String sMName;
    private String sLName;
    private Integer aid;
    private Integer rate;
    private String feedback;
    private Boolean isDelete;
}
