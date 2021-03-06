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

import util.parsing.combinator.RegexParsers

trait ParserBase extends RegexParsers {

  override protected val whiteSpace = "".r

  def chr(c: Char): Parser[Char] = c

  def chrRange(f: Char, t: Char): Parser[Char] = elem("[]", c => f <= c && c <= t)


  lazy val ALPHA_SMALL = chrRange('a', 'z')
  lazy val ALPHA_LARGE = chrRange('A', 'Z')

  lazy val ALPHA: Parser[Char] = ALPHA_SMALL | ALPHA_LARGE
  lazy val CHAR = chrRange(Char.MinValue, Char.MaxValue)
  lazy val DIGIT: Parser[Char] = chrRange('0', '9')

  lazy val CR = chr(0x0D) // \r
  lazy val LF = chr(0x0A) // \n
  lazy val SP = chr(0x20)
  lazy val HT = chr(0x09)
  lazy val HTAB = HT
  lazy val DQUOTE: Parser[Char] = '\"'
  lazy val WSP = SP | HT


  lazy val CRLF: Parser[String] = CR ~ LF ^^ {
    case f ~ s =>
      new StringBuilder().append(f).append(s).result()
  } | (CR | LF) ^^ {
    _.toString
  }

  lazy val LWS: Parser[String] = opt((rep(WSP) ~ CRLF) ^^ {
    case f ~ s =>
      f.mkString + s
  }) ~ rep1(WSP) ^^ {
    case f ~ s =>
      f.getOrElse("") + s.mkString
  }
  lazy val SWS: Parser[Option[String]] = opt(LWS)
  lazy val HCOLON: Parser[Char] = rep(SP | HT) ~> ':' <~ SWS

  lazy val LHEX = DIGIT | chrRange(0x61, 0x66) // 小文字のa-f

  lazy val token: Parser[String] = rep1(alphanum | elem('-') | '.' | '!' | '%' | '*' | '_' | '+' | '`' | '\'' | '~') ^^ {
    _.mkString
  }

  //  lazy val token: Parser[String] = """[a-zA-Z0-9\-.!%_+`'~]+""".r

  lazy val separators: Parser[Char] = elem('(') | ')' | '<' | '>' | '@' |
    ',' | ';' | ':' | '\\' | DQUOTE |
    '/' | '[' | ']' | '?' | '=' |
    '{' | '}' | SP | HT

  lazy val word: Parser[String] = rep1(alphanum | '-' | '.' | '!' | '%' | '*' |
    '_' | '+' | '`' | '\'' | '~' |
    '(' | ')' | '<' | '>' |
    ':' | '\\' | DQUOTE |
    '/' | '[' | ']' | '?' |
    '{' | '}') ^^ {
    _.mkString
  }

  lazy val STAR: Parser[Char] = SWS ~> '*' <~ SWS
  lazy val SLASH: Parser[Char] = SWS ~> '/' <~ SWS
  lazy val EQUAL: Parser[Char] = SWS ~> '=' <~ SWS
  lazy val LPAREN: Parser[Char] = SWS ~> '('
  lazy val RPAREN: Parser[Char] = ')' <~ SWS
  lazy val LAQUOT: Parser[Char] = SWS ~> '<'
  lazy val RAQUOT: Parser[Char] = '>' <~ SWS
  lazy val COMMA: Parser[Char] = SWS ~> ',' <~ SWS
  lazy val SEMI: Parser[Char] = SWS ~> ';' <~ SWS
  lazy val COLON: Parser[Char] = SWS ~> ':' <~ SWS
  lazy val LDQUOTE: Parser[Char] = SWS ~> DQUOTE
  lazy val RDQUOTE: Parser[Char] = DQUOTE <~ SWS

  lazy val comment: Parser[String] = "(" ~ rep(ctext | quotedPair | comment) ~ ")" ^^ {
    case lb ~ texts ~ rb =>
      lb + texts.mkString + rb
  }

  lazy val ctext: Parser[String] =
    (chrRange(0x21, 0x27) |
      chrRange(0x2A, 0x5B) |
      chrRange(0x5D, 0x7E)) ^^ {
      c => c.toString
    } | UTF8_NONASCII | LWS

  lazy val quotedPair: Parser[String] = elem('\\') ~ (chrRange(0x00, 0x09) | chrRange(0x0B, 0x0C) | chrRange(0x0E, 0x7F)) ^^ {
    case bs ~ c =>
      new StringBuilder().append(bs).append(c).result()
  }

