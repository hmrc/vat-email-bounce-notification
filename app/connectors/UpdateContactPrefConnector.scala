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

package connectors

import config.AppConfig
import connectors.httpParsers.UpdateContactPrefHttpParser.UpdateContactPrefReads
import models.{UpdateContactPrefRequest, UpdateContactPrefResponse}
import uk.gov.hmrc.http.{HeaderCarrier, HttpClient, HttpException}
import utils.LoggerUtil

import java.util.UUID.randomUUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UpdateContactPrefConnector @Inject()(val http: HttpClient, val appConfig: AppConfig) extends LoggerUtil {

private[connectors] def updateContactPrefUrl() = s"${appConfig.eisUrl}/income-tax/customer/VATC/contact-preference"


  def updateContactPref(model: UpdateContactPrefRequest,
                        correlationId: Option[String])(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[Option[UpdateContactPrefResponse]] = {

    val correlationIdString: String = correlationId match {
      case Some(value) => value
      case _ => randomUUID().toString
    }

    val eisHeaders = Seq(
      "Authorization" -> s"Bearer ${appConfig.eisToken}",
      "X-Correlation-ID" -> correlationIdString,
      "Environment" -> appConfig.eisEnvironment)

    val url = updateContactPrefUrl()

    logger.debug(s"[UpdateContactPrefConnector][updateContactPref] - Calling PUT $url \nHeaders: $eisHeaders")
    http.PUT(url, model, eisHeaders)(implicitly, UpdateContactPrefReads, hc, ec).recover {
      case ex: HttpException =>
        logger.warn(s"[UpdateContactPrefConnector][updateContactPref] - HTTP exception received: ${ex.message}")
        None
    }
  }
}

