package com.xhance.sdk.event.log;

import android.content.Context;

import com.xhance.sdk.event.XhanceEventManager;

/**
 * Created by t.wang on 2018/5/29.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */

public class XhanceLogManager {
    private static XhanceLogManager sXhanceLogManager;
    private XhanceEventManager mEventManager;

    public static XhanceLogManager getInstance() {
        if (sXhanceLogManager == null) {
            synchronized (XhanceLogManager.class) {
                if (sXhanceLogManager == null) {
                    sXhanceLogManager = new XhanceLogManager();
                }
            }
        }

        return sXhanceLogManager;
    }

    public void init(Context context) {
        if (mEventManager == null) {
            mEventManager = XhanceEventManager.getInstance();
            mEventManager.init(context);
        }
    }

}
