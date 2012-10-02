package org.sisioh.sip.message

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

object StatusCode extends Enumeration {
  /**
   * This response indicates that the request has been received by the
   * next-hop server and that some unspecified action is being taken on
   * behalf of this call (for example, a database is being consulted). This
   * response, like all other provisional responses, stops retransmissions of
   * an INVITE by a UAC. The 100 (Trying) response is different from other
   * provisional responses, in that it is never forwarded upstream by a
   * stateful proxy.
   */
  val TRYING = Value(100, "Trying")

  /**
   * The User Agent receiving the INVITE is trying to alert the user. This
   * response MAY be used to initiate local ringback.
   */
  val RINGING = Value(180, "Ringing")

  /**
   * A server MAY use this status code to indicate that the call is being
   * forwarded to a different set of destinations.
   */
  val CALL_IS_BEING_FORWARDED = Value(181, "Call is beging forwarded")

  /**
   * The called party is temporarily unavailable, but the server has decided
   * to queue the call rather than reject it. When the callee becomes
   * available, it will return the appropriate final status response. The
   * reason phrase MAY give further details about the status of the call,
   * for example, "5 calls queued; expected waiting time is 15 minutes". The
   * server MAY issue several 182 (Queued) responses to update the caller
   * about the status of the queued call.
   */
  val QUEUED = Value(182, "Queued")

  /**
   * The 183 (Session Progress) response is used to convey information about
   * the progress of the call that is not otherwise classified. The
   * Reason-Phrase, header fields, or message body MAY be used to convey more
   * details about the call progress.
   */
  val SESSION_PROGRESS = Value(183, "Session progress")

  /**
   * The request has succeeded. The information returned with the response
   * depends on the method used in the request.
   */
  val OK = Value(200, "OK")

  /**
   * The Acceptable extension response code signifies that the request has
   * been accepted for processing, but the processing has not been completed.
   * The request might or might not eventually be acted upon, as it might be
   * disallowed when processing actually takes place. There is no facility
   * for re-sending a status code from an asynchronous operation such as this.
   * The 202 response is intentionally non-committal. Its purpose is to allow
   * a server to accept a request for some other process (perhaps a
   * batch-oriented process that is only run once per day) without requiring
   * that the user agent's connection to the server persist until the process
   * is completed. The entity returned with this response SHOULD include an
   * indication of the request's current status and either a pointer to a
   * status monitor or some estimate of when the user can expect the request
   * to be fulfilled. This response code is specific to the event notification
   * framework.
   */
  val ACCEPTED = Value(202, "Accepted")

  /**
   * The address in the request resolved to several choices, each with its
   * own specific location, and the user (or UA) can select a preferred
   * communication end point and redirect its request to that location.
   * <p>
   * The response MAY include a message body containing a list of resource
   * characteristics and location(s) from which the user or UA can choose
   * the one most appropriate, if allowed by the Accept request header field.
   * However, no MIME types have been defined for this message body.
   * <p>
   * The choices SHOULD also be listed as Contact fields. Unlike HTTP, the
   * SIP response MAY contain several Contact fields or a list of addresses
   * in a Contact field. User Agents MAY use the Contact header field value
   * for automatic redirection or MAY ask the user to confirm a choice.
   * However, this specification does not define any standard for such
   * automatic selection.
   * <p>
   * This status response is appropriate if the callee can be reached at
   * several different locations and the server cannot or prefers not to
   * proxy the request.
   */
  val MULTIPLE_CHOICES = Value(300, "Multiple choices")

  /**
   * The user can no longer be found at the address in the Request-URI, and
   * the requesting client SHOULD retry at the new address given by the
   * Contact header field. The requestor SHOULD update any local directories,
   * address books, and user location caches with this new value and redirect
   * future requests to the address(es) listed.
   */
  val MOVED_PERMANENTLY = Value(301, "Moved permanently")

