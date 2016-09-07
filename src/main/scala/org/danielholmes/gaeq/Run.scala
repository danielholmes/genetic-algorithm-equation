package org.danielholmes.gaeq

import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.Random

object Run extends App {
  private def printError[T](message: String, operation: () => T): T = {
    println(s"> $message")
    operation.apply()
  }

  private def readPosInt(message: String, default: Int): Int = {
    print(s"$message [$default]: ")
    try {
      val raw = StdIn.readLine()
      if (raw.isEmpty) {
        default
      } else {
        val num = raw.toInt
        if (num <= 0) {
          throw new NumberFormatException
        }
        num
      }
    } catch {
      case ne: NumberFormatException => printError(
        "Must provide positive integer",
        () => readPosInt(message, default)
      )
    }
  }

  val target = readPosInt("Enter a target", 1 + new Random().nextInt(99))
  val populationSize = readPosInt("Population size", 35)
  val numberOfGenerations = readPosInt("Number of generations", 100)
  val chromosomeSize = readPosInt("Chromosome size", 9)

  val crossoverRate = 0.7
  val mutationRate = 0.003

  val encoder = new GeneEncoder
  val runner = new GenerationRunner(encoder, crossoverRate, mutationRate)
  val popFactory = new PopulationFactory(encoder, chromosomeSize)
  val population = popFactory.create(populationSize)

  @tailrec
  def runGeneration(current: Int, total: Int, population: Traversable[Chromosome]): Unit = {
    if (current > total) {
      println("Done")
    } else {
      println(s"$current) " + ("-" * 80))
      val result = runner.run(population, target)

      // println(result.populationResults.map(r => r.chromosome.toBinaryString +
      // " " + r.chromosome.toString + " || " + r.chromosome.toValidSequenceString).mkString("\n"))
      println(s"Distinct chromosomes: ${result.distinctChromosomes}")
      println(s"Average fitness: ${result.averageFitness}")
      println(s"Fittest:")
      println(s"  Equation: ${result.fittestResult.chromosome.toValidSequenceString}")
      println(s"  Value:    ${result.fittestResult.chromosome.toDouble}")
      println(s"  Fitness:  ${result.fittestResult.fitness}")

      print("Continue?")
      StdIn.readLine()

      runGeneration(current + 1, total, result.newPopulation)
    }
  }

  runGeneration(1, numberOfGenerations, population)
}
