package com.yly.kind.commonutils_library.demoActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yly.kind.commonutils_library.R;

public class HelloJniActivity extends AppCompatActivity {
    static {
        System.loadLibrary("helloJni");//加载so文件，不要带上前缀lib和后缀.so
    }

    //    public native String helloJni();//定义本地方法接口，这个方法类似虚方法，实现是用c或者c++实现的
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_jni);
    }
}
