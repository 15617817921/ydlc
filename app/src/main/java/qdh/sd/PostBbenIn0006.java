package qdh.sd;

/**
 * Created by dantevsyou on 2017/12/2.
 */

public class PostBbenIn0006 {

    /**
     * tranCode : 0006
     * args : {"stockOutNo":"C20171130100","bagCode":"17113098","firstCheckUser":"8118","secondCheckUser":"8119","operatorationTime":"2017-11-30 18:36:59","patientId":"7341"}
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
         * stockOutNo : C20171130100
         * bagCode : 17113098
         * firstCheckUser : 8118
         * secondCheckUser : 8119
         * operatorationTime : 2017-11-30 18:36:59
         * patientId : 7341
         */

        private String stockOutNo;
        private String bagCode;
        private String firstCheckUser;
        private String secondCheckUser;
        private String operatorationTime;
        private String patientId;

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

        public String getFirstCheckUser() {
            return firstCheckUser;
        }

        public void setFirstCheckUser(String firstCheckUser) {
            this.firstCheckUser = firstCheckUser;
        }

        public String getSecondCheckUser() {
            return secondCheckUser;
        }

        public void setSecondCheckUser(String secondCheckUser) {
            this.secondCheckUser = secondCheckUser;
        }

        public String getOperatorationTime() {
            return operatorationTime;
        }

        public void setOperatorationTime(String operatorationTime) {
            this.operatorationTime = operatorationTime;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }
    }
}
