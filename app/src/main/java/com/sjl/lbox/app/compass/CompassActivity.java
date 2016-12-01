package com.sjl.lbox.app.compass;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.compass.view.CompassView;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.LogUtil;
import com.sjl.lbox.util.PermisstionUtil;
import com.sjl.lbox.util.ToastUtil;

/**
 * 指南针
 * LocationManager（位置服务）
 * SensorManager（传感器）
 *
 * @author SJL
 * @date 2016/9/18 22:42
 */
public class CompassActivity extends BaseActivity {
    private static final String tag = CompassActivity.class.getSimpleName();

    private TextView tvLocation;
    private TextView tvOrientation;
    private TextView tvSpeed;
    private CompassView compassView;

    //定位
    private LocationManager locationManager;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LogUtil.i(tag, "onLocationChanged:" + location.toString());
            tvLocation.setText("经度:" + location.getLongitude() + ",纬度:" + location.getLatitude());
            tvSpeed.setText("速度：" + location.getSpeed());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LogUtil.i(tag, "onStatusChanged:" + provider + "---" + status + "---" + extras.toString());
        }

        @Override
        public void onProviderEnabled(String provider) {
            LogUtil.i(tag, "onProviderEnabled:" + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            LogUtil.i(tag, "onProviderDisabled:" + provider);
        }
    };

    //传感器
    float[] gravity = new float[3];
    float[] geomagnetic = new float[3];
    private Sensor aSensor;
    private Sensor mSensor;
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                gravity = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                geomagnetic = event.values;
            }
            calculateOrientation();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            LogUtil.i(tag, "onSensorChanged:" + sensor.toString() + "," + accuracy);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        initView();
    }

    private void initView() {
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvOrientation = (TextView) findViewById(R.id.tvOrientation);
        tvSpeed = (TextView) findViewById(R.id.tvSpeed);
        compassView = (CompassView) findViewById(R.id.compassView);
        initLocation();
        initSensor();
    }

    /**
     * 定位初始化
     */
    private void initLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        PermisstionsUtil.checkSelfPermission(mContext, PermisstionsUtil.ACCESS_FINE_LOCATION, PermisstionsUtil.ACCESS_FINE_LOCATION_CODE, "位置权限", new PermisstionsUtil.PermissionResult() {
//            @Override
//            public void granted(int requestCode) {
//                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
//                        LogUtil.i(tag, "NETWORK_PROVIDER");
//                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//                    } else if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
//                        LogUtil.i(tag, "GPS_PROVIDER");
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//                    } else {
//                        ToastUtil.showToast(mContext, "无法定位");
//                    }
//                } else {
//                    LogUtil.i(tag, "无定位权限");
//                }
//
//            }
//
//            @Override
//            public void denied(int requestCode) {
//                ToastUtil.showToast(mContext, "位置权限被拒绝");
//            }
//        });
        PermisstionUtil.requestPermissions(mContext, new String[]{PermisstionUtil.ACCESS_FINE_LOCATION}, PermisstionUtil.ACCESS_FINE_LOCATION_CODE, "位置权限", new PermisstionUtil.OnPermissionResult() {
            @Override
            public void granted(int requestCode) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
                        LogUtil.i(tag, "NETWORK_PROVIDER");
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    } else if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
                        LogUtil.i(tag, "GPS_PROVIDER");
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    } else {
                        ToastUtil.showToast(mContext, "无法定位");
                    }
                } else {
                    LogUtil.i(tag, "无定位权限");
                }
            }

            @Override
            public void denied(int requestCode) {
                ToastUtil.showToast(mContext, "位置权限被拒绝");
            }
        });
    }

    /**
     * 初始化传感器
     */
    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(sensorEventListener, aSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        calculateOrientation();
    }

    /**
     * 计算方向
     */
    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, gravity, geomagnetic);
        SensorManager.getOrientation(R, values);

        //要经过一次数据格式的转换，转换为度
        values[0] = (float) Math.toDegrees(values[0]);
        //同步方向
        updateDirection(values[0]);

        int value0 = Math.round(values[0]);

        if (value0 == 180) {
            tvOrientation.setText("正南");
        }
        if (value0 < 180 && value0 > 90) {
            tvOrientation.setText("南偏西" + (180 - value0) + "度");
        }
        if (value0 == 90) {
            tvOrientation.setText("正西");
        }
        if (value0 < 90 && value0 > 0) {
            tvOrientation.setText("北偏西" + value0 + "度");
        }
        if (value0 == 0) {
            tvOrientation.setText("正北");
        }
        if (value0 < 0 && value0 > -90) {
            tvOrientation.setText("北偏东" + (-value0) + "度");
        }
        if (value0 == -90) {
            tvOrientation.setText("正东");
        }
        if (value0 < -90 && value0 > -180) {
            tvOrientation.setText("南偏东" + (180 + value0) + "度");
        }
        if (value0 == -180) {
            tvOrientation.setText("正南");
        }
    }

    private void updateDirection(float direction) {
        compassView.updateDirection(direction);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(locationListener);
        }
        sensorManager.unregisterListener(sensorEventListener, aSensor);
        sensorManager.unregisterListener(sensorEventListener, mSensor);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
