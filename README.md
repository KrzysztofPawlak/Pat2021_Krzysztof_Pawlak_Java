# Pat2021_Krzysztof_Pawlak_Java

# Requirements
Java version 11+

# Build and run
in repository directory
```javascript
mvn clean install
```
```javascript
java -jar target/Pat2021_Krzysztof_Pawlak_Java-1.0-SNAPSHOT.jar
```

# Operations

calculator allows for operations on:

## 2 numbers: 
- addition
- subtraction
- multiplication
- division
- exponentiation (range 1 - 128)
- square root

## vector - vector: 
- addition
- subtraction

## vector - number: 
- multiplication

## matrix - matrix
- addition
- subtraction
- multiplication

## matrix - vector
- multiplication

# Syntax guide
## NUMBER
Should be without any space. Decimal precision is also accepted.
examples:

1

1.0

## MATRIX
Current setting allow calculate 4x4 size.
Should be surrounded by square brackets: [ values ].

examples:

[2 4;4 5]

One space between brackets [], number and column separator ";" is also accepted.

[ 2 4 ; 4 5 ]

## VECTOR
Current setting allow calculate 4 length.
Should be surrounded by square brackets: [ values ].

examples:

row:
[ 2 4 5 ]

column:
[ 4 ; 5 ; 6 ]

One space between brackets [], number and column separator ";" is also accepted.

# Calculation

## LIST OF OPERATIONS - GET

example: `localhost:8080/info`

## ADD - POST

example: `localhost:8080/add`

- matrix - matrix

```
{
    "values" : [
        "[2 4;4 5]",
        "[2 4;4 5]"
    ]
}
```

- vector - vector

```
{
    "values" : [
        "[123 4]",
        "[11 2]"
    ]
}
```

- numbers
```
{
    "values" : [
        "123.21",
        "11.6"
    ]
}
```

## SUBTRACT - POST

example: `localhost:8080/subtract`

- matrix - matrix

```
{
    "values" : [
        "[2 4;4 5]",
        "[1 2;3 1]"
    ]
}
```

- vector - vector

```
{
    "values" : [
        "[123 4]",
        "[11 2]"
    ]
}
```

- numbers
```
{
    "values" : [
        "123.21",
        "11.6"
    ]
}
```

## MULTIPLY - POST

example: `localhost:8080/multiply`

- matrix - matrix

```
{
    "values" : [
        "[2 4;4 5]",
        "[2 4;4 5]"
    ]
}
```

- matrix - vector

```
{
    "values" : [
        "[2 4;4 5]",
        "[2 4]"
    ]
}
```

- matrix - number

```
{
    "values" : [
        "[2 4;4 5]",
        "3"
    ]
}
```

- vector - number
```
{
    "values" : [
        "[2 4]",
        "11.6"
    ]
}
```

- numbers
```
{
    "values" : [
        "123.21",
        "11.6"
    ]
}
```

## DIVIDE - POST

example: `localhost:8080/divide`

- numbers
```
{
    "values" : [
        "200",
        "4"
    ]
}
```

## EXPONENTIATION - POST

example: `localhost:8080/exponential`

- numbers
```
{
    "values" : [
        "4",
        "3"
    ]
}
```

## SQRT - POST

example: `localhost:8080/sqrt`

- numbers
```
{
    "values" : [
        "16"
    ]
}
```

# History

## LIST OF FILES - GET

example: `localhost:8080/history`

## LAST OPERATIONS - GET

example: `localhost:8080/history/recent`

## THE SPECIFIC FILE - GET

example: `localhost:8080/history/historia_obliczen.txt`