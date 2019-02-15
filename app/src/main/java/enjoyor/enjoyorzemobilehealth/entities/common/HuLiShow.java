package enjoyor.enjoyorzemobilehealth.entities.common;

import enjoyor.enjoyorzemobilehealth.rcy.zhedie.Item;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;

public class HuLiShow extends Item {
    private boolean isShow;
    private String chishu;
    private String TiaoMaID;
    private String name;
    private int paixu;
    private String  chuang;
    private String  brzyID;
    private String  yizhuZT;
    private String  yizhuMC;

    public String getYizhuMC() {
        return yizhuMC == null ? "" : yizhuMC;
    }

    public void setYizhuMC(String yizhuMC) {
        this.yizhuMC = yizhuMC;
    }

    public int getPaixu() {
        return paixu;
    }

    public void setPaixu(int paixu) {
        this.paixu = paixu;
    }

    public String getYizhuZT() {
        return yizhuZT == null ? "" : yizhuZT;
    }

    public void setYizhuZT(String yizhuZT) {
        this.yizhuZT = yizhuZT;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChuang() {
        return chuang == null ? "" : chuang;
    }

    public void setChuang(String chuang) {
        this.chuang = chuang;
    }

    public String getBrzyID() {
        return brzyID == null ? "" : brzyID;
    }

    public void setBrzyID(String brzyID) {
        this.brzyID = brzyID;
    }

    public String getChishu() {
        return chishu == null ? "" : chishu;
    }

    public void setChishu(String chishu) {
        this.chishu = chishu;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getTiaoMaID() {
        return TiaoMaID == null ? "" : TiaoMaID;
    }

    public void setTiaoMaID(String tiaoMaID) {
        TiaoMaID = tiaoMaID;
    }

    @Override
    public int getType() {
        return Constant.TYPE_GROUP;
    }
}
