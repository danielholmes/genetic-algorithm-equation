package org.danielholmes.gaeq.genes

case class NumericGene(value: Int) extends Gene {
  require(NumericGene.ALL_INTS.contains(value))

  lazy val toBooleanSeq = s"%0${Gene.BIT_SIZE}d".format(value.toBinaryString.toInt).map(_ == '1')
  override lazy val toString: String = value.toString
}

object NumericGene {
  val ALL_INTS = Range.inclusive(0, 9)
  val ALL_BOOL_REPRESENTATIONS = ALL_INTS.map(NumericGene(_)).map(_.toBooleanSeq)

  def fromBooleanSeq(rep: Seq[Boolean]): Option[NumericGene] = {
    Some(rep).filter(ALL_BOOL_REPRESENTATIONS.contains)
        .map(_.map(b => if (b) '1' else '0').mkString)
        .map(Integer.parseInt(_, 2))
        .map(NumericGene(_))
  }
}
