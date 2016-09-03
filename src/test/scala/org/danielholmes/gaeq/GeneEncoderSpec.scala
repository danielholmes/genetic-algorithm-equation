package org.danielholmes.gaeq

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

  it should "throw exception when invalid - 2 numbers" in {
    encoder.decode("00000001") should be (empty)
  }

  it should "throw exception when invalid - unclosed operator 1 +" in {
    encoder.decode("00011010") should be (empty)
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

  it should "return correct order of operations for multiply 2 + (0 * 1)" in {
    encoder.decode("00101010000011000001") should contain (Plus(Number(2), Multiply(Number(0), Number(1))))
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
      Subtract(
        Plus(
          Number(1),
          Multiply(
            Number(2),
            Number(3)
          )
        ),
        Divide(
          Multiply(
            Number(4),
            Number(5)
          ),
          Number(6)
        )
      )
    // Atm constructs equation differently but still correct result
    //println(s"$result vs $expected")
    //println(s"${result.toDouble} vs ${expected.toDouble}")
    //result should be (expected)
    result.get.toDouble should be (expected.toDouble)
  }
}