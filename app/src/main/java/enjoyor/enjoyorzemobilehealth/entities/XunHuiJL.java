package enjoyor.enjoyorzemobilehealth.entities;

import enjoyor.enjoyorzemobilehealth.entities.common.HuLiShow;
import enjoyor.enjoyorzemobilehealth.rcy.zhedie.Item;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;

/**
 * Created by Administrator on 2017/7/18.
 */

public class XunHuiJL extends Item {
//         <DR Name="8224237" Num="10">
//          <DC Name="ID" Num="0">4585</DC>
//          <DC Name="XunHuiSJ" Num="0">2019/1/24 15:54:51</DC>
//          <DC Name="XunHuiRen" Num="0">信息科</DC>
//          <DC Name="XunHuiCZ" Num="0">二级护理</DC>
//          <DC Name="BingRenZYID" Num="0">376608</DC>
//          <DC Name="XingMing" Num="0">张恩英</DC>
//          <DC Name="HuLiCZ" Num="0" />
//          <DC Name="BingQuDM" Num="0">216</DC>
//          <DC Name="XunHuiRenID" Num="0">10000</DC>
//          <DC Name="CaoZuoLX" Num="0">0</DC>
//        </DR>
    private String ID;


    private String XunHuiSJ;
    private String XunHuiRen;
    private String XunHuiCZ;
    private String BingRenZYID;
    private String XingMing;
    private String HuLiCZ;
    private String BingQuDM;
    private  String XunHuiRenID;
    private  String CaoZuoLX;

    public String getID() {
        return ID == null ? "" : ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCaoZuoLX() {
        return CaoZuoLX == null ? "" : CaoZuoLX;
    }

    public void setCaoZuoLX(String caoZuoLX) {
        CaoZuoLX = caoZuoLX;
    }

    public String getXunHuiSJ() {
        return XunHuiSJ == null ? "" : XunHuiSJ;
    }

    public void setXunHuiSJ(String xunHuiSJ) {
        XunHuiSJ = xunHuiSJ;
    }

    public String getXunHuiRen() {
        return XunHuiRen == null ? "" : XunHuiRen;
    }

    public void setXunHuiRen(String xunHuiRen) {
        XunHuiRen = xunHuiRen;
    }

    public String getXunHuiCZ() {
        return XunHuiCZ == null ? "" : XunHuiCZ;
    }

    public void setXunHuiCZ(String xunHuiCZ) {
        XunHuiCZ = xunHuiCZ;
    }

    public String getBingRenZYID() {
        return BingRenZYID == null ? "" : BingRenZYID;
    }

    public void setBingRenZYID(String bingRenZYID) {
        BingRenZYID = bingRenZYID;
    }

    public String getXingMing() {
        return XingMing == null ? "" : XingMing;
    }

    public void setXingMing(String xingMing) {
        XingMing = xingMing;
    }

    public String getHuLiCZ() {
        return HuLiCZ == null ? "" : HuLiCZ;
    }

    public void setHuLiCZ(String huLiCZ) {
        HuLiCZ = huLiCZ;
    }

    public String getBingQuDM() {
        return BingQuDM == null ? "" : BingQuDM;
    }

    public void setBingQuDM(String bingQuDM) {
        BingQuDM = bingQuDM;
    }

    public String getXunHuiRenID() {
        return XunHuiRenID == null ? "" : XunHuiRenID;
    }

    public void setXunHuiRenID(String xunHuiRenID) {
        XunHuiRenID = xunHuiRenID;
    }

    //护理查询  折叠
    public HuLiShow huLiShow;
    @Override
    public int getType() {
        return Constant.TYPE_CHILD;
    }
}
