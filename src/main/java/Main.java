import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        final String URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet get = new HttpGet(URI);
        try {
            CloseableHttpResponse response = httpClient.execute(get);
            ObjectMapper mapper = new ObjectMapper();
            List<Facts> facts = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Facts>>(){});
            facts.stream()
                    .filter(fact -> fact.getUpvotes() != null)
                    .filter(fact -> fact.getUpvotes() > 0)
                    .map(fact -> fact.getText())
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
