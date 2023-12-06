package unsw.comp9900.pojo;

import java.util.Date;

public class Message {
    private Integer mid;

    private Integer tid;

    private Integer sid;

    private Date time;

    private Boolean tToS;

    private Boolean isDelete;

    private String message;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Boolean gettToS() {
        return tToS;
    }

    public void settToS(Boolean tToS) {
        this.tToS = tToS;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }
}