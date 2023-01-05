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

package connectors.httpParsers

import models.UpdateContactPrefResponse
import play.api.http.Status.OK
import uk.gov.hmrc.http.{HttpReads, HttpResponse}
import utils.LoggerUtil

object UpdateContactPrefHttpParser extends LoggerUtil {

  implicit object UpdateContactPrefReads extends HttpReads[Option[UpdateContactPrefResponse]] {
    override def read(method: String, url: String, response: HttpResponse): Option[UpdateContactPrefResponse] =
      response.status match {
        case OK =>
          response.json.validate[UpdateContactPrefResponse].fold(
            invalid => {
              logger.warn(s"[UpdateContactPrefReads][read] - Invalid JSON returned by EIS. Validation error: $invalid")
              None
            },
            valid => {
              logger.debug(s"[UpdateContactPrefReads][read] - Valid JSON received: ${response.json}")
              Some(valid)
            }
          )
        case _ =>
          logger.warn(s"[UpdateContactPrefReads][read] - Unexpected response received from EIS. " +
            s"Status: ${response.status}, Body: ${response.body}")
          None
      }
  }
}