  lazy val HOSTNAME = """(([a-zA-Z]|([a-zA-Z0-9])([a-zA-Z0-9]|[-])*([a-zA-Z0-9]))[.])*(([a-zA-Z][a-zA-Z0-9]*[a-zA-Z])|[a-zA-Z])[.]?""".r

  lazy val qdtext: Parser[String] = LWS | (chr(0x21) | chrRange(0x23, 0x5B) | chrRange(0x5D, 0x7E)) ^^ {
    e =>
      e.toString
  } | UTF8_NONASCII

  lazy val quotedString: Parser[String] = SWS ~> (DQUOTE ~> rep(qdtext | quotedPair) <~ DQUOTE) ^^ {
    _.mkString
  }


  lazy val UTF8_CONT: Parser[Char] = chrRange(0x80, 0xBF)

  lazy val UTF8_NONASCII: Parser[String] = (chrRange(0xC0, 0xDF) ~ repN(1, UTF8_CONT) |
    chrRange(0xE0, 0xEF) ~ repN(2, UTF8_CONT) |
    chrRange(0xF0, 0xF7) ~ repN(3, UTF8_CONT) |
    chrRange(0xF8, 0xFb) ~ repN(4, UTF8_CONT) |
    chrRange(0xFC, 0xFD) ~ repN(5, UTF8_CONT)) ^^ {
    case c ~ clist =>
      new StringBuilder().append(c).append(clist.mkString).result()
  }


  lazy val reserved: Parser[Char] = elem(';') | '/' | '?' | ':' | '@' | '&' | '=' | '+' | '$' | ','

  lazy val alphanum: Parser[Char] = ALPHA | DIGIT

  lazy val mark: Parser[Char] = elem('-') | '_' | '.' | '!' | '~' | '*' | '\'' | '(' | ')'

  lazy val unreserved: Parser[Char] = alphanum | mark

  lazy val HEXDIG: Parser[Char] = DIGIT | 'A' | 'B' | 'C' | 'D' | 'E' | 'F'

  lazy val escaped: Parser[Char] = elem('%') ~> HEXDIG ~ HEXDIG ^^ {
    case f ~ s =>
      Integer.parseInt(List(f, s).mkString, 16).toChar
  }

  lazy val Method = INVITEm | ACKm | OPTIONSm | BYEm | CANCELm | REGISTERm | extensionMethod

  lazy val INVITEm = "INVITE"
  lazy val ACKm = "ACK"
  lazy val OPTIONSm = "OPTIONS"
  lazy val BYEm = "BYE"
  lazy val CANCELm = "CANCEL"
  lazy val REGISTERm = "REGISTER"
  lazy val extensionMethod = token

  lazy val deltaSeconds: Parser[String] = rep1(DIGIT) ^^ {
    _.mkString
  }

  lazy val genericParam: Parser[NameValuePair] = token ~ opt(EQUAL ~> genValue) ^^ {
    case n ~ v =>
      NameValuePair(Some(n), v)
  }

  lazy val L_BRACKET = """\[""".r
  lazy val R_BRACKET = """\]""".r

  lazy val IPv4address = ("(" + Host.v4Partial + ")(\\.(" + Host.v4Partial + ")){3}").r

  lazy val IPv6address = (Host.v6PattenBase + "{7}").r

  lazy val IPv6addressReference = L_BRACKET ~>  IPv6address <~ R_BRACKET

  lazy val host = HOSTNAME | IPv4address | IPv6address

  lazy val genValue = token | host | quotedString

  lazy val port = DIGIT

  lazy val SIP_Version: Parser[String] = "SIP" ~ "/" ~ rep1(DIGIT) ~ "." ~ rep1(DIGIT) ^^ {
    case sip ~ slash ~ major ~ dot ~ minar =>
      List(sip, slash, major.mkString, dot, minar.mkString).mkString
  }

  lazy val otherTransport: Parser[String] = token

  lazy val ttl: Parser[Int] = DIGIT ~ opt(DIGIT ~ opt(DIGIT) ^^ {
    case f ~ s =>
      List(Some(f), s).mkString
  }) ^^ {
    case f ~ s =>
      List(Some(f), s).mkString.toInt
  }
}
