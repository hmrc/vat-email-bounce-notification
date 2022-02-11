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

package models

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads}

case class BouncedEmailEvent(emailAddress: Option[String],
                             enrolment: Option[String])

object BouncedEmailEvent {
  private val emailAddressPath = JsPath \ "emailAddress"
  private val enrolmentPath = JsPath \ "enrolment"

  implicit val reads: Reads[BouncedEmailEvent] = (
    emailAddressPath.readNullable[String] and
    enrolmentPath.readNullable[String]
    )(BouncedEmailEvent.apply _)
}