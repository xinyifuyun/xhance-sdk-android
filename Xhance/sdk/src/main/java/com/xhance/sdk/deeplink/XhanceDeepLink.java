package com.xhance.sdk.deeplink;

/**
 * Created by t.wang on 2018/5/31.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */

public class XhanceDeepLink {
    private String mTargetUri;
    private String mLinkArgs;

    public XhanceDeepLink(String targetUri, String linkArgs) {
        mTargetUri = targetUri;
        mLinkArgs = linkArgs;
    }

    public String getTargetUri() {
        return mTargetUri;
    }

    public String getLinkArgs() {
        return mLinkArgs;
    }

}
