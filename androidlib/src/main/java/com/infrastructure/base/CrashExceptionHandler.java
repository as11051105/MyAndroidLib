package com.infrastructure.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class CrashExceptionHandler implements UncaughtExceptionHandler {

    public static final int NO_MESSAGE = 0;

    public static final int NORMAL = 1;

    private Context mContext;

    private File dirFilePath = null;

    private int mode;

    private UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    public CrashExceptionHandler(Context context, File dirPath) {
        this(context, NORMAL, dirPath);
    }

    public CrashExceptionHandler(Context context, int mode, File dirPath) {
        if (mode != NO_MESSAGE && mode != NORMAL) {
            this.mode = NORMAL;
        } else {
            this.mode = mode;
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dirFilePath = dirPath;
        } else {
            return;
        }
        this.mContext = context;
        if (mDefaultUncaughtExceptionHandler == null) {
            mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        if (mode == NORMAL) {
            int versioninfo = getVersionInfo();
            String mobileInfo = getMobileInfo();
            String errorStatck = getErrorInfo(ex);
            String errorInfo = "versionCode=" + versioninfo + "\r\n" + mobileInfo + "\r\n" + errorStatck;
            saveCrashToFile(errorInfo);
        }
        if (mDefaultUncaughtExceptionHandler != null) {
            mDefaultUncaughtExceptionHandler.uncaughtException(thread, ex);
        }
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    private String getErrorInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }

    private String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private int getVersionInfo() {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void saveCrashToFile(String errorInfo) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String name = "crash--" + format.format(Calendar.getInstance().getTime()) + ".txt";
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(dirFilePath + File.separator + name);
            fileOutputStream = new FileOutputStream(file);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            printWriter.write(errorInfo);
            printWriter.close();
            fileOutputStream.flush();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
