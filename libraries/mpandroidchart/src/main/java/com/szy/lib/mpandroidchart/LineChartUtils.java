package com.szy.lib.mpandroidchart;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

/**
 * 图表工具
 * Created by yangle on 2016/11/29.
 */
public class LineChartUtils {


    private static Activity mContext;

    /**
     * 初始化图表
     *
     * @param barChart 原始图表
     * @return 初始化后的图表
     */
    public static LineChart initChart(LineChart barChart, Activity Context, final List<String> xAxisValue) {
        mContext = Context;
        barChart.getDescription().setEnabled(false);//设置描述
        barChart.setPinchZoom(true);//设置按比例放缩柱状图
        barChart.setDoubleTapToZoomEnabled(false);//将此设置为false可禁止通过双击来缩放图表
        //x坐标轴设置
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);//设置X轴两点之间的最小距离
        xAxis.setCenterAxisLabels(false);//设置标签不居中
        xAxis.setLabelRotationAngle(-20);//X轴字体旋转度数
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override//设置X轴的数据可以在此处设置文本内容
            public String getFormattedValue(float v, AxisBase axisBase) {
                String name = "" + v;
                int position = (int) (v + 1) - 1;
                name = "" + position;
                if (xAxisValue != null && xAxisValue.size() > 0 && position > -1 && xAxisValue.size() > position) {
                    name = xAxisValue.get(position);
                }
                return name;
            }
        });
        //y轴设置
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawAxisLine(true);// 显示y轴
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);// 设置y轴数据的位置
        leftAxis.setDrawGridLines(false);// 不从y轴发出横向直线
        xAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(12);
        leftAxis.setXOffset(20f); // 设置y轴数据偏移量
        leftAxis.setAxisMinimum(0f);//设置Y轴最小值
//        leftAxis.setAxisMaximum(45f);//设置Y轴最大值
        leftAxis.setLabelCount(6);//设置Y轴刻度值的数量
//        leftAxis.setLabelCount(6,true);//强制设置Y轴刻度值的数量
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                return v + "";
            }
        });
//        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.getAxisRight().setTextColor(Color.WHITE);
        //图例设置
        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(false);//设置当图例无法在一行展示完整时，不可以进行换行
        legend.setDrawInside(false);
        legend.setMaxSizePercent(1.5f); // 设置，默认0.95f,图例最大尺寸区域占图表区域之外的比例
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12f);
        //不显示图例
        legend.setEnabled(true);
        return barChart;
    }


    /**
     * 设置图表数据
     *
     * @param chart  图表
     * @param values 数据
     */
    public static void setChartData(LineChart chart, List<Entry> values) {
        LineDataSet lineDataSet;
        lineDataSet = new LineDataSet(values, "");
        // 设置曲线颜色
        lineDataSet.setColor(mContext.getResources().getColor(R.color.statics_line_color));
        // 设置平滑曲线
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        // 不显示坐标点的小圆点
        lineDataSet.setDrawCircles(true);
        // 不显示坐标点的数据
        lineDataSet.setDrawValues(true);
        // 不显示定位线
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(mContext.getResources().getColor(R.color.statics_line_fill_color));
        LineData data = new LineData(lineDataSet);

        chart.setData(data);
        chart.invalidate();


    }

    /**
     * 更新图表
     *
     * @param mLineChart 图表
     * @param dataSets   数据
     * @param refresh    是否自动横向放大
     * @param LabelCount X轴数值的总数
     */
    public static void notifyDataSetChanged(LineChart mLineChart, final List<ILineDataSet> dataSets, boolean refresh, int LabelCount) {
        LineData data = new LineData(dataSets);
        data.setValueTextSize(12f);
        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return entry.getY() + "";
            }
        });
        mLineChart.setData(data);
        //将图标按照比例进行缩放
        if (refresh) {
            Matrix mMatrix = new Matrix();
            mMatrix.postScale(scaleNum(LabelCount), 1f);
            mLineChart.getViewPortHandler().refresh(mMatrix, mLineChart, false);
        }
        mLineChart.animateY(1000, Easing.EasingOption.Linear);
        mLineChart.animateX(1000, Easing.EasingOption.Linear);
        mLineChart.invalidate();
    }

    /**
     * 计算横向放大的比例
     *
     * @param xCount
     * @return
     */
    private static float scaleNum(int xCount) {
        float scalePercent = 4f / 30f;
        return xCount * scalePercent;
    }


}
