package controllers.common


import util.RestResourceUtil

import value._
import com.wordnik.swagger.core.util.ScalaJsonUtil

import play.api.mvc._

import java.io.StringWriter
import javax.xml.bind.JAXBContext
import com.wordnik.swagger.model.ResourceListing
import javax.ws.rs.core.MediaType

class ScalaBaseApiController extends Controller with RestResourceUtil {

  protected def jaxbContext(): JAXBContext = JAXBContext.newInstance(classOf[String], classOf[ResourceListing])

  protected def returnXml(request: Request[_]) = request.accepts(MediaType.APPLICATION_XML)

  protected val AccessControlAllowOrigin = (("Access-Control-Allow-Origin", "*"),
  ("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT"),
  ("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization"))

  // APIs
  protected def JsonResponse(data: Any) = {
    val jsonValue: String = toJsonString(data)
    new SimpleResult(header = ResponseHeader(200), body = play.api.libs.iteratee.Enumerator(jsonValue.getBytes())).as("application/json")
      .withHeaders(
        ("Access-Control-Allow-Origin", "*"),
        ("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT"),
        ("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization"))
  }

  protected def JsonResponse(data: Any, code: Int) = {
    val jsonValue: String = toJsonString(data)
    new SimpleResult(header = ResponseHeader(code), body = play.api.libs.iteratee.Enumerator(jsonValue.getBytes())).as("application/json")
      .withHeaders(
        ("Access-Control-Allow-Origin", "*"),
        ("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT"),
        ("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization"))
  }
  //Todo these are NOT valid XML responses and will throw exceptions
  protected def XmlResponse(data: Any) = {
    val xmlValue = toXmlString(data)
    new SimpleResult(header = ResponseHeader(200), body = play.api.libs.iteratee.Enumerator(xmlValue.getBytes())).as("application/xml")
      .withHeaders(
        ("Access-Control-Allow-Origin", "*"),
        ("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT"),
        ("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization"))
  }

  protected def XmlReponse(data: Any, code: Int) = {
    val xmlValue = toXmlString(data)
    new SimpleResult(header = ResponseHeader(code), body = play.api.libs.iteratee.Enumerator(xmlValue.getBytes())).as("application/xml")
      .withHeaders(
        )
  }

  protected def returnValue(request: Request[_], obj: Any): Result = {
    val response = returnXml(request) match {
      case true => XmlResponse(obj)
      case false => JsonResponse(obj)
    }
    response.withHeaders(        ("Access-Control-Allow-Origin", "*"),
      ("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT"),
      ("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization"))
  }

  def toXmlString(data: Any): String = {
    if (data.getClass.equals(classOf[String])) {
      data.asInstanceOf[String]
    } else {
      val stringWriter = new StringWriter()
      jaxbContext.createMarshaller().marshal(data, stringWriter)
      stringWriter.toString
    }
  }

  def toJsonString(data: Any): String = {
    if (data.getClass.equals(classOf[String])) {
      data.asInstanceOf[String]
    } else {
      ScalaJsonUtil.mapper.writeValueAsString(data)
    }
  }
}
