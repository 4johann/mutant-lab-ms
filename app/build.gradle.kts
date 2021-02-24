import kotlin.collections.listOf
import com.benjaminsproule.swagger.gradleplugin.model.InfoExtension
import com.benjaminsproule.swagger.gradleplugin.model.ApiSourceExtension
import com.benjaminsproule.swagger.gradleplugin.model.SecurityDefinitionExtension
import com.benjaminsproule.swagger.gradleplugin.model.TagExtension

object Versions {
    const val SWAGGER_VERSION = "2.9.2"
}

object Swagger {
    val microserviceName = System.getenv("MICROSERVICE_NAME").orEmpty()
    val baseUrl =  System.getenv("BASE_URL").orEmpty()
    val prefixPath =  System.getenv("PREFIX").orEmpty()
    val host =  System.getenv("SWAGGER_HOST").orEmpty()
    val version = System.getenv("VERSION").orEmpty()
    val owner =  System.getenv("OWNER").orEmpty()
    val description = ""
}

plugins{
    id("com.benjaminsproule.swagger") version "1.0.8"
    id ("io.swagger.swaggerhub") version "1.0.1"
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("io.springfox:springfox-swagger2:${Versions.SWAGGER_VERSION}")
    implementation("io.springfox:springfox-swagger-ui:${Versions.SWAGGER_VERSION}")
}

swagger {
    apiSource (
        closureOf<ApiSourceExtension>{
            this.locations = listOf("com.microservice.app.controller", "com.microservice.common")
            this.outputPath = "swagger"
            this.swaggerDirectory = "$buildDir/swagger-doc"
            this.host = Swagger.baseUrl
            this.basePath = Swagger.prefixPath
            this.springmvc = true
            this.expandSuperTypes = false
            this.info(closureOf<InfoExtension> {
                this.title = Swagger.microserviceName
                this.version = Swagger.version
                this.description = Swagger.description
            })
            this.securityDefinition(closureOf<SecurityDefinitionExtension> {
                this.name = "Bearer"
                this.type = "apiKey"
                this.keyLocation = "header"
                this.keyName = "Authorization"
            })
            this.tag(closureOf<TagExtension> {
                this.name = "API"
                this.description = "META-INFO"
            })
        }
    )
}

tasks.getByName("swaggerhubUpload") {
    this.setProperty("api", Swagger.microserviceName)
    this.setProperty("owner", Swagger.owner)
    this.setProperty("version", Swagger.version)
    this.setProperty("inputFile", "$buildDir/swagger-doc/swagger.json")
    this.setProperty("token", "1")
    this.setProperty("host", Swagger.host)
}