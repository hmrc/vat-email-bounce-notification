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

package models

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads}

case class BouncedEmail(eventId: String,
                        groupId: Option[String],
                        timestamp: String,
                        event: BouncedEmailEvent)

object BouncedEmail {
  private val eventIdPath = JsPath \ "eventId"
  private val groupIdPath = JsPath \ "groupId"
  private val timestampPath = JsPath \ "timestamp"
  private val eventPath = JsPath \ "event"

  implicit val reads: Reads[BouncedEmail] = (
    eventIdPath.read[String] and
    groupIdPath.readNullable[String] and
    timestampPath.read[String] and
    eventPath.read[BouncedEmailEvent]
  )(BouncedEmail.apply _)
}
