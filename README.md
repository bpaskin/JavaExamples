# JavaExamples

# JSFParams
Small application tested on tWASv9 and Liberty (jsf-2.2 feature) that has 3 pages.  the index page the user enters a name and it is passed to the backing bean.  Page 2 and Page 3 read the backing bean from the HttpSession.

# CacheTest
Application to test the Object Cache in both tWAS and Liberty. 

For Liberty Add the following features:
``  <featureManager>
        <feature>distributedMap-1.0</feature>
        <feature>ejbLite-3.1</feature>
        <feature>jsf-2.0</feature>
        <feature>servlet-3.1</feature>
    </featureManager>``
 
 And add the following cache:
`` <distributedMap id="cache" jndiName="cache/test"/> ``
 
 For tWAS:
 1. Optional: Create replication domain, if going to be used across servers.  (Environment > Replication Domains > New...)
 2. Create a new Object Cache and link Replication Domain, if using it.  (Resources > Cache Instances > Object Cache Instances > New...).  The JNDI name should be called "cache/test".  Make sure the cache is created at the correct scope.
 
