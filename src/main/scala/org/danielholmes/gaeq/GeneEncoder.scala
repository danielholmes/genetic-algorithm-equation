package org.danielholmes.gaeq

import scala.annotation.tailrec

class GeneEncoder {
  import GeneEncoder._

  def decode(value: String): Option[Gene] = {
    Some(value)
      .filter(_.nonEmpty)
      .filter(_.length % GENE_LENGTH == 0)
      .map(_.grouped(GENE_LENGTH).toList)
      .filter(_.length % 2 == 1)
      .filter(isValid(List.empty, _))
      .map(findLowestPriority(Seq.empty, _))
  }

  @tailrec
  private def isValid(current: List[String], tokens: List[String]): Boolean = {
    if (tokens.isEmpty) {
      true
    } else if (current.isEmpty || OPERATOR_TOKENS.contains(current.last)) {
      NUMBER_TOKENS.contains(tokens.head) && isValid(current :+ tokens.head, tokens.tail)
    } else {
      OPERATOR_TOKENS.contains(tokens.head) && isValid(current :+ tokens.head, tokens.tail)
    }
  }

  // @tailrec
  private def findLowestPriority(current: Seq[String], tokens: Seq[String]): Gene = {
    if (current.isEmpty && tokens.size == 1) {
      tokenToNumber(tokens.head)
    } else if (tokens.isEmpty) {
      findHighestPriority(Seq.empty, current)
    } else {
      tokens.head match {
        case PLUS_TOKEN => Plus(findLowestPriority(Seq.empty, current), findLowestPriority(Seq.empty, tokens.tail))
        case SUBTRACT_TOKEN => Subtract(findLowestPriority(Seq.empty, current), findLowestPriority(Seq.empty, tokens.tail))
        case _ => findLowestPriority(current :+ tokens.head, tokens.tail)
      }
    }
  }
  private def findHighestPriority(current: Seq[String], tokens: Seq[String]): Gene = {
    if (current.isEmpty && tokens.size == 1) {
      tokenToNumber(tokens.head)
    } else if (tokens.isEmpty) {
      throw new RuntimeException(s"Invalid state $current $tokens")
    } else {
      tokens.head match {
        case MULTIPLY_TOKEN => Multiply(findHighestPriority(Seq.empty, current), findHighestPriority(Seq.empty, tokens.tail))
        case DIVIDE_TOKEN => Divide(findHighestPriority(Seq.empty, current), findHighestPriority(Seq.empty, tokens.tail))
        case _ => findHighestPriority(current :+ tokens.head, tokens.tail)
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
