package hello;


import datadog.opentracing.DDTracer;
import io.opentracing.util.GlobalTracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class Application {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    public static void main(String[] args) {
        //You only need to register a new Global Tracer here if you're going to only be using opentracing and no automatic instrumentation
        //if you try to use automatic tracing with this the app will throw an error because there's already a global tracer
        GlobalTracer.register(new DDTracer());

        SpringApplication.run(Application.class, args);

    }
}



