package qdh.sxbbcj;

/**
 * Created by dantevsyou on 2017/12/1.
 */

public class PostBben {

    /**
     * tranCode : 0001
     * args : {"patientId":"","wardCode":"355"}
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
         * wardCode : 355
         */

        private String patientId;
        private String wardCode;

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getWardCode() {
            return wardCode;
        }

        public void setWardCode(String wardCode) {
            this.wardCode = wardCode;
        }
    }
}
