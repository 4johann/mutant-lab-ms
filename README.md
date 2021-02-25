# Mutant Lab

## Problema

Dado un ADN en una matriz de `NxN`, se desea saber si correspone a un mutante o a un humano para lo cual conocemos que un ADN mutante tiene más de una secuencia de cuatro letras iguales, de forma oblicua, horizontal o vertical. A modo de ejemplo se muestra a continuación el ADN de un humano y un mutante.

|  A  |  T  |  G  |  C  |  G  |  A  |
|:---:|:---:|:---:|:---:|:---:|:---:|
|  C  |  A  |  G  |  T  |  G  |  C  |
|  T  |  T  |  A  |  T  |  T  |  T  |
|  A  |  G  |  A  |  C  |  G  |  G  |
|  G  |  C  |  G  |  T  |  C  |  A  |
|  T  |  C  |  A  |  C  |  T  |  G  |

Tabla 1: ADN humano

|  A  |  T  |  G  |  C  |  G  |  A  |
|:---:|:---:|:---:|:---:|:---:|:---:|
|  C  |  A  |  G  |  T  |  G  |  C  |
|  T  |  T  |  A  |  T  |  G  |  T  |
|  A  |  G  |  A  |  A  |  G  |  G  |
|  C  |  C  |  C  |  C  |  T  |  A  |
|  T  |  C  |  A  |  C  |  T  |  G  |

Tabla 1: ADN mutante

## Ejecución
### Herramientas
El proyecto fue realizado con las siguientes herramientas
* Docker v20.10.2
* docker-compose v1.27.4
* JDK v1.8.0_261
### Pasos
1. Nos dirigimos a la carpeta principal del proyecto
```shell
cd mutant-lab-ms
```

2. Buscamos nuestra dirección IP privada ya sea con el comando `ipconfig` o `ifconfig` respectivamente y la asignamos con nuestro editor de texto favorito a las variables de entorno `REDIS_HOST` y `DB_HOST` en el archivo `Dockerfile`.


3. Mediante Docker habilitamos los servicios necesarios para el funcionamiento del proyecto
```shell
docker-compose down && docker-compose up -d
```

4. Compilamos el proyecto
```shell
./gradlew build clean && ./gradlew build
```

5. Creamos la imagen del servicio
```shell
docker build . -t mutant-lab-ms
```

6. Corremos el contenedor de la imagen creada en el anterior paso
```shell
docker run -p 8080:8080 mutant-lab-ms
```

## Funcionamiento
1. Con el fin de conocer si el ADN corresponde o no a un mutante, realizamos un `POST /mutants` cuyo body se muestra a continuación
```json
{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}
```

2. Para conocer las estadísticas basta con realizar una petición `GET /stats` cuya respuesta nos dará la siguiente información
```json
{
    "count_mutant_dna": 1,
    "count_human_dna": 1,
    "ratio": 0.5
}
```