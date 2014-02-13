package controllers.admin

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

  val modulePath = new File("./modules/admin/")

  "AdminApplication" should {

    "send 404 on a bad request" in {
      running(FakeApplication(path = modulePath)) {
        route(FakeRequest(GET, "/boum")) must beNone
      }
    }

    "render the home page" in {
      running(FakeApplication(path = modulePath)) {
        val home = route(FakeRequest(GET, "/")).get

        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("Hello there")
      }
    }

    "respond to admin specific requests" in {
      running(FakeApplication(path = modulePath)) {
        val main = route(FakeRequest(GET, "/admin")).get

        status(main) must equalTo(OK)
        contentAsString(main) must contain ("Only admin will respond to this")

        val metrics = route(FakeRequest(GET, "/admin/metrics")).get

        status(metrics) must equalTo(OK)
        contentAsString(metrics) must contain ("com.kenshoo.play.metrics")
      }
    }

  }
}