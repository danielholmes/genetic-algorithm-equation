package org.danielholmes.gaeq

import org.danielholmes.gaeq.genes._

// Order of operations is just left to right
// Ignores invalid genes
class GeneEncoder {
  def decode(raw: String): Option[Chromosome] = {
    Some(raw)
      .filter(_.nonEmpty)
      .map(_.map(c => c == '1'))
      .filter(_.size % Gene.BIT_SIZE == 0)
      .map(_.grouped(Gene.BIT_SIZE).toSeq)
      .filter(_.nonEmpty)
      .map(decode)
  }

  private def decode(tokens: Seq[Seq[Boolean]]): Chromosome = {
    Chromosome(tokens.map(decodeToken))
  }

  private def decodeToken(token: Seq[Boolean]): Gene = {
    token match {
      case Plus.BOOLEAN_SEQ_REPRESENTATION => Plus()
      case Subtract.BOOLEAN_SEQ_REPRESENTATION => Subtract()
      case Multiply.BOOLEAN_SEQ_REPRESENTATION => Multiply()
      case Divide.BOOLEAN_SEQ_REPRESENTATION => Divide()
      case _ => NumericGene.fromBooleanSeq(token).getOrElse(InvalidGene(token))
    }
  }
}
