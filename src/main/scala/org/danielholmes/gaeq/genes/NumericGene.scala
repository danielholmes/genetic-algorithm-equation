package org.danielholmes.gaeq.genes

case class NumericGene(value: Int) extends Gene {
  require(NumericGene.ALL_INTS.contains(value))

  lazy val toBinaryString: String = s"%0${Gene.BINARY_REPRESENTATION_SIZE}d".format(value.toBinaryString.toInt)
  override lazy val toString: String = value.toString
}

object NumericGene {
  val ALL_INTS = Range.inclusive(0, 9)
  val ALL_BINARY_REPRESENTATIONS = ALL_INTS.map(NumericGene(_)).map(_.toBinaryString)

  def fromBinaryRepresentation(rep: String): Option[NumericGene] = {
    Some(rep).filter(ALL_BINARY_REPRESENTATIONS.contains)
        .map(Integer.parseInt(_, 2))
        .map(NumericGene(_))
  }
}
