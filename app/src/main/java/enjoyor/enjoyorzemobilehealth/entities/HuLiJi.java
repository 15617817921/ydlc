package enjoyor.enjoyorzemobilehealth.entities;

import java.io.Serializable;

public class HuLiJi implements Serializable {
    private String BINGRENZYID;
    private String YIZHUMXID;
    private String YIZHUMC;
    private String YAOPINID;
    private String XINGMING;
    private String CHUANGWEIHAO;
    private String XTSJ;

    public String getBINGRENZYID() {
        return BINGRENZYID == null ? "" : BINGRENZYID;
    }

    public void setBINGRENZYID(String BINGRENZYID) {
        this.BINGRENZYID = BINGRENZYID;
    }

    public String getYIZHUMXID() {
        return YIZHUMXID == null ? "" : YIZHUMXID;
    }

    public void setYIZHUMXID(String YIZHUMXID) {
        this.YIZHUMXID = YIZHUMXID;
    }

    public String getYIZHUMC() {
        return YIZHUMC == null ? "" : YIZHUMC;
    }

    public void setYIZHUMC(String YIZHUMC) {
        this.YIZHUMC = YIZHUMC;
    }

    public String getYAOPINID() {
        return YAOPINID == null ? "" : YAOPINID;
    }

    public void setYAOPINID(String YAOPINID) {
        this.YAOPINID = YAOPINID;
    }

    public String getXINGMING() {
        return XINGMING == null ? "" : XINGMING;
    }

    public void setXINGMING(String XINGMING) {
        this.XINGMING = XINGMING;
    }

    public String getCHUANGWEIHAO() {
        return CHUANGWEIHAO == null ? "" : CHUANGWEIHAO;
    }

    public void setCHUANGWEIHAO(String CHUANGWEIHAO) {
        this.CHUANGWEIHAO = CHUANGWEIHAO;
    }

    public String getXTSJ() {
        return XTSJ == null ? "" : XTSJ;
    }

    public void setXTSJ(String XTSJ) {
        this.XTSJ = XTSJ;
    }
}
