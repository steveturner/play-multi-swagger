package controllers.common

import play.api._
import play.api.mvc._

object Application extends ScalaBaseApiController {

  def status = Action {
    Ok("Everything is great!")
  }
  def api = Action {
    Redirect("/assets/swagger-ui/index.html")
  }

}
