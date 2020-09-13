# Java restful-eshop

A comprehensive guide on how to write applications using Spring 5:
«Pro Spring 5: An In-Depth Guide to the Spring Framework and Its Tools 5th ed. Edition»

https://www.apress.com/gp/book/9781484228074

The most comprehensive book about JPA and Hibernate Persistence:
«Java Persistence with Hibernate, Second Edition»

https://www.manning.com/books/java-persistence-with-hibernate-second-edition

#### Key Features
~~~~
⚫ Java 11
⚫ Spring 5
⚫ Java-based container configuration
⚫ Spring RESTful Web Service
⚫ Spring Web MVC framework + Apache Tiles + JSTL
⚫ Spring Security
⚫ Spring Bean Validation (JSR-349)
⚫ Support for the parameterization and internationalization of the messages
Web interface: 
    ⚫ Google Material Design Light Components (https://getmdl.io)
    ⚫ JavaScript (ajax, jquery)
Persistence Layer
    ⚫ Spring Hibernate
    ⚫ PostgreSQL 9 Database | H2
    ⚫ The EAV data model (https://inviqa.com/blog/eav-data-model)
~~~~

#### Useful links
The EAV data model

https://inviqa.com/blog/eav-data-model

https://en.wikipedia.org/wiki/Entity%E2%80%93attribute%E2%80%93value_model


PostgreSQL
https://www.postgresql.org/
Encrypt connection by NGiNX

Migrating to Java 11

https://medium.com/criciumadev/its-time-migrating-to-java-11-5eb3868354f9
update java version in linux
update-alternatives --config java


Validating Input to a Spring Service Method 

https://reflectoring.io/bean-validation-with-spring-boot/

Run your Java application as a Service on linux

https://medium.com/@sulmansarwar/run-your-java-application-as-a-service-on-ubuntu-544531bd6102

https://docs.spring.io/spring-boot/docs/1.3.1.RELEASE/reference/html/deployment-install.html


The default manager is created when the application starts.
Pay attention to the config file
````
manager:
  email: ${MANAGER_EMAIL}
  password: ${MANAGER_PASSWORD}
  fullName: ${MANAGER_FULL_NAME}
  country: ${MANAGER_COUNTRY}
  postcode: ${MANAGER_POSTCODE}
  city: ${MANAGER_CITY}
  address: ${MANAGER_ADDRESS}
````
###Verify default manager email
````
http POST {YOUR_HOST}/web/rest/api/public/customer/verify/ id:=100 verifyCode=yIkZG
````
###Response
````
HTTP/1.1 200 
{
    "id": 100,
    "verified": true,
    "verifyCode": "yIkZG"
}
````

### Sending multipart requests using Spring Boot and Feign
````
   @PostMapping(
            path = "/fileupload-path",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    FileUploadResponse uploadFile(@RequestParam("key") String key,
                                  @RequestPart("file") MultipartFile file);
````