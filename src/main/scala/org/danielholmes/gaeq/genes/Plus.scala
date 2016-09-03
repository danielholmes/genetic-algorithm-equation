package org.danielholmes.gaeq.genes

case class Plus() extends OperatorGene {
  override lazy val toString = "+"
  def operate(left: Double, right: Double): Double = left + right
  val toBinaryString = Plus.BINARY_REPRESENTATION
}

object Plus {
  val BINARY_REPRESENTATION = "1010"
}
