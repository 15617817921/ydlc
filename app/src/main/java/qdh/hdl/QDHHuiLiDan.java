package qdh.hdl;

/**
 * Created by dantevsyou on 2017/11/29.
 */

public class QDHHuiLiDan {


    /**
     * root : {"caijirq":"2014-10-15","caijisj":"16:30","tiwenlb":"耳温","maibo":"80","huxi":"18","shuzhangya":"123","shousuoya":"78","guandaohuli":{},"ruliang":{},"chuliang":{},"teshuqkycl":" 患者精神略软，卧床休息，偶有恶心，无呕吐，纳差，嘱保持情绪稳定。 ","qianming":"郑勇红"}
     */

    private RootBean root;

    public RootBean getRoot() {
        return root;
    }

    public void setRoot(RootBean root) {
        this.root = root;
    }

    public static class RootBean {
        /**
         * caijirq : 2014-10-15
         * caijisj : 16:30
         * tiwenlb : 耳温
         * maibo : 80
         * huxi : 18
         * shuzhangya : 123
         * shousuoya : 78
         * guandaohuli : {}
         * ruliang : {}
         * chuliang : {}
         * teshuqkycl :  患者精神略软，卧床休息，偶有恶心，无呕吐，纳差，嘱保持情绪稳定。
         * qianming : 郑勇红
         */

        private String caijirq;
        private String caijisj;
        private String tiwenlb;
        private String maibo;
        private String huxi;
        private String shuzhangya;
        private String shousuoya;
        private GuandaohuliBean guandaohuli;
        private RuliangBean ruliang;
        private ChuliangBean chuliang;
        private String teshuqkycl;
        private String qianming;

        public String getCaijirq() {
            return caijirq;
        }

        public void setCaijirq(String caijirq) {
            this.caijirq = caijirq;
        }

        public String getCaijisj() {
            return caijisj;
        }

        public void setCaijisj(String caijisj) {
            this.caijisj = caijisj;
        }

        public String getTiwenlb() {
            return tiwenlb;
        }

        public void setTiwenlb(String tiwenlb) {
            this.tiwenlb = tiwenlb;
        }

        public String getMaibo() {
            return maibo;
        }

        public void setMaibo(String maibo) {
            this.maibo = maibo;
        }

        public String getHuxi() {
            return huxi;
        }

        public void setHuxi(String huxi) {
            this.huxi = huxi;
        }

        public String getShuzhangya() {
            return shuzhangya;
        }

        public void setShuzhangya(String shuzhangya) {
            this.shuzhangya = shuzhangya;
        }

        public String getShousuoya() {
            return shousuoya;
        }

        public void setShousuoya(String shousuoya) {
            this.shousuoya = shousuoya;
        }

        public GuandaohuliBean getGuandaohuli() {
            return guandaohuli;
        }

        public void setGuandaohuli(GuandaohuliBean guandaohuli) {
            this.guandaohuli = guandaohuli;
        }

        public RuliangBean getRuliang() {
            return ruliang;
        }

        public void setRuliang(RuliangBean ruliang) {
            this.ruliang = ruliang;
        }

        public ChuliangBean getChuliang() {
            return chuliang;
        }

        public void setChuliang(ChuliangBean chuliang) {
            this.chuliang = chuliang;
        }

        public String getTeshuqkycl() {
            return teshuqkycl;
        }

        public void setTeshuqkycl(String teshuqkycl) {
            this.teshuqkycl = teshuqkycl;
        }

        public String getQianming() {
            return qianming;
        }

        public void setQianming(String qianming) {
            this.qianming = qianming;
        }

        public static class GuandaohuliBean {
        }

        public static class RuliangBean {
        }

        public static class ChuliangBean {
        }
    }
}
