# Product Api

----------------
## Run locally:
- in order to start keycloak server and postgres db run:
```
docker compose up keycloak products-db
```
- if no docker installed, install keycloak and postgres db on you machine
  - https://www.postgresql.org/download/
  - https://www.keycloak.org/getting-started/getting-started-zip
- configure keycloak on port 8080 and postgres on connection url jdbc:postgresql://localhost:5433/products-db
- access keycloak using admin/password credential
- create a new realm by importing the json from src/main/resources/keycloak/realm-demo-realm.json
- after the db and keycloak server are up, start the application:
```
mvn spring-boot:run
```
- or just simply run it from the IDE and hit localhost:8081

## Run on docker:
```
docker compose up
```
- hit localhost:8081

----------------
### Api explained:
- realm-demo-realm.json contains 3 users (user/user, admin/admin, admin_user/admin_user)
  and 2 roles (role_user, role_admin)
- user -> role_user, admin -> role_admin, admin_user has both roles
- the api exposes a swagger with 4 endpoints protected with authentication and authorization:
  - role_user can access /fetchProducts and /insertProduct
  - role_admin can access all 4 protected endpoints
- a sample exception handling is done using @ControllerAdvice
- for logging I used logback

### Testing:
- test directly in the browser with swagger-ui help;
- access resources/http/http-tests.http file, you have access token endpoint + protected api calls (this methode works only when api is locally running);
- the project contains 2 @Test classes (for the controller and for the service)


 

