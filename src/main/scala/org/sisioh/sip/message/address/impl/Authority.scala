package org.sisioh.sip.message.address.impl

import org.sisioh.sip.util.{Encodable, Encoder, HostPort}
import org.sisioh.sip.core.{GenericObject, Separators}

object Authority {

  implicit object JsonEncoder extends Encoder[Authority] {
    def encode(model: Authority, builder: StringBuilder) = {
      import net.liftweb.json._
      val json = (model.hostPort, model.userInfo) match {
        case (Some(hp), Some(ui)) =>
          JObject(JField("hostPort", parse(hp.encode)) :: JField("userInfo", parse(ui.encode)) :: Nil)
        case (Some(hp), None) =>
          JObject(JField("hostPort", parse(hp.encode)) :: Nil)
        case (None, Some(ui)) =>
          JObject(JField("userInfo", parse(ui.encode)) :: Nil)
        case _ =>
          JNull
      }
      builder.append(compact(render(json)))
    }

  }

}

/**
 * ユーザ情報とホスト情報を併せ持つ
 *
 * @param hostPort
 * @param userInfo
 */
case class Authority(hostPort: Option[HostPort], userInfo: Option[UserInfo]) extends GenericObject[Authority] {
  val userName = userInfo.map(_.name)
  val host = hostPort.map(_.host)
  val port: Option[Int] = hostPort.flatMap(_.port)

  def removePort: Authority = Authority(hostPort.map(_.removePort), userInfo)

  def removeUserInfo: Authority = Authority(hostPort, None)

  def encode(builder: StringBuilder): StringBuilder = {
    (hostPort, userInfo) match {
      case (Some(hp), Some(ui)) =>
        ui.encode(builder).append(Separators.AT)
        hp.encode(builder)
      case (Some(hp), None) =>
        hp.encode(builder)
      case _ =>
        ""
    }
    builder
  }
}