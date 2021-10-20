# short-url

SpringBoot URL Shortener with Redis

The application is deployed on Heroku and the Swagger UI for the API can be accessed using:

``` https://short-url-1701.herokuapp.com/swagger-ui.html  ```

## Build and Run

Prerequisites: Maven, Springboot, Java

```
mvn clean install
mvn clean package
mvn spring-boot:run
```

This will start the application on localhost 8090. The port can be configured in application.properties

## Test

Unit Tests can be executed using the following command ``` mvn clean test ```

## Data Persistence

The application uses a redis instance on AWS to store and retrieve data based on the request. 
The Redis hostname, port and password are defined in application.properties.
The Time to live for the each record in redis is defined as 600 seconds. It can be configured from application.properties.

## Swagger

Swagger is integrated with the application for interacting with the API. The API documentation along with the information for all the end points can be viewed in Swagger.

Swagger UI for deployed application: ``` https://short-url-1701.herokuapp.com/swagger-ui.html ```

After starting the application, Swagger UI can be accessed through ``` http://localhost:8090/swagger-ui.html ```

<img width="1440" alt="Screen Shot 2021-10-19 at 10 24 55 PM" src="https://user-images.githubusercontent.com/59263423/138017694-0eee2bb9-a25e-468f-bfb6-0761acc1db44.png">

## API

#### The application supports six endpoints:

1. Endpoint for generating short-url

    ``` http://localhost:8090/urlShortener ```

    Request Body:

    ``` { "url": "https://www.redis.com/" }  ```
  
    Response Body (Short-url):
    
    ``` b71270ed  ```
  
2. Endpoint for retrieving long-url from short-url

    ``` http://localhost:8090/urlShortener/getLongUrl/{url} ```
  
    Response Body (Long-url):
  
    ``` https://www.redis.com/  ```
    
3. Endpoint for Analytics which indicates how many times a short-url was accessed in 24

    ``` http://localhost:8090/urlShortener/analytics/{url} ```
  
    Response Body:
  
    ``` The URL b71270ed was accessed 4 times in the last 24 hours. ```
    ``` The URL b71270ed was accessed 6 times in the last week. ```
    ``` The URL b71270ed was totally accessed 6 times. ```
    
4. Endpoint for deleting a short-url

    ``` http://localhost:8090/urlShortener/deleteShortUrl/{url} ```
  
    Response Body:
  
    ``` Short Url deleted! ```
    
5. Endpoint for top searched url

    ``` http://localhost:8090/urlShortener/getTopSearch ```
  
    Response Body:
  
    ``` The URL b71270ed was accessed 6 times. ```
    
6. Endpoint for checking the health of the application

    ``` http://localhost:8090/urlShortener/health ```
  
    Response Body:
  
    ``` Up ```
    
    
  



