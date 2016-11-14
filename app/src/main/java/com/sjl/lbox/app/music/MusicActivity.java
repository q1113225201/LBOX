package com.sjl.lbox.app.music;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.MusicUtil;
import com.sjl.lbox.util.UriUtil;
/**
 * 音乐选择播放演示
 * 
 * @author SJL
 * @date 2016/11/14 22:21
 */
public class MusicActivity extends BaseActivity implements View.OnClickListener {

    private Button btnChooseMusic;
    private Button btnPlayMusic;
    private Button btnStopMusic;
    private TextView tvMusic;
    private String musicPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        initView();
    }

    private void initView() {
        btnChooseMusic = (Button) findViewById(R.id.btnChooseMusic);
        btnChooseMusic.setOnClickListener(this);
        btnPlayMusic = (Button) findViewById(R.id.btnPlayMusic);
        btnPlayMusic.setOnClickListener(this);
        btnStopMusic = (Button) findViewById(R.id.btnStopMusic);
        btnStopMusic.setOnClickListener(this);

        tvMusic = (TextView) findViewById(R.id.tvMusic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnChooseMusic:
                MusicUtil.getMusicList(mContext, new MusicUtil.OnMusicResult() {
                    @Override
                    public void musicResult(String name, Uri uri, String hint) {
                        //获取音乐路径
                        musicPath = UriUtil.uriToFile(mContext,uri);
                        tvMusic.setText(name);
                    }
                });
                break;
            case R.id.btnPlayMusic:
                if(!TextUtils.isEmpty(musicPath)){
                    MusicUtil.playMusic(mContext,musicPath);
                }
                break;
            case R.id.btnStopMusic:
                MusicUtil.stopMusic();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MusicUtil.onActivityResult(mContext,requestCode,resultCode,data);
    }
}
