package org.danielholmes.gaeq.genes

case class Plus() extends OperatorGene {
  override lazy val toString = "+"
  def operate(left: Double, right: Double): Double = left + right
  val toBooleanSeq = Plus.BOOLEAN_SEQ_REPRESENTATION
}

object Plus {
  val BOOLEAN_SEQ_REPRESENTATION = Seq(true, false, true, false)
}
