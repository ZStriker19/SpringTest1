package hello;


import io.opentracing.SpanContext;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapExtractAdapter;
import io.opentracing.tag.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import datadog.trace.api.DDTags;
//Users/zach.groves/projects/my_sandboxes/java_apm_lab/lab1/SpringTest0/src/main/java/hello/GreetingController.java
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

@RestController
public class GreetingController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    HttpServletRequest request;

    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Value("#{environment['sleeptime'] ?: '2000'}")
    private long sleepTime;


    @RequestMapping("/ServiceD")
    public String serviceD(HttpServletRequest request) throws InterruptedException {
//see what's coming out on this side

        HashMap<String, String> headers = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.print(key + "\n" + value + "\n");
            headers.put(key, value);
        }


        Tracer tracer = GlobalTracer.get();
        Tracer.SpanBuilder spanBuilder;
        //throw trace propagation metadata into SpanBuilder object to create a scope
        try {
            SpanContext parentSpan = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers));
            if (parentSpan == null) {
                spanBuilder = tracer.buildSpan("ServiceD");
            } else {
                spanBuilder = tracer.buildSpan("ServiceD").asChildOf(parentSpan);
            }

        } catch (IllegalArgumentException e) {
            spanBuilder = tracer.buildSpan("ServiceD");
        }

        //Create span with propagated trace metadata
        try (Scope scope = spanBuilder.withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_SERVER).startActive(true)){
            scope.span().setTag(DDTags.SERVICE_NAME, "springtest1");
            Thread.sleep(230L);
            logger.info("In Service D ***************");
        }


        return "Service D\n";



        //OT w/out trace propagation from other service

//        try (Scope scope = tracer.buildSpan("ServiceD").startActive(true)) {
//            scope.span().setTag(DDTags.SERVICE_NAME, "springtest1");
//            Thread.sleep(230L);
//            logger.info("In Service D ***************");
//            return "Service D\n";
//        }


    }

}

