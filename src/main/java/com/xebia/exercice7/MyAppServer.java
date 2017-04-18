package com.xebia.exercice7;


import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MyAppServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyAppServer.class);

    @Autowired
    private RestTemplate restTemplate;

    protected void start() {
        SpringApplication.run(MyAppServer.class, "--server.tomcat.max-threads=9");
    }

    protected void stop() {
        // stop the server
    }

    @RestController
    @RequestMapping("/messages")
    private class MessageController {

        @Autowired
        @Qualifier("firstRemoteServerClient")
        private RemoteServerClient firstRemoteServerClient;

        @Autowired
        @Qualifier("secondRemoteServerClient")
        private RemoteServerClient secondRemoteServerClient;

        @RequestMapping(value = "/first", method = RequestMethod.GET)
        public String getMessageFromRemote() {
            return firstRemoteServerClient.getMessage();
        }

        @RequestMapping(value = "/second", method = RequestMethod.GET)
        public String getMessage() {
            return secondRemoteServerClient.getMessage();
        }
    }


    private class RemoteServerClient {

        private String url;

        private HystrixCommand.Setter setter;

        public RemoteServerClient(String url) {
            this.setter = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(url))
                                               .andCommandKey(HystrixCommandKey.Factory.asKey("/messages"))
                                               .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                                                                                                           .withCoreSize(
                                                                                                               5))
                                               .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                                                                                     .withExecutionTimeoutInMilliseconds(
                                                                                                         5 * 1000));
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

    @Bean("firstRemoteServerClient")
    RemoteServerClient firstRemoteServerClient() {
        return new RemoteServerClient("http://localhost:8081/first");
    }

    @Bean("secondRemoteServerClient")
    RemoteServerClient secondRemoteServerClient() {
        return new RemoteServerClient("http://localhost:8082/second");
    }

    @Bean
    RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(4 * 1000);
        factory.setConnectTimeout(2000);
        return factory;
    }

}
