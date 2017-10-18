package com.infrastructure.base;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,
 * 由该类来接管程序,并记录发送错误报告.
 * 需要在Application中注册，为了要在程序启动器就监控整个程序。
 * Created by Mr.Z on 2016/8/26 0026.
 */
public class CrashHandler implements UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    private String APP_CACHE_PATH = "";

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler instance;
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null)
            instance = new CrashHandler();
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Context context, File dirPath) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            APP_CACHE_PATH = dirPath.getAbsolutePath();
        } else {
            return;
        }
        this.mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /*
     * 切换发生Crash所在的Activity
     */
    public void switchCrashActivity(Context context) {
        this.mContext = context;
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        //把crash发送到服务器
        //sendCrashToServer(mContext, ex);

        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出...",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        // 保存日志文件
        saveCrachInfoInFile(ex);
        return true;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrachInfoInFile(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";

            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                File dir = new File(APP_CACHE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(APP_CACHE_PATH + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }

            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }

    /**
     * 收集程序崩溃的相关信息
     *
     * @param ctx
     */
    public void sendCrashToServer(Context ctx, Throwable ex) {
        //取出版本号
        PackageManager pm = ctx.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e1) {
            e1.printStackTrace();
        }

        HashMap<String, String> exceptionInfo = new HashMap<String, String>();

        try {
            ActivityManager am = (ActivityManager) ctx
                    .getSystemService(Context.ACTIVITY_SERVICE);
            String pageName = ctx.getClass().getName();
            MemoryInfo mi = new MemoryInfo();
            am.getMemoryInfo(mi);
            String memoryInfo = "Memory info:" + mi.availMem + ",app holds:"
                    + mi.threshold + ",Low Memory:" + mi.lowMemory;

            ApplicationInfo appInfo = ctx.getPackageManager()
                    .getApplicationInfo(ctx.getPackageName(),
                            PackageManager.GET_META_DATA);
            String channelId = appInfo.metaData.getString("UMENG_CHANNEL");

            String version = ctx.getPackageManager().getPackageInfo(
                    ctx.getPackageName(), 0).versionName;


            exceptionInfo.put("PageName", pageName);
            exceptionInfo.put("ExceptionName", ex.getClass().getName());
            exceptionInfo.put("ExceptionType", "1");
            exceptionInfo.put("ExceptionsStackDetail", getStackTrace(ex));
            exceptionInfo.put("AppVersion", version);
            exceptionInfo.put("OSVersion", android.os.Build.VERSION.RELEASE);
            exceptionInfo.put("DeviceModel", android.os.Build.MODEL);
            exceptionInfo.put("DeviceId", getDeviceID(ctx));
            exceptionInfo.put("NetWorkType", String.valueOf(isWifi(mContext)));
            exceptionInfo.put("ChannelId", channelId);
            exceptionInfo.put("ClientType", "100");
            exceptionInfo.put("MemoryInfo", memoryInfo);

            final String rquestParam = exceptionInfo.toString();
            new Thread() {
                @Override
                public void run() {
                   // TODO: 2016/8/26 0026  崩溃日志上传
                }

            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @return String
     * @throws
     * @Title: getDeviceID
     * @Description: 获取手机设备号
     */
    public static final String getDeviceID(Context context) {
        String deviceId = null;
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
            tm = null;
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = Secure.getString(context.getContentResolver(),
                        Secure.ANDROID_ID);
            }

        } catch (Exception e) {
            deviceId = Secure.getString(context.getContentResolver(),
                    Secure.ANDROID_ID);
        }

        return deviceId;
    }

    private String getStackTrace(Throwable th) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);

        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        Throwable cause = th;
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        final String stacktraceAsString = result.toString();
        printWriter.close();

        return stacktraceAsString;
    }

    private static int isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return 1;
        }
        return 0;
    }
}
