package qdh.sxbbcj;

/**
 * Created by dantevsyou on 2017/12/1.
 */

public class PostBben003 {

    /**
     * tranCode : 0003
     * args : {"barCode":"0030020171130100","firstCheckUser":"8118","secondCheckUser":"8119","operatorationTime":"2017-11-30 18:36:59"}
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
         * barCode : 0030020171130100
         * firstCheckUser : 8118
         * secondCheckUser : 8119
         * operatorationTime : 2017-11-30 18:36:59
         */

        private String barCode;
        private String firstCheckUser;
        private String secondCheckUser;
        private String operatorationTime;

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
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
    }
}
