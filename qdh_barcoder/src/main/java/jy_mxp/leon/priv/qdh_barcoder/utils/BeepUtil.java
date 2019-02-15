package jy_mxp.leon.priv.qdh_barcoder.utils;





        import android.content.Context;
        import android.media.AudioManager;
        import android.media.SoundPool;

        import jy_mxp.leon.priv.qdh_barcoder.R;

public class BeepUtil {

    private Context mContext;

    private SoundPool mSoundPool;
    private int mSoundID;
    private AudioManager mAm;

    private static BeepUtil mInstance;

    public static BeepUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BeepUtil(context);
        }
        return mInstance;
    }

    @SuppressWarnings("deprecation")
    public BeepUtil(Context context) {
        mContext = context;

        mSoundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
        mSoundID = mSoundPool.load(mContext, R.raw.beep, 1);
    }

    public void beep() {
        float maxVolumn = getAudioManager().getStreamMaxVolume(
                AudioManager.STREAM_NOTIFICATION);
        float curVolumn = getAudioManager().getStreamVolume(
                AudioManager.STREAM_NOTIFICATION);
        float volumnRatio = curVolumn / maxVolumn;
        mSoundPool.play(mSoundID, volumnRatio, volumnRatio, 1, 0, 1);
    }

    private AudioManager getAudioManager() {
        if (mAm == null) {
            mAm = (AudioManager) mContext
                    .getSystemService(Context.AUDIO_SERVICE);
        }
        return mAm;
    }
}
