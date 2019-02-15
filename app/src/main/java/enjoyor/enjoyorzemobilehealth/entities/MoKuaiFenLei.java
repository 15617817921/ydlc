package enjoyor.enjoyorzemobilehealth.entities;

/**
 * Created by Administrator on 2017/3/2.
 */

public class MoKuaiFenLei {
    private String DR;
    private String MoKuaiFLID;
    private String MoKuaiFLMC;
    private String ShangJiMKFLID;
    private String MoKuaiID;


    public String getDR() {
        return DR == null ? "" : DR;
    }

    public void setDR(String DR) {
        this.DR = DR;
    }

    public String getMoKuaiFLID() {
        return MoKuaiFLID == null ? "" : MoKuaiFLID;
    }

    public void setMoKuaiFLID(String moKuaiFLID) {
        MoKuaiFLID = moKuaiFLID;
    }

    public String getMoKuaiFLMC() {
        return MoKuaiFLMC == null ? "" : MoKuaiFLMC;
    }

    public void setMoKuaiFLMC(String moKuaiFLMC) {
        MoKuaiFLMC = moKuaiFLMC;
    }

    public String getShangJiMKFLID() {
        return ShangJiMKFLID == null ? "" : ShangJiMKFLID;
    }

    public void setShangJiMKFLID(String shangJiMKFLID) {
        ShangJiMKFLID = shangJiMKFLID;
    }

    public String getMoKuaiID() {
        return MoKuaiID == null ? "" : MoKuaiID;
    }

    public void setMoKuaiID(String moKuaiID) {
        MoKuaiID = moKuaiID;
    }
}
