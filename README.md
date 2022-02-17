# Hexagonal Architecture
> The most beautiful demo application!

## What?
This repository contains the code for the CRM application created for JDI.

## How to run?
If you want to run the application with the JPA persistence adapter the database config file needs to be filled in.

1. Copy `infrastructure/adapter-persistence-jpa/src/main/resource/application.properties.template` to `infrastructure/adapter-persistence-jpa/src/main/resource/application.properties`
2. Open the file and fill in the missing parameters
3. Run the application

To choose which adapter is used (for example for persistence), change the @Qualifier in:
`application/src/main/java/config/ApplicationConfiguration.java`