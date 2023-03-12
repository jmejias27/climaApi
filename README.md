# Jonathan Mejias
Email: jmejias.ccm@gmail.com

# climaDemo
Api de consulta del clima desde el servicio de https://developer.accuweather.com/ para test de Vates

## Tecnología
* Java V1.8
* Springframework V2.9.2
* Maven
* SpringBoot
* Hibernate
* JSON
* MariaDB
* Swagger V2.9.2

## Configuración

### Archivo configuration.properties

#### Propiedades del servicio
server.port=8080

#### Propiedades del API de consulta https://developer.accuweather.com/

- Token de identificación, generado con un usuario creado en la página
com.clima.weatherapi.apikey=nlbNRx4V4wpAoSVRKUwGiATxZGSUZw0G

- Idioma de respuesta del servicio, ver otros idiomas en: https://developer.accuweather.com/localizations-by-language
com.clima.weatherapi.language=es-ar

- URL de consulta de clima por localidad
com.clima.weatherapi.uriConsulLocalidad=http://dataservice.accuweather.com/currentconditions/v1/

#### Propiedades del datasource (MariaDB)
spring.jpa.hibernate.ddl-auto=update

spring.datasource.url=jdbc:mariadb://localhost:3306/clima?verifyServerCertificate=false&useSSL=false

spring.datasource.username=demo

spring.datasource.password=Demo

spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

* Cambiar en caso de conexión a otras instancias de DB
* Se debe crear la base de datos de nombre clima
* Si no se usa el usuario demo de BD, crear y dar permisos GRANT sobre la BD.

### Datasource
Base de datos: clima

Tabla: clim_loc_hist

Estructura de la tabla:

CREATE TABLE `clim_loc_hist` (

  `id_reg` bigint(20) NOT NULL AUTO_INCREMENT,
  
  `tip_precipitacion` varchar(255) DEFAULT NULL,
  
  `mca_dia` bit(1) DEFAULT NULL,
  
  `fec_medicion` date DEFAULT NULL,
  
  `fec_reg` date DEFAULT NULL,
  
  `mca_precipitacion` bit(1) DEFAULT NULL,
  
  `hor_medicion` time DEFAULT NULL,
  
  `hor_reg` time DEFAULT NULL,
  
  `id_locacion` int(11) DEFAULT NULL,
  
  `imp_temperatura` varchar(1) DEFAULT NULL,
  
  `imp_val_temperatura` double DEFAULT NULL,
  
  `met_temperatura` varchar(1) DEFAULT NULL,
  
  `met_val_temperatura` double DEFAULT NULL,
  
  `txt_clima` varchar(255) DEFAULT NULL,
  
  PRIMARY KEY (`id_reg`)
  
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

### Documentación del Swagger
../swagger-ui.html

../v2/api-docs

### Servicio disponible
- Servicio de consulta de clima por localidad

Descripción: Servicio de solicitud de estado del clima actual de la localidad (idLocacion) consultada. Durante la ejecución se realiza un llamado al servicio http://dataservice.accuweather.com/currentconditions/v1/ de http://dataservice.accuweather.com/ quedevuelve los siguientes datos, ejemplo:

[

  {
  
    "LocalObservationDateTime": "2023-03-11T13:20:00-03:00",
    
    "EpochTime": 1678551600,
    
    "WeatherText": "Partly sunny",
    
    "WeatherIcon": 3,
    
    "HasPrecipitation": false,
    
    "PrecipitationType": null,
    
    "IsDayTime": true,
    
    "Temperature": {
    
      "Metric": {
      
        "Value": 32.2,
        
        "Unit": "C",
        
        "UnitType": 17
        
      },
      
      "Imperial": {
      
        "Value": 90,
        
        "Unit": "F",
        
        "UnitType": 18
        
      }
      
    },
    
    "MobileLink": "http://www.accuweather.com/en/ar/buenos-aires/7894/current-weather/7894?lang=en-us",
    
    "Link": "http://www.accuweather.com/en/ar/buenos-aires/7894/current-weather/7894?lang=en-us"
    
  }
  
]


Con este resultado, se toman los datos relevantes como son LocalObservationDateTime, WeatherText, HasPrecipitation, PrecipitationType, IsDayTime y Temperature. De este último se toman los valores de la medición en los sistemas métricos e imperial.

Ademas se toma como dato la hora del servidor para registrar en la tabla clim_loc_hist de la base de datos mariadb de nombre clima, y esta genera un id único para cada consulta.

Se guardan en la tabla como registros y finalmente se devuelven los datos relevantes de la consulta del clima como una respuesta del tipo JSON.

Endpoint: /clima/consultaId/{idLocacion}
Método: GET

Parametro de entrada:
Nmbre: idLocacion
Tpo: String

Respuesta:

* Código de respuesta: 200

{

  "clima": {
  
    "esDeDia": true,
    
    "hayPrecipitacion": true,
    
    "temperatura": {
    
      "sisImperial": {
      
        "unidad": "string",
        
        "valor": 0
        
      },
      
      "sisMetrico": {
      
        "unidad": "string",
        
        "valor": 0
        
      }
      
    },
    
    "tipPrecipitacion": "string",
    
    "txtClima": "string"
    
  },
  
  "mensaje": {
  
    "codMsj": "string",
    
    "txtMsj": "string"
    
  }
  
}

* Código de respuesta: 401

Unauthorized

* Código de respuesta: 403

Forbidden

* Código de respuesta: 404

Not Found

### Prueba
Para probar usar el idLocation: 7894 el cual es la identificación para la Ciudad de Buenos Aires en el sistema de accuweater.

Para usar el Servicio de consulta de clima por localidad.
Usar el endpoint: /clima/consultaId/{idLocacion}

Ejemplo: http://localhost:8080/clima/consultaId/7894

El cual responderá con:

{

    "clima": {
    
        "temperatura": {
        
            "sisMetrico": {
            
                "valor": 33.9,
                
                "unidad": "C"
                
            },
            
            "sisImperial": {
            
                "valor": 93.0,
                
                "unidad": "F"
                
            }
            
        },
        
        "hayPrecipitacion": false,
        
        "txtClima": "Soleado",
        
        "esDeDia": true,
        
        "tipPrecipitacion": "null"
        
    },
    
    "mensaje": {
    
        "codMsj": "200",
        
        "txtMsj": "OK"
        
    }
    
}


Para encontrar más locaciones para consultar, usar el servicio de http://dataservice.accuweather.com/ para consultar localidades por autocompletado de palabra: 

http://dataservice.accuweather.com/locations/v1/cities/autocomplete

Por ejemplo, para buscar con la palabra "buenos":

"http://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey=nlbNRx4V4wpAoSVRKUwGiATxZGSUZw0G&q=buenos&language=en-us"

Esta consulta devolvera un arreglo que contiene el siguiente JSON:

{

    "Version": 1,
    
    "Key": "7894",
    
    "Type": "City",
    
    "Rank": 20,
    
    "LocalizedName": "Buenos Aires",
    
    "Country": {
    
      "ID": "AR",
      
      "LocalizedName": "Argentina"
      
    },
    
    "AdministrativeArea": {
    
      "ID": "C",
      
      "LocalizedName": "Buenos Aires - Capital Federal"
      
    }
    
  }

Donde podran obtener el idLocation de cada localidad en el parametro "Key" de cada json del arreglo.
