dependencies {
    testImplementation(project(":app"))
    testImplementation(project(":client"))
    testImplementation(project(":common"))
    testImplementation(project(":domain"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mock-server:mockserver-netty:5.8.1")
    testImplementation("org.mock-server:mockserver-client-java:5.8.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.mock-server:mockserver-junit-jupiter:5.8.1")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:2.2.4.RELEASE")
    implementation("redis.clients:jedis")
    runtimeOnly("com.h2database:h2")
}

jacoco {
    toolVersion = "0.8.6"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.destination = file("${buildDir}/jacoco-html")
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "CLASS"
            limit {
                minimum = "0.81".toBigDecimal()
            }
        }
    }
}