  /**
   * The requesting client SHOULD retry the request at the new address(es)
   * given by the Contact header field. The Request-URI of the new request
   * uses the value of the Contact header field in the response.
   * <p>
   * The duration of the validity of the Contact URI can be indicated through
   * an Expires header field or an expires parameter in the Contact header
   * field. Both proxies and User Agents MAY cache this URI for the duration
   * of the expiration time. If there is no explicit expiration time, the
   * address is only valid once for recursing, and MUST NOT be cached for
   * future transactions.
   * <p>
   * If the URI cached from the Contact header field fails, the Request-URI
   * from the redirected request MAY be tried again a single time. The
   * temporary URI may have become out-of-date sooner than the expiration
   * time, and a new temporary URI may be available.
   */
  val MOVED_TEMPORARILY = Value(302, "Moved temporarily")

  /**
   * The requested resource MUST be accessed through the proxy given by the
   * Contact field.  The Contact field gives the URI of the proxy. The
   * recipient is expected to repeat this single request via the proxy.
   * 305 (Use Proxy) responses MUST only be generated by UASs.
   */
  val USE_PROXY = Value(305, "User proxy")

  /**
   * The call was not successful, but alternative services are possible. The
   * alternative services are described in the message body of the response.
   * Formats for such bodies are not defined here, and may be the subject of
   * future standardization.
   */
  val ALTERNATIVE_SERVICE = Value(380, "Alternative service")

  /**
   * The request could not be understood due to malformed syntax. The
   * Reason-Phrase SHOULD identify the syntax problem in more detail, for
   * example, "Missing Call-ID header field".
   */
  val BAD_REQUEST = Value(400, "Bad request")

  /**
   * The request requires user authentication. This response is issued by
   * UASs and registrars, while 407 (Proxy Authentication Required) is used
   * by proxy servers.
   */
  val UNAUTHORIZED = Value(401, "Unauthorized")

  /**
   * Reserved for future use.
   */
  val PAYMENT_REQUIRED = Value(402, "Payment required")

  /**
   * The server understood the request, but is refusing to fulfill it.
   * Authorization will not help, and the request SHOULD NOT be repeated.
   */
  val FORBIDDEN = Value(403, "Forbidden")

  /**
   * The server has definitive information that the user does not exist at
   * the domain specified in the Request-URI.  This status is also returned
   * if the domain in the Request-URI does not match any of the domains
   * handled by the recipient of the request.
   */
  val NOT_FOUND = Value(404, "Not found")

  /**
   * The method specified in the Request-Line is understood, but not allowed
   * for the address identified by the Request-URI. The response MUST include
   * an Allow header field containing a list of valid methods for the
   * indicated address
   */
  val METHOD_NOT_ALLOWED = Value(405, "Method not allowed")

  /**
   * The resource identified by the request is only capable of generating
   * response entities that have content characteristics not acceptable
   * according to the Accept header field sent in the request.
   */
  val NOT_ACCEPTABLE = Value(406, "Not acceptable")

  /**
   * This code is similar to 401 (Unauthorized), but indicates that the client
   * MUST first authenticate itself with the proxy. This status code can be
   * used for applications where access to the communication channel (for
   * example, a telephony gateway) rather than the callee requires
   * authentication.
   */
  val PROXY_AUTHENTICATION_REQUIRED = Value(407, "Proxy Authentication required")

  /**
   * The server could not produce a response within a suitable amount of
   * time, for example, if it could not determine the location of the user
   * in time. The client MAY repeat the request without modifications at
   * any later time.
   */
  val REQUEST_TIMEOUT = Value(408, "Request timeout")

  /**
   * The requested resource is no longer available at the server and no
   * forwarding address is known. This condition is expected to be considered
   * permanent. If the server does not know, or has no facility to determine,
   * whether or not the condition is permanent, the status code 404
   * (Not Found) SHOULD be used instead.
   */
  val GONE = Value(410, "Gone")

