package com.kt.ciclient.entity;

import com.kt.ciclient.command.response.ValidateSessionResponse;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public class Principal {

    private String uid;

    private String cn;

    private long expiredIn;

    public Principal() {

    }

    public Principal(ValidateSessionResponse validateSessionResponse) {
        setUid(validateSessionResponse.getUid());
        setCn(validateSessionResponse.getCn());
        setExpiredIn(validateSessionResponse.getExpiredIn());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public long getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(long expiredIn) {
        this.expiredIn = expiredIn;
    }
}
