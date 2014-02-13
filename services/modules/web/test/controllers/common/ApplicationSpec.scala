package controllers.web

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import java.io.File

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {

  val modulePath = new File("./modules/web/")
  
  "WebApplication" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication(path = modulePath)) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }

    "respond to web specific requests" in {
      running(FakeApplication(path = modulePath)) {
        val main = route(FakeRequest(GET, "/web")).get
        
        status(main) must equalTo(OK)
        contentAsString(main) must contain ("This is the web module.")

        val lottery = route(FakeRequest(GET, "/web/lottery")).get
        
        status(lottery) must equalTo(OK)
        contentAsString(lottery) must contain ("Your lucky lottery numbers are")
      }
    }

  }
}
