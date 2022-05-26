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

import play.api.libs.json.{JsError, JsResult, JsSuccess, JsValue, Reads}

object ValidEventTypeEnum extends Enumeration {

  val permanentBounce: ValidEventTypeEnum.Value = Value("PermanentBounce")
  val temporaryBounce: ValidEventTypeEnum.Value = Value("TemporaryBounce")
  val rejected: ValidEventTypeEnum.Value = Value("Rejected")

  implicit val reads: Reads[ValidEventTypeEnum.Value] = new Reads[ValidEventTypeEnum.Value] {

    implicit def reads(json: JsValue): JsResult[ValidEventTypeEnum.Value] = {
      json.as[String].toUpperCase match {
        case "PERMANENTBOUNCE" => JsSuccess(permanentBounce)
        case "TEMPORARYBOUNCE" => JsSuccess(temporaryBounce)
        case "REJECTED" => JsSuccess(rejected)
        case e => JsError(s"$e not recognised")
      }
    }
  }

}
