package enjoyor.enjoyorzemobilehealth.utlis;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import enjoyor.enjoyorzemobilehealth.entities.XunHuiJL;
import enjoyor.enjoyorzemobilehealth.entities.ZhiXingJL;
import qdh.jyhd.JianYanJG;

/**
 * 日期时间的工具。
 */
public class DateUtil {

    private static DateUtil util;
    private static int mYear;
    private static int mMonth;
    private static int mNowDay;
    private static int mNextDay;
    private static String m_month;
    private static String m_day;
    private SimpleDateFormat formatter;
    private Calendar calendar;
    private Calendar c;

    public static DateUtil getInstance() {
        if (util == null) {
            util = new DateUtil();
        }
        return util;

    }

    private DateUtil() {
        super();
    }

    public SimpleDateFormat date_Formater_1 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");


    public Date getDate(String dateStr) {
        Date date = new Date();
        if (TextUtils.isEmpty(dateStr)) {
            return date;
        }
        try {
            date = date_Formater_1.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Calendar getCalendar(String dateStr) {
        // 声明一个Date类型的对象
        Date date = null;
        // 声明格式化日期的对象
        SimpleDateFormat format = null;
        if (dateStr != null) {
            // 创建日期的格式化类型
            format = new SimpleDateFormat("yyyy/MM/dd");
            // 创建一个Calendar类型的对象
            calendar = Calendar.getInstance();
            // forma.parse()方法会抛出异常
            try {
                //解析日期字符串，生成Date对象
                date = format.parse(dateStr);
                // 使用Date对象设置此Calendar对象的时间
                calendar.setTime(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return calendar;
    }

    public Calendar getCalendarSfm(String dateStr) {
        // 声明一个Date类型的对象
        Date date = null;
        // 声明格式化日期的对象
        SimpleDateFormat format = null;
        if (dateStr != null) {
            // 创建日期的格式化类型
            format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            // 创建一个Calendar类型的对象
            calendar = Calendar.getInstance();
            // forma.parse()方法会抛出异常
            try {
                //解析日期字符串，生成Date对象
                date = format.parse(dateStr);
                // 使用Date对象设置此Calendar对象的时间
                calendar.setTime(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return calendar;
    }

    /**
     * 时间
     * 年月日时分秒
     *
     * @return
     */
    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        return date;
    }

    /**
     * 时间
     * 年月日
     *
     * @return
     */
    public String getYear_Day() {
        formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间
        String date = formatter.format(curDate);
        String nyr = date.substring(0, date.length() - 8);
        Log.e("年月日", nyr);
        return nyr;
    }

    /**
     * 时间
     * 时分秒
     *
     * @return
     */
    public String getHour_s() {
        formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间
        String date = formatter.format(curDate);
        String sfm = date.substring(date.length() - 8, date.length());

        return sfm;
    }

    /**
     * 时间
     * 时分
     *
     * @return
     */
    public String getHour_Minute() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间
        String date = formatter.format(curDate);
        return date;
    }

    /**
     * 时间
     * 年月日
     *
     * @return
     */
    public String getYear_Month_Day() {
        String sfm = getYear() + "/" + getMonth() + "/" + getDay();
        return sfm;
    }

    /**
     * 获取年
     *
     * @return
     */
    public int getYear() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return
     */
    public int getMonth() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @return
     */
    public int getDay() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.DATE);
    }
    /**
     * 获取明天时间
     * @return
     */
    public String getmingDay() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        Date today = new Date();
        System.out.println("今天是:" + f.format(today));
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天

        Date tomorrow = c.getTime();
        System.out.println("明天是:" + f.format(tomorrow));
        return f.format(tomorrow);
    }
    /**
     * 获取昨天时间
     * @return
     */
    public String getzuoDay() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        Date today = new Date();
        System.out.println("今天是:" + f.format(today));
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, -1);// 今天+1天

        Date tomorrow = c.getTime();
        System.out.println("明天是:" + f.format(tomorrow));
        return f.format(tomorrow);
    }

    /**
     * 获取时
     *
     * @return
     */
    public int getHour() {
        Calendar cd = Calendar.getInstance();
//        return cd.get(Calendar.HOUR);
        return cd.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分
     *
     * @return
     */
    public int getMinute() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MINUTE);
    }

    /**
     * 一小时之后
     *
     * @return
     */
    public String get1Hour() {
        long curren = System.currentTimeMillis();
        curren += 60 * 60 * 1000;
        Date da = new Date(curren);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(da);
    }

    public String getOther1Hour(String shijian) {
        long curren = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date1 = format.parse(shijian);
            curren = date1.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        curren += 60 * 60 * 1000;
        Date da = new Date(curren);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(da);
    }


