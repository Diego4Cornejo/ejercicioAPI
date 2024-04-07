API Rest para la creación de usuarios

Requisitos:
- Java 17
- Maven
- H2 Database
- Postman (Opcional)

Pasos:
1.- Clona el siguiente repositorio:
https://github.com/Diego4Cornejo/ejercicioAPI.git

2.- Abre la terminal en la carpeta del proyecto y ejecuta el siguiente comando (la base de datos se generara automaticamente al iniciar el proyecto)
mvn spring-boot:run

3.-Carga la siguiente URL 
http://localhost:8080/swagger-ui/index.html#/
o
http://localhost:8080/

4.- Puedes entrar a la BD con la siguiente URL 
http://localhost:8080/h2-console/
user:sa
password:password

USO:

Registro de Usuarios:
localhost:8080/crearusuario

El correo debe seguir el siguiente formado: (aaaaaaa@dominio.cl)
La contraseña debe seguir el siguiente formato: 
Al menos un dígito (0-9).
Al menos una letra minúscula (a-z).
Al menos una letra mayúscula (A-Z).
Longitud mínima de 8 caracteres.

BODY(JSON):

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
localhost:8080//valida-token?token={token}
Respuesta: Token Valido - Token Invalido