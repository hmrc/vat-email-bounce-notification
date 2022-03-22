/*
 * Copyright 2022 HM Revenue & Customs
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
import utils.LoggerUtil

import scala.concurrent.Future

class ContactPrefService @Inject()(connector: UpdateContactPrefConnector) extends LoggerUtil {

  def updateContactPref(request : BouncedEmail): Future[Option[UpdateContactPrefResponse]] = {
    val charsToKeep = 9
    val vrn = request.event.enrolment.getOrElse("").takeRight(charsToKeep)
    val vrnRegex = """\d{9}"""

    (vrn.matches(vrnRegex), request.event.emailAddress) match {
      case (true,Some(email)) => val requestModel : UpdateContactPrefRequest =
        UpdateContactPrefRequest(identifier = vrn, identifierType = "VRN", emailaddress = email, unusableStatus = true)
        connector.updateContactPref(requestModel)
      case(true, None) => logger.warn("[ContactPrefService][updateContactPref] no email address provided")
        Future.successful(None)
      case _ => logger.warn("[ContactPrefService][updateContactPref] failed to validate VRN")
        Future.successful(None)

    }
  }
}
