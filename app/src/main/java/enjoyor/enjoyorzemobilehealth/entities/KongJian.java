package enjoyor.enjoyorzemobilehealth.entities;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/2.
 */

public class KongJian implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3351574565397926127L;
    private String DR;
    private String KongJianID;
    private String KongJianMC;
    private String JiChuXiangMuID;
    private String ZhiDanWei;

    private String KongJianLX;
    private String ChangDu;
    private String KuanDu;
    private String XZhou;
    private String YZhou;

    private String ShiFouXS;
    private String MoKuaiID;
    private String MoKuaiFLID;
    private String ShangXianZhi;
    private String XiaXianZhi;

    private String PaiLieSX;
    private String BingQuID;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDR() {
        return DR == null ? "" : DR;
    }

    public void setDR(String DR) {
        this.DR = DR;
    }

    public String getKongJianID() {
        return KongJianID == null ? "" : KongJianID;
    }

    public void setKongJianID(String kongJianID) {
        KongJianID = kongJianID;
    }

    public String getKongJianMC() {
        return KongJianMC == null ? "" : KongJianMC;
    }

    public void setKongJianMC(String kongJianMC) {
        KongJianMC = kongJianMC;
    }

    public String getJiChuXiangMuID() {
        return JiChuXiangMuID == null ? "" : JiChuXiangMuID;
    }

    public void setJiChuXiangMuID(String jiChuXiangMuID) {
        JiChuXiangMuID = jiChuXiangMuID;
    }

    public String getZhiDanWei() {
        return ZhiDanWei == null ? "" : ZhiDanWei;
    }

    public void setZhiDanWei(String zhiDanWei) {
        ZhiDanWei = zhiDanWei;
    }

    public String getKongJianLX() {
        return KongJianLX == null ? "" : KongJianLX;
    }

    public void setKongJianLX(String kongJianLX) {
        KongJianLX = kongJianLX;
    }

    public String getChangDu() {
        return ChangDu == null ? "" : ChangDu;
    }

    public void setChangDu(String changDu) {
        ChangDu = changDu;
    }

    public String getKuanDu() {
        return KuanDu == null ? "" : KuanDu;
    }

    public void setKuanDu(String kuanDu) {
        KuanDu = kuanDu;
    }

    public String getXZhou() {
        return XZhou == null ? "" : XZhou;
    }

    public void setXZhou(String XZhou) {
        this.XZhou = XZhou;
    }

    public String getYZhou() {
        return YZhou == null ? "" : YZhou;
    }

    public void setYZhou(String YZhou) {
        this.YZhou = YZhou;
    }

    public String getShiFouXS() {
        return ShiFouXS == null ? "" : ShiFouXS;
    }

    public void setShiFouXS(String shiFouXS) {
        ShiFouXS = shiFouXS;
    }

    public String getMoKuaiID() {
        return MoKuaiID == null ? "" : MoKuaiID;
    }

    public void setMoKuaiID(String moKuaiID) {
        MoKuaiID = moKuaiID;
    }

    public String getMoKuaiFLID() {
        return MoKuaiFLID == null ? "" : MoKuaiFLID;
    }

    public void setMoKuaiFLID(String moKuaiFLID) {
        MoKuaiFLID = moKuaiFLID;
    }

    public String getShangXianZhi() {
        return ShangXianZhi == null ? "" : ShangXianZhi;
    }

    public void setShangXianZhi(String shangXianZhi) {
        ShangXianZhi = shangXianZhi;
    }

    public String getXiaXianZhi() {
        return XiaXianZhi == null ? "" : XiaXianZhi;
    }

    public void setXiaXianZhi(String xiaXianZhi) {
        XiaXianZhi = xiaXianZhi;
    }

    public String getPaiLieSX() {
        return PaiLieSX == null ? "" : PaiLieSX;
    }

    public void setPaiLieSX(String paiLieSX) {
        PaiLieSX = paiLieSX;
    }

    public String getBingQuID() {
        return BingQuID == null ? "" : BingQuID;
    }

    public void setBingQuID(String bingQuID) {
        BingQuID = bingQuID;
    }

    //
//    public String getDR() {
//        return DR;
//    }
//
//    public void setDR(String dR) {
//        DR = dR;
//    }
//
//    public String getKongJianID() {
//        return KongJianID;
//    }
//
//    public void setKongJianID(String kongJianID) {
//        KongJianID = kongJianID;
//    }
//
//    public String getKongJianMC() {
//        return KongJianMC;
//    }
//
//    public void setKongJianMC(String kongJianMC) {
//        KongJianMC = kongJianMC;
//    }
//
//    public String getJiChuXiangMuID() {
//        return JiChuXiangMuID;
//    }
//
//    public void setJiChuXiangMuID(String jiChuXiangMuID) {
//        JiChuXiangMuID = jiChuXiangMuID;
//    }
//
//    public String getZhiDanWei() {
//        return ZhiDanWei;
//    }
//
//    public void setZhiDanWei(String zhiDanWei) {
//        ZhiDanWei = zhiDanWei;
//    }
//
//    public String getKongJianLX() {
//        return KongJianLX;
//    }
//
//    public void setKongJianLX(String kongJianLX) {
//        KongJianLX = kongJianLX;
//    }
//
//    public String getChangDu() {
//        return ChangDu;
//    }
//
//    public void setChangDu(String changDu) {
//        ChangDu = changDu;
//    }
//
//    public String getKuanDu() {
//        return KuanDu;
//    }
//
//    public void setKuanDu(String kuanDu) {
//        KuanDu = kuanDu;
//    }
//
//    public String getXZhou() {
//        return XZhou;
//    }
//
//    public void setXZhou(String xZhou) {
//        XZhou = xZhou;
//    }
//
//    public String getYZhou() {
//        return YZhou;
//    }
//
//    public void setYZhou(String yZhou) {
//        YZhou = yZhou;
//    }
//
//    public String getShiFouXS() {
//        return ShiFouXS;
//    }
//
//    public void setShiFouXS(String shiFouXS) {
//        ShiFouXS = shiFouXS;
//    }
//
//    public String getMoKuaiID() {
//        return MoKuaiID;
//    }
//
//    public void setMoKuaiID(String moKuaiID) {
//        MoKuaiID = moKuaiID;
//    }
//
//    public String getMoKuaiFLID() {
//        return MoKuaiFLID;
//    }
//
//    public void setMoKuaiFLID(String moKuaiFLID) {
//        MoKuaiFLID = moKuaiFLID;
//    }
//
//    public String getShangXianZhi() {
//        return ShangXianZhi;
//    }
//
//    public void setShangXianZhi(String shangXianZhi) {
//        ShangXianZhi = shangXianZhi;
//    }
//
//    public String getXiaXianZhi() {
//        return XiaXianZhi;
//    }
//
//    public void setXiaXianZhi(String xiaXianZhi) {
//        XiaXianZhi = xiaXianZhi;
//    }
//
//    public String getPaiLieSX() {
//        return PaiLieSX;
//    }
//
//    public void setPaiLieSX(String paiLieSX) {
//        PaiLieSX = paiLieSX;
//    }
//
//    public String getBingQuID() {
//        return BingQuID;
//    }
//
//    public void setBingQuID(String bingQuID) {
//        BingQuID = bingQuID;
//    }
}
