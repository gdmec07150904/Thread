package com.example.chenkangjin.thread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    private int seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        Date theLastDay = new Date();
        Date toDay = new Date();
        seconds = (int) (theLastDay.getTime()-toDay.getTime()/1000);
    }
    public void threadclass(View v){
        class MyThread extends Thread{
            private Random random;
            public MyThread(String tname) {
                super(tname);
                random = new Random();
            }
            @Override
            public void run() {
                super.run();
                for (int i =0; i<10;i++){
                    System.out.println(getName()+":"+i);
                    try {
                        sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(getName()+"完成");
            }
        }
        MyThread th1 = new MyThread("线程1");
        MyThread th2 = new MyThread("线程2");
        th1.start();
        th2.start();
    }
    public void runnable(View v){
        class MyRunnable implements Runnable{
            Random random;
            String name;
            public MyRunnable(String name){
                this.name = name;
                random = new Random();
            }
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    System.out.println(name+":"+i);
                    try {
                        Thread.sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(name+":"+"完成");
            }
        }
        Thread th1 = new Thread(new MyRunnable("线程1"));
        Thread th2 = new Thread(new MyRunnable("线程2"));
        th1.start();
        th2.start();
    }
    public void timetask(View v){
        class Ttask extends TimerTask{
            Random random;
            String name;
            public Ttask(String name){
                this.name = name;
                random = new Random();
            }
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    System.out.println(name+":"+i);
                    try {
                        Thread.sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(name+":"+"完成");
            }
        }
        Ttask tt1 = new Ttask("线程1");
        Ttask tt2 = new Ttask("线程2");
        Timer timer1 = new Timer();
        Timer timer2 = new Timer();
        timer1.schedule(tt1,0);
        timer2.schedule(tt2,0);
    }
    public void handlermessage(View v){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        showmsg(String.valueOf(msg.arg1+msg.getData().get("attach").toString()));
                }
            }
        };
        class Ttask extends TimerTask{
            int countdown;
            double achievement1 = 1,achievement2 = 1;
            public Ttask(int seconds){
                countdown = seconds;
            }
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 1;
                msg.arg1 = countdown--;
                achievement1 = achievement1*1.01;
                achievement2 = achievement2*1.02;
                Bundle bundle = new Bundle();
                bundle.putString("attach","\n努力学习%1"+achievement1+"\n努力学习%2"+achievement2);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }
        Timer timer = new Timer();
        timer.schedule(new Ttask(seconds),1,1000);
    }
    public void showmsg(String msg){
        tv1.setText(msg);
    }
}
