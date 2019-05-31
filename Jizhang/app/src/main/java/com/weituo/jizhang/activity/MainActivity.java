package com.weituo.jizhang.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weituo.jizhang.R;
import com.weituo.jizhang.adapter.ListAdapter;
import com.weituo.jizhang.model.DayModel;
import com.weituo.jizhang.service.KeepLifeService;
import com.weituo.jizhang.utils.ScreenShotUtils;
import com.zhy.changeskin.SkinManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout root;
    private ListView listView;
    private ImageView ivAdd;
    private List<DayModel> mDatas = new ArrayList<>();
    private ListAdapter mListAdapter;
    private TextView mTvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //换肤功能页面注册
        SkinManager.getInstance().register(this);

        initView();
        initData();
    }

    private void initView() {
        root = findViewById(R.id.root);
        ivAdd = findViewById(R.id.iv_add);
        ivAdd.setOnClickListener(this);
        listView = findViewById(R.id.list_view);
        View headView = LayoutInflater.from(this).inflate(R.layout.list_header, null);
        View footView = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
        mTvTotal = footView.findViewById(R.id.tv_total);
        listView.addHeaderView(headView);
        listView.addFooterView(footView);

        mListAdapter = new ListAdapter(mDatas);
        listView.setAdapter(mListAdapter);
    }


    private void initData() {
        int total = 0;
        for(int i = 0; i < 30; i++) {
            DayModel dayModel = new DayModel();
            dayModel.setLaunch(20);
            dayModel.setDinner(30);
            if(i%3 == 0){
                dayModel.setOther(30);
                dayModel.setRemark("我丢雷楼某" + i);
            }

            dayModel.setTotal(dayModel.getLaunch() + dayModel.getDinner() + dayModel.getOther());
            mDatas.add(dayModel);
            total += dayModel.getTotal();
        }
        mTvTotal.setText(total + "￥");
        mListAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add :
//                showPop();

//                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                    requestPermissions(permissions,100);
//                }else{
//                    saveBitmap();
////                    ScreenShotUtils.shotScreen(this);
//                }


                startService(new Intent(this, KeepLifeService.class));

                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        saveBitmap();
//        ScreenShotUtils.shotScreen(this);
    }

    private void showPop() {
//        MyPopupwindow myPopupwindow = new MyPopupwindow(this);
//        myPopupwindow.showAtLocation(root, Gravity.CENTER,0,0);
    }


    private void saveBitmap() {
        Bitmap bitmap = ScreenShotUtils.shotLargeScreen(this, listView);
        ScreenShotUtils.updatePhotos(this,bitmap);
    }

}
