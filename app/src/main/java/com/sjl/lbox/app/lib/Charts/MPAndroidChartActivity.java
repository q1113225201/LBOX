package com.sjl.lbox.app.lib.Charts;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MPAndroidChart图表简单使用
 * https://github.com/PhilJay/MPAndroidChart
 *
 * @author SJL
 * @date 2017/7/24
 */
public class MPAndroidChartActivity extends BaseActivity {

    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandroid_chart);

        initView();
    }

    private void initView() {
        initLineChart();
        initBarChart();
        initPieChart();
    }

    /**
     * 初始化饼状图
     */
    private void initPieChart() {
        PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
        List<PieEntry> pieList = new ArrayList<>();
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.GRAY};
        for (int i = 0; i < 5; i++) {
            pieList.add(new PieEntry(random.nextInt(30), "pie" + i));
        }
        PieDataSet pieDataSet = new PieDataSet(pieList, "");
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
    }

    /**
     * 初始化柱状图
     */
    private void initBarChart() {
        BarChart barChart = (BarChart) findViewById(R.id.barChart);
        List<BarEntry> barList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            barList.add(new BarEntry(i, random.nextInt(30)));
        }
        BarDataSet barDataSet = new BarDataSet(barList, "bar");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setContentDescription("BarChart");
        barChart.invalidate();
    }

    /**
     * 初始化折线图
     */
    private void initLineChart() {
        LineChart lineChart = (LineChart) findViewById(R.id.lineChart);
        List<Entry> lineList1 = new ArrayList<>();
        List<Entry> lineList2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            lineList1.add(new Entry(i, random.nextInt(30)));
            lineList2.add(new Entry(i, random.nextInt(30)));
        }
        LineDataSet lineDataSet1 = new LineDataSet(lineList1, "line1");
        LineDataSet lineDataSet2 = new LineDataSet(lineList2, "line2");
        //设置折线模式
        lineDataSet1.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet1.setColor(Color.GREEN);
        LineData lineData = new LineData(lineDataSet1, lineDataSet2);
        //设置数据
        lineChart.setData(lineData);
        //没数据的文字
        lineChart.setNoDataText("暂无数据");
        //缩放
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        //右侧坐标轴不显示
        lineChart.getAxisRight().setEnabled(false);
        //显示y轴
        lineChart.getXAxis().setDrawAxisLine(true);
        //x坐标显示在下面
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //最小间隔1
        lineChart.getXAxis().setSpaceMin(1);
        lineChart.setContentDescription("LineChart");
        lineChart.invalidate();
    }
}
