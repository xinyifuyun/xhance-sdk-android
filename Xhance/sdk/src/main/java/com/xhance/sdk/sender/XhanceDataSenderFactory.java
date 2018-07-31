package com.xhance.sdk.sender;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by t.wang on 2018/7/20.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */

public class XhanceDataSenderFactory {
    public static Map<String, XhanceDataSender> sCache = new ConcurrentHashMap<>();

    public static XhanceDataSender getXhanceDataSender(String key) {
        if (!sCache.containsKey(key)) {
            XhanceDataSender sender = new XhanceDataSender();
            sCache.put(key, sender);
        }

        XhanceDataSender sender = sCache.get(key);
        return sender;
    }

    public static void removeSenderIfNeccesary(String key) {
        if (sCache.containsKey(key)) {
            sCache.remove(key);
        }
    }
}