  /**
   * The server is refusing to service the PUBLISH request because the
   * entity-tag in the SIP-If-Match header does not match with existing
   * event state.
   *
   * @since v1.2
   */
  val CONDITIONAL_REQUEST_FAILED = Value(412, "Temporarily Unavailable")


  /**
   * The server is refusing to process a request because the request
   * entity-body is larger than the server is willing or able to process. The
   * server MAY close the connection to prevent the client from continuing
   * the request. If the condition is temporary, the server SHOULD include a
   * Retry-After header field to indicate that it is temporary and after what
   * time the client MAY try again.
   */
  val REQUEST_ENTITY_TOO_LARGE = Value(413, "Request entity too large")

  /**
   * The server is refusing to service the request because the Request-URI
   * is longer than the server is willing to interpret.
   */
  val REQUEST_URI_TOO_LONG = Value(414, "Request-URI too large")

  /**
   * The server is refusing to service the request because the message body
   * of the request is in a format not supported by the server for the
   * requested method. The server MUST return a list of acceptable formats
   * using the Accept, Accept-Encoding, or Accept-Language header field,
   * depending on the specific problem with the content.
   */
  val UNSUPPORTED_MEDIA_TYPE = Value(415, "Unsupported media type")

  /**
   * The server cannot process the request because the scheme of the URI in
   * the Request-URI is unknown to the server.
   */
  val UNSUPPORTED_URI_SCHEME = Value(416, "Unsupported URI Scheme")

  /**
   * The server did not understand the protocol extension specified in a
   * Proxy-Require or Require header field. The server MUST include a list of
   * the unsupported extensions in an Unsupported header field in the response.
   */
  val BAD_EXTENSION = Value(420, "Bad extension")

  /**
   * The UAS needs a particular extension to process the request, but this
   * extension is not listed in a Supported header field in the request.
   * Responses with this status code MUST contain a Require header field
   * listing the required extensions.
   * <p>
   * A UAS SHOULD NOT use this response unless it truly cannot provide any
   * useful service to the client. Instead, if a desirable extension is not
   * listed in the Supported header field, servers SHOULD process the request
   * using baseline SIP capabilities and any extensions supported by the
   * client.
   */
  val EXTENSION_REQUIRED = Value(421, "Extension Required")

  /**
   * The server is rejecting the request because the expiration time of the
   * resource refreshed by the request is too short. This response can be
   * used by a registrar to reject a registration whose Contact header field
   * expiration time was too small.
   */
  val INTERVAL_TOO_BRIEF = Value(423, "Interval too brief")

  /**
   * The callee's end system was contacted successfully but the callee is
   * currently unavailable (for example, is not logged in, logged in but in a
   * state that precludes communication with the callee, or has activated the
   * "do not disturb" feature). The response MAY indicate a better time to
   * call in the Retry-After header field. The user could also be available
   * elsewhere (unbeknownst to this server). The reason phrase SHOULD indicate
   * a more precise cause as to why the callee is unavailable. This value
   * SHOULD be settable by the UA. Status 486 (Busy Here) MAY be used to more
   * precisely indicate a particular reason for the call failure.
   * <p>
   * This status is also returned by a redirect or proxy server that
   * recognizes the user identified by the Request-URI, but does not currently
   * have a valid forwarding location for that user.
   */
  val TEMPORARILY_UNAVAILABLE = Value(480, "Temporarily Unavailable")

  /**
   * This status indicates that the UAS received a request that does not
   * match any existing dialog or transaction.
   */
  val CALL_OR_TRANSACTION_DOES_NOT_EXIST = Value(481, "Call leg/Transaction does not exist")

  /**
   * The server has detected a loop.
   */
  val LOOP_DETECTED = Value(482, "Loop detected")

  /**
   * The server received a request that contains a Max-Forwards header field
   * with the value zero.
   */
  val TOO_MANY_HOPS = Value(483, "Too many hops")

