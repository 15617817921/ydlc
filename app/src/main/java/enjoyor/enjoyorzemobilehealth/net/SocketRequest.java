package enjoyor.enjoyorzemobilehealth.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import my_network.MyLogger;
import my_network.tools.CompressTool;
import my_network.tools.DecodeMapTool;
import my_network.tools.DialogUtil;
import my_network.tools.UploadDataTool;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;



public class JsonHttpRequest  implements IHttpRequest {
//public class JsonHttpRequest extends Zhier implements IHttpRequest {

    private String id;
    private String number;
    private String message;
    private String canshu;
    private String dialogmsg;
    private Context context;
    private int port;

    private String url;
    private byte[] data;
    private CallBackListener mCallBackListener;
    private HttpURLConnection urlConnection;

    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private String param_compress;
    private String final_data;
    private Zhier zhier;


    @Override
    public Zhier setId(String id) {
        return null;
    }

    @Override
    public void setZhier(Zhier zhier) {
        this.zhier=zhier;
        id=zhier.getId();
        number=zhier.getNumber();
        message=zhier.getMoKuai();
        canshu=zhier.getParam();
        dialogmsg=zhier.getDialogmsg();
        context=zhier.getContext();
        port=zhier.getPort();
    }

    @Override
    public void setData(byte[] data) {

    }

    @Override
    public void setListener(CallBackListener listener) {
        this.mCallBackListener = listener;
    }

