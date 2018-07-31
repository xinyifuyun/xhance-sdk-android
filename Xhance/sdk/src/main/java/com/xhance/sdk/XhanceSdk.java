package com.xhance.sdk;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.xhance.sdk.config.XhanceSdkConfig;
import com.xhance.sdk.deeplink.XhanceDeepLinkCallback;
import com.xhance.sdk.deeplink.XhanceDeepLinkManager;
import com.xhance.sdk.event.revenue.XhanceRevenueManager;
import com.xhance.sdk.event.session.XhanceSessionManager;
import com.xhance.sdk.install.XhanceInstallManager;
import com.xhance.sdk.utils.DeviceInfo;
import com.xhance.sdk.utils.LogUtils;

import java.util.Map;

/**
 * Created by t.wang on 2018/4/12.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class XhanceSdk {
    private static Context mContext;
    private static XhanceSdkConfig mSdkConfig;
    private static boolean mSdkInited = false;

    private static XhanceInstallManager sInstallManager;
    private static XhanceDeepLinkManager sDeepLinkManager;
    private static XhanceSessionManager sSessionManager;
    private static XhanceRevenueManager sRevenueManager;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        if (mContext == null) {
            mContext = context.getApplicationContext();
        }
    }

    public static XhanceSdkConfig getSdkConfig() {
        return mSdkConfig;
    }

    public static void initSdk(@NonNull Application application, @NonNull String devKey, @NonNull String publicKey,
                               @NonNull String trackUrl, @NonNull String channelId) {
        if (application == null) {
            Log.w(LogUtils.TAG, "Xhance SDK init failed, application can not be null");
            return;
        }

        XhanceSdkConfig config = new XhanceSdkConfig(devKey, publicKey, trackUrl, channelId);
        if (!config.isValid()) {
            Log.w(LogUtils.TAG, "Xhance SDK init failed, config is not valid");
            return;
        }

        mContext = application.getApplicationContext();
        mSdkConfig = config;
        mSdkInited = true;

        sInstallManager = XhanceInstallManager.getInstance();
        sInstallManager.init(application.getApplicationContext());

        sSessionManager = XhanceSessionManager.getInstance();
        sSessionManager.init(application);
    }

    public static void getDeepLink(@NonNull Context context, @NonNull XhanceDeepLinkCallback callback) {
        if (!mSdkInited) {
            Log.w(LogUtils.TAG, "XhanceSdk has not inited, firstly call initSdk please");
            return;
        }

        if (callback == null) {
            Log.w(LogUtils.TAG, "getDeepLink is called, callback can not be null");
            return;
        }

        XhanceDeepLinkManager.getInstance().fetchDeepLink(context, callback);
    }

    public static void thirdPayWithProductPrice(@NonNull String price, @NonNull String currency,
                                                String productId, String productType) {
        if (!mSdkInited) {
            Log.w(LogUtils.TAG, "XhanceSdk has not be inited, firstly call initSdk please");
            return;
        }

        if (TextUtils.isEmpty(price)) {
            Log.w(LogUtils.TAG, "thirdPayWithProductPrice is called, price can not be empty");
            return;
        }
        if (TextUtils.isEmpty(currency)) {
            Log.w(LogUtils.TAG, "thirdPayWithProductPrice is called, currency can not be empty");
            return;
        }

        if (sRevenueManager == null) {
            sRevenueManager = XhanceRevenueManager.getInstance();
            sRevenueManager.init(mContext);
        }
        sRevenueManager.logRevenue(price, currency, productId, productType);
    }

    public static void googlePayWithProductPrice(@NonNull String price, @NonNull String currency,
                                                 @NonNull String publicKey, @NonNull String dataSignature,
                                                 @NonNull String purchaseData, Map<String, String> params) {
        if (!mSdkInited) {
            Log.w(LogUtils.TAG, "XhanceSdk has not inited, firstly call initSdk please");
            return;
        }

        if (TextUtils.isEmpty(price)) {
            Log.w(LogUtils.TAG, "googlePayWithProductPrice is called, price can not be empty");
            return;
        }
        if (TextUtils.isEmpty(currency)) {
            Log.w(LogUtils.TAG, "googlePayWithProductPrice is called, currency can not be empty");
            return;
        }
        if (TextUtils.isEmpty(publicKey)) {
            Log.w(LogUtils.TAG, "googlePayWithProductPrice is called, publicKey can not be empty");
            return;
        }
        if (TextUtils.isEmpty(dataSignature)) {
            Log.w(LogUtils.TAG, "googlePayWithProductPrice is called, dataSignature can not be empty");
            return;
        }
        if (TextUtils.isEmpty(purchaseData)) {
            Log.w(LogUtils.TAG, "googlePayWithProductPrice is called, purchaseData can not be empty");
            return;
        }

        if (sRevenueManager == null) {
            sRevenueManager = XhanceRevenueManager.getInstance();
            sRevenueManager.init(mContext);
        }
        sRevenueManager.logRevenueVerify(price, currency, publicKey, dataSignature, purchaseData, params);
    }

    public static void setTestMode(boolean testMode) {
        XhanceSdkConfig.setTestMode(testMode);
    }

    public static void enableLogger(boolean logger) {
        XhanceSdkConfig.enableLogger(logger);
    }

    public static void setAndroidId(@NonNull Context context, String androidId) {
        DeviceInfo.getInstance().setAndroidId(context, androidId);
    }

}
