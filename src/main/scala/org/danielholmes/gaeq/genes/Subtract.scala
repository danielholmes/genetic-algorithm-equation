package org.danielholmes.gaeq.genes

case class Subtract() extends OperatorGene {
  override lazy val toString = "-"
  def operate(left: Double, right: Double): Double = left - right
  val toBooleanSeq = Subtract.BOOLEAN_SEQ_REPRESENTATION
}

object Subtract {
  val BOOLEAN_SEQ_REPRESENTATION = Seq(true, false, true, true)
}
