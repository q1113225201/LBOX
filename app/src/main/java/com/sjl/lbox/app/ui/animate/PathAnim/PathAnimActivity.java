package com.sjl.lbox.app.ui.animate.PathAnim;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.animate.PathAnim.view.PathView;
import com.sjl.lbox.base.BaseActivity;

/**
 * 轨迹动画
 *
 * @author SJL
 * @date 2017/3/21
 */
public class PathAnimActivity extends BaseActivity implements View.OnClickListener {

    private Spinner spinner;
    private Button btnPath;
    private PathView pathView;
    private FloatingActionButton btnFloat;

    //当前绘制路径
    private Path currentPath;
    private Path linePath;
    private Path circlePath;
    private Path quadPath;
    private Path cubicPath;
    private ObjectAnimator pathAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_anim);

        initView();
    }

    private void initView() {
        spinner = (Spinner) findViewById(R.id.spinner);
        btnPath = (Button) findViewById(R.id.btnPath);
        btnPath.setOnClickListener(this);
        pathView = (PathView) findViewById(R.id.pathView);
        btnFloat = (FloatingActionButton) findViewById(R.id.btnFloat);
        initData();
    }


    private void initData() {
        //线性
        linePath = new Path();
        linePath.moveTo(100, 100);
        linePath.lineTo(400, 400);
        //圆
        circlePath = new Path();
        circlePath.addCircle(300,300,200, Path.Direction.CW);
        //二阶贝塞尔
        quadPath = new Path();
        quadPath.moveTo(100, 100);
        quadPath.quadTo(300, 400, 500, 200);
        //三阶贝塞尔
        cubicPath = new Path();
        cubicPath.moveTo(100, 100);
        cubicPath.cubicTo(200, 600, 400, 200, 700, 600);
        pathTypeValues = new Path[]{linePath,circlePath, quadPath, cubicPath};

        //初始化下拉菜单
        spinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_expandable_list_item_1, pathTypeNames));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setPathType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPath:
                pathAnimator.start();
                break;
        }
    }

    private String[] pathTypeNames = new String[]{"线性","圆", "二阶内塞尔", "三阶贝塞尔"};
    private Path[] pathTypeValues;

    /**
     * 设置路径类型
     *
     * @param index
     */
    private void setPathType(int index) {
        currentPath = pathTypeValues[index];
        pathView.setPath(pathTypeValues[index]);
        pathAnimator = ObjectAnimator.ofFloat(btnFloat, "x", "y", currentPath);
        pathAnimator.setInterpolator(new DecelerateInterpolator());
        pathAnimator.setDuration(3000);
    }
}
