package org.danielholmes.gaeq

import scala.util.Random

class PopulationFactory(encoder: GeneEncoder, chromosomeSize: Int) {
  val randomGenerator = new Random

  def create(amount: Int): Traversable[Chromosome] = {
    require(amount >= 0)
    Range(0, amount).map(_ => createChromosome())
  }

  private def createChromosome(): Chromosome = {
    encoder.decode(Range(0, chromosomeSize).map(_ => createGene()).mkString).get
  }

  private def createGene(): String = {
    Range(0, 4).map(_ => randomGenerator.nextInt(2).toString).mkString
  }
}
