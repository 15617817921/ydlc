package enjoyor.enjoyorzemobilehealth.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.entities.TiWen;
import enjoyor.enjoyorzemobilehealth.utlis.ScreenUtils;
import my_network.MyLogger;

/**
 * Created by Administrator on 2017/5/3.
 */

public class AutoTemperatureLineChart extends View {
    private String[] yLineTiwenData = new String[]{"35", "36", "37", "38", "39", "40", "41", "42"};
    private String[] yLineMaiboData = new String[]{"40", "60", "80", "100", "120", "140", "160", "180"};

    private List<TiWen> valueList = new ArrayList<>();
    private List<TiWen> tiwenList = new ArrayList<>();
    private List<TiWen> maiboList = new ArrayList<>();

    private static final int PER_PAGE_COUNT = 4;
    private Context context;
    private MyLogger log;

    private int shi = 10;

    public AutoTemperatureLineChart(Context context) {
        super(context);
    }

    public AutoTemperatureLineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoTemperatureLineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


//        public AutoTemperatureLineChart(Context context, String[] xLineData, String[] value) {
//        super(context);
//        this.context=context;
//        this.xLineData = xLineData;
//        this.value = value;
//    }

    public AutoTemperatureLineChart(Context context, List<TiWen> list) {
        super(context);
        this.context = context;
        log = MyLogger.kLog();
        screen = ScreenUtils.getScreenWidth(context) / 8;
        choose(list);//筛选 体温和脉搏
        setWillNotDraw(false);
//        log.e(list.size() + "个数--" + valueList.size());
    }

    private void choose(List<TiWen> list) {
        //        for (int i = 0; i < list.size(); i++) {
//            tiWen = list.get(i);
//            if (tiWen.getLeiXing().equals("1")) {
//                valueList.add(tiWen);
//            } else {
//
//            }
//        }
//        log.e(valueList.size() + "实例");



        TiWen tiWen = null;
        for (int i = 0; i < list.size(); i++) {
            tiWen = list.get(i);
            //从第二个开始  每一个对比上一个的日期时间
            if ((i + 1) % 2 == 0) {    //偶数
                if (tiWen.getCaiJiRQ().equals(list.get(i - 1).getCaiJiRQ()) && tiWen.getCaiJiSJ().equals(list.get(i - 1).getCaiJiSJ())) {
                    TiWen bean = new TiWen();
                    bean.setCaiJiRQ(tiWen.getCaiJiRQ());
                    bean.setCaiJiSJ(tiWen.getCaiJiSJ());
                    if (tiWen.getLeiXing().equals("1")) {   //当前类型为体温
                        bean.setWendu(tiWen.getTiWen());
                        bean.setMaibo(list.get(i - 1).getTiWen());
                    } else {  //当前类型为脉搏
                        bean.setWendu(list.get(i - 1).getTiWen());
                        bean.setMaibo(tiWen.getTiWen());
                    }
                    log.e(bean.getWendu() + "--" + bean.getMaibo());
                    valueList.add(bean);
                }
            }
        }
    }

    private int screen;  // 屏幕宽度的八分之一


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //图标宽度
        int chartWidth = this.getWidth();
        //图标高度
        int chartHeight = this.getHeight();
        //上边距
        int padding_top = chartHeight / 8;
//        //右边距
//        int padding_right = screen;
//        //左边距
//        int padding_left = screen;
        //下边距
        int padding_bottom = chartHeight / 6;

        //原点坐标
        int xPoint = 2 * screen;
        int xWendu =screen;
        int xMaibo = 0+10;
        int yPoint = chartHeight - padding_bottom;


