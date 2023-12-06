package unsw.comp9900.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {
    private Integer mid;
    private Integer tid;
    private Integer sid;
    private Date time;
    @JsonProperty("tToS")
    private Boolean tToS;
    private Boolean isDelete;
    private String message;
    private String StudentFirstName;
    private String StudentMiddleName;
    private String StudentLastName;
    private String TutorFirstName;
    private String TutorMiddleName;
    private String TutorLastName;
}
