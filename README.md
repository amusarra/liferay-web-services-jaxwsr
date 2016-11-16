# Liferay JAX-WS e JAX-RS

![](https://travis-ci.org/amusarra/liferay-web-services-jaxwsrs.svg?branch=master)

Questo progetto è stato realizzato in occasione del [Liferay Symposium del 2016 (9-10 Novembre)](https://web.liferay.com/it/web/events2016/italy/home) e in particolare per la giornata di Bootcamp e il talk [Come sviluppare servizi SOAP e REST in standard JAX-WS e JAX-RS su Liferay](https://web.liferay.com/it/web/events2016/italy/agenda) tenuto da me, _di persona, personalmente (cit. Commissario Montalbano)_.

Dopo il Bootcamp sarà mia cura inserire il link al PDF della presentazione dove troverete la descrizione del progetto.

### 1. Build del progetto
Il progetto è organizzato secondo la struttura del [Liferay Workspace](https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-0/liferay-workspace). Requisiti minimi:

* Oracle JDK 1.8
* Liferay 7 CE GA3
* Git

**Attenzione!!!** E' richiesta la GA3 di Liferay 7 perchè sulla GA2 esiste un errore che accade in fase di registrazione del servizio SOAP. Questo problema è stato risolto sulla GA3. Per i più curiosi ecco l'eccezione in fase di registrazione del servizio SOAP sulla GA2.

```
07:33:24,782 INFO  [fileinstall-/opt/liferay-ce-portal-7.0-ga3/osgi/modules][BundleStartStopLogger:35] STARTED it.dontesta.liferay.symposium.jaxrsws.user.service.impl.rs_1.0.0 [586]
07:33:25,212 WARN  [fileinstall-/opt/liferay-ce-portal-7.0-ga3/osgi/modules][com_liferay_portal_remote_soap_extender:103] Invocation of 'addService' failed.
java.lang.NoSuchMethodError: org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean.isWrapperPartQualified(Lorg/apache/cxf/service/model/MessagePartInfo;)Z
```

Per il build e deploy procedere in questo modo:
* clone del repository
* build del progetto tramite gradle
* deploy sull'istanza Liferay
* configurazione Portal SOAP e REST Extender

```
$ git clone https://github.com/amusarra/liferay-web-services-jaxwsrs.git
$ cd liferay-web-services-jaxwsrs
$ ./gradlew build
```
**Console 1** - Clone del progetto e build

Per il deploy è possibile procedere in due modi:
* copia dei _bundle_ (jar generati in fase di build) all'interno della directory $LIFERAY_HOME/deploy
* configurazione del parametro **liferay.workspace.home.dir** sul file gradle.properties (in project.root) impostando il valore al path assoluto della propria installazione di Liferay

```
liferay.workspace.home.dir=/opt/liferay-ce-portal-7.0-ga3
```
**Console 2** - Esempio di configurazione dell'istanza Liferay su cui installare i bundle

Se optate per la seconda opzione, allora eseguire il task gradle _deploy_
```
$ ./gradlew deploy
```
**Console 3** - Deploy via gradle task

Dopo il deploy è possibile verificare la corretta installazione dei bundle (che sono quattro) interrogando la gogo shell di Apache Felix.

```
$ telnet localhost 11311
$ lb | grep Custom
```
**Console 4** - Connessione alla gogo shell e verifica dei bundle installati

```
572|Active     |   10|Custom User Service REST API End Point (1.0.0)
573|Active     |   10|Custom User Service API (1.0.0)
574|Active     |   10|Custom User Service Impl (1.0.0)
575|Active     |   10|Custom User Service WS API End Point (1.0.0)
```
**Console 5** - Lista dei bundle appena installati e attivi

Se volessimo vedere nel dettaglio le implementazioni dell'interfaccia _it.dontesta.liferay.symposium.jaxrsws.user.api.PersonService_ possiamo interrogare il container OSGi tramite la gogo shell attraverso il comando **services** che restituisce dettagli sul servizio specificato come [OSGi filter](https://osgi.org/javadoc/r6/core/org/osgi/framework/Filter.html).

```
$ telnet localhost 11311
$ services (objectClass=it.dontesta.liferay.symposium.jaxrsws.user.api.PersonService)

{it.dontesta.liferay.symposium.jaxrsws.user.api.PersonService}={component.name=it.dontesta.liferay.symposium.jaxrsws.user.service.impl.PersonServiceImpl, component.id=2373, service.id=6524, service.bundleid=574, service.scope=bundle}
  "Registered by bundle:" it.dontesta.liferay.symposium.jaxrsws.user.service.impl_1.0.0 [574]
  "Bundles using service"
    it.dontesta.liferay.symposium.jaxrsws.user.service.impl.ws_1.0.0 [575]
    it.dontesta.liferay.symposium.jaxrsws.user.service.impl.rs_1.0.0 [572]
```


### 2. Configurazione Liferay SOAP e REST Extender
Dal _Control Panel => Configuration => System Settings_ occorre configurare
* Apache CXF EndPoints
* SOAP Extender
* REST Extender

A seguire le configurazioni da applicare per questo progetto.

![Configurazione Apache CXF EndPoints](http://www.dontesta.it/blog/wp-content/uploads/2014/02/CXFEndPoint_1.png)

**Figura 1** - Configurazione Apache CXF EndPoints

![Configurazione Apache CXF EndPoint REST](http://www.dontesta.it/blog/wp-content/uploads/2014/02/CXFEndPoint_2.png)

**Figura 2** - Configurazione End Point CXF per i servizi REST

![Configurazione Apache CXF EndPoint SOAP](http://www.dontesta.it/blog/wp-content/uploads/2014/02/CXFEndPoint_3.png)

**Figura 3** - Configurazione End Point CXF per i servizi SOAP


![Configurazione REST Extenders](http://www.dontesta.it/blog/wp-content/uploads/2014/02/RESTExtenders.png)

**Figura 4** - Configurazione REST Extender

![Configurazione Apache CXF EndPoint](http://www.dontesta.it/blog/wp-content/uploads/2014/02/SOAPExtenders.png)

**Figura 5** - Configurazione SOAP Extender



### 3. Test del servizi REST e SOAP
Gli endpoint dei servizi web (sulla propria istanza Liferay) sono:

* http://localhost:8080/o/rest/ext.persons
* http://localhost:8080/o/web-services/CustomUserServiceWSEndPoint

Per il servizio SOAP il WSDL è disponibile alla URL [Custom User Service WSDL](http://localhost:8080/o/web-services/CustomUserServiceWSEndPoint?wsdl)

Per i test dei servizi SOAP e REST è possibile utilizzare sia SOAP UI sia cURL. A titolo esemplificativo a seguire un esempio di chiamata al servizio REST via cURL.

```
$ curl -v -H "Accept: application/xml" \
 http://localhost:8080/o/rest/ext.persons/list/tag/it-architect | \
 xmllint --format --recover -

$ curl -v -H "Accept: application/json" \
 http://localhost:8080/o/rest/ext.persons/list/tag/it-architect | \
 python -m json.tool
```
**Console 6** - Test del servizio REST via cURL.

A seguire l'output delle due chiamate al servizio.

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<personList>
  <persons>
    <email>antonio.musarra@gmail.com</email>
    <firstName>Antonio</firstName>
    <lastName>Musarra</lastName>
    <userId>34469</userId>
    <userName>amusarra</userName>
  </persons>
  <persons>
    <email>vrossi@example.com</email>
    <firstName>Vittorio</firstName>
    <lastName>Rossi</lastName>
    <userId>34479</userId>
    <userName>vrossi</userName>
  </persons>
</personList>
```
**Console 7** - Output del servizio in formato xml

```
{
    "persons": [
        {
            "email": "antonio.musarra@gmail.com",
            "firstName": "Antonio",
            "lastName": "Musarra",
            "userId": 34469,
            "userName": "amusarra"
        },
        {
            "email": "vrossi@example.com",
            "firstName": "Vittorio",
            "lastName": "Rossi",
            "userId": 34479,
            "userName": "vrossi"
        }
    ]
}
```
**Console 8** Output del servizio in formato JSON

A seguire un esempio di chiamata al servizio SOAP e in particolare del metodo _getUsersByTag()_ tramite SOAP UI.

![Esempio di chiamata al servizio SOAP per il metodo getUsersByTag](http://www.dontesta.it/blog/wp-content/uploads/2014/02/TEST_SERVIZIO_SOAP_CustomUserServiceWS_1.png)

**Figura 6** - Esempio di chiamata al servizio SOAP per il metodo getUsersByTag

```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.impl.service.user.jaxrsws.symposium.liferay.dontesta.it/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:getUsersByTag>
         <!--Optional:-->
         <arg0>it-architect</arg0>
      </ws:getUsersByTag>
   </soapenv:Body>
</soapenv:Envelope>
```
**Code 1** - SOAP Request per il metodo getUsersByTag

```
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:getUsersByTagResponse xmlns:ns2="http://ws.impl.service.user.jaxrsws.symposium.liferay.dontesta.it/">
         <return>
            <persons>
               <email>antonio.musarra@gmail.com</email>
               <firstName>Antonio</firstName>
               <lastName>Musarra</lastName>
               <userId>30817</userId>
               <userName>amusarra</userName>
            </persons>
         </return>
      </ns2:getUsersByTagResponse>
   </soap:Body>
</soap:Envelope>
```
**Code 2** - SOAP Response per il metodo getUsersByTag

```
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <soap:Fault>
         <faultcode>soap:Server</faultcode>
         <faultstring>To be implements</faultstring>
         <detail>
            <ns1:CustomUserServiceException xmlns:ns1="http://ws.impl.service.user.jaxrsws.symposium.liferay.dontesta.it/"/>
         </detail>
      </soap:Fault>
   </soap:Body>
</soap:Envelope>
```
**Code 3** - SOAP Fault Response per il metodo getUsersByTags che deve essere implementato