    /**
     * 四小时之后
     *
     * @return
     */
    public String get4Hour() {
        long curren = System.currentTimeMillis();
        curren += 60 * 4 * 60 * 1000;
        Date da = new Date(curren);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(da);
    }
    //将List按照时间倒序排列
    @SuppressLint("SimpleDateFormat")
    public List<ZhiXingJL> invertOrderList(boolean iszheng,List<ZhiXingJL> L){
        SimpleDateFormat sdf =null;
        if(L.get(0).getZhiXingSJ().contains("/")){
            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }else if(L.get(0).getZhiXingSJ().contains("-")){
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        Date d1;
        Date d2;
        ZhiXingJL temp_r = new ZhiXingJL();
        //做一个冒泡排序，大的在数组的前列
        for(int i=0; i<L.size()-1; i++){
            for(int j=i+1; j<L.size();j++){
                ParsePosition pos1 = new ParsePosition(0);
                ParsePosition pos2 = new ParsePosition(0);
                d1 = sdf.parse(L.get(i).getZhiXingSJ(), pos1);
                d2 = sdf.parse(L.get(j).getZhiXingSJ(), pos2);
                if(iszheng){
                    if(d2.before(d1)){//正序
                        temp_r = L.get(i);
                        L.set(i, L.get(j));
                        L.set(j, temp_r);
                    }
                }else {
                    if(d1.before(d2)){//逆序
                        temp_r = L.get(i);
                        L.set(i, L.get(j));
                        L.set(j, temp_r);
                    }
                }

            }
        }
        return L;
    }


    //将List按照时间倒序排列  护理查询页面
    @SuppressLint("SimpleDateFormat")
    public List<XunHuiJL> hulichaxunOrderList(boolean iszheng, List<XunHuiJL> L){
        SimpleDateFormat sdf =null;
        if(L.get(0).getXunHuiSJ().contains("/")){
            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }else if(L.get(0).getXunHuiSJ().contains("-")){
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        Date d1;
        Date d2;
        XunHuiJL temp_r = new XunHuiJL();
        //做一个冒泡排序，大的在数组的前列
        for(int i=0; i<L.size()-1; i++){
            for(int j=i+1; j<L.size();j++){
                ParsePosition pos1 = new ParsePosition(0);
                ParsePosition pos2 = new ParsePosition(0);
                d1 = sdf.parse(L.get(i).getXunHuiSJ(), pos1);
                d2 = sdf.parse(L.get(j).getXunHuiSJ(), pos2);
                if(iszheng){
                    if(d2.before(d1)){//正序
                        temp_r = L.get(i);
                        L.set(i, L.get(j));
                        L.set(j, temp_r);
                    }
                }else {
                    if(d1.before(d2)){//逆序
                        temp_r = L.get(i);
                        L.set(i, L.get(j));
                        L.set(j, temp_r);
                    }
                }

            }
        }
        return L;
    }

    /*
    * 抽血核对排序*/
    //将List按照时间倒序排列
    @SuppressLint("SimpleDateFormat")
    public List<JianYanJG> jyhduiOrderList(boolean iszheng, List<JianYanJG> L){
        SimpleDateFormat sdf =null;
        if(L.get(0).getJianYanSJ().contains("/")){
            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }else if(L.get(0).getJianYanSJ().contains("-")){
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        List<JianYanJG> list_ture=new ArrayList<>();
        List<JianYanJG> list_false=new ArrayList<>();
        for (int i = 0; i < L.size(); i++) {
            if(StringUtils.stringNull(L.get(i).getZXSJ()).equals("")){
                list_false.add(L.get(i));
            }else {
                list_ture.add(L.get(i));
            }
        }

        Date d1;
        Date d2;
        JianYanJG temp_r = new JianYanJG();
        //做一个冒泡排序，大的在数组的前列
        for(int i=0; i<list_ture.size()-1; i++){
            for(int j=i+1; j<list_ture.size();j++){
                ParsePosition pos1 = new ParsePosition(0);
                ParsePosition pos2 = new ParsePosition(0);
                d1 = sdf.parse(list_ture.get(i).getJianYanSJ(), pos1);
                d2 = sdf.parse(list_ture.get(j).getJianYanSJ(), pos2);
                if(iszheng){
                    if(d2.before(d1)){//正序
                        temp_r = list_ture.get(i);
                        list_ture.set(i, list_ture.get(j));
                        list_ture.set(j, temp_r);
                    }
                }else {
                    if(d1.before(d2)){//逆序
                        temp_r = list_ture.get(i);
                        list_ture.set(i, list_ture.get(j));
                        list_ture.set(j, temp_r);
                    }
                }

            }
        }
        for(int i=0; i<list_false.size()-1; i++){
            for(int j=i+1; j<list_false.size();j++){
                ParsePosition pos1 = new ParsePosition(0);
                ParsePosition pos2 = new ParsePosition(0);
                d1 = sdf.parse(list_false.get(i).getJianYanSJ(), pos1);
                d2 = sdf.parse(list_false.get(j).getJianYanSJ(), pos2);
                if(iszheng){
                    if(d2.before(d1)){//正序
                        temp_r = list_false.get(i);
                        list_false.set(i, list_false.get(j));
                        list_false.set(j, temp_r);
                    }
                }else {
                    if(d1.before(d2)){//逆序
                        temp_r = list_false.get(i);
                        list_false.set(i, list_false.get(j));
                        list_false.set(j, temp_r);
                    }
                }

            }
        }
        List<JianYanJG> list_all=new ArrayList<>();

        for (int i = 0; i <list_false.size() ; i++) {
            list_all.add(list_false.get(i));
        }
        for (int i = 0; i <list_ture.size() ; i++) {
            list_all.add(list_ture.get(i));
        }

        return list_all;
    }
    /*
    * 巡回记录 展示排序
    * */
    //将List按照时间倒序排列
//    @SuppressLint("SimpleDateFormat")
//    public List<Yizhu> xunhuiOrderList(List<Yizhu> L){
//        SimpleDateFormat sdf =null;
//        if(L.get(0).getZhiXingSJ().contains("/")){
//            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        }else if(L.get(0).getZhiXingSJ().contains("-")){
//            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        }
//
//        Date d1;
//        Date d2;
//        ZhiXingJL temp_r = new ZhiXingJL();
//        //做一个冒泡排序，大的在数组的前列
//        for(int i=0; i<L.size()-1; i++){
//            for(int j=i+1; j<L.size();j++){
//                ParsePosition pos1 = new ParsePosition(0);
//                ParsePosition pos2 = new ParsePosition(0);
//                d1 = sdf.parse(L.get(i).getZhiXingSJ(), pos1);
//                d2 = sdf.parse(L.get(j).getZhiXingSJ(), pos2);
//                if(d1.before(d2)){//如果队前日期靠前，调换顺序
//                    temp_r = L.get(i);
//                    L.set(i, L.get(j));
//                    L.set(j, temp_r);
//                }
//            }
//        }
//        return L;
//    }
    public String getOther4Hour(String shijian) {
        long curren = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date1 = format.parse(shijian);
            curren = date1.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        curren += 60 * 4 * 60 * 1000;
        Date da = new Date(curren);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(da);
    }

    /**
     * 12小时之后
     *
     * @return
     */
    public String get12Hour() {
        long curren = System.currentTimeMillis();
        curren += 60 * 12 * 60 * 1000;
        Date da = new Date(curren);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(da);
    }

    public String getOther12Hour(String shijian) {
        long curren = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date1 = format.parse(shijian);
            curren = date1.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        curren += 60 * 12 * 60 * 1000;
        Date da = new Date(curren);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(da);
    }


    /**
     * 得到一周前的日期
     *
     * @return
     */
    public String lastWeek() {
        Date date = new Date();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(date)) - 7;

        if (day < 1) {
            month -= 1;
            if (month == 0) {
                year -= 1;
                month = 12;
            }
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                day = 30 + day;
            } else if (month == 1 || month == 3 || month == 5 || month == 7
                    || month == 8 || month == 10 || month == 12) {
                day = 31 + day;
            } else if (month == 2) {
                if (year == 0 || (year % 4 == 0 && year != 0))
                    day = 29 + day;
                else
                    day = 28 + day;
            }
        }
        String y = year + "";
        String m = "";
        String d = "";
        if (month < 10)
            m = "0" + month;
        else
            m = month + "";
        if (day < 10)
            d = "0" + day;
        else
            d = day + "";

        return y + "/" + m + "/" + d;
    }

    /**
     * 得到一周后的日期
     *
     * @return
     */
    public String afterWeek() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        // 这边定义的年月日变量都要int类型 ， 不要问我为什么
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mNowDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

        c.set(Calendar.DAY_OF_MONTH, mNowDay + 7); // 延后7天
        mNextDay = c.get(Calendar.DAY_OF_MONTH);
        // 判断延后的日期小于今天的日期，月份加一
        if (mNowDay > mNextDay) {
            mMonth += 1;
        }
        // 判断延后的月份大于本月的月份，月份设置为一月份，年份加一
        if (mMonth > 12) {
            mMonth = 1;
            mYear += 1;
        }
        // 测试今天的日期
        Log.e("今日时间===>", mYear + "年" + mMonth + "月" + mNowDay + "日");
        // 如果 月份为个位数则加个0在前面
        if (mMonth < 10) {
            m_month = "0" + mMonth;
        } else {
            m_month = "" + mMonth;
        }
        // 如果 天数为个位数则加个0在前面
        if (mNextDay < 10) {
            m_day = "0" + mNextDay;
        } else {
            m_day = "" + mNextDay;
        }
        return mYear + "/" + m_month + "/" + m_day;
    }

