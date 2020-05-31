dependencies {
    compile("org.springframework.boot:spring-boot-devtools")
    compile("javax.validation:validation-api")
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework.boot:spring-boot-starter-hateoas")
    compile("com.github.dozermapper:dozer-core:6.5.0")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("com.h2database:h2")

    testCompile("com.jayway.jsonpath:json-path:2.4.0")    
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("io.rest-assured:rest-assured")
    testCompile("io.rest-assured:spring-mock-mvc")
}

tasks.test {
    useJUnitPlatform()
}