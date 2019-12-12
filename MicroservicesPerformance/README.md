Over the past few years, there has been a rush to Microservices and containerization without the consideration of performance.  The breaking up of monolithic applications into smaller pieces can yield better response times when a small part of the application is used by other services.   However, it can lead to much worse response times if the applications continuously need to call each other.  This problem has existed for decades and spans myriads of languages.

The best results are when the application is monolithic, and a local API is in use.  In Java injecting the resource yields these results.  When applications are broken up and dependent upon each other, it is better to package them in the same App Server so that a global namespace lookup can be used to retrieve the remote resource without existing the JVM.  Lastly,  a REST call, even when not existing the same host, is much more costly.

The tests consist of running 25 threads for a total of 10,000 times for a total of 250,000 transactions.  Each test was executed 5 times.  The tests call a REST service that calls out to get a random number with minimums and maximums passed via the request.

| Method | Response time |
| ------ | ------------- |
| CDI injection | 7071 ms |
| no interface | 8057 ms |
| remote | 10112 ms |
| REST | 22530 ms |
