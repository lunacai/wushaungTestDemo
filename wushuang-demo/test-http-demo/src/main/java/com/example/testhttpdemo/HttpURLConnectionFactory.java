package com.example.testhttpdemo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

public class HttpURLConnectionFactory {

    public static int DEFAULT_CONN_TIMEOUR = 30000;

    public static HttpURLConnection getConn(String url) {
        HttpURLConnection conn = null;
        try {
            URL http = new URL(url);
            if (url.startsWith("https:")) {
                HttpsURLConnection httpsConn = null;
                SSLContext sslContext = null;
                sslContext = SSLContext.getInstance("TLS");
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                httpsConn = (HttpsURLConnection) http.openConnection();
                httpsConn.setSSLSocketFactory(ssf);
                httpsConn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                conn = httpsConn;
            } else {
                conn = (HttpURLConnection) http.openConnection();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static String sendGet(HttpURLConnection con) {
        StringBuffer resultBuffer = new StringBuffer();
        BufferedReader br = null;
        addConnProp(con, "GET", true);
        try {
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                } finally {
                    if (con != null) {
                        con.disconnect();
                        con = null;
                    }
                }
            }
        }
        return resultBuffer.toString();
    }

    public static String sendPost(HttpURLConnection con, String body) {
        addConnProp(con, "POST", true);
        OutputStream outStream = null;
        BufferedReader responseReader = null;
        StringBuffer sb = new StringBuffer();
        try {
            if (body != null) {
                outStream = con.getOutputStream();
                byte[] data = body.getBytes();
                outStream.write(data);
                outStream.flush();
                outStream.close();
            }
            int resultCode = con.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                String readLine = new String();
                responseReader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    System.out.println(readLine);
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static void addConnProp(HttpURLConnection conn, String method, boolean flag) {
        try {
            conn.setRequestMethod(method);
            conn.setConnectTimeout(DEFAULT_CONN_TIMEOUR);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setUseCaches(false);
            if (flag) {
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("connection", "Keep-Alive");
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }
}