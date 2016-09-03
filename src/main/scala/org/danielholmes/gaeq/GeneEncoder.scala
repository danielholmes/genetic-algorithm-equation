package org.danielholmes.gaeq

import org.danielholmes.gaeq.genes._

import scala.annotation.tailrec

// Order of operations is just left to right
// Ignores invalid genes
class GeneEncoder {
  def decode(raw: String): Option[Chromosome] = {
    Some(raw)
      .filter(_.nonEmpty)
      .filter(_.length % Gene.BINARY_REPRESENTATION_SIZE == 0)
      .map(_.grouped(Gene.BINARY_REPRESENTATION_SIZE).toSeq)
      .filter(_.nonEmpty)
      .map(decode)
  }

  private def decode(tokens: Seq[String]): Chromosome = {
    Chromosome(tokens.map(decodeToken))
  }

  private def decodeToken(token: String): Gene = {
    token match {
      case Plus.BINARY_REPRESENTATION => Plus()
      case Subtract.BINARY_REPRESENTATION => Subtract()
      case Multiply.BINARY_REPRESENTATION => Multiply()
      case Divide.BINARY_REPRESENTATION => Divide()
      case _ => NumericGene.fromBinaryRepresentation(token).getOrElse(InvalidGene(token))
    }
  }
}
