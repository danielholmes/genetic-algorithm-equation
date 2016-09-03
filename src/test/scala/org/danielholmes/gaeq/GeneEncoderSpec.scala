package org.danielholmes.gaeq

import org.danielholmes.gaeq.genes._
import org.scalatest._

class GeneEncoderSpec extends FlatSpec with Matchers {
  val encoder = new GeneEncoder

  "GeneEncoder" should "return number for 0" in {
    encoder.decode("0000") should contain (Chromosome(Seq(NumericGene(0))))
  }

  it should "return number for 1" in {
    encoder.decode("0001") should contain (Chromosome(Seq(NumericGene(1))))
  }

  it should "return number for 9" in {
    encoder.decode("1001") should contain (Chromosome(Seq(NumericGene(9))))
  }

  it should "throw exception when uneven number of characters" in {
    encoder.decode("100110") should be (empty)
  }

  it should "throw exception when less than 4" in {
    encoder.decode("10") should be (empty)
  }

  it should "throw exception when empty" in {
    encoder.decode("") should be (empty)
  }

  it should "ignore invalid tokens as per tutorial example" in {
    // 0 2 + n/a
    // 0 + 7
    encoder.decode("0000001010101110") should contain (Chromosome(Seq(NumericGene(0), NumericGene(2), Plus(), InvalidGene("1110"))))
  }

  it should "include second number when invalid sequence of 2 numbers" in {
    encoder.decode("00000001") should contain (Chromosome(Seq(NumericGene(0), NumericGene(1))))
  }

  it should "ignore operator when invalid - unclosed operator 1 +" in {
    encoder.decode("00011010") should contain (Chromosome(Seq(NumericGene(1), Plus())))
  }

  it should "return + for 1010" in {
    encoder.decode("000010100000") should contain (Chromosome(Seq(NumericGene(0), Plus(), NumericGene(0))))
  }

  it should "return * for 1100" in {
    encoder.decode("000011000000") should contain (Chromosome(Seq(NumericGene(0), Multiply(), NumericGene(0))))
  }

  it should "return - for 1100" in {
    encoder.decode("000010110000") should contain (Chromosome(Seq(NumericGene(0), Subtract(), NumericGene(0))))
  }

  it should "return / for 1100" in {
    encoder.decode("000011010000") should contain (Chromosome(Seq(NumericGene(0), Divide(), NumericGene(0))))
  }

  it should "return simple order of operations for multiply (2 + 0) * 1" in {
    encoder.decode("00101010000011000001") should contain (Chromosome(Seq(NumericGene(2), Plus(), NumericGene(0), Multiply(), NumericGene(1))))
  }

  it should "return correct order of operations for multiply (2 * 0) + 1" in {
    encoder.decode("00101100000010100001") should contain (Chromosome(Seq(NumericGene(2), Multiply(), NumericGene(0), Plus(), NumericGene(1))))
  }
}
