# LibertyCheckCompliance
A Java program that can read in a Liberty server.xml (or any other XML files) and use Bean Validation 2.0 outside of JEE to validate the entries.  This is good to check that all Liberty servers are following the standards set forth by an organization.

This is only as an example and must be expanded to meet the needs of an organziation.

Usage: 

`java -cp "LibertyComplianceCheck-1.0.0.0.jar:lib/*" com.ibm.validation.liberty.CheckCompliance /path/to/server.xml`
