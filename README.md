# API REST para la Creación de Usuarios

## Requisitos

- Java 17: [Descargar JDK 17](https://jdk.java.net/archive/)
- Maven
- Postman (Opcional)
- Asegúrate de que `JAVA_PATH` esté configurado correctamente para que el entorno funcione correctamente.

## Pasos para Ejecutar

1. Clona el siguiente repositorio:

   ```bash
   git clone https://github.com/Diego4Cornejo/ejercicioAPI.git

2. Abre la terminal en la carpeta del proyecto y ejecuta el siguiente comando (la base de datos se generara automaticamente al iniciar el proyecto):
  
    ```bash
    mvn spring-boot:run
    o
    mvnw spring-boot:run


3. Carga la siguiente URL
    ```bash 
    http://localhost:8080/swagger-ui/index.html#/
    o
    http://localhost:8080/

4.  Puedes entrar a la BD con la siguiente URL 
    ```bash 
    http://localhost:8080/h2-console/
    URL=jdbc:h2:mem:apiusuariodb
    user:sa
    password:password

## USO:

1. Registro de Usuarios:
    ```bash 
    localhost:8080/crearusuario

2. El correo debe seguir el siguiente formado: 
(aaaaaaa@dominio.cl)
La contraseña debe seguir el siguiente formato: 
Al menos un dígito (0-9).
Al menos una letra minúscula (a-z).
Al menos una letra mayúscula (A-Z).
Longitud mínima de 8 caracteres.

# Ejemplo

BODY(JSON):
```bash 
{
  "name": "Nombre Apellido",
  "email": "correo@example.com",
  "password": "contraseña123",
  "phones": [
    {
      "number": "123456789",
      "citycode": "1",
      "countrycode": "57"
    }
  ]
}

Respuesta Exitosa: 
```bash 
{
  "id": "1",
  "user": "correo@example.com",
  "created": "2024-04-09T10:00:00",
  "modified": "2024-04-09T10:00:00",
  "last_login": "2024-04-09T10:00:00",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "isactive": true
}

Validacion para el token 
    ```bash 
    localhost:8080//valida-token?token={token}
    Respuesta: Token Valido - Token Invalido