        //画X轴
        Paint paintXY = new Paint();
        //虚线效果，第一个参数:先画长度3的实线，间隔长度为8的空白，数组中可以有很多数，需要成对出现
        // 第二个参数为第一种参数这种效果执行时后面复制这种效果间隔的空白的长度
        DashPathEffect pathEffect = new DashPathEffect(new float[]{3, 8}, 0);
        paintXY.setStyle(Paint.Style.STROKE);
        paintXY.setStrokeWidth(3);
        paintXY.setColor(0xFF91908E);
        paintXY.setAntiAlias(true);
        Path path = new Path();
        path.moveTo(xPoint, yPoint);
        path.lineTo(chartWidth - screen, yPoint);
        paintXY.setPathEffect(pathEffect);
//        //画X轴虚线
        canvas.drawPath(path, paintXY);
//
        //画X轴,Y轴文字的画笔
        Paint paintXYText = new Paint();
        paintXYText.setStyle(Paint.Style.FILL);
        paintXYText.setStrokeWidth(1);
        paintXYText.setColor(0xFF91908E);
        paintXYText.setAntiAlias(true);
        paintXYText.setTextSize(18);
        //画X轴文字
//        int perWidth=(chartWidth-padding_left-padding_right)/(xLineData.length-1);
//        int perWidth=(chartWidth-padding_left-padding_right)/PER_PAGE_COUNT;
        int perWidth = (ScreenUtils.getScreenWidth(context) - screen - screen) / (PER_PAGE_COUNT - 1);
        log.e(valueList.size() + "onDraw");
        for (int i = 0; i < valueList.size(); i++) {
            String xRQData = valueList.get(i).getCaiJiRQ();
            String xSJdata = valueList.get(i).getCaiJiSJ();
            float textWidthRQ = paintXYText.measureText(xRQData);
            float textWidthSJ = paintXYText.measureText(xSJdata);
            canvas.drawText(xRQData, xPoint + i * perWidth - textWidthRQ / 2, yPoint + 30, paintXYText);
            canvas.drawText(xSJdata, xPoint + i * perWidth - textWidthSJ / 2, yPoint + 50, paintXYText);
        }
        //画X轴单位
        canvas.drawText("(t)", chartWidth - screen + 20, yPoint + 10, paintXYText);
//
//
//        //画Y轴相关的文字，单位及横向虚线
        int perHeight = (chartHeight - padding_top - padding_bottom) / (yLineTiwenData.length - 1);
//
        for (int i = 0; i < yLineTiwenData.length; i++) {
            if (i > 0) {
                Path pathY = new Path();
                pathY.moveTo(xPoint, yPoint - perHeight * i);
                pathY.lineTo(chartWidth - xWendu, yPoint - perHeight * i);
                //画折线区域内横向虚线
                canvas.drawPath(pathY, paintXY);
            }
            //画Y轴文字
            canvas.drawText(yLineTiwenData[i], xWendu, yPoint - i * perHeight, paintXYText);
        }

        for (int i = 0; i < yLineMaiboData.length; i++) {
            if (i > 0) {
                Path pathY = new Path();
                pathY.moveTo(xPoint, yPoint - perHeight * i);
                pathY.lineTo(chartWidth - xMaibo, yPoint - perHeight * i);
                //画折线区域内横向虚线
                canvas.drawPath(pathY, paintXY);
            }
            //画Y轴文字  40  60  80 100 120
            canvas.drawText(yLineMaiboData[i], xMaibo, yPoint - i * perHeight, paintXYText);
        }
        //画Y轴单位
        canvas.drawText("(℃)", xWendu, yPoint - (yLineTiwenData.length - 1) * perHeight - 30, paintXYText);//20
        canvas.drawText("次/分", xMaibo, yPoint - (yLineMaiboData.length - 1) * perHeight - 30, paintXYText);

//
        // 画折线区域内点及折线的画笔  温度
        Paint paintContent = new Paint();
        paintContent.setStyle(Paint.Style.FILL);
        paintContent.setStrokeWidth(3);
        paintContent.setColor(Color.BLUE);
//        paintContent.setColor(0xFFFF4900);
        paintContent.setAntiAlias(true);

        //画折线区域内数字的画笔  温度
        Paint paintContentText = new Paint();
        paintContentText.setStyle(Paint.Style.FILL);
        paintContentText.setStrokeWidth(1);
        paintContentText.setColor(Color.BLUE);
        paintContentText.setAntiAlias(true);
        paintContentText.setTextSize(20);

