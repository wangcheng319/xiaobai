package com.xiaobai.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

/**
 * 工具类
 */
public class Utils {
    // 手机分辨率宽高
    public static int screenHeight_;
    public static int screenWidth_;
    static final String LOG_TAG = "PullToRefresh";

    /**
     * 验证手机号码格式表达式
     */
    public static Pattern MobilePattern = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$");

    /**
     * 获取屏幕分辨率
     *
     * @param context
     */
    public static void windowScreenSize(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenHeight_ = outMetrics.heightPixels;
        screenWidth_ = outMetrics.widthPixels;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
//			LogUtils.d(versionName);

            return versionName;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 货币转换 1000000 return 1百万 10000 return 1万 1000 return 1千
     *
     * @param amount
     * @return
     */
    public static HashMap<String, String> amountConversionMap(long amount) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (amount >= 1000000) {
            map.put("amount", amount / 1000000 + "");
            map.put("amoun_unit", "百万");
            return map;
        }

        if (amount >= 10000) {
            map.put("amount", amount / 10000 + "");
            map.put("amoun_unit", "万元");
            return map;
        }

        if (amount >= 1000) {
            map.put("amount", amount / 1000 + "");
            map.put("amoun_unit", "千元");
            return map;
        }

        map.put("amount", amount + "");
        map.put("amoun_unit", "元");
        return map;
    }

    /**
     * 1000000 return 1百万 10000 return 1万 1000 return 1千
     *
     * @param amount
     * @return
     */
    public static String amountConversion(long amount) {
        if (amount >= 1000000) {
            return amount / 1000000 + "百万";
        }

        if (amount >= 10000) {
            return amount / 10000 + "万元";
        }

        if (amount >= 1000) {
            return amount / 1000 + "千元";
        }

        return amount + "元";
    }

    /**
     * 10000 return 10,000
     *
     * @param amount
     * @return
     */
    public static String amountConversionFormat(long amount) {
        return NumberFormat.getInstance().format(amount);
    }

    /**
     * 10000 return 10,000
     *
     * @param amount
     * @return
     */
    public static String amountConversionFormat(double amount) {
        return NumberFormat.getInstance().format(amount);
    }

    /**
     * 1 Year 1年 1 Month 1个月 1 Day 1天
     *
     * @param period
     * @param periodUnit
     * @return
     */
    public static String periodConversion(int period, String periodUnit) {
        if ("Year".equals(periodUnit)) {
            return period + "年";
        }

        if ("Month".equals(periodUnit)) {
            return period + "个月";
        }

        if ("Day".equals(periodUnit)) {
            return period + "天";
        }

        return period + "";
    }

    /**
     * 1 Year 1年 1 Month 1个月 1 Day 1天
     *
     * @param periodUnit
     * @return
     */
    public static String periodConversion(String periodUnit) {
        if ("Year".equals(periodUnit)) {
            return "年";
        }

        if ("Month".equals(periodUnit)) {
            return "个月";
        }

        if ("Day".equals(periodUnit)) {
            return "天";
        }

        return "";
    }

