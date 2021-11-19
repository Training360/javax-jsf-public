# Java EE alkalmazás átmigrálva Spring Bootra

Lépések:

* `pom.xml` módosítások
* `src/main/WEB-APP/*.xhtml` átmozgatva a `src/main/resources/META-INF/resources` könyvtárba
* `EmployeesApplication` létrehozása
* `web.xml` fájlban a verzió `3.0`, `FacesServlet` eltávolítása 
* `ModelMapperConfig` helyett a `ModelMapper` az `EmployeesApplication` osztályba
* `IndexController` osztály
* `@Component` és `@RequestScope` annotációk használata `@Model` annotáció helyett
* `SimpleHelloControllerIT` és `CreateEmployeeIT` Arquillian teszteset eltávolítása, `pom.xml`-ből Arquillian eltávolítása 
* `org.glassfish.jaxb:jaxb-runtime` függőség
* Flow helyett Session scoped controller
* Fájl fel- és letöltés törlése