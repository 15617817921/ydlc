package qdh.sxbbcj;

import java.util.List;

/**
 * Created by dantevsyou on 2017/12/1.
 */

public class ResultBben {

    /**
     * Message : [{"sampleName":"输注悬浮红细胞*1U","patientName":"测试输血","wardName":"H7N9病区","patientId":"","bedNo":"10","wardCode":"15","printUser":"admin","sampleState":"","printTime":"2017-09-08 17:55:29.315","patientCode":"00240916","barCode":"030000085900"},{"sampleName":"输注血浆*1ml","patientName":"测试输血","wardName":"H7N9病区","patientId":"","bedNo":"10","wardCode":"15","printUser":"admin","sampleState":"","printTime":"2017-09-08 17:55:29.315","patientCode":"00240916","barCode":""}]
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
         * sampleName : 输注悬浮红细胞*1U
         * patientName : 测试输血
         * wardName : H7N9病区
         * patientId :
         * bedNo : 10
         * wardCode : 15
         * printUser : admin
         * sampleState :
         * printTime : 2017-09-08 17:55:29.315
         * patientCode : 00240916
         * barCode : 030000085900
         */

        private String sampleName;
        private String patientName;
        private String wardName;
        private String patientId;
        private String bedNo;
        private String wardCode;
        private String printUser;
        private String sampleState;
        private String printTime;
        private String patientCode;
        private String barCode;

        public String getSampleName() {
            return sampleName;
        }

        public void setSampleName(String sampleName) {
            this.sampleName = sampleName;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public String getWardName() {
            return wardName;
        }

        public void setWardName(String wardName) {
            this.wardName = wardName;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getBedNo() {
            return bedNo;
        }

        public void setBedNo(String bedNo) {
            this.bedNo = bedNo;
        }

        public String getWardCode() {
            return wardCode;
        }

        public void setWardCode(String wardCode) {
            this.wardCode = wardCode;
        }

        public String getPrintUser() {
            return printUser;
        }

        public void setPrintUser(String printUser) {
            this.printUser = printUser;
        }

        public String getSampleState() {
            return sampleState;
        }

        public void setSampleState(String sampleState) {
            this.sampleState = sampleState;
        }

        public String getPrintTime() {
            return printTime;
        }

        public void setPrintTime(String printTime) {
            this.printTime = printTime;
        }

        public String getPatientCode() {
            return patientCode;
        }

        public void setPatientCode(String patientCode) {
            this.patientCode = patientCode;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }
    }
}
