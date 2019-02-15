package qdh.sxq;

import java.util.List;

/**
 * Created by dantevsyou on 2017/12/4.
 */

public class PostBbenOut0009 {


    /**
     * Message : [{"dataName":"CVC","dataSort":"1","dataCode":"0"},{"dataName":"PICC","dataSort":"2","dataCode":"0"},{"dataName":"外周浅静脉留置针","dataSort":"3","dataCode":"0"},{"dataName":"其他","dataSort":"4","dataCode":"0"}]
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
         * dataName : CVC
         * dataSort : 1
         * dataCode : 0
         */

        private String dataName;
        private String dataSort;
        private String dataCode;

        public String getDataName() {
            return dataName;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }

        public String getDataSort() {
            return dataSort;
        }

        public void setDataSort(String dataSort) {
            this.dataSort = dataSort;
        }

        public String getDataCode() {
            return dataCode;
        }

        public void setDataCode(String dataCode) {
            this.dataCode = dataCode;
        }
    }
}
