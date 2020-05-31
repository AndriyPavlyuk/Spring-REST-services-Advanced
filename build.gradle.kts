plugins { 
  java
  eclipse
  id("org.springframework.boot") version "2.3.0.RELEASE" apply false
}

allprojects {
   group = "it.discovery"
}

subprojects {  
   apply(plugin = "java")

   java.sourceCompatibility = JavaVersion.VERSION_14
   java.targetCompatibility = JavaVersion.VERSION_14

   repositories {
     jcenter()
   }

   var springBootVersion = "2.3.0.RELEASE"
   
   dependencies {
        implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
        compile("org.springframework.boot:spring-boot-starter-web")
        compile("org.apache.commons:commons-lang3")

        compile("javax.xml.bind:jaxb-api:2.3.0")
        runtime("com.sun.xml.bind:jaxb-impl:2.3.0")
        runtime("com.sun.xml.bind:jaxb-core:2.3.0")
        runtime("javax.annotation:javax.annotation-api:1.3.1")
        compileOnly("org.projectlombok:lombok:1.18.12")
        annotationProcessor("org.projectlombok:lombok:1.18.12")
   } 
}

