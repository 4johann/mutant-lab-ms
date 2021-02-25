dependencies {
    compileOnly(project(":common"))
    implementation(project(":domain"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:2.2.4.RELEASE")
    implementation("redis.clients:jedis")
}