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

# How to use
All operation except square root require providing 2 data. When 2 data is present in the application, a list of operations will be displayed. 

For the square root, after the first number is entered a following message will be displayed: 
`Enter some data or switch to extended mode (SQRT) typing: "o".`
So we can press this option and the list will be displayed.

After each calculation, the result is remembered in memory so you can continue with the next calculation. 
If you need to clear an item from memory, enter: "c" (all memory), "c1" (memory slot 1) or "c2" (memory slot 2). 

You can close the application by entering "q!" 

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

# All options

q! - exit  
c - clear all memory  
c1 - remove value from memory1  
c2 - remove value from memory2  
v - show current values in memory  
h - show help  
s - syntax guide  
o - extended mode for sqrt (only available when one number in memory is present)  

# Calculation

## add - POST

example: `localhost:8080/add`

```
{
    "values" : [
        "[2 4;4 5]",
        "[2 4;4 5]"
    ]
}
```