class: inverse, center, middle

# JavaServer Faces

---

class: inverse, center, middle

# Tematika

---

## Tematika

* Egyszerű oldal felépítése
* Kérés életútja, navigáció, átirányítás és hibakezelés
* Képernyő fejlesztés menete (megjelenítés, űrlap)
* Navigáció
* Konvertálás, validáció
* I18N
* Stílusok
* Facelets alapú sablonkezelés
* PrimeFaces
* Összetett és egyedi komponensfejlesztés

---

## Források

* Antonio Goncalves: Beginning Java EE 7 (Expert Voice in Java)
* David Geary,‎ Cay S. Horstmann: Core JavaServer Faces (3rd Edition)
* [Java Platform, Enterprise Edition 7: The Java EE Tutorial](https://docs.oracle.com/javaee/7/tutorial/index.html)

---

class: inverse, center, middle

# Egyszerű Java EE JSF alkalmazás

---

## JSF koncepció

* Java EE specifikáció webes felhasználói felület implementálására
* Komponensek, események, Java beanek kérés/válasz helyett
* Model-View-Controller (MVC) tervezési minta
* Könnyen tanulható, egységes, szabványos
* Specifikáció, különböző implementációkkal, mint pl. [Mojarra](https://projects.eclipse.org/projects/ee4j.mojarra) (RI),
 [Apache MyFaces](http://myfaces.apache.org/), [PrimeFaces](https://www.primefaces.org/), stb.

---

## Környezet

* Jetty, Tomcat - a JSF a CDI-re épül, a web konténerek nem tartalmazzák
  * Körülményesen integrálható
* Java EE 8 alkalmazásszerver
* Spring Frameworkkel integrálható

---

## JSF architektúra áttekintés

* MVC: model (backing bean), view (Facelets), controller (FacesServlet)

![JSF architektúra](images/jsf.gif)

---

## JSF fogalmai

* Faces Servlet (Front Controller tervezési minta)
  * Opcionális konfiguráció a `faces-config.xml` fájlban, helyette annotációk
* Oldalak és komponensek: több page declaration language (PDL) <br /> vagy view declaration language (VDL), JSF 2.0 óta Facelets javasolt
  * Expression Language: komponens és backing bean kapcsolata <br /> (variable és action bind)
* Renderer: komponens megjelenítése, felhasználói adatbevitel
* Backing beans: üzleti logika és navigáció
* Converter: szöveg és Java adattípusok közötti konverziók
* Validator: adatbevitel ellenőrzése, <br /> delegáció Bean Validation felé

---

## Backing bean

* Controller komponens (elnevezési konvenció)
* ` @javax.inject.Named` annotáció
  * Paraméterezhető a `value` attribútummal (alapértelmezetten az osztály nevéből)
* Scope: pl. `@RequestScoped`
* `@javax.enterprise.inject.Model` = `@Named` + `@RequestScoped`
* getter/setter az attribútumokhoz (property)
    * setter űrlap esetén
* metódusok a felhasználói műveletekhez
* Életciklushoz tartozó metódusok <br /> `@PostConstruct` és `@PreDestroy` metódusokkal

---

## Backing bean forráskód

```java
@Model
public class HelloController {

    public String getMessage() {
        return "Hello JSF!";
    }
}
```

---

## Facelets

Maven esetén az `src/main/webapp/hello.xhtml` fájlba

```xml
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html">

<h:head>
    <title>JSF Hello World</title>
</h:head>
<h:body>
    <h1>JSF Hello World</h1>

    #{helloController.message}
</h:body>
</html>
```

---

## `faces-config.xml`

Maven esetén az `src/main/webapp/WEB-INF/faces-config.xml` fájlba

```xml
<?xml version="1.0" encoding="UTF-8"?>
<faces-config
   xmlns="http://xmlns.jcp.org/xml/ns/javaee"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation=
     "http://xmlns.jcp.org/xml/ns/javaee/web-facesconfig_2_3.xsd"
   version="2.3">
</faces-config>
```

Ekkor nem szükséges `web.xml`!

---

## `web.xml`

Maven esetén az `src/main/webapp/WEB-INF/web.xml` fájlba

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app
        xmlns="http://xmlns.jcp.org/xml/ns/javaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
          http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
        version="4.0">
    <welcome-file-list>
        <welcome-file>index.xhtml</welcome-file>
    </welcome-file-list>
</web-app>
```

* `welcome-file` miatt

---

## Maven `pom.xml`

A `pom.xml` állományban Java EE függőség:

```xml
<dependency>
    <groupId>javax</groupId>
    <artifactId>javaee-api</artifactId>
    <version>8.0</version>
    <scope>provided</scope>
</dependency>
```

---

class: inverse, center, middle

# Egyszerű Spring Boot JSF alkalmazás

---

## Spring Framework integráció

* [JoinFaces](http://joinfaces.org/) - JSF Spring Boot Starters

---

## Backing bean

```java
@Component
@RequestScope
public class HelloController {

    public String getMessage() {
        return "Hello JSF!";
    }
}
```

---

## Facelets

Maven esetén az `src/main/resources/META-INF/resources/hello.xhtml` fájlba

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html">

<h:head>
    <title>JSF Hello World</title>
</h:head>
<h:body>
    <h1>JSF Hello World</h1>

    #{helloController.message}
</h:body>
</html>
```

---

## Átirányítás

* A `web.xml` `welcome-file` nem működik

```java
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "forward:hello.xhtml";
    }

}
```

---

## faces-config.xml

* Nem kötelező megadni, csak ha valamit konfigurálni szeretnénk
* Maven esetén az `src/main/resources/META-INF/faces-config.xml` fájlba

```xml
<?xml version="1.0" encoding="UTF-8"?>
<faces-config
  xmlns="http://xmlns.jcp.org/xml/ns/javaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation=
    "http://xmlns.jcp.org/xml/ns/javaee/web-facesconfig_2_2.xsd"
  version="2.2">
</faces-config>
```

---

## faces-config.xml megkötések

* Megadása esetén `2.2` verziónak kell benne szerepelnie, <br /> `2.3` esetén `Caused by: javax.faces.FacesException:` <br /> `Unable to find CDI BeanManager`
* Nem kötelező a `web.xml`
* Megadása esetén `version` `3.1` verziónak kell lennie, <br /> `4.0` esetén az előbbi exception

---

## `pom.xml`

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.joinfaces</groupId>
      <artifactId>joinfaces-dependencies</artifactId>
      <version>4.3.6</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependencies>

  <!-- ... -->

  <dependency>
    <groupId>org.joinfaces</groupId>
    <artifactId>jsf-spring-boot-starter</artifactId>
  </dependency>
</dependencies>  
```

---

class: inverse, center, middle

# Űrlap kezelés

---

## Controller

* Property getter és setter metódussal az űrlap elemeinek tárolására
* Metódus `void` visszatérési típussal, az adott oldalon marad

---

## Controller forráskód

```java
@Model
public class HelloController implements Serializable {

    @Getter
    private String message;

    @Getter
    @Setter
    private String name;

    public void sayHello() {
        message = String.format("Hello %s!", name);
    }
}
```

---

## Facelets

* `h:form`
* Beviteli komponensek
  * Pl. `h:inputText`, `value` attribútum: backing bean bind
* `h:commandButton`
  * `value` attribútum: szöveg
  * `action` attribútum: backing bean metódus, paraméterezhető

---

## Facelets forráskód

```xml
#{helloController.message}

<h:form>
  <h:inputText value="#{helloController.name}"></h:inputText>
  <h:commandButton value="Say hello" 
    action="#{helloController.sayHello()}">
  </h:commandButton>
</h:form>
```

További komponensek:

* `h:inputSecret`
* `h:inputTextarea`

---

class: inverse, center, middle

# Redirect after post

---

## Redirect after post

* Űrlap POST után átirányítás
* History működése
* Refresh működése
* Könyvjelzőzhetőség

---

## Controller

* Metódus, `String` visszatérési értékkel
  * JSF 2.0 óta használható az oldal fizikai neve (böngészőben megjelenő)
  * `null` esetén az oldalon marad
  * Átirányítás: `?faces-redirect=true`

---

## Controller forráskód

```java
@Named
@SessionScoped
public class HelloController implements Serializable {

  // ...

  public String sayHello() {
    message = String.format("Hello %s!", name);
    return "/hello.xhtml?faces-redirect=true";
  }
}
```

---

## Flash scope

* Következő kérésig él (a keretrendszer a sessionben tárolja, majd el is távolítja az első felhasználáskor)

```java
@Model
public class HelloController implements Serializable {

  @Getter @Setter
  private String name;

  public String sayHello() {
    FacesContext.getCurrentInstance()
      .getExternalContext()
      .getFlash()
      .put("message", 
        String.format("Hello %s!", name));
    return "/hello.xhtml?faces-redirect=true";
  }

}
```

---

## Flash scope-ú változó elérése <br /> Facelets-ből

```xml
<c:if test="#{flash.containsKey('successMessage')}">
        #{flash['successMessage']}
</c:if>
```

---

## További implicit objektumok

* `applicationScope` - az összes application scope <br /> managed beant tartalmazó `Map`
* `sessionScope` - egy `Map`, amely az összes session scope <br /> managed beant tartalmazza
* `viewScope` - egy `Map`, amely az összes view scope <br /> managed beant tartalmazza
* `requestScope` - egy `Map`, amely az összes request scope <br /> managed beant tartalmazza
* `flowScope` - egy `Map`, amely az összes flow scope <br /> managed beant tartalmazza

---

## Megjegyzés elhelyezése

```xml
<ui:remove>
    <!-- Ez csak így nem jelenik meg a HTML oldal forrásában. -->
</ui:remove>
```

A `xmlns:ui="http://java.sun.com/jsf/facelets"` névtérben

---

## Scope-ok összefoglalása

* Application (`@ApplicationScoped`)
* Session (`@SessionScoped`)
* Conversation (`@ConversationScoped`)
* View (`@ViewScoped`) - oldalon marad, azaz pl `void` vagy `null` esetén
* Request (`@RequestScoped`)
* Flash - nincs rá annotáció
* Flow (`@FlowScoped`)
* Javasolt minél kisebb scope használata
* Resquest scope-on kívül `Serializable`-nek kell lennie

---

class: inverse, center, middle

# Alkalmazás architektúra - listázás és felvitel

---

## Alkalmazás architektúra

* Klasszikus háromrétegű alkalmazás
    * Perzisztens réteg (pl. JPA hívások)
    * Üzleti logika (pl. service elnevezési konvenció)
    * Felhasználói felület (JSF)
* Entitás nem küldhető felületre, nem fogadható felületről
    * Helyette DTO (Data transfer object)
    * Felületről érkező DTO-k (pl. command elnevezési konvenció)
* Controllerbe service injektálás

---

## Üzleti logika réteg

Alkalmazottak kezelésére CRUD alkalmazás

![Üzleti logika](images/arch-class-uml.png)

---

## Alkalmazottak megjelenítése - controller

```java
@Model
public class EmployeesController {

    @Inject
    private EmployeesService employeesService;

    @Getter
    private List<EmployeeDto> employees;

    @PostConstruct
    public void initEmployees() {
        employees = employeesService.listEmployees();
    }

}
```

---

## Alkalmazottak megjelenítése - <br /> Facelets

```xml
<h:dataTable value="#{employeesController.employees}" var="employee">
    <h:column>
        <f:facet name="header">
            Id
        </f:facet>
        #{employee.id}
    </h:column>

    <h:column>
        <f:facet name="header">
            Name
        </f:facet>
        #{employee.name}
    </h:column>
</h:dataTable>
```

---

## Alkalmazott felvitele - link

```xml
<h:link value="Create employee" outcome="create-employee.xhtml" />
```

* A html-ben `<a href="create-employee.xhtml">Create employee</a>` <br /> kerül renderelésre
* Külső oldalakra a `h:outputLink` taggel kell hivatkozni

---

## Alkalmazott felvitele <br /> - controller

```java
@Model
public class CreateEmployeeController {

  @Inject
  private EmployeesService employeesService;

  @Getter
  private CreateEmployeeCommand command = 
    new CreateEmployeeCommand();

  public String create() {
      employeesService.createEmployee(command);
      return "/index.xhtml?faces-redirect=true";
  }
}
```

---

## Alkalmazott felvétele <br /> - Facelets

```xml
<h:form>
  <h:inputText value="#{createEmployeeController.command.name}">
  </h:inputText>
  <h:commandButton value="Create" 
    action="#{createEmployeeController.create()}">
  </h:commandButton>
</h:form>
```

---

## Alkalmazott felvétele <br /> - üzenet

```java
FacesContext
  .getCurrentInstance()
  .addMessage(null, new FacesMessage("Employee has created"));
// Redirect after post miatt
FacesContext
  .getCurrentInstance()
  .getExternalContext()
  .getFlash()
  .setKeepMessages(true);
```

```xml
<h:messages />
```

---

class: inverse, center, middle

# Alkalmazás architektúra - módosítás és törlés

---

## Alkalmazott módosítása - link

```xml
<h:link value="#{employee.id}" outcome="update-employee.xhtml">
    <f:param name="id" value="#{employee.id}"/>
</h:link>
```

* A html-ben `<a href=...` kerül renderelésre
* GET HTTP metódussal
* Bookmarkolható
* A Facelets-ben `metadata` taggel lehet kiolvasni


---

## Alkalmazott módosítása <br /> - controller - űrlap feltöltése

```java
@Model
public class UpdateEmployeeController {

  @Inject
  private EmployeesService employeesService;

  @Getter @Setter
  private String id;

  @Getter
  private UpdateEmployeeCommand command = 
    new UpdateEmployeeCommand();

  public void findEmployeeById() {
    command = mapper.map(
      employeesService.findEmployeeById(id).get(), 
      UpdateEmployeeCommand.class);
  }
}
```

---

## Alkalmazott módosítása <br /> - controller - űrlap elküldése

```java
public String update() {
  employeesService.updateEmployee(command);

  FacesContext
    .getCurrentInstance()
    .addMessage(null, new FacesMessage("Employee has updated"));
  FacesContext
   .getCurrentInstance()
   .getExternalContext()
   .getFlash().setKeepMessages(true);

  return "/index.xhtml?faces-redirect=true";
}
```

---

## Alkalmazott módosítása <br /> - Facelets

```xml
<f:metadata>
  <f:viewParam name="id" value="#{updateEmployeeController.id}"/>
  <f:viewAction action="#{updateEmployeeController.findEmployeeById}"/>
</f:metadata>

<h:form>

<h:inputHidden 
  value="#{updateEmployeeController.command.id}" />
<h:inputText 
  value="#{updateEmployeeController.command.name}" />
<h:commandButton value="Update employee" 
  action="#{updateEmployeeController.update()}" />

</h:form>
```

---

## Alkalmazott törlése <br /> - controller

```java
public String deleteEmployee(String id) {
  employeesService.deleteEmployee(id);
  FacesContext
    .getCurrentInstance()
    .addMessage(null, new FacesMessage("Employee has deleted"));
  FacesContext
    .getCurrentInstance()
    .getExternalContext()
    .getFlash()
    .setKeepMessages(true);

  return "/index.xhtml?faces-redirect=true";
}
```

---
## Alkalmazott törlése <br /> - Facelets

* `h:form` nélkül nem működik

```xml
<h:form>
  <!-- ... -->

  <h:commandButton value="Delete"
    action="#{employeesController.deleteEmployee(employee.id)}">
  </h:commandButton>
</h:form>
```

---

class: inverse, center, middle

# Controllerek unit tesztelése

---

## Controllerek unit tesztelése

* Service réteg mockolása
* Annak ellenőrzése, hogy mit ad vissza a Facelets felé
* Annak ellenőrzése, hogy hogyan hívja a service réteget

---

## Controller unit teszt

```java
@ExtendWith(MockitoExtension.class)
public class UpdateEmployeeControllerTest {

  @Mock
  EmployeesService service;

  @InjectMocks
  UpdateEmployeeController controller;

  @Test
  void testFillForm() {
    when(service.findEmployeeById(any()))
      .thenReturn(Optional.of(
        new EmployeeDto("abcd1234", "John Doe")));
    controller.setId("abcd1234");
    controller.findEmployeeById();
    assertEquals("John Doe", 
      controller.getCommand().getName());
  }
}
```

---

## FacesContext használata

```java
FacesContext.getCurrentInstance()
  .addMessage(null, new FacesMessage(message));
FacesContext.getCurrentInstance()
  .getExternalContext().getFlash().setKeepMessages(true);
```

* Statikus metódus: nem mockolható
* Demeter törvénye: "csak a közvetlen barátaiddal beszélgess"
  * Trainwreck: kapcsolódó objektum belső struktúrájának kihasználása
* Nem unit és integrációs tesztelhető

---

## FacesContext megoldás

```java
@Named
@ApplicationScoped
public class MessageContext {

  public void addFlashMessage(String message) {
    FacesContext.getCurrentInstance()
      .addMessage(null, new FacesMessage(message));
    FacesContext.getCurrentInstance()
      .getExternalContext()
      .getFlash().setKeepMessages(true);
  }
}
```

---

## MessageContext használata

```java
@Inject
private MessageContext messageContext;

public String update() {
  employeesService.updateEmployee(command);
  messageContext
    .addFlashMessage("Employee has updated");
  return "/index.xhtml?faces-redirect=true";
}
```

---

## FacesContext tesztelése

```java
@Mock
MessageContext messageContext;

@Test
void testSubmit() {

  verify(messageContext)
    .addFlashMessage(eq("Employee has updated"));
  verify(employeesService).updateEmployee(
     argThat(c -> c.getName().equals("Joe Doe") && c.getId().equals("abcd1234")));
}
```

---

## JUnit és Mockito függőség

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.6.3</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>3.3.3</version>
    <scope>test</scope>
</dependency>
```
---

class: inverse, center, middle

# E2E tesztelés Selenium használatával

---

## Selenium

* Böngésző automatizálás, tipikusan webes alkalmazások tesztelésére
* Képes meghajtani a különböző böngészőket is <br /> (Firefox, Internet Explorer, Safari, Opera, Chrome)
* Selenium WebDriver: böngészővezérlés <br /> (pl. programozási nyelvekből API-n keresztül)
* Különböző programozási nyelvekhez illesztés: <br /> C#, Groovy, Java, Perl, PHP, Python, Ruby and Scala
* Driver böngészőnként (Firefoxhoz geckodriver, https://github.com/mozilla/geckodriver)

---

## Tesztosztály

```java
public class CreateEmployeeSeleniumIT {

    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        // driver = new FirefoxDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void testCreateEmployee() {
        // ...
    }
}
```

---

## Selenium WebDriver teszteset

* Forráskód módosítás: Facletekben `id` attribútumok bevezetése

```java
@Test
public void testCreateEmployee() {
  driver.get("http://localhost:8080/empapp/");
  driver.findElement(By.linkText("Create employee")).click();
  driver.findElement(By.id("create-employee-form:name-input")).click();
  String name = "Jack Doe " + System.currentTimeMillis();
  driver.findElement(By.id("create-employee-form:name-input")).sendKeys(name);
  driver.findElement(By.id("create-employee-form:create-button")).click();

  assertEquals("Employee has created", 
    driver.findElement(By.xpath("/html/body/ul/li"))
      .getText());

  List<WebElement> elements = 
    driver.findElements(By.xpath("//tr/td[2]"));
  assertTrue(elements.stream()
    .map(WebElement::getText)
    .anyMatch(s -> s.equals(name)));
}
```

---

## Selenium függőség

```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>3.141.59</version>
    <scope>test</scope>
</dependency>
```

---

class: inverse, center, middle

# Űrlap GET metódussal

---

## Űrlap GET metódussal

* Űrlap adatai URL-ben kerülnek elküldésre
* Bookmarkolható, átküldhető
* Tipikusan keresési feltételek megadására
* Szerver oldalon nincs állapotváltozás

---

## Controller

```java
@Model
public class EmployeesController {

  // ...

  @Getter
  private List<EmployeeDto> employees;

  @Getter @Setter
  private String query;

  public void initEmployees() {
    employees = 
      employeesService.listEmployees(query);
  }
}
```

---

## Facelets

```xml
<f:metadata>
  <f:viewParam id="query" name="query" 
    value="#{employeesController.query}" />
  <f:viewAction action="#{employeesController.initEmployees}" />
</f:metadata>

<form action="index.xhtml">
  <input name="query" value="#{employeesController.query}" />
  <input type="submit" value="Search" />
</form>
```
---

class: inverse, center, middle

# Űrlap előzetes feltöltése

---

## Űrlap előzetes feltöltése

* Pl. legördülő menü előzetes feltöltése
* Commandban a kiválasztott érték felvétele attribútumként
* Controllerben a kollekció felvétele attribútumként
* Controllerben a kollekció feltöltése pl. `@PostConstruct` metódusban
* Facletben a megfelelő komponens elhelyezése

---

## Command

```java
public class CreateEmployeeCommand {
  // ...

  private String type;

  private List<String> skills;

}
```

---

## Controller

```java
public class CreateEmployeeController {

  // ...

  @Getter
  private List<String> types;

  @Getter
  private List<Skill> skills;

  @PostConstruct
  public void init() {
      types = employeesService.listTypes();
      skills = employeesService.listSkills();
  }    
}
```

---

## Facelets

```xml
<h:selectOneMenu value="#{createEmployeeController.command.type}" >
    <f:selectItems value="#{createEmployeeController.types}" />
</h:selectOneMenu>

<h:selectManyCheckbox value="#{createEmployeeController.command.skills}">
    <f:selectItems value="#{createEmployeeController.skills}" var="n"
        itemLabel="#{n.name}" itemValue="#{n.code}"/>
</h:selectManyCheckbox>
```

---

## Select elemek

* `h:selectBooleanCheckbox` - `boolean` érték beállítása checkbox-szal
* `h:selectManyCheckbox` - több checkbox
* `h:selectManyListbox` - több elem kiválasztása listából, html `select elem`
* `h:selectManyMenu` - html `select`, de a mérete `1`
* `h:selectOneListbox` - html `select`, de csak egy választható ki
* `h:selectOneMenu` - legördülő menü, több érték közül választani
* `h:selectOneRadio` - egy rádiógomb

Belül `f:selectItem` vagy `f:selectItems` tagek, <br /> ez utóbbi pl. kollekció alapján

---

class: inverse, center, middle

# Fájl fel- és letöltés

---

## Fájl feltöltés - Facelets

```xml
<h:form enctype="multipart/form-data">

    <h:inputFile value="#{createEmployeeController.command.file}" />

</h:form>
```

---

## Fájl feltöltés - Command

```java
public class CreateEmployeeCommand {

  private Part file;

}
```

* `javax.servlet.http.Part` interfész
    * `getSubmittedFileName()` metódusával a fájlnév
    * `getInputStream()` metódusával a tartalom

---

## Fájl letöltés

```java
@WebServlet("/files/*")
public class DownloadServlet extends HttpServlet {

  @Inject
  private EmployeesService employeesService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
      throws ServletException, IOException {
    String filename = req.getRequestURI()
      .substring(
        req.getRequestURI().lastIndexOf("/") + 1);
    Path file = employeesService.getFile(filename);
    String contentType = Files.probeContentType(file);
    resp.setContentType(contentType);
    resp.setHeader("Content-disposition", 
      "attachment; filename=" + filename);
    Files.copy(file, resp.getOutputStream());
  }
}
```

---

## Fájl letöltés - hivatkozás

```xml
<h:graphicImage 
  value="/files/#{employeeDetailsController.employee.fileName}"
  alt="#{employeeDetailsController.employee.name}"/>
```

---

class: inverse, center, middle

# Konvertálás

---

## Konverzió

* Beépített converterek, `f:convertDateTime`, `f:convertNumber`

```xml
<h:outputText value="#{employee.startedAt}">
    <f:convertDateTime pattern="yyyy-MM-dd HH:mm" />
</h:outputText>
```

```xml
<h:inputText value="#{createEmployeeController.employee.startedAt}">
    <f:convertDateTime pattern="yyyy-MM-dd HH:mm" />
</h:inputText>
```

---

## Hibás formátum

```
11:08:10,541 INFO  [javax.enterprise.resource.webcontainer.jsf.renderkit] 
(default task-1) WARNING: FacesMessage(s) have been enqueued, 
but may not have been displayed.
sourceId=j_idt6:j_idt8[severity=(ERROR 2), 
summary=(j_idt6:j_idt8: 'a2019-10-24 09:08' 
could not be understood as a date.), 
detail=(j_idt6:j_idt8: 'a2019-10-24 09:08' 
could not be understood as a date. Example: 2019-10-24 09:08 )]
```

```xml
<h:messages />
```

---

## Converter fejlesztése

```java
@FacesConverter("localDateTimeConverter")
public class LocalDateConverter implements Converter<LocaleDateTime> {

  private static final DateTimeFormatter FORMATTER
          = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public LocalDateTime getAsObject(FacesContext context, 
      UIComponent component, String value) {
    return LocalDateTime.parse(value, FORMATTER);
  }

  @Override
  public String getAsString(FacesContext context, 
      UIComponent component, LocalDateTime value) {
    return value.format(FORMATTER);
  }
}
```

---

## Converter használata

```xml
<h:outputText value="#{employee.startedAt}">
    <f:converter converterId="localDateTimeConverter" />
</h:outputText>
```

```xml
<h:inputText value="#{createEmployeeController.command.startedAt}">
    <f:converter converterId="localDateTimeConverter" />
</h:inputText>
```

---

## Converter típushoz

```java
@FacesConverter(forClass=LocalDateTime.class)
```

---

## Converter hibakezelés

```java
try {
  return LocalDateTime.parse(s, FORMATTER);
}
catch (Exception ex) {
  throw new ConverterException(
    new FacesMessage("Can not convert time"));
}
```

---

class: inverse, center, middle

# Validálás

---

## Required validátor

* `required` attribútum

```xml
<h:inputText value="#{createEmployeeController.command.name}" 
  required="true" />
```

---

## Ki nem írt üzenet

```
11:35:01,762 INFO  [javax.enterprise.resource.webcontainer.jsf.renderkit] 
(default task-1) WARNING: FacesMessage(s) have been enqueued, 
but may not have been displayed.
sourceId=j_idt6:j_idt7[severity=(ERROR 2), 
summary=(j_idt6:j_idt7: Validation Error: Value is required.), 
detail=(j_idt6:j_idt7: Validation Error: Value is required.)]
```

---

## Beépített validátorok

* `DoubleRangeValidator`
* `LengthValidator`
* `LongRangeValidator`
* `MethodExpressionValidator`
* `RequiredValidator`
* `RegexValidator`

```xml
<h:inputText 
    value="#{createEmployeeController.command.name}">
  <f:validateLength minimum="2" maximum="20"/>
</h:inputText>
```

---

## Saját validátor

```java
@FacesValidator("nameValidator")
public class NameValidator implements Validator<String> {

  @Override
  public void validate(FacesContext context, 
      UIComponent component, String value) 
      throws ValidatorException {
    if (value == null || value.length() < 2) {
      throw new ValidatorException(
        new FacesMessage(FacesMessage.SEVERITY_ERROR,
          "Too short name", 
          "Please input a longer name"));
    }
  }
}
```

```xml
<h:inputText 
    value="#{createEmployeeController.command.name}">
  <f:validator validatorId="nameValidator" />
</h:inputText>
```

---

## Validátor paraméterezése

* Validátor nem paraméterezhető
* Komponensnek lehet attribútuma

```xml
<h:inputText value="#{createEmployeeController.command.name}">
    <f:attribute name="validatorMinLength" value="2" />
    <f:validator validatorId="nameValidator" />
</h:inputText>
```

```java
int minLength = Integer.parseInt(
  (String) uiComponent.getAttributes()
  .get("validatorMinLength"));
```

---

## Bean Validation

* Out of the box működik
* `f:validateBean` validation group definiálásához

```java
public class CreateEmployeeCommand {

    @NotBlank
    private String name;

}
```

---

class: inverse, center, middle

# Validálás üzleti logikában

---

## Üzenetek

* Service visszatérési értéke alapján
* `FacesContext.addMessage(FacesMessage)`
* `return null` - ugyanarra a view-ra tér vissza

```java
if (employeesService
    .isEmployeeWithCardNumber(command.getCardNumber())) {
  FacesContext.getCurrentInstance()
    .addMessage("create-employee-form:card-number-input",
      new FacesMessage(FacesMessage.SEVERITY_ERROR, 
        "Invalid card number", 
        "Existing card number"));
    return null;
}
```

---

class: inverse, center, middle

# Ismétlődés és panel típusú komponensek

---

## Ismétlődés

```xml
<ui:repeat value="#{employeesController.employees}" var="employee">
  #{employee.name}
</ui:repeat>  
```

---

## panelGroup

* `layout="block"` esetén `div`, ellenkező esetben `span` kerül renderelésre
* Ha nincs `id`, nem jelenik meg

```xml
<h:panelGroup id="employee-div" layout="block">
  #{employee.name}
</h:panelGroup>
```

---

## panelGrid

* `table` tag kerül renderelésre
* Statikus elemek táblázatos megjelenítésére
    * Nem táblázatos adatokra - arra a `dataTable` tag használandó
* Használjunk pozícionálásra `panelGroup` taget

```xml
<h:panelGrid columns="2">
    <h:outputText value="#{employee.id}" />

    <h:outputText value="#{employee.name}" />
</h:panelGrid>
```

---

class: inverse, center, middle

# Hibakezelés

---

## Hibakezelés

* Nincs JSF támogatás
* Servlet API: `web.xml`

```xml
<error-page>
  <error-code>500</error-code>
  <location>/WEB-INF/errorpages/500.xhtml</location>
</error-page>
```

* Requestben `javax.servlet.error.request_uri` meghívott url 
* `javax.servlet.error.exception` dobott kivétel

---

class: inverse, center, middle

# I18N

---

## Nyelvek megadása

* Nyelvek a `faces-config.xml` állományban

```xml
<application>
    <locale-config>
        <default-locale>en</default-locale>
        <supported-locale>hu</supported-locale>
    </locale-config>
</application>
```

---

## Globális bundle-ök

* Globális üzenetek a `faces-config.xml` állományban

```xml
<application>
  <resource-bundle>
    <base-name>Messages</base-name>
    <var>msgs</var>
  </resource-bundle>
</application>
```

---

## Resource Bundle fájlok

* Az `src/main/resources` könyvtárban `Messages.properties` nyelvi változatai

```
Messages_en.properties

title = Employees
update-employee.h1 = Change '{0}' employee
```

```
Messages_hu.properties

title = Alkalmazottak
update-employee.h1 = A "{0}" alkalmazott módosítása
```

---

## Nyelvesített üzenetek <br /> megjelenítése

```xml
#{msgs.title}

#{msgs['title']}
```

Paraméterezve:

```xml
<h:outputFormat value="#{msgs['update-employee.h1']}">
  <f:param value="#{updateEmployeeController.command.name}" />
</h:outputFormat>
```

---

## Lokális bundle-ök

```xml
<f:loadBundle basename="Welcome" var="localmsgs" />
```

Keresett a `Welcome.properties` nyelvi változatai

---

## Beépített hibaüzenetek <br /> nyelvesítése

* [javax/faces/Messages_en.properties](https://github.com/javaserverfaces/mojarra/blob/master/impl/src/main/resources/javax/faces/Messages_en.properties)
* Beviteli komponensek attribútumai:
    * `requiredMessage`
    * `converterMessage`
    * `validatorMessage`

---

## Szerver oldali nyelvi feloldás

```java
ResourceBundle bundle = 
  FacesContext.getCurrentInstance().getApplication()
  .evaluateExpressionGet(
    FacesContext.getCurrentInstance(), 
    "#{msgs}", ResourceBundle.class);
String msg = 
  bundle.getString("create-employee.success");

FacesContext.getCurrentInstance()
  .addMessage(null, new FacesMessage(msg));
```

---

## Bean Validation nyelvesítése

* https://github.com/hibernate/hibernate-validator/blob/master/engine/src/main/resources/org/hibernate/validator/ValidationMessages.properties
* `ValidationMessages.properties` nyelvi változatai

```java
public class CreateEmployeeCommand {

  @NotBlank(
    message = "{create-employee.validation.not-blank}")
  private String name;

}
```

---

class: inverse, center, middle

# Nyelv váltása

---

## Locale beállítása

* Külső forrásból beállítható az oldalra
* Session scoped managed bean (pl. bejelentkezett felhasználó, vagy választott nyelv)
* Szerver oldalon locale lekérdezése

```java
FacesContext.getCurrentInstance()
  .getExternalContext().getRequestLocale()
```

---

## Controller

```java
@Named
@SessionScoped
public class LocaleController implements Serializable {

    @Getter
    private Locale locale;

    public void changeLocale(String lang) {
        locale = new Locale(lang);
    }
}
```

---

## Facelets

```xml
<f:view locale="#{localeController.locale}" >

  <h:form>
      <h:commandLink action="#{localeController.changeLocale('hu')}"
                     value="Magyar" />
      <h:commandLink action="#{localeController.changeLocale('en')}"
                     value="English" />
  </h:form>

</f:view>
```

---

class: inverse, center, middle

# Stílus

---

## Erőforrás kezelés

* `src/main/webapp/resources` 
* a jar `META-INF/resources` könyvtárából

```xml
<h:outputStylesheet library="css" name="employees.css"/>
```

---

## WebJars

```xml
<h:outputStylesheet library="webjars" 
  name="/bootstrap/4.3.1/css/bootstrap.min.css"/>
```

A `pom.xml` fájlban:

```xml
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>bootstrap</artifactId>
    <version>4.3.1</version>
</dependency>
```

---

## Validálás

```xml
<h:form id="create-employee-form">
  <div class="form-group">
    <h:outputLabel for="name-input">Name</h:outputLabel>

    <h:inputText id="name-input" 
      value="#{createEmployeeController.command.name}"
      styleClass="form-control #{component.valid ? '' : 'is-invalid'}">
    </h:inputText>
    <h:message for="name-input" styleClass="invalid-feedback"/>
  </div>
</h:form>
```

* Az `outputLabel` helyesen állítja be a `for` értékét: <br /> `create-employee-form:name-input`

```xml
<h:messages infoClass="alert alert-info" 
  globalOnly="true" />
```

---

class: inverse, center, middle

# Template kezelés

---

## Template kezelés

* Template-ben placeholder definiálása: `<ui:insert name="title">`
* Elhelyezés pl. a `WEB-INF/templates` könyvtárban
* Konkrét oldalon hivatkozás a template-re, majd behelyettesítés
* Hivatkozás, relatív vagy abszolút módon

---

## Template

```xml
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
  xmlns:h="http://java.sun.com/jsf/html" 
  xmlns:ui="http://java.sun.com/jsf/facelets">

<h:body>

  <h1><ui:insert name="title">Default title</ui:insert></h1>

  <ui:insert name="content"><p>Default content</p></ui:insert>

</h:body>

</html>
```

---

## Oldal

```xml
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:f="http://xmlns.jcp.org/jsf/core" 
      xmlns:ui="http://java.sun.com/jsf/facelets">

<ui:composition template="/WEB-INF/templates/layout.xhtml">

  <ui:define name="title">List employees</ui:define>

  <ui:define name="content">
    ...
  </ui:define>

</ui:composition>

</html>
```

---

## Include

```xml
<ui:include src="/WEB-INF/includes/footer.xhtml" />
```

---

class: inverse, center, middle

# Navigációs szabályok

---

## Navigációs szabályok

* A Faceletsben és controllerben csak logikai view nevek
* A `faces-context.xml` oldja fel a logikai neveket fizikai nevekre
* Elavult az implicit navigáció miatt, nehezen karbantartható

---

## Egyszerű link

```xml
<h:link value="Create employee" outcome="create" />
```

```xml
<navigation-rule>
        <from-view-id>index.xhtml</from-view-id>
        <navigation-case>
            <from-outcome>create</from-outcome>
            <to-view-id>create-employee.xhtml</to-view-id>
        </navigation-case>
</navigation-rule>
```

---

## Átirányítás controllerből

```java
return "success";
```

```xml
<navigation-rule>
    <from-view-id>create-employee.xhtml</from-view-id>
    <navigation-case>
        <from-outcome>success</from-outcome>
        <to-view-id>index.xhtml</to-view-id>
        <redirect />
    </navigation-case>
</navigation-rule>
```

---

## Feltételes navigáció

```xml
<h:link value="#{employee.id}" outcome="update">
    <f:param name="id" value="#{employee.id}"/>
</h:link>
```

```xml
<navigation-case>
    <from-outcome>update</from-outcome>
    <if>#{employeesController.readOnly}</if>
    <to-view-id>employee-details.xhtml</to-view-id>
</navigation-case>
<navigation-case>
    <from-outcome>update</from-outcome>
    <to-view-id>update-employee.xhtml</to-view-id>
</navigation-case>
```

---

class: inverse, center, middle

# AJAX

---

## AJAX

* Asynchronous JavaScript and XML
* Technológia a HTML dokumentum (DOM fa) módosítására, <br /> lap váltása nélkül,
  szerver oldali adatok alapján
* Megnövelt felhasználói élmény
* Kisebb adatforgalom, csökkenő szerver terhelés
* XML helyett már JSON
* Aszinkron: callback akkor hívódik meg, ha megérkezett a válasz a szervertől

---

## AJAX form validáció

```xml
<h:inputText id="name-input" value="#{employeesController.command.name}">
    <f:ajax event="blur" render="name-error-message" />    
</h:inputText>
<h:message for="name-input" id="name-error-message"/>
```

---

## AJAX Action Listener

```xml
<h:inputText id="name-input" value="#{employeesController.command.name}">
  <h:outputText value="#{employeesController.monogram}" 
    id="monogram-text" />
  <f:ajax event="keyup" 
    listener="#{employeesController.calculateMonogram}" 
    render="monogram-text" />
</h:inputText>
```

```java
public void calculateMonogram() {
  monogram = Arrays.stream(
      command.getName().split(" "))
    .map(s -> Character.toString(s.charAt(0)))
    .collect(Collectors.joining());
}
```

---

## AJAX form submit

```xml
<h:commandButton value="Create" action="#{employeesController.create}">
  <f:ajax event="action" execute="@form" render="@all" />
</h:commandButton>
```

```java
public String create() {
  // Nem kell flash scope
  FacesContext
    .getCurrentInstance()
    .addMessage(
      null, 
      new FacesMessage("Employee has created: " 
        + command.getName()));

  // Táblázat frissítése
  initEmployees();
  // Űrlap ürítése
  command = new CreateEmployeeCommand();
  // Nincs másik view-ra váltás
  return null;
}
```

---

class: inverse, center, middle

# Flow

---

## Flow

* Összetartozó oldalak egy csoportja
* Saját flow scope
* Üzleti logika: alfolyamat
* Saját navigációs szabályok
* Egymásba ágyazhatóak: subflow definiálható

---

## Flow erőforrások

* `create-employee.xhtml` -> `create-employee-card.xhtml`
* `create-employee-flow.xml`

---

## Flow konfiguráció

* Convention over configuration, Java nyelven is megadható

```xml
<faces-config>

    <flow-definition id="create-employee">
        <flow-return id="exit">
            <from-outcome>/index.xhtml?faces-redirect=true</from-outcome>
        </flow-return>
    </flow-definition>

</faces-config>
```

* `flow-return` - innen tudja, hogy itt van vége
* Fájl neve: `<id>-flow.xml`
* Első képernyője: `<id>.xhtml`

---

## Flow indítása

```xml
<h:link value="Create employee" outcome="create-employee" />
```

---

## Flow scoped controller


```java
@Named
@FlowScoped("create-employee")
public class CreateEmployeeController implements Serializable {

    // ...

    @PostConstruct
    public void init() {
        System.out.println("Create employee - start");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Create employee - end");
    }

    public String create() {
        // Kilépés, mivel flow-return tagre hivatkozik
        return "exit";
    }
}
```

---

class: inverse, center, middle

# Kompozit komponensek

---

## Kompozit komponens

* Külön xhtml fájl
* Definiálandó az interfész
* `http://xmlns.jcp.org/jsf/composite` névtér
* `webapp/resources` könyvtár alatt új könyvtár, és benne a komponensek
    * pl. `webapp/resources/employees/employee-div.xhtml`

---

## Kompozit komponens

```
xmlns:composite="http://xmlns.jcp.org/jsf/composite"
```

```xml
<composite:interface>
    <composite:attribute name="employee"/>
</composite:interface>

<composite:implementation>
    <h:panelGroup id="employee-panel-group" layout="block">
        #{cc.attrs.employee.name} <br/>
    </h:panelGroup>
</composite:implementation>
```

---

## Kompozit komponens <br /> használata

```
xmlns:emp="http://java.sun.com/jsf/composite/employees"
```

```xml
<emp:employee-div employee="#{employee}" />
```

---

class: inverse, center, middle

# JSF implementációk

---

## JSF szabvány és implementációi

* Szabvány
* Referencia implementáció: Mojarra
* Leggyakoribb implementációk: PrimeFaces, RichFaces, IceFaces, MyFaces
* További komponensek

---

## PrimeFaces

* Több, mint száz komponens
* Dialógusablakok kezelése
* Kliens oldali validálás (PrimeFaces Client Side Validation (CSV) Framework)
* JQuery alapú kliens oldali JavaScript függvények
* Különböző témák
* AJAX támogatás
* Mobil támogatás

---

## Névtér és komponensek

```xml
xmlns:p="http://primefaces.org/ui"
```

```xml
<p:spinner />
```

---

## Függőség

```xml
<dependency>
    <groupId>org.primefaces</groupId>
    <artifactId>primefaces</artifactId>
    <version>7.0</version>
</dependency>
```

---

class: inverse, center, middle

# WebSocket

---

## WebSocket

* WildFly-on redeploy után nem működik

https://github.com/eclipse-ee4j/mojarra/issues/4572
https://github.com/javaserverfaces/mojarra/issues/4368
