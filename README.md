# ExampleMicroserviceKafka
Ejemplo de microservicios con conexión asincrónica utilizando kafka y Postgresql

## Acerca del proyecto
El proyecto es un ejemplo de conexión entre dos microservicios de forma asíncrona, utilizando JPA con Postgresql.

El fin del proyecto es realizar la creación, consulta, eliminación lógica y acualización de clientes - cuentas, así como llevar un control de movimientos, esto de forma básica.

Se utilizan @ControllerAdvice para controlar los errores, evitando la utilización de try y catch lo cual aumenta la complejidad de la función según la lógica del método.


## Levantar microservicios
Primeramente se debe levantar el microservicio desde el path /microservicio, ejecutar desde una terminal lo siguiente.

```
docker compose build
docker compose up -d
```

Una vez revisado que no existen problemas al levantar ingresar a la carpeta /baseDatos y ejecutar primeramente todos archivos dentro de la carpeta /ddl después son las demás.

La carpeta /ddl contiene los script para crear las tablas por lo que este paso debe ser inicial luego de visualizar que la base se encuentra levantada.

Se ha configurado para que el microservicio entidadms se ejecute en el puerto 8080 y el transactionms en el 8180.

## Endpoints
Para visualizar los endpoints puede direccionar a los siguientes enlaces o en el proyecto carpeta /microservicio se encuentra el postman puedes utilizarlo con [POSTMAN](https://www.postman.com/downloads/) o [INSOMNIA](https://insomnia.rest/download)

### Entidad ms
[Swagger EntidadMS Localhost](http://localhost:8080/swagger-ui/index.html)

### Transaction ms
[Swagger TransactionMS Localhost](http://localhost:8180/swagger-ui/index.html)

