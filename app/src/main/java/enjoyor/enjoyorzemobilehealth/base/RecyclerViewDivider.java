package enjoyor.enjoyorzemobilehealth.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by admin on 2017/7/14.
 */

public class RecyclerViewDivider extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight = 2;//分割线高度，默认为1px
    private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private int left=0;//
    private int right=0;//
    /**
     * 26      * 默认分割线：高度为2px，颜色为灰色
     * 27      *
     * 28      * @param context
     * 29      * @param orientation 列表方向
     * 30
     */
    public RecyclerViewDivider(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 43      * 自定义分割线
     * 44      *
     * 45      * @param context
     * 46      * @param orientation 列表方向
     * 47      * @param drawableId  分割线图片
     * 48
     */
    public RecyclerViewDivider(Context context, int orientation, int drawableId) {
        this(context, orientation);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     *       * 自定义分割线
     *       * @param context
     *       * @param orientation   列表方向
     *       * @param dividerHeight 分割线高度
     *       * @param dividerColor  分割线颜色
     *
     */
    public RecyclerViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
        this(context, orientation);
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);

    }
    public RecyclerViewDivider(Context context, int orientation, int dividerHeight, int dividerColor,int left,int right) {
        this(context, orientation);
        this.left=left;
        this.right=right;
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }


    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        ////设定底部边距为1px
        outRect.set(left, 0, right, mDividerHeight);
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
//        //没有子view或者没有没有颜色直接return
//        if (mDivider == null || layoutManager.getChildCount() == 0) {
//            return;
//        }
//        int left;
//        int right;
//        int top;
//        int bottom;
//        final int childCount = parent.getChildCount();
//        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
//            for (int i = 0; i < childCount - 1; i++) {
//                final View child = parent.getChildAt(i);
//                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//                //将有颜色的分割线处于中间位置
//                float center = (layoutManager.getTopDecorationHeight(child) - topBottom) / 2;
//                //计算下边的
//                left = layoutManager.getLeftDecorationWidth(child);
//                right = parent.getWidth() - layoutManager.getLeftDecorationWidth(child);
//                top = (int) (child.getBottom() + params.bottomMargin + center);
//                bottom = top + topBottom;
//                mDivider.setBounds(left, top, right, bottom);
//                mDivider.draw(c);
//            }
//        } else {
//            for (int i = 0; i < childCount - 1; i++) {
//                final View child = parent.getChildAt(i);
//                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//                //将有颜色的分割线处于中间位置
//                float center = (layoutManager.getLeftDecorationWidth(child) - leftRight) / 2;
//                //计算右边的
//                left = (int) (child.getRight() + params.rightMargin + center);
//                right = left + leftRight;
//                top = layoutManager.getTopDecorationHeight(child);
//                bottom = parent.getHeight() - layoutManager.getTopDecorationHeight(child);
//                mDivider.setBounds(left, top, right, bottom);
//                mDivider.draw(c);
//            }
//        }
        ////////////////////////
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

}