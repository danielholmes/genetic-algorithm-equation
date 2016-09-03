package org.danielholmes.gaeq.genes

trait Gene {
  val toBinaryString: String

  //require(toBinaryString.length == Gene.BINARY_REPRESENTATION_SIZE)
  //require(toBinaryString.forall(Set('1', '0').contains))
}

object Gene {
  val BINARY_REPRESENTATION_SIZE = 4
}
