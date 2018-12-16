# Swagger PetAPI

Used technologies and frameworks:
- Java
- Rest Assured
- JUnit
- Allure Framework
- Jetty

## Getting Started

How to run unit tests? You can use a few options below:

#Manual run
right-click on the PetCRUD.java class -> run 'PetCRUD' (or Ctrl + Shift + F10 in Intellij IDEA)

#Run all the unit test classes  
```mvn clean test```

#Run a single test class  
```mvn test -Dtest=PetCRUD```

#Run a single test method from a test class  
```mvn -Dtest=PetCRUD#testA_isSwaggerUp test```


## How to see allure report

In order to check the results in the Allure report, run the following maven command  
```mvn clean test site jetty:run```

Open a browser in http://localhost:8080 so as to see the generated report by Allure
