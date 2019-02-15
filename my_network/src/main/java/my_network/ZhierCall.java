package my_network;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import my_network.tools.DialogUtil;

/**
 * Created by Administrator on 2016/12/14.
 */

public class ZhierCall extends ZhierCallAbs {

    private int port = 0;//访问端口
    private String id = null;//登录人的id
    private String number = null;//要执行的方法
    private String moKuai = null;
    private String param = null;//参数
    private Context context = null;

    private String dialogmsg = "";//弹窗内容

    NetWork z;


    public ZhierCall( ) {

    }


    @Override
    public ZhierCallAbs setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public ZhierCallAbs setNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public ZhierCallAbs setMessage(String message) {
        this.moKuai = message;
        return this;
    }

    @Override
    public ZhierCallAbs setCanshu(String canshu) {
        this.param = canshu;
        return this;
    }

    @Override
    public ZhierCallAbs setContext(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public ZhierCallAbs setPort(int port) {
        this.port = port;
        return this;
    }

    @Override
    public ZhierCallAbs setDialogmes(String dialogmsg) {
        this.dialogmsg = dialogmsg;
        return this;
    }

//    @Override
//    public ZhierCallAbs setd(String dialogmsg) {

//    }


    public ZhierCall build() {
        z = new NetWork(id, number, moKuai, param, context, port,dialogmsg);
        return this;
    }
    protected ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    public void start(final NetWork.SocketResult s) {
        DialogUtil.showProgressDialog(scanForActivity(context), dialogmsg);
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                z.start(s);
            }
        });
    }
    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());

        return null;
    }
}
