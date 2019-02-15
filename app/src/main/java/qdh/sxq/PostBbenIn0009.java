package qdh.sxq;

/**
 * Created by dantevsyou on 2017/12/4.
 */

public class PostBbenIn0009 {

    /**
     * tranCode : 0009
     * args : {"dataType":"输血前用药"}
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
         * dataType : 输血前用药
         */

        private String dataType;

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
    }
}
