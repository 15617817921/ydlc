package qdh.sxq;

/**
 * Created by dantevsyou on 2017/12/2.
 */

public class PostBbenIn0007 {

    /**
     * tranCode : 0007
     * args : {"stepCode":"1","bagCode":"17113098","firstCheckUser":"8118","secondCheckUser":"8119","operatorationTime":"2017-11-30 18:36:59","temperature":"37.2","bloodPressHigh":"134","bloodPressLow":"46","heartRate":"93","breathing":"13","bloodOxygen":"99","InfusionMethod":"CVC","InfusionPosition":"左","transfusionMedicine":""}
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
         * stepCode : 1
         * bagCode : 17113098
         * firstCheckUser : 8118
         * secondCheckUser : 8119
         * operatorationTime : 2017-11-30 18:36:59
         * temperature : 37.2
         * bloodPressHigh : 134
         * bloodPressLow : 46
         * heartRate : 93
         * breathing : 13
         * bloodOxygen : 99
         * InfusionMethod : CVC
         * InfusionPosition : 左
         * transfusionMedicine :
         */

        private String stepCode="";
        private String bagCode="";
        private String firstCheckUser="";
        private String secondCheckUser="";
        private String operatorationTime="";
        private String temperature="";
        private String bloodPressHigh="";
        private String bloodPressLow="";
        private String heartRate="";
        private String breathing="";
        private String bloodOxygen="";
        private String InfusionMethod="";
        private String InfusionPosition="";
        private String transfusionMedicine="";

        public String getStepCode() {
            return stepCode;
        }

        public void setStepCode(String stepCode) {
            this.stepCode = stepCode;
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

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getBloodPressHigh() {
            return bloodPressHigh;
        }

        public void setBloodPressHigh(String bloodPressHigh) {
            this.bloodPressHigh = bloodPressHigh;
        }

        public String getBloodPressLow() {
            return bloodPressLow;
        }

        public void setBloodPressLow(String bloodPressLow) {
            this.bloodPressLow = bloodPressLow;
        }

        public String getHeartRate() {
            return heartRate;
        }

        public void setHeartRate(String heartRate) {
            this.heartRate = heartRate;
        }

        public String getBreathing() {
            return breathing;
        }

        public void setBreathing(String breathing) {
            this.breathing = breathing;
        }

        public String getBloodOxygen() {
            return bloodOxygen;
        }

        public void setBloodOxygen(String bloodOxygen) {
            this.bloodOxygen = bloodOxygen;
        }

        public String getInfusionMethod() {
            return InfusionMethod;
        }

        public void setInfusionMethod(String InfusionMethod) {
            this.InfusionMethod = InfusionMethod;
        }

        public String getInfusionPosition() {
            return InfusionPosition;
        }

        public void setInfusionPosition(String InfusionPosition) {
            this.InfusionPosition = InfusionPosition;
        }

        public String getTransfusionMedicine() {
            return transfusionMedicine;
        }

        public void setTransfusionMedicine(String transfusionMedicine) {
            this.transfusionMedicine = transfusionMedicine;
        }
    }
}
