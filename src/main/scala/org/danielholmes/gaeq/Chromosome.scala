package org.danielholmes.gaeq

import org.danielholmes.gaeq.genes.{Gene, NumericGene, OperatorGene}

import scala.annotation.tailrec

case class Chromosome(genes: Seq[Gene]) {
  require(genes.nonEmpty)

  lazy val validSequence: Seq[Gene] = {
    normaliseWithNumberToken(Seq.empty, genes)
  }

  lazy val toBooleanSeq = genes.flatMap(_.toBooleanSeq)

  lazy val toValidSequenceString = validSequence.map(_.toString).mkString(" ")

  lazy val toDouble: Double = toDouble(0, validSequence)

  private def toDouble(current: Double, genes: Seq[Gene]): Double = {
    if (genes.isEmpty) {
      current
    } else {
      genes.head match {
        case n: NumericGene => toDouble(n.value, genes.tail)
        case o: OperatorGene => toDouble(
          o.operate(current, toDouble(0, Seq(genes.tail.head))),
          genes.tail.tail
        )
        case _ => throw new RuntimeException("Unknown genes")
      }
    }
  }

  @tailrec
  private def normaliseWithNumberToken(current: Seq[Gene], remaining: Seq[Gene]): Seq[Gene] = {
    if (remaining.isEmpty) {
      current
    } else {
      remaining.head match {
        case n: NumericGene => normaliseWithOperatorToken(current :+ remaining.head, remaining.tail)
        case _ => normaliseWithNumberToken(current, remaining.tail)
      }
    }
  }

  @tailrec
  private def normaliseWithOperatorToken(current: Seq[Gene], remaining: Seq[Gene]): Seq[Gene] = {
    if (remaining.isEmpty) {
      current
    } else {
      remaining.head match {
        case o: OperatorGene => {
          val next = normaliseWithNumberToken(Seq.empty, remaining.tail)
          if (next.isEmpty) {
            current
          } else {
            (current :+ remaining.head) ++ next
          }
        }
        case _ => normaliseWithOperatorToken(current, remaining.tail)
      }
    }
  }

  override lazy val toString = genes.map(_.toString).mkString(" ")
}
