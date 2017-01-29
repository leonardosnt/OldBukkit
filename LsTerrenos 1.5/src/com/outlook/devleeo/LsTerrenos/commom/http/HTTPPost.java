package com.outlook.devleeo.LsTerrenos.commom.http;

import java.net.*;
import java.io.*;
import java.util.*;

public class HTTPPost
{
    private HashMap<String, String> params;
    private String url;
    
    public HTTPPost(final String url) {
        this.params = new HashMap<String, String>();
        this.url = url;
    }
    
    public void addParameter(final String key, final Object value) {
        if (!this.params.containsKey(key)) {
            this.params.put(key, value.toString());
        }
    }
    
    public URLConnection post() {
        try {
            final StringBuilder sb = new StringBuilder(this.url.endsWith("?") ? this.url : (String.valueOf(this.url) + "?"));
            for (final String k : this.params.keySet()) {
                sb.append(String.valueOf(k) + "=" + this.params.get(k) + "&");
            }
            this.url = sb.toString().substring(0, sb.toString().length() - 1);
            return new URL(this.url).openConnection();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
