package com.example.mama.slidingview;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private Button btn,btn2;
    private TextView tv_out;
    private LinearLayout ll_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
    }

    private void initView() {

        ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(btn,1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(btn2,2);
            }
        });

        ll_layout.setOnTouchListener(this);

    }

    View popupView;
    PopupWindow window;
    private int types =0;
    private void showDialog(View viewss,int type) {
        types=type;
        //应用区域的宽高
        Rect outRect1 = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        if(type==1){
            popupView = getLayoutInflater().inflate(R.layout.dialoglayout, null);
        }else if(type==2){
            popupView = getLayoutInflater().inflate(R.layout.dialoglayout2, null);
        }
        tv_out = (TextView) popupView.findViewById(R.id.tv_out);
        tv_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Message msg = new Message();
                Bundle budle = new Bundle();
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        msg.what=1;
                        budle.putDouble("downx",event.getX());
                        budle.putDouble("downy",event.getY());
                        msg.setData(budle);
                        handler.sendMessage(msg);
                        Log.d("ACTION_DOWN",":::::::::::X"+event.getX()+":::::::::::Y"+event.getY()+":::::::::::RX"+event.getRawX()+":::::::::::RY"+event.getRawY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        msg.what=3;
                        budle.putDouble("downx",event.getX());
                        budle.putDouble("downy",event.getY());
                        msg.setData(budle);
                        handler.sendMessage(msg);
                        Log.d("2222222","333333333333");
                        Log.d("ACTION_MOVE",":::::::::::X"+event.getX()+":::::::::::Y"+event.getY()+":::::::::::RX"+event.getRawX()+":::::::::::RY"+event.getRawY());
                        break;
                    case MotionEvent.ACTION_UP:
                        msg.what=2;
                        budle.putDouble("downx",event.getX());
                        budle.putDouble("downy",event.getY());
                        msg.setData(budle);
                        handler.sendMessage(msg);
                        Log.d("555555555","66666666666");
                        Log.d("ACTION_UP",":::::::::::X"+event.getX()+":::::::::::Y"+event.getY()+":::::::::::RX"+event.getRawX()+":::::::::::RY"+event.getRawY());
                        break;
                }

                return true;
            }
        });
        // 创建PopupWindow对象，指定宽度和高度
        window = new PopupWindow(popupView, outRect1.width(),outRect1.height());
        // 设置动画
        if(type==1){
            window.setAnimationStyle(R.style.popup_window_anim);
        }else if(type==2){
            window.setAnimationStyle(R.style.popup_window_anim2);
        }
        // 设置背景颜色
        //Color.parseColor("#F8F8F8")
        window.setBackgroundDrawable(new ColorDrawable());
        // 设置可以获取焦点
        window.setFocusable(true);
        // 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // 更新popupwindow的状态
        window.update();
        // 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(viewss, 0,0, Gravity.CENTER);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("ACTION_DOWN",":::::::::::X"+event.getX()+":::::::::::Y"+event.getY()+":::::::::::RX"+event.getRawX()+":::::::::::RY"+event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("2222222","333333333333");
                Log.d("ACTION_MOVE",":::::::::::X"+event.getX()+":::::::::::Y"+event.getY()+":::::::::::RX"+event.getRawX()+":::::::::::RY"+event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                Log.d("555555555","66666666666");
                Log.d("ACTION_UP",":::::::::::X"+event.getX()+":::::::::::Y"+event.getY()+":::::::::::RX"+event.getRawX()+":::::::::::RY"+event.getRawY());
                break;
        }
        //必须返回true。如果返回false，只有MotionEvent.ACTION_DOWN生效，ACTION_MOVE和ACTION_UP失效
        return true;
    }

    private double downX=0;
    private double downY=0;
    private double upX=0,upY=0;
    private double moveX=0,moveY=0;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle budle;
            switch (msg.what){
                case 1:
                    budle=msg.getData();
                    downX=budle.getDouble("downx");
                    downY=budle.getDouble("downy");
                    break;
                case 2:
                    budle=msg.getData();
                    upX=budle.getDouble("downx");
                    upY=budle.getDouble("downy");
                    Log.d("999999999",":::::::X"+(upX-downX)+":::::::::::y"+(upY-downY));
                    break;
                case 3:
                    budle=msg.getData();
                    moveX=budle.getDouble("downx");
                    moveY=budle.getDouble("downy");
                    Log.d("999999999",":::::::X"+(upX-downX)+":::::::::::y"+(upY-downY));
                    if(types==1){
                        if((downX-upX)>200){
                            window.dismiss();
                        }
                    }else if(types==2){
                        if((upX-downX)>200){
                            window.dismiss();
                        }
                    }

                    break;
            }
        }
    };
}
