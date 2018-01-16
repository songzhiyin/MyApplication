package com.szy.lib.mpandroidchart;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 图表工具
 * Created by yangle on 2016/11/29.
 */
public class BarChartUtils {


    private static Activity mContext;

    /**
     * 初始化图表
     *
     * @param barChart 原始图表
     * @return 初始化后的图表
     */
    public static BarChart initChart(BarChart barChart, Activity Context, final List<String> xAxisValue) {
        mContext = Context;
        barChart.getDescription().setEnabled(false);//设置描述
        barChart.setHighlightFullBarEnabled(true);//不设置高亮显示
        //将其设置为false，Chart以防止点击手势突出显示值。仍然可以通过拖动或以编程方式突出显示值。默认值：true
        barChart.setHighlightPerTapEnabled(true);
        barChart.setDoubleTapToZoomEnabled(false);//将此设置为false可禁止通过双击来缩放图表
        barChart.setPinchZoom(true);//设置按比例放缩柱状图
        CustomMarkerView mv = new CustomMarkerView(Context,
                R.layout.custom_marker_view_layout);
        barChart.setMarker(mv);//设置点击柱状图的柱子时弹出的pop
        //x坐标轴设置
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-20);//X轴字体旋转度数
        xAxis.setGranularity(1f);//设置X轴两点之间的最小距离
        if (xAxisValue != null) {
            xAxis.setLabelCount(xAxisValue.size());//设置X轴坐标上的个数
        }
        xAxis.setCenterAxisLabels(true);//设置标签居中
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
        leftAxis.setAxisMinimum(0);//设置Y轴最小值
//        leftAxis.setAxisMaximum(maximun);//设置Y轴最大值
        leftAxis.setLabelCount(6);//设置Y轴刻度值的数量
        //        leftAxis.setLabelCount(6,true);//强制设置Y轴刻度值的数量
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                return v + "";
            }
        });
        barChart.getAxisRight().setEnabled(false);
        //图例设置
        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setMaxSizePercent(1.5f); // 设置，默认0.95f,图例最大尺寸区域占图表区域之外的比例
        legend.setYEntrySpace(0);  // 设置垂直图例间间距，默认0
        legend.setFormToTextSpace(5);    // 设置图例的标签与图形之间的距离，默认5dp
        legend.setWordWrapEnabled(true);//设置当图例无法在一行展示完整时，可以进行换行
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12f);
        //不显示图例
        legend.setEnabled(true);
        return barChart;
    }


    /**
     * 更新图表
     *
     * @param chart       图表
     * @param barDataSets 数据
     * @param refresh     是否自动横向放大
     * @param LabelCount  X轴数值的总数
     */
    public static void notifyDataSetChanged(BarChart chart, final ArrayList<IBarDataSet> barDataSets, boolean refresh, final int LabelCount) {
        float groupSpace = 0.14f;//两组柱图之间的距离
        float barSpace = 0.00f;//一组柱图的间隔
        float barWidth = (1 - groupSpace) / barDataSets.size() - barSpace;//一组柱图的宽度
        // (0.45 + 0.03) * 2 + 0.04 = 1，即一个间隔为一组，包含两个柱图 -> interval per "group"
//判断图标是否已经设置过数据
        BarData barData = new BarData(barDataSets);
        barData.setValueTextSize(12f);
        barData.setBarWidth(0.45f);
        barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return StringUtils.double2String(value, 2);
            }
        });
        chart.setData(barData);

        //将图标按照比例进行缩放
        if (refresh) {
            Matrix mMatrix = new Matrix();
            mMatrix.postScale(scaleNum(LabelCount), 1f);
            chart.getViewPortHandler().refresh(mMatrix, chart, false);
        }
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(0f);
        chart.getXAxis().setAxisMaximum(chart.getBarData().getGroupWidth(groupSpace, barSpace) * LabelCount + 0);
        chart.groupBars(0f, groupSpace, barSpace);
        chart.animateY(1000, Easing.EasingOption.Linear);
        chart.animateX(1000, Easing.EasingOption.Linear);
        chart.invalidate();
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
