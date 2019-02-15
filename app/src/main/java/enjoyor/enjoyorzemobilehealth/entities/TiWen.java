package enjoyor.enjoyorzemobilehealth.entities;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11.
 */

public class TiWen implements Serializable{
    private String CaiJiRQ;
    private String CaiJiSJ;
    private String TiWen;
    private String LeiXing;//类型1-温度 2-脉搏

    private String wendu;
    private String maibo;

    public String getWendu() {
        return wendu == null ? "" : wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getMaibo() {
        return maibo == null ? "" : maibo;
    }

    public void setMaibo(String maibo) {
        this.maibo = maibo;
    }

    public TiWen( ) {
    }






    public String getLeiXing() {
        return LeiXing == null ? "" : LeiXing;
    }

    public void setLeiXing(String leiXing) {
        LeiXing = leiXing;
    }

    public String getCaiJiRQ() {
        return CaiJiRQ;
    }

    public void setCaiJiRQ(String caiJiRQ) {
        CaiJiRQ = caiJiRQ;
    }

    public String getCaiJiSJ() {
        return CaiJiSJ;
    }

    public void setCaiJiSJ(String caiJiSJ) {
        CaiJiSJ = caiJiSJ;
    }

    public String getTiWen() {
        return TiWen;
    }

    public void setTiWen(String tiWen) {
        TiWen = tiWen;
    }
}
