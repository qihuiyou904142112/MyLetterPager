package com.qhy.letter.activity;


import android.Manifest;
import android.app.DialogFragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qhy.letter.R;
import com.qhy.letter.fragment.AddFragment;
import com.qhy.letter.fragment.ChatFragment;
import com.qhy.letter.fragment.HomeFragment;
import com.qhy.letter.fragment.LifeFragment;
import com.qhy.letter.fragment.PersonalFragment;
import com.qhy.letter.utils.CommonUtil;
import com.qhy.letter.utils.T;
import com.qhy.letter.view.HintDialogFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private long exitTime = 0;
    private TextView mMain_home, mMain_chat, mMain_life, mMain_personal;
    private ImageView mMain_add;
    private ArrayList<Fragment> mFsList = new ArrayList<>();
    private static final int LOCATION_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }

        setContentView(R.layout.activity_main);

        initView();
        initTabFragment();
        initListener();
        checkLocationPermission();
        checkStoragePermission();
    }


    private void initView() {
        mMain_home = (TextView) findViewById(R.id.main_home);
        mMain_chat = (TextView) findViewById(R.id.main_chat);
        mMain_add = (ImageView) findViewById(R.id.main_add);
        mMain_life = (TextView) findViewById(R.id.main_life);
        mMain_personal = (TextView) findViewById(R.id.main_personal);

    }


    private void initListener() {
        mMain_home.setOnClickListener(this);
        mMain_chat.setOnClickListener(this);
        mMain_add.setOnClickListener(this);
        mMain_life.setOnClickListener(this);
        mMain_personal.setOnClickListener(this);
    }

    private void initTabFragment() {
        mFsList.add(new HomeFragment());
        mFsList.add(new LifeFragment());
        mFsList.add(new AddFragment());
        mFsList.add(new ChatFragment());
        mFsList.add(new PersonalFragment());

        changeFrag(0);

    }

    private void changeFrag(int curIndex) {
        for (int i = 0; i < mFsList.size(); i++) {
            if (i == curIndex) {
                showFragment(mFsList.get(i));
            } else {
                hideFragment(mFsList.get(i));
            }
        }
    }


    protected void hideFragment(Fragment currFragment) {
        if (currFragment == null)
            return;
        FragmentTransaction currFragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        currFragment.onPause();
        if (currFragment.isAdded()) {
            currFragmentTransaction.hide(currFragment);
            currFragmentTransaction.commitAllowingStateLoss();
        }
    }

    protected void showFragment(Fragment startFragment) {
        if (startFragment == null)
            return;
        FragmentTransaction startFragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (!startFragment.isAdded()) {
            startFragmentTransaction.add(R.id.fl_main_content, startFragment);
        } else {
            startFragment.onResume();
            startFragmentTransaction.show(startFragment);
        }
        startFragmentTransaction.commitAllowingStateLoss();
    }


    /**
     * 导航栏TOP图标
     *
     * @param id
     * @param view
     */
    private void style(TextView view,int id) {
        view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, ContextCompat.getDrawable(this, id), null, null);
    }

    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - exitTime) > 3000) {
            T.showLong(this, "再按一次退出信笺");
            exitTime = System.currentTimeMillis();
            return;
        }
        CommonUtil.exitApp(MainActivity.this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_home:

                changeFrag(0);

                style(mMain_home,R.mipmap.tab_news_selected);
                style(mMain_life,R.mipmap.tab_find_normal);
                style(mMain_chat,R.mipmap.tab_battle_normal);
                style(mMain_personal,R.mipmap.tab_auxiliary_normal);

                break;
            case R.id.main_life:

                changeFrag(1);


                style(mMain_home,R.mipmap.tab_news_normal);
                style(mMain_life,R.mipmap.tab_find_selected);
                style(mMain_chat,R.mipmap.tab_battle_normal);
                style(mMain_personal,R.mipmap.tab_auxiliary_normal);

                break;
            case R.id.main_add:

                changeFrag(2);


                style(mMain_home,R.mipmap.tab_news_normal);
                style(mMain_life,R.mipmap.tab_find_normal);
                style(mMain_chat,R.mipmap.tab_battle_normal);
                style(mMain_personal,R.mipmap.tab_auxiliary_normal);

                break;
            case R.id.main_chat:

                changeFrag(3);

                style(mMain_home,R.mipmap.tab_news_normal);
                style(mMain_life,R.mipmap.tab_find_normal);
                style(mMain_chat,R.mipmap.tab_battle_selected);
                style(mMain_personal,R.mipmap.tab_auxiliary_normal);

                break;
            case R.id.main_personal:

                changeFrag(4);

                style(mMain_home,R.mipmap.tab_news_normal);
                style(mMain_life,R.mipmap.tab_find_normal);
                style(mMain_chat,R.mipmap.tab_battle_normal);
                style(mMain_personal,R.mipmap.tab_auxiliary_selected);

                break;
        }
    }

    private void checkLocationPermission() {
        // 检查是否有定位权限
        // 检查权限的方法: ContextCompat.checkSelfPermission()两个参数分别是Context和权限名.
        // 返回PERMISSION_GRANTED是有权限，PERMISSION_DENIED没有权限
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //没有权限，向系统申请该权限。
            Log.i("MY","没有权限");
            requestPermission(LOCATION_PERMISSION_CODE);
        } else {
            //已经获得权限，则执行定位请求。
            Toast.makeText(MainActivity.this, "已获取定位权限",Toast.LENGTH_SHORT).show();
        }
    }

    private void checkStoragePermission() {
        // 检查是否有存储的读写权限
        // 检查权限的方法: ContextCompat.checkSelfPermission()两个参数分别是Context和权限名.
        // 返回PERMISSION_GRANTED是有权限，PERMISSION_DENIED没有权限
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //没有权限，向系统申请该权限。
            Log.i("MY","没有权限");
            requestPermission(STORAGE_PERMISSION_CODE);
        } else {
            //同组的权限，只要有一个已经授权，则系统会自动授权同一组的所有权限，比如WRITE_EXTERNAL_STORAGE和READ_EXTERNAL_STORAGE
            Toast.makeText(MainActivity.this, "已获取存储的读写权限",Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermission(int permissioncode) {
        String permission = getPermissionString(permissioncode);
        if (!IsEmptyOrNullString(permission)){
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    permission)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                if(permissioncode == LOCATION_PERMISSION_CODE) {
                    DialogFragment newFragment = HintDialogFragment.newInstance(R.string.location_description_title,
                            R.string.location_description_why_we_need_the_permission,
                            permissioncode);
                    newFragment.show(MainActivity.this.getFragmentManager(), HintDialogFragment.class.getSimpleName());
                } else if (permissioncode == STORAGE_PERMISSION_CODE) {
                    DialogFragment newFragment = HintDialogFragment.newInstance(R.string.storage_description_title,
                            R.string.storage_description_why_we_need_the_permission,
                            permissioncode);
                    newFragment.show(MainActivity.this.getFragmentManager(), HintDialogFragment.class.getSimpleName());
                }

            } else {
                Log.i("MY","返回false 不需要解释为啥要权限，可能是第一次请求，也可能是勾选了不再询问");
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{permission}, permissioncode);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this,"定位权限已获取",Toast.LENGTH_SHORT).show();
                    Log.i("MY","定位权限已获取");
                } else {
                    Toast.makeText(MainActivity.this,"定位权限被拒绝",Toast.LENGTH_SHORT).show();
                    Log.i("MY","定位权限被拒绝");
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                        DialogFragment newFragment = HintDialogFragment.newInstance(R.string.location_description_title,
                                R.string.location_description_why_we_need_the_permission,
                                requestCode);
                        newFragment.show(MainActivity.this.getFragmentManager(), HintDialogFragment.class.getSimpleName());
                        Log.i("MY","false 勾选了不再询问，并引导用户去设置中手动设置");

                        return;
                    }
                }
                return;
            }
            case STORAGE_PERMISSION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this,"存储权限已获取",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,"存储权限被拒绝",Toast.LENGTH_SHORT).show();
                    Log.i("MY","定位权限被拒绝");
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        DialogFragment newFragment = HintDialogFragment.newInstance(R.string.storage_description_title,
                                R.string.storage_description_why_we_need_the_permission,
                                requestCode);
                        newFragment.show(MainActivity.this.getFragmentManager(), HintDialogFragment.class.getSimpleName());
                        Log.i("MY","false 勾选了不再询问，并引导用户去设置中手动设置");
                    }
                    return;
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public static boolean IsEmptyOrNullString(String s) {
        return (s == null) || (s.trim().length() == 0);
    }

    private String getPermissionString(int requestCode){
        String permission = "";
        switch (requestCode){
            case LOCATION_PERMISSION_CODE:
                permission = Manifest.permission.ACCESS_FINE_LOCATION;
                break;
            case STORAGE_PERMISSION_CODE:
                permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                break;
        }
        return permission;
    }

}
