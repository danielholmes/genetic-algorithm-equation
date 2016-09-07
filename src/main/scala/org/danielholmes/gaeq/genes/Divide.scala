package org.danielholmes.gaeq.genes

case class Divide() extends OperatorGene {
  override lazy val toString = "/"
  def operate(left: Double, right: Double): Double = left / right
  val toBooleanSeq = Divide.BOOLEAN_SEQ_REPRESENTATION
}

object Divide {
  val BOOLEAN_SEQ_REPRESENTATION = Seq(true, true, false, true)
}
