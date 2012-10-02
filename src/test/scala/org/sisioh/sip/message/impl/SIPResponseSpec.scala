package org.sisioh.sip.message.impl

import org.specs2.mutable.Specification
import org.sisioh.sip.core.Separators
import org.sisioh.sip.message.header.impl.{Product, Server}

class SIPResponseSpec extends Specification with SIPMessageSpecSupport {
  "SIPResponse" should {
    "リクエストからレスポンスを生成する" in {
      val target = createBasicRequest.createResponse(200, Some("Test"), Some(Server(List(Product("ABC")))))
      val encodeObject = target.encode()
      val lines = encodeObject.split(Separators.NEWLINE)
      lines(0) must_== """SIP/2.0 200 Test"""
      lines(1) must_== """Server: ABC"""
    }
  }
}
