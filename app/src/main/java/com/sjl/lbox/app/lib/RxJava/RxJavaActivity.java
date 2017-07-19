package com.sjl.lbox.app.lib.RxJava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.network.http.NoHttp.http.HttpRequestCallback;
import com.sjl.lbox.app.network.http.NoHttp.util.NoHttpUtil;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "RxJavaActivity";
    private TextView tvShow;
    private Button btnSimpleUse;
    private Button btnThread;
    private Button btnOperatorMap;
    private Button btnRxBusRegister;
    private Button btnRxBusDoPost;
    private Button btnRxBusDestory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        initView();
    }

    private void initView() {
        tvShow = (TextView) findViewById(R.id.tvShow);
        btnSimpleUse = (Button) findViewById(R.id.btnSimpleUse);
        btnSimpleUse.setOnClickListener(this);
        btnThread = (Button) findViewById(R.id.btnThread);
        btnThread.setOnClickListener(this);
        btnOperatorMap = (Button) findViewById(R.id.btnOperatorMap);
        btnOperatorMap.setOnClickListener(this);
        btnRxBusRegister = (Button) findViewById(R.id.btnRxBusRegister);
        btnRxBusRegister.setOnClickListener(this);
        btnRxBusDoPost = (Button) findViewById(R.id.btnRxBusDoPost);
        btnRxBusDoPost.setOnClickListener(this);
        btnRxBusDestory = (Button) findViewById(R.id.btnRxBusDestory);
        btnRxBusDestory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        tvShow.setText("");
        switch (v.getId()) {
            case R.id.btnSimpleUse:
                simpleUse();
                break;
            case R.id.btnThread:
                threadSchedule();
                break;
            case R.id.btnOperatorMap:
                operatorMap();
                break;
            case R.id.btnRxBusRegister:
                registerRxBus();
                break;
            case R.id.btnRxBusDoPost:
                RxBus.getInstance().post(1);
                break;
            case R.id.btnRxBusDestory:
                RxBus.getInstance().post("destory");
                RxBus.getInstance().post("start");
                break;
        }
    }

    /**
     * 注册RxBus响应
     */
    private void registerRxBus() {
        RxBus.getInstance().subscribe(this,Integer.class, new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer s) throws Exception {
                log("RxBus Integer normal accept:"+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                log("RxBus Integer error accept:"+throwable.getMessage());
            }
        });
        RxBus.getInstance().subscribe(this, String.class, new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                log("RxBus String normal accept:"+s);
                if("start".equals(s)){
                    startActivity(new Intent(mContext, RxJavaActivity.class));
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                log("RxBus String error accept:"+throwable.getMessage());
            }
        });
    }

    /**
     * 操作符map
     * map是一对一的数据类型转换
     * flatMap可以进行一对多的数据类型转换
     */
    private void operatorMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Random random = new Random();
                e.onNext(random.nextInt(90));
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        Thread.sleep(2000);
                        log("Integer = " + integer);
                        if (integer > 60) {
                            return "success";
                        } else if (integer > 30) {
                            return "failure";
                        } else {
                            //制造异常
                            int i = 1 / 0;
                            return "error";
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        log("String = " + s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        log("subscribe normal accept = " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        log("subscribe error accept = " + throwable.getMessage());
                    }
                });
    }

    /**
     * 线程调度
     */
    private void threadSchedule() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                log("当前线程：" + Thread.currentThread().getName());
                e.onNext("1");
                e.onComplete();
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        log("当前线程 doOnNext：" + Thread.currentThread().getName());
                        log("accept：" + s);
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        log("当前线程 doOnNext：" + Thread.currentThread().getName());
                        log("accept：" + s);
                        String url = "http://op.juhe.cn/robot/index";
                        int what = 0;
                        Map<String, String> postMap = new HashMap<String, String>();
                        postMap.put("info", "测试");
                        postMap.put("key", "09c309a2e401d78bb768d4cdb585c79c");
                        NoHttpUtil.sendPostRequest(url, what, postMap, new HttpRequestCallback<String>() {
                            @Override
                            public void onSucceed(String data) {
                                log(data);
                            }

                            @Override
                            public void onFailed(String error) {
                                log("error:" + error);
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String s) throws Exception {
                        log("当前线程 subscribe：" + Thread.currentThread().getName());
                        log("accept：" + s);
                    }
                });
    }

    /**
     * 简单使用
     */
    private void simpleUse() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                log("subscribe:1");
                e.onNext(1);
                log("subscribe:2");
                e.onNext(2);
                log("subscribe onComplete");
                e.onComplete();
                log("subscribe:3");
                e.onNext(3);
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                log("onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                log("onNext:" + integer);
                if (integer == 1) {
                    //解除订阅
//                    disposable.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                log("onError:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                log("Observer onComplete");
            }
        });
    }

    private void log(final String msg) {
        LogUtil.i(TAG, msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvShow.append(msg + "\n");
            }
        });
    }
}
