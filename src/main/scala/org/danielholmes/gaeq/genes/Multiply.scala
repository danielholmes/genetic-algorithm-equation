package org.danielholmes.gaeq.genes

case class Multiply() extends OperatorGene {
  override lazy val toString = "*"
  def operate(left: Double, right: Double): Double = left * right
  val toBooleanSeq = Multiply.BOOLEAN_SEQ_REPRESENTATION
}

object Multiply {
  val BOOLEAN_SEQ_REPRESENTATION = Seq(true, true, false, false)
}
