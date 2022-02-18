# Hexagonal Architecture
> De zeshoekige architectuur, of poorten en adaptersarchitectuur

## Wat?
Deze repository bevat de code voor de CRM demo applicatie welke gemaakt is voor JDI

## In welke omgeving moet de applicatie geinstalleerd worden?
Dit is een demo applicatie en is ontwikkeld om te draaien in een Intelij maven omgeving waarbij de onderstaande database aanwezig moet zijn.

- SQL database (Database scripts zijn gebaseerd om MySQL)

## Hoe moet het gedraaid worden?
Als je de applicatie wilt uitvoeren met de JPA-persistentie-adapter, moet het database configuratiebestand worden ingevuld.


1. Kopieer `infrastructure/adapter-persistence-jpa/src/main/resource/application.properties.template` naar `infrastructure/adapter-persistence-jpa/src/main/resource/application.properties`
2. Open het bestand en vul de ontbrekende parameters in
3. Verander indien gewenst de parameter `spring.datasource.driver-class-name` om een andere database te gebruiken dan MySQL
4. Voer de applicatie uit

Om te kiezen welke adapter wordt gebruikt (bijvoorbeeld voor persistentie), wijzigt je de @Qualifier in
`application/src/main/java/config/ApplicationConfiguration.java`

## Hoe te benaderen?
Om de applicatie te benaderen wordt er gebruikt gemaakt van Swagger. Wanneer de applicatie is uitgevoerd is deze te benaderen via volgende link http://localhost:8080/swagger-ui.html

## De onderzoeken
De onderzoeks verslagen zijn te vinden in de onderzoeken branch en zijn opgeleverd in PDF vorm