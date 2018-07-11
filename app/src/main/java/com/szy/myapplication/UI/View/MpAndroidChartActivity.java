package com.szy.myapplication.UI.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.szy.lib.mpandroidchart.BarChartUtils;
import com.szy.lib.mpandroidchart.LineChartUtils;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 图表
 */
public class MpAndroidChartActivity extends BaseActivity {
    private LinearLayout linearLayout;
    private TextView tv_lineChart, tv_barChart;
    private List<String> xAxisValue = new ArrayList<>();
    private ArrayList<IBarDataSet> dataSets = new ArrayList<>();
    private List<ILineDataSet> lineDataSets = new ArrayList<>();
    private int[] data_color = new int[]{R.color.bardata1, R.color.bardata2, R.color.bardata3};
    private int min = 5, max = 99;
    private Random random;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_mp_android_chart;
    }

    @Override
    protected void initViews() {
        super.initViews();
        linearLayout = $(R.id.line_mp_chart);
        tv_lineChart = $(R.id.tv_chart_lineChart);
        tv_barChart = $(R.id.tv_chart_barChart);
        setTextTitleName("图表");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
        tv_barChart.setOnClickListener(onClickListener);
        tv_lineChart.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_chart_barChart://柱状图
                    setBarChart();
                    break;
                case R.id.tv_chart_lineChart://折线图
                    setLineChart();
                    break;
            }
        }
    };

    /**
     * 设置折线图表的样式属性
     */
    private void setLineChart() {
        linearLayout.removeAllViews();
        LineChart mLineChart = new LineChart(mContext);
        mLineChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(mLineChart);
        LineChartUtils.initChart(mLineChart, mContext, xAxisValue);
        LineChartUtils.notifyDataSetChanged(mLineChart, lineDataSets, true, xAxisValue.size());
    }

    /**
     * 设置柱状图表的样式属性
     */
    private void setBarChart() {
        linearLayout.removeAllViews();
        BarChart mBarChart = new BarChart(mContext);
        mBarChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(mBarChart);
        BarChartUtils.initChart(mBarChart, mContext, xAxisValue);
        BarChartUtils.notifyDataSetChanged(mBarChart, dataSets, true, xAxisValue.size());
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        for (int i = 0; i < 15; i++) {
            xAxisValue.add((i + 1) + "日");
        }
        random = new Random();
        setLineChartData();
        setBarChartData();
        setLineChart();
    }

    /**
     * 初始化折线图的数据
     *
     * @return
     */
    private List<ILineDataSet> setLineChartData() {


        lineDataSets.clear();
        //折线1
        List<Entry> values = new ArrayList<>();
        for (int i = 0; i < xAxisValue.size(); i++) {
            values.add(new Entry(i, random.nextInt(max) % (max - min + 1) + min));
        }
        LineDataSet mLineDataSet = new LineDataSet(values, "折线图");
        // 设置曲线颜色
        mLineDataSet.setColor(mContext.getResources().getColor(R.color.statics_line_color));
        // 设置平滑曲线
        mLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        // 不显示坐标点的小圆点
        mLineDataSet.setDrawCircles(true);
        // 不显示坐标点的数据
        mLineDataSet.setDrawValues(true);
        // 不显示定位线
        mLineDataSet.setHighlightEnabled(false);
        //设置范围的颜色
        mLineDataSet.setDrawFilled(true);
        mLineDataSet.setFillColor(mContext.getResources().getColor(R.color.statics_line_fill_color));
        lineDataSets.add(mLineDataSet);
        return lineDataSets;
    }

    /**
     * 初始化柱状图的数据
     *
     * @return
     */
    private ArrayList<IBarDataSet> setBarChartData() {
        dataSets.clear();
        for (int i = 0; i < 2; i++) {
            List<BarEntry> barEntries = new ArrayList<>();
            for (int j = 0; j < xAxisValue.size(); j++) {
                barEntries.add(new BarEntry(j, random.nextInt(max) % (max - min + 1) + min));
            }
            BarDataSet iBarDataSet = new BarDataSet(barEntries, "柱状图" + i);
            iBarDataSet.setColors(getResources().getColor(data_color[i]));
            dataSets.add(iBarDataSet);
        }
        return dataSets;
    }
}