    public String afterOtherWeek(String shijian) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date1 = format.parse(shijian);
            c.setTime(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 这边定义的年月日变量都要int类型 ， 不要问我为什么
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mNowDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

        c.set(Calendar.DAY_OF_MONTH, mNowDay + 7); // 延后7天
        mNextDay = c.get(Calendar.DAY_OF_MONTH);
        // 判断延后的日期小于今天的日期，月份加一
        if (mNowDay > mNextDay) {
            mMonth += 1;
        }
        // 判断延后的月份大于本月的月份，月份设置为一月份，年份加一
        if (mMonth > 12) {
            mMonth = 1;
            mYear += 1;
        }
        // 测试今天的日期
        Log.e("今日时间===>", mYear + "年" + mMonth + "月" + mNowDay + "日");
        // 如果 月份为个位数则加个0在前面
        if (mMonth < 10) {
            m_month = "0" + mMonth;
        } else {
            m_month = "" + mMonth;
        }
        // 如果 天数为个位数则加个0在前面
        if (mNextDay < 10) {
            m_day = "0" + mNextDay;
        } else {
            m_day = "" + mNextDay;
        }
        return mYear + "/" + m_month + "/" + m_day;
    }

    /**
     * 得到14天后的日期
     *
     * @return
     */
    public String after14D() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        // 这边定义的年月日变量都要int类型 ， 不要问我为什么
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mNowDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