    /**
     * 123123123 2015-05-18 10:54:27
     *
     * @param time
     * @return
     */
    public static String getDateString(long time) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return myFmt.format(new Date(time));
    }

    public static String getDateStringMinute(long time) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return myFmt.format(new Date(time));
    }

    /**
     * 123123123 2015-05-18
     *
     * @param time
     * @return
     */
    public static String getDateStringDay(long time) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");

        return myFmt.format(new Date(time));
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 跳转到内部WebView
     *
     * @param context
     * @param url
     * @param title
     */
    /*
     * public static void jumpURL(Context context, String url, String title) {
	 * if (TextUtils.isEmpty(url) || "#".equals(url)) { return; }
	 * 
	 * Intent intent = new Intent(context, WebActivity.class);
	 * intent.putExtra("url", url); intent.putExtra("name", title);
	 * context.startActivity(intent); }
	 */

	/*	*//**
     * 9.658 return 9.65
     */
    /*
     * public static BigDecimal scale(double scale){ return new
	 * BigDecimal(scale).setScale(2, BigDecimal.ROUND_DOWN); }
	 */

    /**
     * 9.658 return 9.65
     */
    public static BigDecimal scale(BigDecimal scale) {
        return scale.setScale(2, BigDecimal.ROUND_DOWN);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 13817124233 138****4233
     *
     * @param phone
     * @return
     */
    public static String hidePhone(String phone) {
        return phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
    }

    public static boolean regPatternsPassword(Context context, String password) {
        if (password.length() < 6 || password.length() > 20) {
//			MyToast.showToast(context, "密码长度只能在6-20位字符之间");
            return true;
        }

        Pattern sameCharsPattern = Pattern.compile("(\\w)(\\1)+");
        if (sameCharsPattern.matcher(password).matches()) {
//			MyToast.showToast(context, "密码不能为同一个字符");
            return true;
        }

        Pattern containsCharsPattern = Pattern.compile("^[0-9]*$");
        Pattern containsNumberPattern = Pattern.compile("^[a-zA-Z]+$");
        if (containsCharsPattern.matcher(password).matches()
                || containsNumberPattern.matcher(password).matches()) {
//			MyToast.showToast(context, "密码需要包含字母和数字");
            return true;
        }

        return false;
    }

    /**
     * 跳转到webview
     *
     * @param url
     */
    public static void jumpExplorer(Context context, String url) {
        if (TextUtils.isEmpty(url) || "#".equals(url)) {
            return;
        }

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    public static String getTime() {

        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String date = simpleFormat.format(System.currentTimeMillis());
        return date;

    }

    // 生成请求签名
    public static String getSign(Context context, String sign) {

        String signMd5 = md5(sign).toUpperCase();
        String newEncryptStr = null;

        try {
            CertificateFactory cff = CertificateFactory.getInstance("X.509");
            AssetManager am = context.getResources().getAssets();
            InputStream is = am.open("hmyd_app_pub.cer");
            Certificate cf = cff.generateCertificate(is);
            PublicKey pk1 = cf.getPublicKey(); // 得到证书文件携带的公钥
            Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // 定义算法：RSA
            c1.init(Cipher.ENCRYPT_MODE, pk1);
            byte[] encryptStr = c1.doFinal(signMd5.getBytes()); // 加密后的数据
//			newEncryptStr = BaseEncoding.base64().encode(encryptStr);

        } catch (Exception e) {

        }

        return newEncryptStr;

    }


    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static void closeBoard(Activity mActivity) {
        ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 计算金额
     *
     * @param argStr
     * @return
     */
    public static String getFloatDotStr(String argStr) {
        float arg = Float.valueOf(argStr);
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return fnum.format(arg);
    }

    /**
     * 判断应用程序是否安装
     *
     * @param context
     * @param packageName 应用程序安装包名
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            // 获取安装包信息
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }

        if (null == packageInfo) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 从 Assets 文件夹中获取图片
     *
     * @param assetName 文件名称
     * @return null 文件不存在
     */
    public static Bitmap getBitmapFromAssets(Activity activity, String assetName) {
        InputStream input = null;
        Bitmap bmp = null;
        try {
            input = activity.getAssets().open(assetName);
            bmp = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            bmp = null;
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bmp;
    }

    /**
     * 获取SDK版本
     *
     * @return
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

	/* 控制台统一输出信息 start */

    // 是否打印标志
    private static boolean isPrintMessage_;

    public static void isPrintMessage(boolean isprint) {
        isPrintMessage_ = isprint;
    }

    /**
     * 控制台统一打印
     *
     * @param obj
     */
    public final static void printOutToConsole(Object obj) {
        if (isPrintMessage_) {
            if (obj instanceof String) {
                printOutToConsole((String) obj);
            } else if (obj instanceof byte[]) {
                printOutToConsole((byte[]) obj);
            } else {
                System.out.println(obj);
            }
        }
    }

    /**
     * @param s
     */
    public final static void printOutToConsole(String s) {
        if (isPrintMessage_) {
            int length = s.length();
            int offset = 3000;
            if (length > offset) {// 解决报文过长，打印不全的问题
                int n = 0;
                for (int i = 0; i < length; i += offset) {
                    n += offset;
                    if (n > length)
                        n = length;
                    System.out.println(s.substring(i, n));
                }
            } else {
                System.out.println(s);
            }
        }
    }

    /**
     * @param byts
     */
    public final static void printOutToConsole(byte[] byts) {
        if (isPrintMessage_) {
            if (byts == null) {
                return;
            }
            for (int i = 0; i < byts.length; i++) {
                System.out.print("[" + i + "]" + " : \t");
                System.out.println(byts[i]);
            }
        }
    }

    /**
     * 异常统一打印
     *
     * @param e
     */
    public final static void printException(Exception e) {
        if (isPrintMessage_) {
            e.printStackTrace();
        }
    }

	/* 信息输出 end */

	/* 网络判断 start */

    /**
     * 获取网络链接类型信息
     *
     * @return
     */
    public static int getConnectedType(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conn.getActiveNetworkInfo();
        if (null == netInfo || !netInfo.isAvailable()) {
            return netInfo.getType();
        }
        return -1;
    }


    /**
     * 判断网络是否可用
     *
     * @return
     */
    public static boolean isConnectByNet(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conn.getActiveNetworkInfo();
        if (null == netInfo || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    /**
     * 判断wifi是否可用
     *
     * @return
     */
    public static boolean isConnectByWifi(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null == wifiInfo || !wifiInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    /**
     * 判断Mobile网络是否可用
     *
     * @return
     */
    public static boolean isConnectByMobile(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null == mobileInfo || !mobileInfo.isAvailable()) {
            return false;
        }
        return true;
    }

	/* 网络判断 end */

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWith(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static void warnDeprecation(String depreacted, String replacement) {
        Log.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
    }


    /**
     * 判断当前字符是否是表情字符
     *
     * @param codePoint
     * @return
     */
    public static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 判断当前字符
     *
     * @param str
     * @return
     */
    public static boolean isEmojiCharacter(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 改变部分字体大小
     *
     * @param str
     * @param view
     */
    public static void changeTextSize(String str, TextView view) {
        int start = 0;
        int end = 0;
        if (str.contains("[")) {
            start = str.indexOf("[");
        }
        if (str.contains("]")) {
            end = str.indexOf("]");
        }
        String leftString = str.replace("[", "");
        String rightString = leftString.replace("]", "");
        Spannable WordtoSpan = new SpannableString(rightString);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(34), start, end - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(20), -1, str.length() - 2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(WordtoSpan);
    }

    /**
     * 改变字体大小和颜色
     *
     * @param str
     * @param view
     */
    public static void changeSizeAndColor(String str, TextView view) {
        int start = 0;
        int end = 0;
        if (str.contains("[")) {
            start = str.indexOf("[");
        }
        if (str.contains("]")) {
            end = str.indexOf("]");
        }
        String leftString = str.replace("[", "");
        String rightString = leftString.replace("]", "");
        Spannable WordtoSpan = new SpannableString(rightString);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(34), start, end - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), start, end - 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(20), end - 1, str.length() - 2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(WordtoSpan);
    }

    /**
     * 生成一个随机色块
     *
     * @return
     */
    public static Drawable makeDrable() {
        final String[] colors = new String[]{"#a04940", "#ee8d7b", "#7065a3", "#85916d", "#c1d8ac", "#8c8684", "#c1d8ac",
                "#c18dac", "#93b69c", "#c85179", "#9dc357", "#FFE4B5", "#7fcce3", "#73b8e2", "#cbb994", "#5b7e91"};

        final int random = new Random().nextInt(16);
        Drawable drawable = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawColor(Color.parseColor(colors[random]));
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };

        return drawable;
    }
}
