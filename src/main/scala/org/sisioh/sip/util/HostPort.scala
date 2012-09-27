package org.sisioh.sip.util

/*
 * Copyright 2012 Sisioh Project and others. (http://www.sisioh.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

import org.sisioh.sip.core.GenericObject
import util.parsing.combinator.RegexParsers
import net.liftweb.json

object HostPortDecoder extends HostPortDecoder

class HostPortDecoder extends SIPDecoder[HostPort] with HostPortParser {

  def decode(source: String): HostPort = decodeTarget(source, hostPort)
}

trait HostPortParser extends ParserBase with HostParser {

  def hostPort: Parser[HostPort] = hostToModel ~ opt(COLON ~> rep1(port)) ^^ {
    case host ~ port =>
      new HostPort(host, port.map(_.mkString.toInt))
  }

}

/**
 * [[org.sisioh.sip.util.HostPort]]のコンパニオンオブジェクト
 */
object HostPort {

  def apply(host: Host, port: Option[Int]) = new HostPort(host, port)

  def decode(source: String) = HostPortDecoder.decode(source)

  def decodeFromJson(source: String) = JsonDecoder.decode(source)

  import net.liftweb.json._
  import net.liftweb.json.JsonDSL._

  object JsonDecoder extends JsonDecoder[HostPort] {

    def decode(json: JsonAST.JValue): HostPort = {
      val host = Host.JsonDecoder.decode(json \ "host")
      val port = (json \ "port").toOpt.map {
        e =>
          val i = e.asInstanceOf[JInt]
          i.num.toInt
      }
      HostPort(host, port)
    }

    def decode(source: String) =
      decode(parse(source))

  }

  /**
   * Jsonエンコーダー。
   */
  object JsonEncoder extends JsonEncoder[HostPort] {

    def encode(model: HostPort, builder: StringBuilder) =
      builder.append(compact(render(encode(model))))

    def encode(model: HostPort) =
      ("host" -> parse(model.host.encodeByJson())) ~
        ("port" -> model.port)

  }

}

/**
 * ホストとポートを表す値オブジェクト。
 *
 * @param host [[org.sisioh.sip.util.Host]]
 * @param port ポート
 */
class HostPort(val host: Host, val port: Option[Int]) extends GenericObject {

  val inetAddress = host.inetAddress

  def removePort =
    HostPort(host, None)

  override def toString = encode

  def encode(builder: StringBuilder) =
    builder.append(port.map("%s:%s".format(host.encode(), _)).getOrElse(host.toString))

  override def hashCode() = 31 * host.## + 31 * port.##

  override def equals(obj: Any) = obj match {
    case that: HostPort =>
      host == that.host && port == that.port
    case _ => false
  }

  def encodeByJson(builder: StringBuilder) = encode(builder, HostPort.JsonEncoder)
}
