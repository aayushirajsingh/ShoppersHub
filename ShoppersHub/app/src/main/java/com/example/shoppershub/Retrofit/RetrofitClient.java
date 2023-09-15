package com.example.shoppershub.Retrofit;

import com.example.shoppershub.Constants;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String URL = Constants.BASE_URL_IP+"shoppershub_api/";

    private static RetrofitClient clientObject;

    private static Retrofit retrofit;

    // Trust certificates because it was throwing error CertPathValidatorException : Trust anchor for certificate path not found
    private RetrofitClient() {

        TrustManager[] trustAllCertificates = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }
        };

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCertificates[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized RetrofitClient getInstance() {
        if (clientObject == null) {
            clientObject = new RetrofitClient();
        }
        return clientObject;
    }

    public ApiInterface getApi() {
        return retrofit.create(ApiInterface.class);
    }
}