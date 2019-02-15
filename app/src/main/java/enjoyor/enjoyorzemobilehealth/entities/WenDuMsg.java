package enjoyor.enjoyorzemobilehealth.entities;

public class WenDuMsg {
    private String time;//
    private String tiwen;//

    public WenDuMsg() {

    }

    public WenDuMsg(String time, String tiwen) {
        this.time = time;
        this.tiwen = tiwen;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTiwen() {
        return tiwen == null ? "" : tiwen;
    }

    public void setTiwen(String tiwen) {
        this.tiwen = tiwen;
    }
}
