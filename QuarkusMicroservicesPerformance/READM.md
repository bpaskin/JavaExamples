The goal of this example was to compare the performance of Quarkus compiled code and those with GraalVM native images.  The results are a mixed bag.

[Quarkus](https://quarkus.io) takes many of the JEE/Jakarta EE standards and makes them runable without the need for an application server.  It is only a subset of the features and does not support all, especially when using GraalVM.  This is like SpringBoot for JEE/Jarakta EE.

[GraalVM](https://www.graalvm.org) is polyglot virtual machine that can run several different languages.  One of the unique features is using *native-image*, which ahead of time compiles code into a binary for the particular system it is compiled on.  

I used Quarkus 1.1.0.Final and GraalVM 19.2.1 CE (based on OpenJDK 1.8.0_232), as that is what is supported by this version of Quarkus.  This version of GraalVM did not support Java 11, so everything was compiled with Java 8.  GraalVM does now support Java 11, but Quarkus 1.1 does not.

I was trying to compare these performance numbers to the same application I ran before using [Liberty 19.0.0.11](https://github.com/bpaskin/JavaExamples/tree/master/MicroservicesPerformance) and Java 11. One issue is that the Quarkus does not support EJBs, so I had to remove the EJBs and change the main EJB to an injected singleton.  So remote EJBs could not be tested.  The other issue was GraalVM had issues with using the native REST client, which worked but kept throwing lots of errors, so I had to transform that call into a [MicroProfile Client](https://github.com/eclipse/microprofile-rest-client) call.  

The results are a mixed bag.  I ran the packaged jar with Java 11 (AdoptOpenJDK 11.0.5+10 with openj9-0.17.0), the GraalVM and the the native-image on my Ubuntu Linux 18.04.3 system, which has 32 GB of RAM.  I am hoepful once Quarkus supports newer GraalVMs things will look better.

The tests consist of running 25 threads for a total of 10,000 times for a total of 250,000 transactions. Each test was executed 5 times. The tests call a REST service that calls out to get a random number with minimums and maximums passed via the request.

Commands used:
native-image : 
`/path/to/MicroservicesPerformance -Dhttp.maxConnections=100 -Dhttp.keepAlive=true`

Java 11: `/path/to/java -Xms1G -Xmx1G -Dhttp.maxConnections=100 -Dhttp.keepAlive=true -jar /path/to/MicroservicesPerformance-1.0.0-runner.jar`

GraalVM:
`/path/to/java -Xms1G -Xmx1G -Dhttp.maxConnections=100 -Dhttp.keepAlive=true -jar /path/to/MicroservicesPerformance-1.0.0-runner.jar`


| Method | Liberty 190011 | Native Image | Java 11 | GraalVM |
| ------ | -------------- | ------------ | ------- | ------- |
| CDI injection | 7071 ms | 6652 ms | 6386 ms | 8003 ms |
| no interface | 8057 ms | 6709 ms | 6305 ms | 7888 ms|
| remote | 10112 ms | NA | NA | NA|
| REST | 22530 ms | 22030 ms | 30127 ms | 65395 ms |

The results between Liberty and the rest are not exactly the same test.  I would need to modify the source code to compare exactly, but it does show that Quarkus, without GraalVM, does a good job.

To compile a runnable jar use: `mvn package`

to compile a runnable executable use: `mvn package -Pnative`