        // 画折线区域内点及折线的画笔  温度
        Paint pMaiboContent = new Paint();
        pMaiboContent.setStyle(Paint.Style.FILL);
        pMaiboContent.setStrokeWidth(3);
        pMaiboContent.setColor(Color.RED);
        pMaiboContent.setAntiAlias(true);

        //画折线区域内数字的画笔  脉搏
        Paint pMaiboContentText = new Paint();
        pMaiboContentText.setStyle(Paint.Style.FILL);
        pMaiboContentText.setStrokeWidth(1);
        pMaiboContentText.setColor(Color.RED);
        pMaiboContentText.setAntiAlias(true);
        pMaiboContentText.setTextSize(20);

        //画温度标志画笔
        Paint pwendutagText = new Paint();
        pwendutagText.setStyle(Paint.Style.FILL);
        pwendutagText.setStrokeWidth(1);
        pwendutagText.setColor(Color.RED);
        pwendutagText.setAntiAlias(true);
        pwendutagText.setTextSize(24);
        //画温度值及连接线
        for (int i = 0; i < valueList.size(); i++) {
            String temperatureData = valueList.get(i).getWendu();
            String maibo = valueList.get(i).getMaibo();
            float ymaibo = yPoint - yMaiboPosition(maibo) * perHeight;

            int xPos = xPoint + perWidth * i;
            float yPos = yPoint - yTiwenPosition(temperatureData) * perHeight;
//            log.e("t-" + yPos + "--" + ymaibo);
//            //画脉搏圆圈
            canvas.drawCircle(xPos, ymaibo, 4, pMaiboContent);
            //画温度x号
            float[] chahao = {(float) (xPos - 5), (float) (yPos + 5), (float) (xPos + 5), (float) (yPos - 5), (float) (xPos - 5), (float) (yPos - 5), (float) (xPos + 5), (float) (yPos + 5)};
            canvas.drawLines(chahao, paintContent);

            //新增温度v符号
            if(i!=0){
                Double cur_wd=  Double.parseDouble(temperatureData);
                Double up_wd=  Double.parseDouble(valueList.get(i-1).getWendu());
                if((cur_wd-up_wd)>=1.5||(cur_wd-up_wd)<=-2){
                    canvas.drawText("v", xPos-6, yPos-8, pwendutagText);
                }
            }




            //画圆圈上数字
            canvas.drawText(maibo, xPos - 15, ymaibo - 15, pMaiboContentText);
            canvas.drawText(temperatureData, xPos - 15, chartHeight - padding_bottom, paintContentText);
//            canvas.drawText(temperatureData, xPos - 15, yPos - 15, paintContentText);

            //有效值判断
            if (i > 0 && yTiwenPosition(valueList.get(i - 1).getWendu()) != -999f && yTiwenPosition(temperatureData) != -999f) {
                int xPrePos = xPoint + perWidth * (i - 1);
                float yPrePos = yPoint - yTiwenPosition(valueList.get(i - 1).getWendu()) * perHeight;
                float yMaiboPos = yPoint - yMaiboPosition(valueList.get(i - 1).getMaibo()) * perHeight;
                //画相邻圆圈间的连接线
                canvas.drawLine(xPrePos, yPrePos, xPos, yPos, paintContent);
                canvas.drawLine(xPrePos, yMaiboPos, xPos, ymaibo, pMaiboContent);
            }
        }
    }

    /**
     * 计算相对于y轴原点偏移量
     *
     * @param yValue
     * @return
     */
    private float yTiwenPosition(String yValue) {
        try {
            return Float.parseFloat(yValue) - 35;
        } catch (Exception e) {
            e.printStackTrace();
            return -999f;
        }
    }

    private float yMaiboPosition(String yValue) {
        try {
            return (Float.parseFloat(yValue) - 40) / (20);
        } catch (Exception e) {
            e.printStackTrace();
            return -999f;
        }
    }

}
