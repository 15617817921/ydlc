package enjoyor.enjoyorzemobilehealth.utlis;

import java.lang.reflect.Field;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.entities.Yizhu;

public class YiHuUtlis {
    public YiHuUtlis() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 根据状态  返回 字符
     */
    public static String ztToString(String zt) {
        String text = "";
        switch (zt) {
            case "0":
                text = "未执行";
                break;
            case "1":
                text = "结束";
                break;
            case "2":
                text = "异常中断";
            case "3":
                text = "暂停";
                break;
            case "4":
                text = "停用";
                break;
            case "5":
                text = "继续";
                break;
            case "6":
                text = "开始";
                break;
            default:
                break;
        }
        return text;
    }

    /**
     * 根据状态  返回 字符
     */
    public static String stringToZt(String text) {
        if (text == null) {
            return "";
        }
        return text;
    }

    public static String createXml(List<Yizhu> list) throws Exception {
        //1.头部
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"utf-16\"?>" + "\r\n");
        s.append("<DS Name=\"59408853\" Num=\"1\">" + "\r\n");
        s.append("<DT Name=\"my_YiZhu\" Num=\"" + list.size() + "\">" + "\r\n");

        //2.中部
        for (int j = 0; j < list.size(); j++) {
            Field[] fields = list.get(j).getClass().getDeclaredFields();
            s.append("<DR Name=\"56152722\" Num=\"35\">" + "\r\n");
            for (int i = 0; i < fields.length; i++) {
                s.append("<DC Name=\"" + fields[i].getName() + "\" Num=\"0\">" + fields[i].get(list.get(j)) + "</DC>" + "\r\n");
            }
            s.append("</DR>" + "\r\n");
        }

        //3.尾部
        s.append("</DT>" + "\r\n");
        s.append("</DS>");
        return s.toString();
    }
}