    protected EnumNet startSocket() {
        try {
            String ip="";
            //通过之前的存储找到ip地址
            if (null != zhier.getContext()) {
                SharedPreferences pref = context.getSharedPreferences("data", MODE_PRIVATE);
                ip = pref.getString("ip", "192.168.0.12");//第二个参数为默认值
            }
//            MyLogger.kLog().e("执行方法startSocket--"+Thread.currentThread().getName());
            socket = new Socket();

            socket.connect(new InetSocketAddress(ip, port), 1000);//设置链接请求时间1s"重新操作"
            socket.setSoTimeout(15000);//设置读操作时间5s
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-16"));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return EnumNet.RIGHT;
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            MyLogger.kLog().e("ConnectTimeoutException" + e.getMessage() + "--" + e.toString());
            return EnumNet.NETWORK_ERROR;
        } catch (SocketTimeoutException xe) {
            MyLogger.kLog().e("检查网络是否连接正确后重试" + "SocketTimeoutException" + xe.getMessage() + "--" + xe.toString());
            xe.printStackTrace();
            return EnumNet.NETWORK_ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            MyLogger.kLog().e("服务器连接失败" + "IOException" + e.getMessage() + "--" + e.toString());
//            showShortSafe(context, e.getMessage());
            return EnumNet.NETWORK_ERROR;
        }


    }
    private String headers;
    /**
     * 传送消息头和参数
     */
    private void uploadData() {
        try {
            /*拼接消息头*/
            headers = id + Constant.FUHAO + "高坡" + Constant.FUHAO + "你电脑的mac地址"
                    + Constant.FUHAO + message + Constant.FUHAO + number + Constant.FUHAO
                    + param_compress.length();
            UploadDataTool.uploadData(headers, writer, reader);
            UploadDataTool.uploadData(param_compress, writer, reader);
        } catch (Exception e) {
            MyLogger.kLog().e("uploadData()方法错误");
        }
    }
    /**
     * 下载数据
     */
    public SparseArray<String> loadData() {
        try {
            String[] handerItems;
            while (true) {
                char[] dataHander = new char[256 + 32];
                int length = reader.read(dataHander);
//                MyLogger.kLog().e("loadData() length长度" + length + "\n");
                String dataHeader = new String(dataHander, 0, length);
                handerItems = dataHeader.split("@");
                if (("GPStart".equals(handerItems[0].trim()))
                        && ("GPEnd".equals(handerItems[4].trim()))) {
                    writer.write("GPStart@1@1@true@GPEnd");
                    writer.flush();
                    break;
                } else {
                    writer.write("GPStart@1@1@false@GPEnd");
                    writer.flush();
                }
            }

            int dataLen = Integer.parseInt(handerItems[3].trim());
            int count = (int) Math.ceil(dataLen / 256.0);
//            MyLogger.kLog().e(" 个数:" + "\n" + count + "\n" + "handerItems:" + handerItems[3] + "\n");
            SparseArray<String> map = new SparseArray<String>();
            // 得到服务器传输过来的数据
            for (int i = 0; i < count; i++) {
                // Log.i("cishu", i+"");
                char[] dataChars = new char[256 + 32];
                int len = reader.read(dataChars);
                String dataStr = new String(dataChars, 0, len);
                String[] dataItems = dataStr.split("@");
//                MyLogger.kLog().e(" 从服务端下载的数据:" + "\n" + dataItems + "\n");
//                MyLogger.kLog().e(" 从服务端下载的数据:"+"\n"+dataItems+"\n");

                if ("GPStart".equals(dataItems[0].trim())
                        && "GPEnd".equals(dataItems[4].trim())) {
                    map.put(i, dataItems[3].trim());
//                    MyLogger.kLog().e(" map中放入的数据:" + dataItems[3].trim() + "\n");
//                    MyLogger.kLog().e(" map中放入的数据:"+dataItems[3].trim()+"\n");
                    writer.write("GPStart@1@1@true@GPEnd");
                    writer.flush();
                } else {
                    writer.write("GPStart@1@1@false@GPEnd");
                    writer.flush();
                    i--;
                }
            }
            closeSocket();
            return map;
        } catch (Exception e) {
            MyLogger.kLog().e("loadData()方法错误" + e.toString() + "--" + e.getMessage());
//            MyLogger.kLog().e(" loadData()方法错误"+e.toString()+"\n");
            return null;
        }
    }
    /**
     * Socket关闭
     */
    protected void closeSocket() {
        try {
            reader.close();
            writer.close();
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static final int CORRECT = 0;
    public static final int ERROR = -1;
    /**
     * 从map中获取数据，并解压和解密
     */
    public EnumNet decodeMap(SparseArray<String> map) {
        try {
            String data = DecodeMapTool.decodeMap(map);
            MyLogger.kLog().e("服务器返回数据:" + data);
            String[] items = data.split(Constant.FUHAO);
            // 以ReturnCode判断正确与否
            if ((CORRECT + "").equals(items[0])) {
                final_data = items[2];
//                MyLogger.kLog().e("最正确终数据为:" + "\n" + final_data + "\n");
                return EnumNet.RIGHT;
            }
            if ((ERROR + "").equals(items[0])) {
                final_data = items[1];
                DialogUtil.closeProgressDialog();
//                closeDialog(final_data);
                MyLogger.kLog().e("最错误终数据为:" + final_data);
//                MyLogger.kLog().e("最错误终数据为:"+"\n"+final_data+"\n");
                return EnumNet.NOT_RIGHT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyLogger.kLog().e("decodeMap()方法错误" + e.getMessage() + "--" + e.toString());
            DialogUtil.closeProgressDialog();
//            closeDialog("");
//            MyLogger.kLog().e("decodeMap()方法错误"+"\n");
            return EnumNet.NOT_RIGHT;
        }
        return EnumNet.NOT_RIGHT;
    }
    @Override
    public void exexute() {
        Observable.create(new Observable.OnSubscribe<EnumNet>() {
            @Override
            public void call(Subscriber<? super EnumNet> subscriber) {
                if (startSocket() == EnumNet.RIGHT) {
//                    MyLogger.kLog().e("Socket创建成功" + "\n");
                    param_compress =  CompressTool.compress(canshu);

                    uploadData();
                    SparseArray<String> map = loadData();
                    EnumNet enumTest = decodeMap(map);
                    MyLogger.kLog().e("-RIGHT-");
                    if (enumTest.equals(EnumNet.NOT_RIGHT)) {
//                        int j = 8 / 0;
                        MyLogger.kLog().e(enumTest + "-enumTest");
                        subscriber.onNext(enumTest);
                    } else {
                        MyLogger.kLog().e(enumTest + "-enumTest");
                        subscriber.onNext(enumTest);
                    }
                } else if (startSocket() == EnumNet.NETWORK_ERROR) {
                    MyLogger.kLog().e("-NETWORK_ERROR-");
                    //subscriber.onNext(EnumTest.NETWORK_ERROR);
//                    int j = 8 / 0;
                    subscriber.onNext(EnumNet.NETWORK_ERROR);
                }
            }
        })
                .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EnumNet>() {
                    @Override
                    public void onCompleted() {
                        MyLogger.kLog().e("-onCompleted-");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogUtil.closeProgressDialog();
//                        socketResult.fail(e.getMessage());
                        mCallBackListener.onFailure(e.getMessage());
//                        Toast.makeText(context, "Rxjava产生错误", Toast.LENGTH_LONG).show();
                        MyLogger.kLog().e(e.getMessage() + "--" + e.toString());
                    }

                    @Override
                    public void onNext(EnumNet enumTest) {
                        switch (enumTest) {
                            case RIGHT:
                                mCallBackListener.onSuccess(final_data);
//                                socketResult.success(final_data);
                                closeSocket();
                                DialogUtil.closeProgressDialog();
//                                MyLogger.kLog().e("success~回调函数返回结果,并关闭连接");
                                break;
                            case NOT_RIGHT:
                                mCallBackListener.onFailure("返回数据错误");
                                DialogUtil.closeProgressDialog();
                                //start(socketResult);
//                                closeSocket();
                                //int i=8/0;
                                Observable.error(new RuntimeException());
                                MyLogger.kLog().e("fail~回调函数返回结果，并关闭连接" + "\n");
                                break;
                            case NETWORK_ERROR:
                                DialogUtil.closeProgressDialog();
                                mCallBackListener.onFailure("网络错误，请稍候后重试！");
                                //start(socketResult);
//                                closeSocket();
                                //int j=8/0;
                                Observable.error(new RuntimeException());
                                MyLogger.kLog().e("fail~回调函数返回结果，并关闭连接" + "\n");
                                break;
                        }
                    }
                });
    }
}
