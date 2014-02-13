package value

import javax.xml.bind.annotation._
import play.mvc.Http

object ApiResponse {
  val FORBIDDEN = Http.Status.FORBIDDEN
  val ERROR = Http.Status.INTERNAL_SERVER_ERROR
  val BAD_REQUEST = Http.Status.BAD_REQUEST
  val UNAUTHORIZED = Http.Status.UNAUTHORIZED
  val CREATED = Http.Status.CREATED
  val OK = Http.Status.OK
  val NOT_FOUND = Http.Status.NOT_FOUND
}

@XmlRootElement
class ApiResponse(@XmlElement var code: Int, @XmlElement var message: String) {
  def this() = this(0, null)

  @XmlTransient
  def getCode(): Int = code
  def setCode(code: Int) = this.code = code

  def getType(): String = code match {
    case ApiResponse.ERROR => "internal server error"
    case ApiResponse.BAD_REQUEST => "bad request"
    case ApiResponse.CREATED => "created"
    case ApiResponse.OK => "ok"
    case ApiResponse.UNAUTHORIZED => "unauthorized"
    case ApiResponse.FORBIDDEN => "forbidden"
    case ApiResponse.NOT_FOUND => "not found"
    case _ => "unknown"
  }
  def setType(`type`: String) = {}

  def getMessage(): String = message
  def setMessage(message: String) = this.message = message
}