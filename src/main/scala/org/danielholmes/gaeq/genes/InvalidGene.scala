package org.danielholmes.gaeq.genes

case class InvalidGene(toBinaryString: String) extends Gene {
  override lazy val toString = "?"
}
