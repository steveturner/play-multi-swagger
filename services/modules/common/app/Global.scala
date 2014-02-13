import com.codahale.metrics.JmxReporter
import com.wordnik.swagger.model.ApiInfo
import filter.AccessLog
import play.api._

import com.wordnik.swagger.config._
import com.wordnik.swagger.model._
import com.kenshoo.play.metrics.{MetricsRegistry, MetricsFilter}
import play.api.mvc._
import scala.Some

trait CommonSettings extends GlobalSettings {
  val info = ApiInfo(
    title = "Service component API.",
    description = """Common features of Services""",
    termsOfServiceUrl = "http://your.domain.com",
    contact = "yourname@gmail.com",
    license = "N/A",
    licenseUrl = "N/A")

  ConfigFactory.config.info = Some(info)
}


object Global extends WithFilters(MetricsFilter, AccessLog) with CommonSettings {
  override def onStart(app: Application) {
    val jmxReporter = JmxReporter.forRegistry(MetricsRegistry.default).build
    jmxReporter.start
  }
}