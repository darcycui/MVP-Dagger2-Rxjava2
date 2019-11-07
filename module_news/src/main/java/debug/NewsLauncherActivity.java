package debug;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zenglb.framework.news.DynamicProxy.Sell;
import com.zenglb.framework.news.DynamicProxy.Vendor;
import com.zenglb.framework.news.R;
import com.zenglb.framework.news.fileDownLoad.FileDownLoadObserver;
import com.zenglb.framework.news.handylife.NewsPackageActivity;
import com.zenglb.framework.news.http.NewsApiService;
import com.zlb.Sp.SPDao;
import com.zlb.base.BaseActivity;
import com.zlb.http.HttpRetrofit;
import com.zlb.utils.antigpsfake.AntiGPSFake;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 单独的Module 也需要Launcher
 *
 */
public class NewsLauncherActivity extends BaseActivity {
    private static final int FINISH_LAUNCHER = 0;

    @Inject
    SPDao spDao;

    @Override
    public void msgManagement(int what) {
        //没有登陆过就去指导页面（Guide Page）
        Intent i1 = new Intent();
        i1.setClass(NewsLauncherActivity.this, NewsPackageActivity.class);
        startActivity(i1);
        NewsLauncherActivity.this.finish();
    }


    @Override
    public int getLayoutId() {
        return R.layout.launcher_layout;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.MyAppTheme);
        super.onCreate(savedInstanceState);
        setToolBarVisible(View.GONE);  //这里是不需要Base 中的Toolbar,不要的情况毕竟是少数

//        setContentView(R.layout.launcher_layout);

        AntiGPSFake.areThereMockPermissionApps(this);
        AntiGPSFake.isMockSettingsON(this);

        Log.e("AntiGPSFake",AntiGPSFake.isMockSettingsON(this)+"  "+AntiGPSFake.areThereMockPermissionApps(this));

        sendMsg(FINISH_LAUNCHER, 2500);


        //动态代理测试
        Sell sell = (Sell) Proxy.newProxyInstance(Sell.class.getClassLoader(), new Class<?>[]{Sell.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.e("HHHH", "Before");
                Object object = method.invoke(new Vendor(), args);
                Log.e("HHHH", "After");
                return object;
            }
        });

        sell.ad();



        //==========================

        Sell sell1=(Sell) Proxy.newProxyInstance(Sell.class.getClassLoader(), new Class<?>[]{Sell.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.e("HHHH", "Before");
                Object object = method.invoke(new Vendor(), args);
                Log.e("HHHH", "After");
                return object;
            }
        });

    }


}
