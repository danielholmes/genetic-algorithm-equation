package org.danielholmes.gaeq

case class GenerationResult(populationResults: Traversable[ChromosomeResult], newPopulation: Traversable[Chromosome]) {
  require(populationResults.nonEmpty)

  lazy val averageFitness = populationResults.map(_.fitness).sum / populationResults.size
  lazy val fittestResult = populationResults.maxBy(_.fitness)
}
