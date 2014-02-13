package controllers.admin

import _root_.controllers.common.ScalaBaseApiController
import play.api._
import play.api.mvc._

object Application extends ScalaBaseApiController {

  def home = Action {
    Ok(views.html.index("Hello there!"))
  }

  def main = Action {
    Ok("Only admin will respond to this.")
  }



}