        c.set(Calendar.DAY_OF_MONTH, mNowDay + 14); // 延后7天
        mNextDay = c.get(Calendar.DAY_OF_MONTH);
        // 判断延后的日期小于今天的日期，月份加一
        if (mNowDay > mNextDay) {
            mMonth += 1;
        }
        // 判断延后的月份大于本月的月份，月份设置为一月份，年份加一
        if (mMonth > 12) {
            mMonth = 1;
            mYear += 1;
        }
        // 测试今天的日期
        Log.e("今日时间===>", mYear + "年" + mMonth + "月" + mNowDay + "日");
        // 如果 月份为个位数则加个0在前面
        if (mMonth < 10) {
            m_month = "0" + mMonth;
        } else {
            m_month = "" + mMonth;
        }
        // 如果 天数为个位数则加个0在前面
        if (mNextDay < 10) {
            m_day = "0" + mNextDay;
        } else {
            m_day = "" + mNextDay;
        }
        return mYear + "/" + m_month + "/" + m_day;
    }

    public String afterOther14D(String shijian) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date1 = format.parse(shijian);
            c.setTime(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 这边定义的年月日变量都要int类型 ， 不要问我为什么
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mNowDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

        c.set(Calendar.DAY_OF_MONTH, mNowDay + 14); // 延后7天
        mNextDay = c.get(Calendar.DAY_OF_MONTH);
        // 判断延后的日期小于今天的日期，月份加一
        if (mNowDay > mNextDay) {
            mMonth += 1;
        }
        // 判断延后的月份大于本月的月份，月份设置为一月份，年份加一
        if (mMonth > 12) {
            mMonth = 1;
            mYear += 1;
        }
        // 测试今天的日期
        Log.e("今日时间===>", mYear + "年" + mMonth + "月" + mNowDay + "日");
        // 如果 月份为个位数则加个0在前面
        if (mMonth < 10) {
            m_month = "0" + mMonth;
        } else {
            m_month = "" + mMonth;
        }
        // 如果 天数为个位数则加个0在前面
        if (mNextDay < 10) {
            m_day = "0" + mNextDay;
        } else {
            m_day = "" + mNextDay;
        }
        return mYear + "/" + m_month + "/" + m_day;
    }

    /**
     * 得到一天后的日期
     *
     * @return
     */
    public String afterDay() {

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        // 这边定义的年月日变量都要int类型 ， 不要问我为什么
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mNowDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

        c.set(Calendar.DAY_OF_MONTH, mNowDay + 1); // 延后1天
        mNextDay = c.get(Calendar.DAY_OF_MONTH);
        // 判断延后的日期小于今天的日期，月份加一
        if (mNowDay > mNextDay) {
            mMonth += 1;
        }
        // 判断延后的月份大于本月的月份，月份设置为一月份，年份加一
        if (mMonth > 12) {
            mMonth = 1;
            mYear += 1;
        }
        // 测试今天的日期
        Log.e("今日时间===>", mYear + "年" + mMonth + "月" + mNowDay + "日");
        // 如果 月份为个位数则加个0在前面
        if (mMonth < 10) {
            m_month = "0" + mMonth;
        } else {
            m_month = "" + mMonth;
        }
        // 如果 天数为个位数则加个0在前面
        if (mNextDay < 10) {
            m_day = "0" + mNextDay;
        } else {
            m_day = "" + mNextDay;
        }
        return mYear + "/" + m_month + "/" + m_day;
    }

    public String afterOtherDay(String shijian) {

        final Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date1 = format.parse(shijian);
            c.setTime(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 这边定义的年月日变量都要int类型 ， 不要问我为什么
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mNowDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

        c.set(Calendar.DAY_OF_MONTH, mNowDay + 1); // 延后1天
        mNextDay = c.get(Calendar.DAY_OF_MONTH);
        // 判断延后的日期小于今天的日期，月份加一
        if (mNowDay > mNextDay) {
            mMonth += 1;
        }
        // 判断延后的月份大于本月的月份，月份设置为一月份，年份加一
        if (mMonth > 12) {
            mMonth = 1;
            mYear += 1;
        }
        // 测试今天的日期
        Log.e("今日时间===>", mYear + "年" + mMonth + "月" + mNowDay + "日");
        // 如果 月份为个位数则加个0在前面
        if (mMonth < 10) {
            m_month = "0" + mMonth;
        } else {
            m_month = "" + mMonth;
        }
        // 如果 天数为个位数则加个0在前面
        if (mNextDay < 10) {
            m_day = "0" + mNextDay;
        } else {
            m_day = "" + mNextDay;
        }
        return mYear + "/" + m_month + "/" + m_day;
    }

    /**
     * 得到三天后的日期
     *
     * @return
     */
    public String after3Day() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // 这边定义的年月日变量都要int类型 ， 不要问我为什么
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mNowDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

        c.set(Calendar.DAY_OF_MONTH, mNowDay + 3); // 延后3天
        mNextDay = c.get(Calendar.DAY_OF_MONTH);
        // 判断延后的日期小于今天的日期，月份加一
        if (mNowDay > mNextDay) {
            mMonth += 1;
            mNowDay = 1;
        }
        // 判断延后的月份大于本月的月份，月份设置为一月份，年份加一
        if (mMonth > 12) {
            mMonth = 1;
//            mYear +=1 ;
        }
        // 测试今天的日期
        Log.e("今日时间===>", mYear + "年" + mMonth + "月" + mNowDay + "日");
        // 如果 月份为个位数则加个0在前面
        if (mMonth < 10) {
            m_month = "0" + mMonth;
        } else {
            m_month = "" + mMonth;
        }
        // 如果 天数为个位数则加个0在前面
        if (mNextDay < 10) {
            m_day = "0" + mNextDay;
        } else {
            m_day = "" + mNextDay;
        }
        return mYear + "/" + m_month + "/" + m_day;
    }

    public String afterOther3Day(String shijian) {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date1 = format.parse(shijian);
            c.setTime(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 这边定义的年月日变量都要int类型 ， 不要问我为什么
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mNowDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

        c.set(Calendar.DAY_OF_MONTH, mNowDay + 3); // 延后3天
        mNextDay = c.get(Calendar.DAY_OF_MONTH);
        // 判断延后的日期小于今天的日期，月份加一
        if (mNowDay > mNextDay) {
            mMonth += 1;
            mNowDay = 1;
        }
        // 判断延后的月份大于本月的月份，月份设置为一月份，年份加一
        if (mMonth > 12) {
            mMonth = 1;
//            mYear +=1 ;
        }
        // 测试今天的日期
        Log.e("今日时间===>", mYear + "年" + mMonth + "月" + mNowDay + "日");
        // 如果 月份为个位数则加个0在前面
        if (mMonth < 10) {
            m_month = "0" + mMonth;
        } else {
            m_month = "" + mMonth;
        }
        // 如果 天数为个位数则加个0在前面
        if (mNextDay < 10) {
            m_day = "0" + mNextDay;
        } else {
            m_day = "" + mNextDay;
        }
        return mYear + "/" + m_month + "/" + m_day;
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 3000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
