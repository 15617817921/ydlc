package enjoyor.enjoyorzemobilehealth.entities;

import enjoyor.enjoyorzemobilehealth.entities.common.HuLiShow;
import enjoyor.enjoyorzemobilehealth.rcy.zhedie.Item;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;

/**
 * Created by Administrator on 2017/8/28.
 */

public class ZhiXingJL extends Item {
    private String YiZhuZXMXID;
    private String YiZhuZXZT;
    private String ZhiXingRen;
    private String ZhiXingSJ;
    private String TiaoMaID;
    private String BingRenZYID;
    private String YongHuID;
    private String BeiZhu;
    private String cishu;
    private String SZD;

    public String getSZD() {
        return SZD == null ? "" : SZD;
    }

    public void setSZD(String SZD) {
        this.SZD = SZD;
    }

    public String getCishu() {
        return cishu == null ? "" : cishu;
    }

    public void setCishu(String cishu) {
        this.cishu = cishu;
    }

    public String getYiZhuZXMXID() {
        return YiZhuZXMXID;
    }

    public void setYiZhuZXMXID(String yiZhuZXMXID) {
        YiZhuZXMXID = yiZhuZXMXID;
    }


    public String getYiZhuZXZT() {
        return YiZhuZXZT == null ? "" : YiZhuZXZT;
    }

    public void setYiZhuZXZT(String yiZhuZXZT) {
        YiZhuZXZT = yiZhuZXZT;
    }

    public String getZhiXingRen() {
        return ZhiXingRen;
    }

    public void setZhiXingRen(String zhiXingRen) {
        ZhiXingRen = zhiXingRen;
    }

    public String getZhiXingSJ() {
        return ZhiXingSJ;
    }

    public void setZhiXingSJ(String zhiXingSJ) {
        ZhiXingSJ = zhiXingSJ;
    }

    public String getTiaoMaID() {
        return TiaoMaID;
    }

    public void setTiaoMaID(String tiaoMaID) {
        TiaoMaID = tiaoMaID;
    }

    public String getBingRenZYID() {
        return BingRenZYID;
    }

    public void setBingRenZYID(String bingRenZYID) {
        BingRenZYID = bingRenZYID;
    }

    public String getYongHuID() {
        return YongHuID;
    }

    public void setYongHuID(String yongHuID) {
        YongHuID = yongHuID;
    }

    public String getBeiZhu() {
        return BeiZhu;
    }

    public void setBeiZhu(String beiZhu) {
        BeiZhu = beiZhu;
    }


    //护理查询  折叠
    public HuLiShow huLiShow;
    @Override
    public int getType() {
        return Constant.TYPE_CHILD;
    }
}
