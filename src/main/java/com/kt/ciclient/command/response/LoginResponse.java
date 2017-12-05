package com.kt.ciclient.command.response;

import com.kt.ciclient.command.CiResponse;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public class LoginResponse extends CiResponse {
    private int code;

    private String tokenId;

    private long expiredIn;

    public boolean isSuccess() {
        return code == 0 && StringUtils.isNotBlank(tokenId);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public long getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(long expiredIn) {
        this.expiredIn = expiredIn;
    }
}
