package enjoyor.enjoyorzemobilehealth.views;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Administrator on 2018/3/5.
 */

public class ShowDialog {
    private ShowDialog() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setShowErro(Context context, String data) {
        /**
         * 设置弹出对话框
         * */
        new AlertDialog.Builder(context)
                .setTitle("错误信息")
                .setMessage(data)
//                .setCancelable(false)
                .show();
    }

}
