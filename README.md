# PurkiadaServer
Toto je repozitář pro hlavní API systému Purkiáda.

## Dokumentace API
Dokumentaci můžete najít vždy na endpointu  _/docs_ , pokud není změněno v  _application.yaml_.

## Konfigurace
Veškerá konfigurace systému se dá provádět pomocí environmentálních proměnných a nebo přímo v souboru  _application.yaml_.

## Autentikace a Autorizace
Veškerá autentikace a autorizace se řeší přes protokol OpenID Connect (OIDC).

Momentálně využíváme self-hosted Keycloak instanci.

Pro změnu OIDC providera nestačí pouze změnit hodnoty v konfiguraci, ale musí se také vytvořit nová implementace interfacu _AuthService_.

## Build
```bash
mvn clean package -Dmaven.test.skip=true
docker build -t registry.matejbucek.cz/purkiada/purkiada-server .
docker push registry.matejbucek.cz/purkiada/purkiada-server
```

## Docker
```yaml
version: "3.7"
services:
  purkiada-server:
    image: registry.matejbucek.cz/purkiada/purkiada-server
    restart: unless-stopped
    depends_on:
      - db
    links:
      - db
    ports:
      - "8080:8080"
    environment:
      - MYSQL_USERNAME=root
      - MYSQL_HOST=db
      - MYSQL_PASSWORD=
      - OAUTH_CLIENT_SECRET=
  db:
    image: mysql
    command: --default-authentication-plugin=mysql_native_password
    restart: unless-stopped
    environment:
      - MYSQL_DATABASE=purkiada
      - MYSQL_ROOT_PASSWORD=
    volumes:
      - "./data:/var/lib/mysql"
```

```bash
docker-compose up -d
```
## Autoři

*   Matěj Bucek
