package my_network.tools;

import android.app.Activity;
import android.app.ProgressDialog;

import java.lang.ref.WeakReference;

public class DialogUtil {
    public static void showProgressDialog(Activity activity, String message) {

        showProgressDialog(activity, message, true);
    }

    private static WeakReference<Activity> mWeakReference;
    private static ProgressDialog mProgressDialog;

    /**
     * @param activity 需要弹窗的activity
     * @param message  弹窗展示的内容
     * @param flag     触摸弹窗外区域，是否取消窗口
     */
    public static void showProgressDialog(Activity activity, String message, boolean flag) {
        if (!isLiving(activity)) {
            return;
        }

        if (mWeakReference == null) {
            mWeakReference = new WeakReference(activity);
        }

        activity = mWeakReference.get();

        if (mProgressDialog == null) {
            if (activity.getParent() != null) {
                mProgressDialog = new ProgressDialog(activity.getParent());
            } else {
                mProgressDialog = new ProgressDialog(activity);
            }
        }

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(flag);
//            mProgressDialog.setIcon(R.mipmap.ic_launcher);
            mProgressDialog.show();
        } else {
            mProgressDialog.setMessage(message);
        }
    }



    /**
     * 关闭进度框
     */
    public static void closeProgressDialog() {
        if (isShowing(mProgressDialog) && isExist_Living(mWeakReference)) {
            if (isShowing(mProgressDialog)) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
                mWeakReference.clear();
                mWeakReference = null;
            }
        }
    }

    public static void closeProgressDialog(boolean flag) {
        if (isShowing(mProgressDialog) && flag) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
            mWeakReference.clear();
            mWeakReference = null;
        }
    }
//    这个方法与之前的方法类似，唯一的不同之处就在于有一个传入参数，在onPause方法中调用时，直接传入true即可。
//    @Override
//    protected void onPause() {
//        super.onPause();
//        DialogUtil.closeProgressDialog(true);
//    }


    private static boolean isExist_Living(WeakReference<Activity> weakReference) {

        if (weakReference != null) {
            Activity activity = weakReference.get();
            if (activity == null) {
                return false;
            }
            if (activity.isFinishing()) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 判断进度框是否正在显示
     */
    private static boolean isShowing(ProgressDialog dialog) {

        boolean isShowing = dialog != null && dialog.isShowing();


        return isShowing;
    }


    /**
     * 判断activity是否存活
     */
    private static boolean isLiving(Activity activity) {
        if (activity == null) {
            return false;
        }

        if (activity.isFinishing()) {
            return false;
        }

        return true;
    }
}

