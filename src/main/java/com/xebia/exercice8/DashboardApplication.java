package com.xebia.exercice8;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.xebia.MessageApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The goal here is to use Hystrix configuration hot reload and dashboard features.
 * Dashboard is available at http://localhost:8080/hystrix (stream URL is http://localhost:8080/hystrix.stream).
 * Configuration can be updated either via JMX (jconsole) or via src/main/resources config.properties file.
 */
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication(scanBasePackageClasses = DashboardApplication.class)
public class DashboardApplication {

    public static void main(String[] args) {

        // Reload configuration every 1s instead of 60s
        System.setProperty("archaius.fixedDelayPollingScheduler.delayMills", String.valueOf(1000));

        SpringApplication.run(DashboardApplication.class, args);
    }

    @RestController
    @RequestMapping("/api")
    class DashboardApi {

        private final MessageApi messageApi;

        private final HystrixCommand.Setter setter;

        public DashboardApi(MessageApi messageApi) {
            this.messageApi = messageApi;
            this.setter = HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("DashboardGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("DashboardCommand"));
        }

        @GetMapping
        public String hello() throws Exception {

            HystrixCommand<String> command = new HystrixCommand<String>(setter) {

                @Override
                protected String run() throws Exception {

                    return messageApi.getMessage("Bob");
                }

            };

            return command.execute();
        }

    }

    @Bean
    public MessageApi slowService() {

        return userName -> {

            // purposely slow to illustrate configuration hot reload and dashboard features
            try {
                Thread.sleep(1_500L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return "Hello " + userName;
        };

    }
}
