package qdh.sxbbsc;

/**
 * Created by dantevsyou on 2017/12/3.
 */

public class PostBben0004In {

    /**
     * tranCode : 0004
     * args : {"barCode":"0030020171130100","sendOutUser":"8118","operatorationTime":"2017-11-30 18:36:59"}
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
         * sendOutUser : 8118
         * operatorationTime : 2017-11-30 18:36:59
         */

        private String barCode;
        private String sendOutUser;
        private String operatorationTime;

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public String getSendOutUser() {
            return sendOutUser;
        }

        public void setSendOutUser(String sendOutUser) {
            this.sendOutUser = sendOutUser;
        }

        public String getOperatorationTime() {
            return operatorationTime;
        }

        public void setOperatorationTime(String operatorationTime) {
            this.operatorationTime = operatorationTime;
        }
    }
}
