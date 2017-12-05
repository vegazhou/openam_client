package com.kt.ciclient;

import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.io.StringReader;

/**
 * Created by Vega Zhou on 2017/7/25.
 */
public class XMLHandler extends DefaultHandler {
    public XMLHandler() {
    }

    public InputSource resolveEntity(String aPublicID, String aSystemID) {
        String sysid = aSystemID.trim();
        if(sysid.toLowerCase().startsWith("jar://")) {
            String dtdname = sysid.substring(5);
            InputStream is = this.getClass().getResourceAsStream(dtdname);
            if(is != null) {
                return new InputSource(is);
            }
        }

        return new InputSource(new StringReader(""));
    }
}
