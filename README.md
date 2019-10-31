### Setup




command to run with auto tracing:  `java -javaagent:./dd-java-agent.jar -Ddd.service.name=springtest0 -jar build/libs/springtest0-1.0.jar --server.port=9393
`

we now have this map_test_z map in case I want to add headers e.g `curl --header "name: zach" localhost:9393/ServiceC`

Side note: I also installed the latest tracer version and deleted the on that came with the project

When adding open tracing had to manually set the datadog ot tracing version

When using just the opentracer(make sure to instantiate tracer in main method) I run the command: `java -Ddd.trace.analytics.enabled=true -jar build/libs/springtest1-1.0.jar --server.port=9393`

When tracing using the Ddd.trace.methods system property run: `java -javaagent:./dd-java-agent.jar -Ddd.service.name=springtest0 -Ddd.trace.methods=hello.GreetingController[doSomeStuff,doSomeOtherStuff]  -Ddd.jmxfetch.enabled=true  -jar build/libs/springtest0-1.0.jar --server.port=9393

To start just this service:

java -javaagent:./dd-java-agent.jar -Ddd.trace.analytics.enabled=true -Ddd.service.name=springtest1 -Ddd.jmxfetch.enabled=true  -jar build/libs/springtest1-1.0.jar --server.port=9393