package com.ru.waka

object Main {
  val rules = Seq(Birth, Survive, Depopulation, Overpulation)

  def main(args: Array[String]) {
    val game = Array(
      Array(Cell(Dead),Cell(Dead),Cell(Dead)),
      Array(Cell(Alive),Cell(Alive),Cell(Alive)),
      Array(Cell(Dead),Cell(Dead),Cell(Dead))
    )
    while (true) {

      printGame(game)
      Thread.sleep(1000l)
      System.exit(0)
    }
  }

  def applyRules(game: Array[Array[Cell]]): Array[Array[Cell]] = {
    game.zipWithIndex.map{ case (lines, y) => lines.zipWithIndex.map { case (c, x) => {
      val nearby = findNearbyCells(game, (x, y))
//      rules.
      // 変化みたいなクラスにしたほうがいいかも
      // Nothingが欲しくなる

    }}}
  }

  def findNearbyCells(game: Array[Array[Cell]], index: (Int, Int)): Seq[Cell] = {
    Nil
  }

  def printGame(game: Array[Array[Cell]]) = {
    game.foreach(lines => {
      lines.foreach(_.state match {
        case Alive => print("■")
        case _ => print("□")
      })
      println()
    })
  }
}

sealed trait State

case object Alive extends State

case object Dead extends State

case class Cell(state: State)

sealed trait Rule {
  def apply(cell: Cell, nearbyCells: Seq[Cell]): State
}

case object Birth extends Rule {
  def apply(cell: Cell, nearbyCells: Seq[Cell]): State = cell.state match {
    case Alive =>
      Alive
    case Dead if nearbyCells.count(_.state == Alive) == 3 =>
      Alive
    case _ =>
      Dead
  }
}

case object Survive extends Rule {
  def apply(cell: Cell, nearbyCells: Seq[Cell]): State = {
    val aliveCells = nearbyCells.count(_.state == Alive)
    cell.state match {
      case Alive if aliveCells == 2 || aliveCells == 3 =>
        Alive
      case _ =>
        Dead
    }
  }
}

case object Depopulation extends Rule {
  def apply(cell: Cell, nearbyCells: Seq[Cell]): State = cell.state match {
    case Alive if nearbyCells.count(_.state == Alive) > 1 =>
      Alive
    case _ =>
      Dead
  }
}

case object Overpulation extends Rule {
  def apply(cell: Cell, nearbyCells: Seq[Cell]): State = cell.state match {
    case Alive if nearbyCells.count(_.state == Alive) < 4 =>
      Alive
    case _ =>
      Dead
  }
}
