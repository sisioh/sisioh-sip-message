package org.sisioh.sip.message.header.impl

import org.specs2.mutable.Specification
import org.sisioh.sip.core.Separators

class ServerSpec extends Specification {
  "Server" should {
    val target = Server(List(Product("PRODUCT1")))
    "正しい属性が取得できること" in {
      target.serverVals must_== List(Product("PRODUCT1"))
    }
    "プロダクトを追加できること" in {
      target.addProduct(Product("PRODUCT2")).serverVals must_== List(Product("PRODUCT2"), Product("PRODUCT1"))
    }
    "エンコード結果を取得できること" in {
      target.encode() must_== """Server: PRODUCT1""" + Separators.NEWLINE
      target.encodeByJson() must_== """["PRODUCT1"]"""
    }
  }
}
