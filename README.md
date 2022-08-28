# Repositorio Backend proyecto Trabajo Fin de Grado Josgongon9 - Configuración arranque en Local

## Configuración Base de Datos con Docker
En primer lugar debemos de levantar nuestra base de datos MongoDB. Ejecutamos el fichero ```docker-compose-bbdd.yml```  lanzando el comando:
```sh
docker-compose -f docker-compose-dev.yml up
```
## Carga básica de datos
Debemos iniciar la terminal de mongo (desde Docker) con el comando “mongo” y lanzar la inicialización de los roles:
```MongoDB
Use tfg-bbdd
```
```MongoDB
db.roles.insertMany([
   { name: "ROLE_USER" },
   { name: "ROLE_MODERATOR" },
   { name: "ROLE_ADMIN" },
])”
```

## Lanzar Backend en IntelliJ
Una vez tengamos la base de datos MongoDB debemos definir la siguiente configración de arranque (para JDK 15):
```sh
spring-boot:run -Dspring-boot.run.fork=false
```
Pulsar sobre "Run" con la configuración definida anteriormente.

*Por defecto, el Backend se levantára en el puerto 8080, en caso de modificación de puerto editar el fichero application.properties.*

## Pruebas en puesto Local y Swagger
Si todo ha ido bien, tendriamos que tener accesible el Swagger en la siguiente dirección: http://localhost:8080/swagger-ui.html (depende del puerto configurado).

Para realizar pruebas mediante Postman basta con indicar en el "Header" el par clave-valor: "Authorization" - "Bearer + TOKEN " , del mismo modo que en Swagger.

## Integración con Frontal
Para continuar con la guía y desplegar el Frontend: https://github.com/josgongon9/tfg-josgongon9-frontend