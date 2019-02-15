package enjoyor.enjoyorzemobilehealth.utlis;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import enjoyor.enjoyorzemobilehealth.entities.CbMoreBean;

/**
 * Created by Administrator on 2018/3/28.
 */

public class StringUtils {
    public StringUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getAssetsData(Context context, String path) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = context.getAssets().open(path);
            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }
    public static List<CbMoreBean> jsonToBean(String result) {
        List<CbMoreBean> lists = new ArrayList<>();
        CbMoreBean cmb = null;
        try {
            JSONObject data = new JSONObject(result);
            String s = data.optString("result");
            String tag = data.optString("tag");
            String type = data.optString("type");
            JSONArray list = new JSONArray(s);
            for (int i = 0; i < list.length(); i++) {
                JSONObject value = list.getJSONObject(i);
                String title = value.getString("title");

                String moreBean = value.getString("bean");
                JSONArray array = new JSONArray(moreBean);
                CbMoreBean.Bean bean = null;
                List<CbMoreBean.Bean> list1 = new ArrayList<>();
                for (int j = 0; j < array.length(); j++) {
                    JSONObject item = array.getJSONObject(j);
                    String name = item.has("name") ? item.getString("name") : "";//标题
                    String sorce = item.has("sorce") ? item.getString("sorce") : "";//
                    bean = new CbMoreBean.Bean();
                    bean.setName(name);
                    bean.setSorce(sorce);
                    bean.setMsg("0");
                    Log.e("0000", title + name + sorce);
                    list1.add(bean);
                }
                cmb = new CbMoreBean();
                cmb.setTitle(title);
                cmb.setTag(tag);
                cmb.setType(type);
                cmb.setCbMoreBean(list1);
                lists.add(cmb);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }
    /**
     * 判断字符串是否null
     */
    public static String stringNull(String text) {
        if (text == null) {
            return "";
        }
        return text;
    }

    /**
     * 判断字符串是否以,结尾，并去除
     */
    public static String stringSubEnds(String text) {
        if (text.endsWith(",")) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    //   判断 List
    public static boolean useList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    public static boolean useSet(String[] arr, String targetValue) {
        Set<String> set = new HashSet<String>(Arrays.asList(arr));
        return set.contains(targetValue);
    }
}
