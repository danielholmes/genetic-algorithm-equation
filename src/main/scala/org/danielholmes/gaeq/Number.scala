package org.danielholmes.gaeq

case class Number(value: Int) extends Gene {
  require(value >= 0 && value <= 9)

  lazy val toDouble = value.toDouble
  override lazy val toString: String = value.toString
}
