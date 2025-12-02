package com.example.client;

import com.example.interceptor.LoggingInterceptor;
import com.example.pojo.VatInfoRequest;
import eu.viesapi.client.VIESAPIClient;
import eu.viesapi.client.VIESData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

@Component
public class VeisApiClient {

    private final RestClient restClient;
    JdkClientHttpRequestFactory jdkClientHttpRequestFactory = new JdkClientHttpRequestFactory();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public VeisApiClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("https://viesapi.eu")
                .requestFactory(new BufferingClientHttpRequestFactory(jdkClientHttpRequestFactory))
                .requestInterceptor(new LoggingInterceptor())
                .build();
    }

    public String validateUsingRestAPI(VatInfoRequest vatInfoRequest){

        String apiResponseCache = Objects.requireNonNull(redisTemplate.opsForValue().get(vatInfoRequest.getId())).toString();

        if(!apiResponseCache.isEmpty()){
            return apiResponseCache;
        }else{
            String authToken = "Basic dGVzdF9pZDp0ZXN0X2tleQ==";
            String response = restClient.get()
                    .uri("/api-test/get/vies/euvat/" + vatInfoRequest.getId())
                    .header("Authorization", authToken)
                    .retrieve()
                    .body(String.class);

            redisTemplate.opsForValue().set(vatInfoRequest.getId(), response);
            return response;
        }
    }

    public String validateUsingLibrary(VatInfoRequest vatInfoRequest) throws MalformedURLException {
        System.out.println("validateUsingLibrary using vat: " + vatInfoRequest.getId());
        URL url = new URL("https://viesapi.eu/api-test");
        VIESAPIClient viesapi = new VIESAPIClient("test_id", "test_key");

        viesapi.setURL(url);

        VIESData vies = viesapi.getVIESData(vatInfoRequest.getId());

        if (vies != null) {
            System.out.println(vies);
            return vies.toString();
        }
        else {
            System.out.println("Error: " + viesapi.getLastError() + " (code: " + viesapi.getLastErrorCode() + ")");
        }
       return "";

    }
}