  /**
   * The server received a request with a Request-URI that was incomplete.
   * Additional information SHOULD be provided in the reason phrase. This
   * status code allows overlapped dialing. With overlapped dialing, the
   * client does not know the length of the dialing string. It sends strings
   * of increasing lengths, prompting the user for more input, until it no
   * longer receives a 484 (Address Incomplete) status response.
   */
  val ADDRESS_INCOMPLETE = Value(484, "Address incomplete")

  /**
   * The Request-URI was ambiguous. The response MAY contain a listing of
   * possible unambiguous addresses in Contact header fields. Revealing
   * alternatives can infringe on privacy of the user or the organization.
   * It MUST be possible to configure a server to respond with status 404
   * (Not Found) or to suppress the listing of possible choices for ambiguous
   * Request-URIs. Some email and voice mail systems provide this
   * functionality. A status code separate from 3xx is used since the
   * semantics are different: for 300, it is assumed that the same person or
   * service will be reached by the choices provided. While an automated
   * choice or sequential search makes sense for a 3xx response, user
   * intervention is required for a 485 (Ambiguous) response.
   */
  val AMBIGUOUS = Value(485, "Ambiguous")

  /**
   * The callee's end system was contacted successfully, but the callee is
   * currently not willing or able to take additional calls at this end
   * system. The response MAY indicate a better time to call in the Retry-After
   * header field. The user could also be available elsewhere, such as
   * through a voice mail service. Status 600 (Busy Everywhere) SHOULD be
   * used if the client knows that no other end system will be able to accept
   * this call.
   */
  val BUSY_HERE = Value(486, "Busy here")

  /**
   * The request was terminated by a BYE or CANCEL request. This response is
   * never returned for a CANCEL request itself.
   */
  val REQUEST_TERMINATED = Value(487, "Request Terminated")

  /**
   * The response has the same meaning as 606 (Not Acceptable), but only
   * applies to the specific resource addressed by the Request-URI and the
   * request may succeed elsewhere. A message body containing a description
   * of media capabilities MAY be present in the response, which is formatted
   * according to the Accept header field in the INVITE (or application/sdp
   * if not present), the same as a message body in a 200 (OK) response to
   * an OPTIONS request.
   */
  val NOT_ACCEPTABLE_HERE = Value(488, "Not Acceptable here")

  /**
   * The Bad Event extension response code is used to indicate that the
   * server did not understand the event package specified in a "Event"
   * header field. This response code is specific to the event notification
   * framework.
   */
  val BAD_EVENT = Value(489, "Bad Event")

  /**
   * The request was received by a UAS that had a pending request within
   * the same dialog.
   */
  val REQUEST_PENDING = Value(491, "Request Pending")

  /**
   * The request was received by a UAS that contained an encrypted MIME body
   * for which the recipient does not possess or will not provide an
   * appropriate decryption key. This response MAY have a single body
   * containing an appropriate public key that should be used to encrypt MIME
   * bodies sent to this UA.
   */
  val UNDECIPHERABLE = Value(493, "Undecipherable")

  /**
   * The server encountered an unexpected condition that prevented it from
   * fulfilling the request. The client MAY display the specific error
   * condition and MAY retry the request after several seconds. If the
   * condition is temporary, the server MAY indicate when the client may
   * retry the request using the Retry-After header field.
   */
  val SERVER_INTERNAL_ERROR = Value(500, "Server Internal Error")

  /**
   * The server does not support the functionality required to fulfill the
   * request. This is the appropriate response when a UAS does not recognize
   * the request method and is not capable of supporting it for any user.
   * Proxies forward all requests regardless of method. Note that a 405
   * (Method Not Allowed) is sent when the server recognizes the request
   * method, but that method is not allowed or supported.
   */
  val NOT_IMPLEMENTED = Value(501, "Not implemented")

  /**
   * The server, while acting as a gateway or proxy, received an invalid
   * response from the downstream server it accessed in attempting to
   * fulfill the request.
   */
  val BAD_GATEWAY = Value(502, "Bad gateway")

