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