# RentaSolution

Rent a Car application with microservices architecture.

* Contributors
  * Jovan Vunic
  * Boris Opacic
  * Dejan Radulovic
  * Dusan Petkovic

## Rent a Car application architecture

Application has following services:

* [account-service](https://github.com/vunicjovan/xws/tree/master/account-service) - the ```Account Service```
* [agent-service](https://github.com/vunicjovan/xws/tree/master/agent-service) - the ```Agent Service```
* [android-service](https://github.com/vunicjovan/xws/tree/master/android-service) - the ```Android Service```
* [catalog-service](https://github.com/vunicjovan/xws/tree/master/catalog-service) - the ```Catalog Service```
* [gateway](https://github.com/vunicjovan/xws/tree/master/gateway) - the ```Zuul API Gateway``` 
* [mail-service](https://github.com/vunicjovan/xws/tree/master/mail-service) - the ```Mailing Service```
* [message-service](https://github.com/vunicjovan/xws/tree/master/message-service) - the ```Messaging Service```
* [renting-service](https://github.com/vunicjovan/xws/tree/master/renting-service) - the ```Renting Service```
* [search-service](https://github.com/vunicjovan/xws/tree/master/search-service) - the ```Searching Service```
* [service-discovery](https://github.com/vunicjovan/xws/tree/master/service-discovery) - the ```Eureka Service Discovery```
* [view-service](https://github.com/vunicjovan/xws/tree/master/view-service) - the ```View Service```

Client application: 

* [main-front](https://github.com/vunicjovan/xws/tree/master/frontend/main-front) - the Frontend Application

## Services architecture

### Inter-process communication

* The services have REST API

### Business logic designe

Following schemas represent model for business logic

![Image of first diagram](https://github.com/vunicjovan/xws/blob/master/class-diagrams/diagrams1.jpg)

![Image of second diagram](https://github.com/vunicjovan/xws/blob/master/class-diagrams/diagrams2.jpg)

All business logic is implemented in service domains:

* ```Account Service```
  
  * [User](https://github.com/vunicjovan/xws/blob/master/account-service/src/main/java/com/uns/ftn/accountservice/domain/User.java)
  * [Administrator](https://github.com/vunicjovan/xws/blob/master/account-service/src/main/java/com/uns/ftn/accountservice/domain/Administrator.java)
  * [Agent](https://github.com/vunicjovan/xws/blob/master/account-service/src/main/java/com/uns/ftn/accountservice/domain/Agent.java)
  * [SimpleUser](https://github.com/vunicjovan/xws/blob/master/account-service/src/main/java/com/uns/ftn/accountservice/domain/SimpleUser.java)
  * [Company](https://github.com/vunicjovan/xws/blob/master/account-service/src/main/java/com/uns/ftn/accountservice/domain/Company.java)
  * [Role](https://github.com/vunicjovan/xws/blob/master/account-service/src/main/java/com/uns/ftn/accountservice/domain/Role.java)
  * [Permission](https://github.com/vunicjovan/xws/blob/master/account-service/src/main/java/com/uns/ftn/accountservice/domain/Permission.java)
  
* ```Agent Service```

  * [Advertisment](https://github.com/vunicjovan/xws/blob/master/agent-service/src/main/java/com/uns/ftn/agentservice/domain/Advertisement.java)
  * [Comment](https://github.com/vunicjovan/xws/blob/master/agent-service/src/main/java/com/uns/ftn/agentservice/domain/Comment.java)
  * [Photo](https://github.com/vunicjovan/xws/blob/master/agent-service/src/main/java/com/uns/ftn/agentservice/domain/Photo.java)
  * [PriceList](https://github.com/vunicjovan/xws/blob/master/agent-service/src/main/java/com/uns/ftn/agentservice/domain/PriceList.java)
  * [PriceListItem](https://github.com/vunicjovan/xws/blob/master/agent-service/src/main/java/com/uns/ftn/agentservice/domain/PriceListItem.java)
  * [RentingInterval](https://github.com/vunicjovan/xws/blob/master/agent-service/src/main/java/com/uns/ftn/agentservice/domain/RentingInterval.java)
  * [Vehicle](https://github.com/vunicjovan/xws/blob/master/agent-service/src/main/java/com/uns/ftn/agentservice/domain/Vehicle.java)
  
* ```Catalog Service``` 
  
  * [AdRequest](https://github.com/vunicjovan/xws/tree/master/catalog-service/src/main/java/com/uns/ftn/catalogservice/domain)
  * [Brand](https://github.com/vunicjovan/xws/blob/master/catalog-service/src/main/java/com/uns/ftn/catalogservice/domain/Brand.java)
  * [FuelType](https://github.com/vunicjovan/xws/blob/master/catalog-service/src/main/java/com/uns/ftn/catalogservice/domain/FuelType.java)
  * [GearboxType](https://github.com/vunicjovan/xws/blob/master/catalog-service/src/main/java/com/uns/ftn/catalogservice/domain/GearboxType.java)
  * [Model](https://github.com/vunicjovan/xws/blob/master/catalog-service/src/main/java/com/uns/ftn/catalogservice/domain/Model.java)
  * [VehicleClass](https://github.com/vunicjovan/xws/blob/master/catalog-service/src/main/java/com/uns/ftn/catalogservice/domain/VehicleClass.java)
  
* ```Messaging Service```

  * [Message](https://github.com/vunicjovan/xws/blob/master/message-service/src/main/java/com/uns/ftn/messageservice/domain/Message.java)
  
* ```Renting Service```

  * [Cart](https://github.com/vunicjovan/xws/blob/master/renting-service/src/main/java/com/uns/ftn/rentingservice/domain/Cart.java)
  * [CartItem](https://github.com/vunicjovan/xws/blob/master/renting-service/src/main/java/com/uns/ftn/rentingservice/domain/CartItem.java)
  * [Debt](https://github.com/vunicjovan/xws/blob/master/renting-service/src/main/java/com/uns/ftn/rentingservice/domain/Debt.java)
  * [RentingReport](https://github.com/vunicjovan/xws/blob/master/renting-service/src/main/java/com/uns/ftn/rentingservice/domain/RentingReport.java)
  * [RentingRequest](https://github.com/vunicjovan/xws/blob/master/renting-service/src/main/java/com/uns/ftn/rentingservice/domain/RentingRequest.java)
  * [RentingStatus](https://github.com/vunicjovan/xws/blob/master/renting-service/src/main/java/com/uns/ftn/rentingservice/domain/RequestStatus.java)
  

## Agent application architecture

This is monolith application with [backend](https://github.com/vunicjovan/xws/tree/master/agent) and [frontend](https://github.com/vunicjovan/xws/tree/master/frontend/agent-front)

### Communication with microservices

* Application uses SOAP messages

### Business logic

* Application domain

  * [Advertisment](https://github.com/vunicjovan/xws/blob/master/agent/src/main/java/com/uns/ftn/agent/domain/Advertisement.java)
  * [Agent](https://github.com/vunicjovan/xws/blob/master/agent/src/main/java/com/uns/ftn/agent/domain/Agent.java)
  * [Brand](https://github.com/vunicjovan/xws/blob/master/agent/src/main/java/com/uns/ftn/agent/domain/Brand.java)
  * [FuelType](https://github.com/vunicjovan/xws/blob/master/agent/src/main/java/com/uns/ftn/agent/domain/FuelType.java)
  * [GearboxType](https://github.com/vunicjovan/xws/blob/master/agent/src/main/java/com/uns/ftn/agent/domain/GearboxType.java)
  * [Model](https://github.com/vunicjovan/xws/blob/master/agent/src/main/java/com/uns/ftn/agent/domain/Model.java)
  * [Photo](https://github.com/vunicjovan/xws/blob/master/agent/src/main/java/com/uns/ftn/agent/domain/Photo.java)
  * [RentingInterval](https://github.com/vunicjovan/xws/blob/master/agent/src/main/java/com/uns/ftn/agent/domain/RentingInterval.java)
  * [Vehicle](https://github.com/vunicjovan/xws/blob/master/agent/src/main/java/com/uns/ftn/agent/domain/Vehicle.java)
  * [VehicleClass](https://github.com/vunicjovan/xws/blob/master/agent/src/main/java/com/uns/ftn/agent/domain/VehicleClass.java)
  
## Building and running application

### Pre-requisites

* Java 8+
* NodeJS
* Docker and Docker Compose
* Internet access for downloading dependencies

### Building and Running

Build and run services using

```bash
$ docker-compose up --build
```

this can take a while

### Using the application

You can access health check on ```API Gateway```

* Account Service - ```http://localhost:8089/account/health```
* Agent Service - ```http://localhost:8089/agent/health```
* Android Service - ```http://localhost:8089/android/health```
* Catalog Service - ```http://localhost:8089/catalog/health```
* Mailing Service - ```http://localhost:8089/mail/health```
* Messaging Service - ```http://localhost:8089/message/health```
* Renting Service - ```http://localhost:8089/rent/health```
* Search Service - ```http://localhost:8089/search/health```
* View Service - ```http://localhost:8089/view/health```

Also if you go to ```http://localhost:8761/``` you can see Eureka UI with all available services
