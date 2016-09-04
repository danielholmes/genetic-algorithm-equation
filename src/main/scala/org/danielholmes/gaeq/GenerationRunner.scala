package org.danielholmes.gaeq

import scala.annotation.tailrec
import scala.util.Random

class GenerationRunner(encoder: GeneEncoder, crossoverRate: Double, mutationRate: Double) {
  require(crossoverRate >= 0 && crossoverRate <= 1)

  private val randomGenerator = new Random()

  def run(population: Traversable[Chromosome], target: Int): GenerationResult = {
    val results = population.map(p => getResult(p, target))
    GenerationResult(results, reproduce(results))
  }

  private def reproduce(chromosomes: Traversable[ChromosomeResult]): Traversable[Chromosome] = {
    if (chromosomes.size < 2) {
      Traversable.empty
    } else {
      Range(0, chromosomes.size).map(_ => createChild(chromosomes))
    }
  }

  private def createChild(chromosomes: Traversable[ChromosomeResult]): Chromosome = {
    val parents = findParents(chromosomes.toSeq)
    mutate(crossover(parents._1, parents._2))
  }

  // Dependent on the crossover rate crossover the bits from each chosen chromosome at a randomly chosen point.
  private def crossover(parent1: Chromosome, parent2: Chromosome): Chromosome = {
    if (randomGenerator.nextDouble() > crossoverRate) {
      parent1
    } else {
      val crossoverIndex = randomGenerator.nextInt(parent1.genes.size)
      Chromosome(parent1.genes.slice(0, crossoverIndex) ++ parent2.genes.slice(crossoverIndex, parent2.genes.size))
    }
  }

  // Step through the chosen chromosomes bits and flip dependent on the mutation rate.
  private def mutate(chromosome: Chromosome): Chromosome = {
    encoder.decode(
      chromosome.toBinaryString
        .map(c => {
          if (randomGenerator.nextDouble() < mutationRate) {
            c match {
              case '0' => '1'
              case '1' => '0'
            }
          } else {
            c
          }
        })
        .mkString
    ).get
  }

  private def findParents(chromosomes: Seq[ChromosomeResult]): (Chromosome, Chromosome) = {
    val parent1 = findParent(chromosomes)
    val parent2 = findParent(chromosomes.diff(List(parent1)))
    (parent1.chromosome, parent2.chromosome)
  }

  private def findParent(chromosomes: Seq[ChromosomeResult]): ChromosomeResult = {
    val totalFitness = chromosomes.map(_.fitness).sum
    if (totalFitness == Double.PositiveInfinity) {
      val infinities = chromosomes.filter(_.fitness == Double.PositiveInfinity)
      chromosomes(randomGenerator.nextInt(infinities.size))
    } else {
      findParent(0, chromosomes, randomGenerator.nextDouble() * totalFitness)
    }
  }

  @tailrec
  private def findParent(currentScore: Double, results: Traversable[ChromosomeResult], target: Double): ChromosomeResult = {
    if (results.isEmpty) {
      results.last
    } else {
      val newScore = results.head.fitness + currentScore
      if (newScore >= target) {
        results.head
      } else {
        findParent(newScore, results.tail, target)
      }
    }
  }

  private def getResult(chromosome: Chromosome, target: Int): ChromosomeResult = {
    ChromosomeResult(chromosome, calculateFitness(chromosome, target))
  }

  // Smaller the number, the lower the fitness. Approaches infinity as gets closer
  private def calculateFitness(chromosome: Chromosome, target: Int): Double = {
    1.0 / (target - chromosome.toDouble)
  }
}
