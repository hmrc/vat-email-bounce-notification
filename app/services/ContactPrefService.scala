/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package services

import com.google.inject.Inject
import connectors.UpdateContactPrefConnector
import models.{BouncedEmail, UpdateContactPrefRequest, UpdateContactPrefResponse}
import uk.gov.hmrc.http.HeaderCarrier
import utils.LoggerUtil

import scala.concurrent.{ExecutionContext, Future}

class ContactPrefService @Inject()(connector: UpdateContactPrefConnector,
                                   auditService: AuditService)
                                  (implicit ec: ExecutionContext) extends LoggerUtil {

  def updateContactPref(request : BouncedEmail, correlationId: Option[String])(implicit headerCarrier: HeaderCarrier): Future[Option[UpdateContactPrefResponse]] = {

    val charsToKeep = 9
    val noSpaces = request.event.enrolment.replace(" ", "")
    val vrn = noSpaces.takeRight(charsToKeep)
    val email =request.event.emailAddress
    val vrnRegex = """\d{9}"""

    val data = Map[String, String](elems =
      "retrievedEventId" -> request.eventId,
      "retrievedGroupId" -> request.groupId.getOrElse(""),
      "retrievedTimestamp" -> request.timestamp,
      "retrievedEmailAddress" -> request.event.emailAddress,
      "retrievedEnrolment" -> request.event.enrolment,
      "retrievedEvent" -> request.event.event.toString,
      "attemptedIdentifier" -> vrn,
      "attemptedEmail" -> email
    )

    auditService.sendAuditEvent("BouncedEmailData", data)

    vrn.matches(vrnRegex) match {
      case true => val requestModel : UpdateContactPrefRequest =
        UpdateContactPrefRequest(identifier = vrn, identifierType = "VRN", emailAddress = email, unusableStatus = true)
        connector.updateContactPref(requestModel, correlationId)(HeaderCarrier(), ec)
      case _ => logger.warn(s"[ContactPrefService][updateContactPref] failed to validate vrn - $vrn")
        Future.successful(None)

    }
  }
}
