package my_network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import my_network.tools.CompressTool;
import my_network.tools.DecompressTool;
import my_network.tools.DialogUtil;

import static android.content.Context.MODE_PRIVATE;

/**
 * 实现网络层的封装
 */

abstract class NetWorkAbs implements CompressInterface, DecompressInterface {
    /**
     * 分隔符
     * 传输正确
     * 数据传输错误
     */
    public static final String SEPARATE = "¤";
    public static final int CORRECT = 0;
    public static final int ERROR = -1;

    /**
     * Socket使用
     */
    protected BufferedReader reader;
    protected BufferedWriter writer;
    private String ip;
    private int port = 5000;
    private Context context;
    private Socket socket;
    /**
     * 配置文档
     */
    public static final String IPFILE = "ipsetting";
    public static final String IPNODE = "ip";
    public static final String SYSTEM_MESSAGE = "ENJOYOR.MICS.Business.GongYong/ENJOYOR.MICS.Business.GongYong.MessageOption";
    public static final String YIZHU_ZHIXING = "ENJOYOR.MICS.Business.YiZhu/ENJOYOR.MICS.Business.YiZhu.MessageOption";
    public static final String BINGREN_XX = "ENJOYOR.MICS.Business.BingRenXX/ENJOYOR.MICS.Business.BingRenXX.MessageOption";
    public static final String SMTZ = "ENJOYOR.MICS.Business.ShengMingTZ/ENJOYOR.MICS.Business.ShengMingTZ.MessageOption";
    public static final String HULIJIRU = "ENJOYOR.MICS.Business.HuLiJiLu/ENJOYOR.MICS.Business.HuLiJiLu.MessageOption";
    public static final String PINGGUD = "ENJOYOR.MICS.Business.PingGuD/ENJOYOR.MICS.Business.PingGuD.MessageOption";
    public static final String NOTEBOOK = "ENJOYOR.MICS.Business.NursingReport/ENJOYOR.MICS.Business.NursingReport.MessageOption";
    public static final String GongYong = "ENJOYOR.MICS.Business.GongYong/ENJOYOR.MICS.Business.GongYong.MessageOption";
    public static final String HealthEDU = "ENJOYOR.MICS.Business.NursingReport/ENJOYOR.MICS.Business.NursingReport.MessageOption";
    public static final String GongZuoLiang = "ENJOYOR.MICS.Business.HuLiGongZuoLiang/ENJOYOR.MICS.Business.HuLiGongZuoLiang.MessageOption";
    ;

    /**
     * 枚举类
     * RIGHT:正确
     * NOT_RIGHT:错误
     */
    protected enum EnumTest {
        RIGHT,
        NOT_RIGHT,
        NETWORK_ERROR,
    }

    public NetWorkAbs(int port, Context context) {
        this.port = port;
        this.context = context;
        //通过之前的存储找到ip地址
        if (null != context) {
            /*SharedPreferences sp = context.getSharedPreferences(IPFILE, MODE_PRIVATE);
            ip = sp.getString(IPNODE, "192.168.7.82");*/

            SharedPreferences pref = context.getSharedPreferences("data", MODE_PRIVATE);
            ip = pref.getString("ip", "192.168.0.12");//第二个参数为默认值
        }
    }

    /**
     * Socket连接
     *
     * @param progressDialog
     */
    /*protected EnumTest startSocket(){
        try {
           MyLogger.kLog().e("执行方法startSocket()"+"\n");
            socket=new Socket(ip,port);
            socket.setSoTimeout(10000);
            writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-16"));
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socket.setSoTimeout(10000);
            return EnumTest.RIGHT;
        } catch (IOException e) {
            e.printStackTrace();
            return EnumTest.NOT_RIGHT;
        }
    }*/
    int num = 0;

    protected EnumTest startSocket() {
        try {
//            MyLogger.kLog().e("执行方法startSocket--"+Thread.currentThread().getName());
            socket = new Socket();

            socket.connect(new InetSocketAddress(ip, port), 1000);//设置链接请求时间1s"重新操作"
            socket.setSoTimeout(15000);//设置读操作时间5s
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-16"));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return EnumTest.RIGHT;
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            DialogUtil.closeProgressDialog();

            MyLogger.kLog().e("ConnectTimeoutException" + e.getMessage() + "--" + e.toString());
            showShortSafe(context, "连接超时，稍后重试");

            return EnumTest.NOT_RIGHT;
        } catch (SocketTimeoutException xe) {
            DialogUtil.closeProgressDialog();
            MyLogger.kLog().e("检查网络是否连接正确后重试" + "SocketTimeoutException" + xe.getMessage() + "--" + xe.toString());
//            showShortSafe(context, "检查是否连接到指定的网络");
            xe.printStackTrace();
            return EnumTest.NOT_RIGHT;
        } catch (IOException e) {
            DialogUtil.closeProgressDialog();
            e.printStackTrace();
            MyLogger.kLog().e("IOException" + e.getMessage() + "--" + e.toString());
//            showShortSafe(context, e.getMessage());
            return EnumTest.NOT_RIGHT;
        }


    }

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    /**
     * 安全地显示短时吐司
     *
     * @param context
     * @param text    文本
     */
    private void showShortSafe(final Context context, final CharSequence text) {
        num += 1;
        if (num == 1) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

//    public boolean ping() {
//        boolean fale=true;
//        try {
//            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
//
//                //获得ConnectivityManager对象
//                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//                //获取ConnectivityManager对象对应的NetworkInfo对象
//                //获取WIFI连接的信息
//                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//                //获取移动数据连接的信息
//                //NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//                if (wifiNetworkInfo.isConnected()) {
//                    fale=true;
//                    // Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
//                } else {
//                    fale=false;
//                    // Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
//                }
////API大于23时使用下面的方式进行网络监听
//            } else {
//
//               MyLogger.kLog().eln("API level 大于23");
//                //获得ConnectivityManager对象
//                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//                //获取所有网络连接的信息
//                Network[] networks = connMgr.getAllNetworks();
//                //用于存放网络连接信息
//                StringBuilder sb = new StringBuilder();
//                //通过循环将网络信息逐个取出来
//                for (int i = 0; i < networks.length; i++) {
//                    //获取ConnectivityManager对象对应的NetworkInfo对象
//                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
//                    sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
//                    fale=networkInfo.isConnected();
//                }
//                //Toast.makeText(context, sb.toString(),Toast.LENGTH_SHORT).show();
//            }
//        }
//        catch (Exception ex)
//        {
//            fale=false;
//        }
//        return  fale;
//    }


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

    /**
     * 解压
     */
    @Override
    public byte[] decompress(byte[] b) {
        return DecompressTool.decompress(b);
    }

    /**
     * 压缩
     */
    @Override
    public String compress(String s) {
        return CompressTool.compress(s);
    }

    /**
     * getData()返回数据
     */
    abstract String getData();

}
