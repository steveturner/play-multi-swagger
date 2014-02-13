package filter

import play.api.mvc._
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext



object AccessLog extends Filter {
   override def apply(next: RequestHeader => Future[SimpleResult])(request: RequestHeader): Future[SimpleResult] = {
    val msg = s"method=${request.method} uri=${request.uri} remote-address=${request.remoteAddress} " +
      s"domain=${request.domain} query-string=${request.rawQueryString} " +
      s"referrer=${request.headers.get("referrer").getOrElse("N/A")} " +
      s"user-agent=[${request.headers.get("user-agent").getOrElse("N/A")}]"
    play.Logger.of("accesslog").info(msg)
    next(request)
  }
}
