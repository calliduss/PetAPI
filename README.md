# Swagger PetAPI

Used technologies and frameworks:
- Java
- Rest Assured
- TestNG
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

You can generate a report using one of the following command:

1) ```mvn allure:serve```
Report will be generated into temp folder. Web server with results will start automatically and open a browser window.

2) ```mvn allure:report```
Report will be generated tо directory: target/site/allure-maven/index.html

After executing ```mvn allure:report``` you need to start jetty server with the following command:
```mvn jetty:run```

Then open a browser in http://localhost:8080 so as to see the generated report by Allure

Allure report example: 

![AllureReport](https://github.com/Usertiron/VaadinWebApplication/blob/master/allure_example/AllureReport.gif)
