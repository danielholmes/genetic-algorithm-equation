package org.danielholmes.gaeq.genes

case class Plus(left: Gene, right: Gene) extends OperatorGene {
  val sign = "+"
  lazy val toDouble = left.toDouble + right.toDouble
}
