package enjoyor.enjoyorzemobilehealth.net;

public class NETOKhttp {
    public static <T, M> void sendJsonRequest(Zhier zhier, IDataListener listener) {
        IHttpRequest httpRequest=new SocketRequest();
        CallBackListener callBackListener=new JsonCallBackListener(listener);
        HttpTask ht=new HttpTask(zhier,httpRequest,callBackListener);
        ThreadPoolManger.getInstance().addTask(ht);
    }
}
