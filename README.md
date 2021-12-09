# Pokemon challenge API

## Description

Fun project playing around the [PokeAPI](https://pokeapi.co/) and [FunTranslations API](https://funtranslations.com/).

## How to run the API?

### Pre-requisites

You need Docker installed. Docker is supported on Windows, Mac OS and most Linux distributions. 
You can find more information on [how to install it here](https://docs.docker.com/engine/install/).

### Build the image

```shell
docker image build -t poke-challenge .
```
## Starting a container from the image

```shell
docker run -p 5000:5000 poke-challenge
```

## Endpoints available

### Basic Pokemon Information

```text
GET /pokemon/{pokemon name}
```

Example response for request `GET /pokemon/wartortle`

```text
{
    "name": "Wartortle",
    "description": "Often hides in\nwater to stalk\nunwary prey. For\fswimming fast, it\nmoves its ears to\nmaintain balance.",
    "habitat": "waters-edge",
    "legendary": false
}
```

### Translated Pokemon Information

```text
GET /pokemon/translated/{pokemon name}
```

Example response for request `GET /pokemon/translated/wartortle`

```text
{
    "name": "Wartortle",
    "description": "Oft hides in water to stalk unwary prey. For swimming festinate,  't moves its ears to maintain balance.",
    "habitat": "waters-edge",
    "legendary": false
}
```

## Potential improvements to make it production ready

- Add healthcheck endpoints for integration with Kubernetes and other devops tools to validate the 
deployment.
- Add caching at the PokemonService level either and at the FunTranslationService level, in-memory
could be sufficient if only one instance is deployed. Using Redis might be more suitable if multiple
instances of this service must run together.
- Add documentation through Swagger endpoints if developers are to consume this API.
- Allow setting the language through configuration rather than constantly using english.