package org.danielholmes.gaeq

case class Plus(left: Gene, right: Gene) extends OperationGene {
  val sign = "+"
  lazy val toDouble = left.toDouble + right.toDouble
}
