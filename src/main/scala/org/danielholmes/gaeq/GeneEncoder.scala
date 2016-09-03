package org.danielholmes.gaeq

import org.danielholmes.gaeq.genes._

import scala.annotation.tailrec

// Order of operations is just left to right
// Ignores invalid genes
class GeneEncoder {
  import GeneEncoder._

  // TODO: Chromosome type - String of / 4 tokens
  def decode(chromosome: String): Option[Gene] = {
    Some(chromosome)
      .filter(_.nonEmpty)
      .filter(_.length % GENE_LENGTH == 0)
      .map(_.grouped(GENE_LENGTH).toList)
      .map(normaliseTokens)
      .filter(_.nonEmpty)
      .map(decode)
  }

  private def normaliseTokens(tokens: Seq[String]): Seq[String] = {
    normaliseWithNumberToken(Seq.empty, tokens)
  }

  @tailrec
  private def normaliseWithNumberToken(current: Seq[String], tokens: Seq[String]): Seq[String] = {
    if (tokens.isEmpty) {
      current
    } else {
      if (NUMBER_TOKENS.contains(tokens.head)) {
        normaliseWithOperatorToken(current :+ tokens.head, tokens.tail)
      } else {
        normaliseWithNumberToken(current, tokens.tail)
      }
    }
  }

  @tailrec
  private def normaliseWithOperatorToken(current: Seq[String], tokens: Seq[String]): Seq[String] = {
    if (tokens.isEmpty) {
      current
    } else {
      if (OPERATOR_TOKENS.contains(tokens.head)) {
        val next = normaliseTokens(tokens.tail)
        if (next.isEmpty) {
          current
        } else {
          (current :+ tokens.head) ++ next
        }
      } else {
        normaliseWithOperatorToken(current, tokens.tail)
      }
    }
  }

  private def decode(tokens: Seq[String]): Gene = {
    decode(
      tokenToNumber(tokens.head),
      tokens.tail
    )
  }

  @tailrec
  private def decode(current: Gene, tokens: Seq[String]): Gene = {
    if (tokens.isEmpty) {
      current
    } else {
      tokens.head match {
        case PLUS_TOKEN => decode(Plus(current, tokenToNumber(tokens.tail.head)), tokens.tail.tail)
        case SUBTRACT_TOKEN => decode(Subtract(current, tokenToNumber(tokens.tail.head)), tokens.tail.tail)
        case MULTIPLY_TOKEN => decode(Multiply(current, tokenToNumber(tokens.tail.head)), tokens.tail.tail)
        case DIVIDE_TOKEN => decode(Divide(current, tokenToNumber(tokens.tail.head)), tokens.tail.tail)
        case _ => throw new RuntimeException("Invalid state")
      }
    }
  }

  private def tokenToNumber(token: String): Gene = {
    if (!NUMBER_TOKENS.contains(token)) {
      throw new IllegalArgumentException(s"Token $token isn't numeric")
    }
    Number(Integer.parseInt(token, 2))
  }
}

object GeneEncoder {
  val GENE_LENGTH = 4
  val PLUS_TOKEN = "1010"
  val SUBTRACT_TOKEN = "1011"
  val MULTIPLY_TOKEN = "1100"
  val DIVIDE_TOKEN = "1101"

  val OPERATOR_TOKENS = Set(PLUS_TOKEN, MULTIPLY_TOKEN, SUBTRACT_TOKEN, DIVIDE_TOKEN)

  val NUMBER_TOKENS = Range.inclusive(0, 9).map(_.toBinaryString.toInt).map(i => s"%0${GENE_LENGTH}d".format(i)).toSet

  val ALL_TOKENS = OPERATOR_TOKENS ++ NUMBER_TOKENS
}
