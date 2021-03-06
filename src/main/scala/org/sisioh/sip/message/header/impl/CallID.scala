package org.sisioh.sip.message.header.impl

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

import org.sisioh.sip.message.header.CallIdHeader
import org.sisioh.sip.util._
import net.liftweb.json._

object CallIdDecoder extends CallIdDecoder

class CallIdDecoder extends SIPDecoder[CallId] with CallIdParser {

  def decode(source: String): CallId = decodeTarget(source, Call_IDWithCrLfOpt)

}

trait CallIdParser extends ParserBase {

  lazy val Call_IDWithCrLfOpt: Parser[CallId] = Call_ID <~ opt(CRLF)

  lazy val Call_ID: Parser[CallId] = ("Call-ID" | "i") ~> (HCOLON ~> callid) ^^ {
    case callid =>
      CallId(callid)
  }

  lazy val callid: Parser[String] = repsep(word, "@") ^^ {
    _.mkString("@")
  }

}

object CallIdEncoder extends SIPEncoder[CallId] {

  def encode(model: CallId, builder: StringBuilder) =
    model.callIdentity.encode(builder)

}

trait CallIdJsonFieldNames extends JsonFieldNames {
  val CALL_ID = "callId"
}


object CallIdJsonDecoder extends JsonDecoder[CallId] with CallIdJsonFieldNames {

  def decode(json: JsonAST.JValue) = {
    requireHeaderName(json, CallIdHeader.NAME)
    val JString(callId) = json \ CALL_ID
    CallId(callId)
  }

}

object CallIdJsonEncoder extends JsonEncoder[CallId] with CallIdJsonFieldNames {

  def encode(model: CallId) = {
    JObject(
      getHeaderNameAsJValue(model) ::
        JField(CALL_ID, JString(model.callId)) :: Nil
    )
  }

}

object CallId {

  def decode(source: String): CallId = CallIdDecoder.decode(source)

  def decodeFromJson(source: String): CallId = CallIdJsonDecoder.decode(source)

}

case class CallId(callId: String) extends SIPHeader with CallIdHeader {

  val callIdentity = CallIdentifier.from(callId)

  val headerName = CallIdHeader.NAME
  val name = headerName

  def encodeBody(builder: StringBuilder) = CallIdEncoder.encode(this, builder)

  def encodeAsJValue() = CallIdJsonEncoder.encode(this)

  override def toString = encode()

}
