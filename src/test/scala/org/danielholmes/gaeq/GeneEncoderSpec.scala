package org.danielholmes.gaeq

import org.danielholmes.gaeq.genes._
import org.scalatest._

class GeneEncoderSpec extends FlatSpec with Matchers {
  val encoder = new GeneEncoder

  "GeneEncoder" should "return number for 0" in {
    encoder.decode("0000") should contain (Number(0))
  }

  it should "return number for 1" in {
    encoder.decode("0001") should contain (Number(1))
  }

  it should "return number for 9" in {
    encoder.decode("1001") should contain (Number(9))
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
    // 0 2 + n/a - 7 2
    // 0 + 7
    encoder.decode("0000001010101110101101110010") should contain (Plus(Number(0), Number(7)))
  }

  it should "ignore second number when invalid sequence of 2 numbers" in {
    encoder.decode("00000001") should contain (Number(0))
  }

  it should "ignore operator when invalid - unclosed operator 1 +" in {
    encoder.decode("00011010") should contain (Number(1))
  }

  it should "return + for 1010" in {
    encoder.decode("000010100000") should contain (Plus(Number(0), Number(0)))
  }

  it should "return * for 1100" in {
    encoder.decode("000011000000") should contain (Multiply(Number(0), Number(0)))
  }

  it should "return - for 1100" in {
    encoder.decode("000010110000") should contain (Subtract(Number(0), Number(0)))
  }

  it should "return / for 1100" in {
    encoder.decode("000011010000") should contain (Divide(Number(0), Number(0)))
  }

  it should "return simple order of operations for multiply (2 + 0) * 1" in {
    encoder.decode("00101010000011000001") should contain (Multiply(Plus(Number(2), Number(0)), Number(1)))
  }

  it should "return correct order of operations for multiply (2 * 0) + 1" in {
    encoder.decode("00101100000010100001") should contain (Plus(Multiply(Number(2), Number(0)), Number(1)))
  }

  it should "return correct order of operations for large example 1 + 2 * 3 - 4 * 5 / 6" in {
    val result = encoder.decode(
      Seq(
        "0001",
        GeneEncoder.PLUS_TOKEN,
        "0010",
        GeneEncoder.MULTIPLY_TOKEN,
        "0011",
        GeneEncoder.SUBTRACT_TOKEN,
        "0100",
        GeneEncoder.MULTIPLY_TOKEN,
        "0101",
        GeneEncoder.DIVIDE_TOKEN,
        "0110"
      ).mkString
    )
    val expected =
      Divide(
        Multiply(
          Subtract(
            Multiply(
              Plus(
                Number(1),
                Number(2)
              ),
              Number(3)
            ),
            Number(4)
          ),
          Number(5)
        ),
        Number(6)
      )
    result should contain (expected)
    result.get.toDouble should be (expected.toDouble)
  }
}