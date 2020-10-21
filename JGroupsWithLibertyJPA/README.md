The JPA specification has two different [caches](https://en.wikibooks.org/wiki/Java_Persistence/Caching).  The first level cache (L1) the persistence context.  The second level cache (L2) shares the state across the persistence contexts.  These caches prevent unneeded database access.  For example, a database table that contains the names of the regions of Italy does not need to be read very often, and this data could be kept in L2 cache.

If there is more than one application server or application in a cluster there is no way to share the caches.  This is where clustered caches, like [JGroups](http://www.jgroups.org), play an important role.  JGroups allows for a cluster of members to send and receive updates to Objects.  [EclipseLink](https://www.eclipse.org/eclipselink/) which is used by [OpenLiberty](https://www.openliberty.io) as its main JPA driver supports JGroups, albeit some configuration and files are needed.  [EclipseLink Extensions](https://www.eclipse.org/eclipselink/documentation/2.7/jpa/extensions/toc.htm) and JGroups are needed to be placed in a shared library.

This example using OpenLiberty/Liberty uses JGroups.  The example is a simple REST query that goes to a database to retrieve all frields in a table and returns the results in JSON format.  

1. Create a directory on the host system that contains the necessary JGroups and Eclipse Extensions libraries.  As of this writing, Eclipse 2.x does not support JGroups 5.x.  Liberty 20.0.0.9 uses EclipseLink 2.x.

JGroups: 
https://mvnrepository.com/artifact/org.jgroups/jgroups

EclipseLink Extensions: 
https://mvnrepository.com/artifact/org.eclipse.persistence/org.eclipse.persistence.extension

EclipseLink Core: 
https://mvnrepository.com/artifact/org.eclipse.persistence/org.eclipse.persistence.core

EclipseLink ASM: 
https://mvnrepository.com/artifact/org.eclipse.persistence/org.eclipse.persistence.asm

2. Update the server.xml.  The JGroups library `fileset` needs to point to the correct directory where the files were placed in step 1.

3. Update the persistence.xml and add the following two properties:
```
<property name="eclipselink.cache.coordination.protocol" value="jgroups"/>`
<property name="eclipselink.cache.coordination.jgroups.config" value="jgroupsConfig.xml"/>
```
This tells EclipseLink to use JGroups and where the JGroups configuration file is located and named.

4. Update the `jgroupsConfig.xml` file and update the hostnames and ports.  In the sample TCP is being used, but JGroups supports other protocols, like UDP.

5. The only programming difference is that in JPA the regular annotation for caching is `@Cacheable`, however the application must use the specific EclipseLink `@Cache` which can be seen in the `Perfuser` Entity.