  /**
   * The server is temporarily unable to process the request due to a
   * temporary overloading or maintenance of the server. The server MAY
   * indicate when the client should retry the request in a Retry-After
   * header field. If no Retry-After is given, the client MUST act as if it
   * had received a 500 (Server Internal Error) response.
   * <p>
   * A client (proxy or UAC) receiving a 503 (Service Unavailable) SHOULD
   * attempt to forward the request to an alternate server. It SHOULD NOT
   * forward any other requests to that server for the duration specified
   * in the Retry-After header field, if present.
   * <p>
   * Servers MAY refuse the connection or drop the request instead of
   * responding with 503 (Service Unavailable).
   */
  val SERVICE_UNAVAILABLE = Value(503, "Service unavailable")

  /**
   * The server did not receive a timely response from an external server
   * it accessed in attempting to process the request. 408 (Request Timeout)
   * should be used instead if there was no response within the
   * period specified in the Expires header field from the upstream server.
   */
  val SERVER_TIMEOUT = Value(504, "Gateway timeout")

  /**
   * The server does not support, or refuses to support, the SIP protocol
   * version that was used in the request. The server is indicating that
   * it is unable or unwilling to complete the request using the same major
   * version as the client, other than with this error message.
   */
  val VERSION_NOT_SUPPORTED = Value(505, "SIP version not supported")

  /**
   * The server was unable to process the request since the message length
   * exceeded its capabilities.
   */
  val MESSAGE_TOO_LARGE = Value(513, "Message Too Large")

  /**
   * The callee's end system was contacted successfully but the callee is
   * busy and does not wish to take the call at this time. The response
   * MAY indicate a better time to call in the Retry-After header field.
   * If the callee does not wish to reveal the reason for declining the call,
   * the callee uses status code 603 (Decline) instead. This status response
   * is returned only if the client knows that no other end point (such as a
   * voice mail system) will answer the request. Otherwise, 486 (Busy Here)
   * should be returned.
   */
  val BUSY_EVERYWHERE = Value(600, "Busy everywhere")

  /**
   * The callee's machine was successfully contacted but the user explicitly
   * does not wish to or cannot participate. The response MAY indicate a
   * better time to call in the Retry-After header field. This status
   * response is returned only if the client knows that no other end point
   * will answer the request.
   */
  val DECLINE = Value(603, "Decline")

  /**
   * The server has authoritative information that the user indicated in the
   * Request-URI does not exist anywhere.
   */
  val DOES_NOT_EXIST_ANYWHERE = Value(604, "Does not exist anywhere")

  /**
   * The user's agent was contacted successfully but some aspects of the
   * session description such as the requested media, bandwidth, or addressing
   * style were not acceptable. A 606 (Not Acceptable) response means that
   * the user wishes to communicate, but cannot adequately support the
   * session described. The 606 (Not Acceptable) response MAY contain a list
   * of reasons in a Warning header field describing why the session described
   * cannot be supported.
   * <p>
   * A message body containing a description of media capabilities MAY be
   * present in the response, which is formatted according to the Accept
   * header field in the INVITE (or application/sdp if not present), the same
   * as a message body in a 200 (OK) response to an OPTIONS request.
   * <p>
   * It is hoped that negotiation will not frequently be needed, and when a
   * new user is being invited to join an already existing conference,
   * negotiation may not be possible. It is up to the invitation initiator to
   * decide whether or not to act on a 606 (Not Acceptable) response.
   * <p>
   * This status response is returned only if the client knows that no other
   * end point will answer the request. This specification renames this
   * status code from NOT_ACCEPTABLE as in RFC3261 to SESSION_NOT_ACCEPTABLE
   * due to it conflict with 406 (Not Acceptable) defined in this interface.
   */
  val SESSION_NOT_ACCEPTABLE = Value(606, "Session Not acceptable")


}

trait Response extends Message {

  val statusCode: StatusCode.Value

  val reasonPhrase: String

}

