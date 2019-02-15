package qdh.sd;

import java.util.List;

/**
 * Created by dantevsyou on 2017/12/2.
 */

public class PostBbeanOut0002 {


    /**
     * Message : [{"patientName":"测试输血","bagCode":"1201","aboName":"A型","patientId":"175205","hasEnd":"0","bloodUnit":"u","rhName":"阳性","bloodState":"已出库","stockOutNo":"201712020003","bloodName":"悬浮红细胞","wardName":"H7N9病区","hasStart":"0","bedNo":"10","bloodAmount":"2","stockOutUser":"admin","hasCheck":"1","wardCode":"15","hasFifteen":"0","stockOutTime":"2017-12-02 14:59:46.306","patientCode":"00240916"},{"patientName":"测试输血","bagCode":"1202","aboName":"A型","patientId":"175205","hasEnd":"0","bloodUnit":"u","rhName":"阳性","bloodState":"已出库","stockOutNo":"201712020003","bloodName":"悬浮红细胞","wardName":"H7N9病区","hasStart":"0","bedNo":"10","bloodAmount":"1","stockOutUser":"admin","hasCheck":"0","wardCode":"15","hasFifteen":"0","stockOutTime":"2017-12-02 14:59:46.306","patientCode":"00240916"}]
     * State : 1
     */

    private int State;
    private List<MessageBean> Message;

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public List<MessageBean> getMessage() {
        return Message;
    }

    public void setMessage(List<MessageBean> Message) {
        this.Message = Message;
    }

    public static class MessageBean {
        /**
         * patientName : 测试输血
         * bagCode : 1201
         * aboName : A型
         * patientId : 175205
         * hasEnd : 0
         * bloodUnit : u
         * rhName : 阳性
         * bloodState : 已出库
         * stockOutNo : 201712020003
         * bloodName : 悬浮红细胞
         * wardName : H7N9病区
         * hasStart : 0
         * bedNo : 10
         * bloodAmount : 2
         * stockOutUser : admin
         * hasCheck : 1
         * wardCode : 15
         * hasFifteen : 0
         * stockOutTime : 2017-12-02 14:59:46.306
         * patientCode : 00240916
         */

        private String patientName;
        private String bagCode;
        private String aboName;
        private String patientId;
        private String hasEnd;
        private String bloodUnit;
        private String rhName;
        private String bloodState;
        private String stockOutNo;
        private String bloodName;
        private String wardName;
        private String hasStart;
        private String bedNo;
        private String bloodAmount;
        private String stockOutUser;
        private String hasCheck;
        private String wardCode;
        private String hasFifteen;
        private String stockOutTime;
        private String patientCode;

        private String patientAboName;
        private String patientRhName;

        public String getPatientAboName() {
            return patientAboName;
        }

        public void setPatientAboName(String patientAboName) {
            this.patientAboName = patientAboName;
        }

        public String getPatientRhName() {
            return patientRhName;
        }

        public void setPatientRhName(String patientRhName) {
            this.patientRhName = patientRhName;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public String getBagCode() {
            return bagCode;
        }

        public void setBagCode(String bagCode) {
            this.bagCode = bagCode;
        }

        public String getAboName() {
            return aboName;
        }

        public void setAboName(String aboName) {
            this.aboName = aboName;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getHasEnd() {
            return hasEnd;
        }

        public void setHasEnd(String hasEnd) {
            this.hasEnd = hasEnd;
        }

        public String getBloodUnit() {
            return bloodUnit;
        }

        public void setBloodUnit(String bloodUnit) {
            this.bloodUnit = bloodUnit;
        }

        public String getRhName() {
            return rhName;
        }

        public void setRhName(String rhName) {
            this.rhName = rhName;
        }

        public String getBloodState() {
            return bloodState;
        }

        public void setBloodState(String bloodState) {
            this.bloodState = bloodState;
        }

        public String getStockOutNo() {
            return stockOutNo;
        }

        public void setStockOutNo(String stockOutNo) {
            this.stockOutNo = stockOutNo;
        }

        public String getBloodName() {
            return bloodName;
        }

        public void setBloodName(String bloodName) {
            this.bloodName = bloodName;
        }

        public String getWardName() {
            return wardName;
        }

        public void setWardName(String wardName) {
            this.wardName = wardName;
        }

        public String getHasStart() {
            return hasStart;
        }

        public void setHasStart(String hasStart) {
            this.hasStart = hasStart;
        }

        public String getBedNo() {
            return bedNo;
        }

        public void setBedNo(String bedNo) {
            this.bedNo = bedNo;
        }

        public String getBloodAmount() {
            return bloodAmount;
        }

        public void setBloodAmount(String bloodAmount) {
            this.bloodAmount = bloodAmount;
        }

        public String getStockOutUser() {
            return stockOutUser;
        }

        public void setStockOutUser(String stockOutUser) {
            this.stockOutUser = stockOutUser;
        }

        public String getHasCheck() {
            return hasCheck;
        }

        public void setHasCheck(String hasCheck) {
            this.hasCheck = hasCheck;
        }

        public String getWardCode() {
            return wardCode;
        }

        public void setWardCode(String wardCode) {
            this.wardCode = wardCode;
        }

        public String getHasFifteen() {
            return hasFifteen;
        }

        public void setHasFifteen(String hasFifteen) {
            this.hasFifteen = hasFifteen;
        }

        public String getStockOutTime() {
            return stockOutTime;
        }

        public void setStockOutTime(String stockOutTime) {
            this.stockOutTime = stockOutTime;
        }

        public String getPatientCode() {
            return patientCode;
        }

        public void setPatientCode(String patientCode) {
            this.patientCode = patientCode;
        }
    }
}
