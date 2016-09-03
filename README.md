# Genetic Algorithm Equation

An implementation of the the problem outlined here: (http://www.ai-junkie.com/ga/intro/gat1.html). i.e.

    Given the digits 0 through 9 and the operators +, -, * and /,  find a sequence that will represent a given target 
    number. The operators will be applied sequentially from left to right as you read.


## Dependencies

 - SBT
 - JDK 8+
 - Scala 2.11

To find available SBT dependency updates run `sbt dependencyUpdates`


## Tests

All: `sbt test`
Individual: `sbt "test-only org.danielholmes.gaeq.GeneEncoderSpec"`
Individual continuous: `sbt ~"test-only org.danielholmes.gaeq.GeneEncoderSpec"`


## Running

`sbt run` 


## Encoding

| value | gene |
|-------|------|
| 0     | 0000 |
| 1     | 0001 |
| 2     | 0010 |
| 3     | 0011 |
| 4     | 0100 |
| 5     | 0101 |
| 6     | 0110 |
| 7     | 0111 |
| 8     | 1000 |
| 9     | 1001 |
| +     | 1010 |
| -     | 1011 |
| *     | 1100 |
| /     | 1101 |

Unused/Ignored Genes: 1110 1111