package hello;


//import io.opentracing.SpanContext;
//import io.opentracing.propagation.Format;
//import io.opentracing.propagation.TextMapExtractAdapter;
//import io.opentracing.tag.Tags;
//import com.squareup.okhttp.Headers;
//import com.squareup.okhttp.Request;
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
import java.util.List;
import java.util.Map;

//import datadog.trace.api.DDTags;
////Users/zach.groves/projects/my_sandboxes/java_apm_lab/lab1/SpringTest0/src/main/java/hello/GreetingController.java
//import io.opentracing.Scope;
//import io.opentracing.Tracer;
//import io.opentracing.util.GlobalTracer;

@RestController
public class GreetingController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    //for HTTP requests
    HttpServletRequest request;
    // for okhttp
    //Request request;

    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Value("#{environment['sleeptime'] ?: '2000'}")
    private long sleepTime;

//for normal httpservlet requests:
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


        Thread.sleep(230L);

        return "Service D\n";
        }



    @RequestMapping("/ServiceY")
    public String serviceY(HttpServletRequest request) throws InterruptedException {
        Thread.sleep(230L);

        return "Service Y\n";
    }

//This is for okHTTP receiving:
//    @RequestMapping("/ServiceD")
//    public String serviceD(Request request) throws InterruptedException {
////see what's coming out on this side
//
//            HashMap<String, String> headers = new HashMap<String, String>();
//
//            Headers headerNames = request.headers();
//
//            String singleValue = headerNames.get("this is a header");
//
//            System.out.println(singleValue);
//
//
//
//            Thread.sleep(230L);
//
//            return "Service D\n";



        //OT w/out trace propagation from other service

//        try (Scope scope = tracer.buildSpan("ServiceD").startActive(true)) {
//            scope.span().setTag(DDTags.SERVICE_NAME, "springtest1");
//            Thread.sleep(230L);
//            logger.info("In Service D ***************");
//            return "Service D\n";
//        }


    }



