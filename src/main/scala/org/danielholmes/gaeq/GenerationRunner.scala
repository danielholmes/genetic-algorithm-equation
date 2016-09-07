package org.danielholmes.gaeq

import scala.annotation.tailrec
import scala.util.Random

class GenerationRunner(encoder: GeneEncoder, crossoverRate: Double, mutationRate: Double) {
  require(crossoverRate >= 0 && crossoverRate <= 1)

  private val randomGenerator = new Random()

  def run(population: Traversable[Chromosome], target: Int): GenerationResult = {
    val results = population.map(p => getResult(p, target))
    GenerationResult(results, reproduce(Seq.empty, results))
  }

  @tailrec
  private def reproduce(current: Seq[Chromosome], chromosomes: Traversable[ChromosomeResult]): Traversable[Chromosome] = {
    if (chromosomes.size < 2) {
      Traversable.empty
    } else if (current.size == chromosomes.size) {
      current
    } else {
      reproduce(current ++ createChild(chromosomes), chromosomes)
    }
  }

  private def createChild(chromosomes: Traversable[ChromosomeResult]): Option[Chromosome] = {
    val parents = pickParentsBasedOnFitness(chromosomes.toSeq)
    crossover(parents._1, parents._2).map(mutate)
  }

  // Crossover doesn't happen every opportunity
  private def crossover(parent1: Chromosome, parent2: Chromosome): Option[Chromosome] = {
    Some(randomGenerator.nextDouble())
        .filter(_ <= crossoverRate)
        .map(r => {
          val crossoverIndex = randomGenerator.nextInt(parent1.genes.size)
          Chromosome(parent1.genes.slice(0, crossoverIndex) ++ parent2.genes.slice(crossoverIndex, parent2.genes.size))
        })
  }

  // Step through the chosen chromosomes bits and flip dependent on the mutation rate.
  private def mutate(chromosome: Chromosome): Chromosome = {
    encoder.decode(
      chromosome.toBooleanSeq
        .map(c => {
          if (randomGenerator.nextDouble() <= mutationRate) {
            !c
          } else {
            c
          }
        })
        .map(if (_) '1' else '0')
        .mkString
    ).get
  }

  private def pickParentsBasedOnFitness(chromosomes: Seq[ChromosomeResult]): (Chromosome, Chromosome) = {
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
    val newScore = results.head.fitness + currentScore
    // TODO: Shouldn't need this if results.size == 1 I don't think
    if (newScore >= target || results.size == 1) {
      results.head
    } else {
      findParent(newScore, results.tail, target)
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
