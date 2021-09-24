# Mini-Loan Application Service and Client Application

## Task Description
### Part 1: RESTful Service
Pending

#### Loan Application Request

```json
{
   "lanetakere":[
      {
         "fnr":"01056000069",
         "navn":"Kari Nordmann"
      },
      {
         "fnr":"01056000301",
         "navn":"Ola Nordmann"
      }
   ],
   "lanebelop":2450000,
   "behov":"Vi skal låne penger til........",
   "lopetid":300,
   "avdragsfriPeriode":12,
   "type":"annuitet"
}
```

### Part 2: Angular Client Application
Pending

## Requirements
Pending

## Build and Run the Application
In the project's top-level directory execute the following command:
```./mvnw package``` 

After executing the above command, ```cd``` to the _target_ directory and run the following command:
```java -jar target/loan-0.0.1-SNAPSHOT.jar```

## Calling Endpoints
Pending

## OpenAPI Documentation
Swagger-based OpenAPI documentation is available for the application, here: ```http://localhost:8080/swagger-ui.html```

## Comments and Considerations
- The application's Spring Boot service is targetting Java 16. If this was an actual production service, I would have used a 
[Java LTS release](https://www.oracle.com/java/technologies/java-se-support-roadmap.html), currently either Java Release 11 
or &mdash;the very recently released&mdash; Java Release 17.
- Pending

## Relevant Resources
- [What is HATEOAS?](https://dzone.com/articles/rest-api-what-is-hateoas)
- [HAL - Hypertext Application Language](https://stateless.group/hal_specification.html)
- [Best Practices in API Design](https://swagger.io/resources/articles/best-practices-in-api-design/)
