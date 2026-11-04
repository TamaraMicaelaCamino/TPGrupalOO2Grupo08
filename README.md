# TPGrupalOO2Grupo08
## Grupo

- **Grupo número 8**

## Integrantes
- **Lautaro Leiva Szapajko - szapajko**
- **Tamara Camino - TamaraMicaelaCamino**


## Pasos para correr la aplicación(crear bd, variables de entorno, etc). 
Aplicación web desarrollada con Spring Boot 4.1.1, Spring Security, Thymeleaf, JPA/Hibernate y MySQL.

## Requisitos previos

- **JDK 17** o superior (versión definida en el `pom.xml`)
- **Maven** (o usar el wrapper `./mvnw` incluido en el proyecto)
- **MySQL 8** corriendo en `localhost:3306`
- **IntelliJ IDEA** (recomendado)
- Una cuenta de **Gmail** con *contraseña de aplicación* (para el envío de mails)

## 1. Clonar el repositorio

```bash
git clone <https://github.com/TamaraMicaelaCamino/TPGrupalOO2Grupo08.git>
cd tpgrupalhito2grupo08
```
## 2. Crear la base de datos y cargar el script
 
Si la base `epicentro_gourmet` no existe, hay que crearla primero y después cargar el script SQL del proyecto (`<RUTA_DEL_SCRIPT>/<NOMBRE_DEL_SCRIPT>.sql`) sobre esa base.
 
### Desde MySQL Workbench
 
1. Conectarse al servidor local (`localhost:3306`).
2. Crear la base (si no existe):
```sql
   CREATE DATABASE IF NOT EXISTS epicentro_gourmet;
   USE epicentro_gourmet;
```
3. Ir a **File → Open SQL Script…**, elegir el script del proyecto y ejecutarlo con el botón del rayo ⚡ asegurándose de que la base `epicentro_gourmet` esté seleccionada.


## 3. Configurar las variables de entorno
### En IntelliJ IDEA

1. Ir a **Run → Edit Configurations…**
2. Seleccionar **Tpgrupalhito2grupo08Application**
3. Click en **Modify options → Environment variables**
4. Agregar las siguientes variables (separadas por `;`):

| Variable        | Valor                                                                                                                                  |
|-----------------|----------------------------------------------------------------------------------------------------------------------------------------|
| `DB_URL`        | `jdbc:mysql://localhost:3306/epicentro_gourmet?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` |
| `DB_USER`       | tu usuario de MySQL                                                                                                      |
| `DB_PASSWORD`   | tu clave de MySQL                                                                                                                      |
| `MAIL_USERNAME` | email remitente (cuenta Gmail)                                                                                                         |
| `MAIL_PASSWORD` | contraseña de aplicación de Gmail (no la contraseña normal)                                                                            |

## 4. Obtener la contraseña de aplicación de Gmail

1. Activar la **verificación en dos pasos** en la cuenta de Google.
2. Ir a <https://myaccount.google.com/apppasswords>.
3. Crear una contraseña de aplicación (ej: nombre "Epicentro Gourmet").
4. Copiar los 16 caracteres generados y usarlos como `MAIL_PASSWORD` (sin espacios).

## 5. Ejecutar la aplicación

- **Desde IntelliJ:** abrir `Tpgrupalhito2grupo08Application` y presionar **Run** ▶️.
- **Desde la terminal:** `./mvnw spring-boot:run`

Cuando en la consola aparezca `Started Tpgrupalhito2grupo08Application`, abrir en el navegador:

```
http://localhost:8080
```



## Diagrama de Clases:
<img width="2033" height="1517" alt="EPICENTRO_GOURMET_DIAGRAMA drawio" src="https://github.com/user-attachments/assets/4cd8eb34-4d32-434f-a671-7461b4d72634" />

## Casos de uso implementados y responsables:

- **Registro de Unidad de Venta + Responsable** — Tamara Camino  
  [PR #11](https://github.com/TamaraMicaelaCamino/TPGrupalOO2Grupo08/pull/11)

- **Consultar Unidadades de Venta de un festival** — Tamara Camino  
  [PR #14](https://github.com/TamaraMicaelaCamino/TPGrupalOO2Grupo08/pull/14)

- **Consultar Ranking de Unidades de Venta por Recaudación** — Tamara Camino  
  [PR #15](https://github.com/TamaraMicaelaCamino/TPGrupalOO2Grupo08/pull/15)

- **Consultar Unidades de Venta con mayor canon** — Tamara Camino  
  [PR #16](https://github.com/TamaraMicaelaCamino/TPGrupalOO2Grupo08/pull/16)

- **Liquidación de Haberes** — Tamara Camino  
  [PR #19](https://github.com/TamaraMicaelaCamino/TPGrupalOO2Grupo08/pull/19)

- **Consultar Festival finalizado y activo con sus unidades de venta** — Tamara Camino  
  [PR #24](https://github.com/TamaraMicaelaCamino/TPGrupalOO2Grupo08/pull/24)

- **Reporte de Recaudación Total por Festival** — Tamara Camino  
  [PR #26](https://github.com/TamaraMicaelaCamino/TPGrupalOO2Grupo08/pull/26)

- **Generar PDF recibo de sueldo** — Tamara Camino  
  [PR #30](https://github.com/TamaraMicaelaCamino/TPGrupalOO2Grupo08/pull/30)
  



