package com.xebia.exercice7;

import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * The goal here is to use HystrixCommand features in a real application.
 * Application expose 2 services which call 2 different remote services.
 * Hystrix allows here to isolate server resources (Tomcat Worker Threads) used by each service.
 */
@SpringBootApplication(scanBasePackageClasses = MessageApplication.class)
public class MessageApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageApplication.class);

    @RestController
    @RequestMapping("/messages")
    public class MessageController {

        private final MessageClient firstClient;

        private final MessageClient secondClient;

        public MessageController() {

            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setReadTimeout(4_000);
            factory.setConnectTimeout(2_000);

            RestTemplate restTemplate = new RestTemplate(factory);

            this.firstClient = new MessageClient("http://localhost:8081/first", restTemplate);
            this.secondClient = new MessageClient("http://localhost:8082/second", restTemplate);
        }

        @GetMapping("/first")
        public String getFirstMessage() {
            return firstClient.getMessage();
        }

        @GetMapping("/second")
        public String getSecondMessage() {
            return secondClient.getMessage();
        }

    }

    private class MessageClient {

        private final String url;

        private final RestTemplate restTemplate;

        private final HystrixCommand.Setter setter;

        MessageClient(String url, RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
            this.setter = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(url))
                .andCommandKey(HystrixCommandKey.Factory.asKey("/messages"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(5))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(5_000));
            this.url = url;
        }

        public String getMessage() {

            return new HystrixCommand<String>(setter) {

                @Override
                protected String run() throws Exception {
                    return restTemplate.getForEntity(url + "/messages", String.class).getBody();
                }

                @Override
                public String getFallback() {
                    LOGGER.error(getExecutionException().getLocalizedMessage(), getExecutionException());
                    return String.format("Remote server %s is unavailable", url);
                }

            }.execute();
        }
    }

}
