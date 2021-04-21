# Microtis-GattoVerde :smirk_cat: :green_heart:

Tribes project for the GattoVerde team JAVA
:guardsman::moyai::bomb::crown::european_castle::circus_tent::no_mobile_phones::underage::trophy:

**Contributors:**

Martin Prívara (Kingdom Model, Kingdom Service, Location, Building Purchase System, Tests) <br/>
Hashem Abdelrhman (Authentication, Security, Mappers, Tests) <br/>
Zuzana Stašová (Troop Model, Services, Endpoints, Logging, Tests) <br/>
Lucie Jindřichová (Resource Model, Starter Pack Service, Global Exception Handler, Refactoring, Tests) <br/>
Zdeněk Pelcl (Building Model, Troop Purchase System, Time Service, Tests) <br/>
Robert Pajić (Player Model, Services, Heroku Deployment, Tests) <br/>


**Environment _variables:_**
 

| Variable name :lock: | Value :key: |
| -------------------- | ----------- |
|DATASOURCE_URL|jdbc:mysql://localhost/\{your database name}|
|DATASOURCE_USERNAME|\{your local mysql username}|
|DATASOURCE_PASSWORD|\{your local mysql password}|
|HIBERNATE_DIALECT|org.hibernate.dialect.MySQL5Dialect|
|SERVER_PORT|{your local server port ex:8080}|
|TRIBES_GAMETICK_LEN|{*/{tick period in sec} * * * * *}|
|SECRET_KEY|{thisisaverylongsecretKey}|
|LOG_LEVEL|for production: info / for development: debug|

**Authentication:**

| key           |  Value       |
| ------------- | -----------  |
| X-tribes-token| Bearer receivedToken|

To reach any endpoint first login with an existing user, then you will receive a valid 
Jwt token, use it in the header of any request. Request header is X-tribes-token.

**DB Migration (FlyWay):**

In case a new attribute is added/removed for an entity then adding/deleting 
a new column in DB is a must, in resources/db.migration create a new empty version/file 
(don't modify the old version) V1.1_1__descriptive_action.sql add the new sql statement
in it for adding that column, also this applies if you need to add or change constraints.

**Spring profiles**

| Key           |  Value       |
| ------------- | -----------  |
| PROFILE | test / Heroku / Development|

**The following Spring profiles are _available:_**

| PROFILE       |  SETTINGS    |
| ------------- | -----------  |
| test       | with H2 database  |
| Heroku     | Flyway is disabled, mySQL database |
| Development| Flyway is enabled, mySQL database      |
