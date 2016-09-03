package org.danielholmes.gaeq

import org.danielholmes.gaeq.genes._
import org.scalatest._

class ChromosomeSpec extends FlatSpec with Matchers {
  "Chromosome" should "return valid sequence for number" in {
    Chromosome(Seq(NumericGene(0))).validSequence should be (Seq(NumericGene(0)))
  }

  it should "return valid sequence for operator" in {
    Chromosome(Seq(Plus())).validSequence should be (empty)
  }

  it should "return valid sequence for mixed" in {
    Chromosome(Seq(NumericGene(0), Plus(), NumericGene(1), Multiply())).validSequence should be (Seq(NumericGene(0), Plus(), NumericGene(1)))
  }

  it should "return correct value for numeric" in {
    Chromosome(Seq(NumericGene(5))).toDouble should be (5.0)
  }

  it should "return correct value for small equation" in {
    Chromosome(Seq(NumericGene(5), Plus(), NumericGene(8))).toDouble should be (13.0)
  }

  it should "return correct value for longer equation" in {
    Chromosome(Seq(NumericGene(5), Plus(), NumericGene(8), Multiply(), NumericGene(2))).toDouble should be (26.0)
  }
}
