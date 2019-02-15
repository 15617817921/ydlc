package qdh.sd;

/**
 * Created by dantevsyou on 2017/12/2.
 */

public class PostBeanIn0002 {


    /**
     * tranCode : 0002
     * args : {"patientId":"","stockOutNo":"C0002017113001","bagCode":"17113001"}
     */

    private String tranCode;
    private ArgsBean args;

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public ArgsBean getArgs() {
        return args;
    }

    public void setArgs(ArgsBean args) {
        this.args = args;
    }

    public static class ArgsBean {
        /**
         * patientId :
         * stockOutNo : C0002017113001
         * bagCode : 17113001
         */

        private String patientId;
        private String stockOutNo;
        private String bagCode;

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getStockOutNo() {
            return stockOutNo;
        }

        public void setStockOutNo(String stockOutNo) {
            this.stockOutNo = stockOutNo;
        }

        public String getBagCode() {
            return bagCode;
        }

        public void setBagCode(String bagCode) {
            this.bagCode = bagCode;
        }
    }
}
