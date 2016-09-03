package org.danielholmes.gaeq.genes

trait OperatorGene extends Gene {
  val left: Gene
  val right: Gene
  val sign: String

  override lazy val toString: String = s"($left $sign $right)"
}
