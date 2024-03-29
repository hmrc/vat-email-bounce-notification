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

package helpers

import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, put, stubFor}
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import config.AppConfig
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.JsValue
import play.api.libs.ws.{WSClient, WSResponse}
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import play.api.{Application, Environment, Mode}
import uk.gov.hmrc.http.{HeaderCarrier, HttpClient}

import scala.concurrent.ExecutionContext

trait IntegrationBaseSpec extends AnyWordSpecLike with Matchers with GuiceOneServerPerSuite with WireMockHelper with
  BeforeAndAfterEach with BeforeAndAfterAll {

  val mockHost: String = WireMockHelper.host
  val mockPort: String = WireMockHelper.wireMockPort.toString
  val mockUrl = s"http://$mockHost:$mockPort"
  val appRouteContext: String = "/vat-email-bounce-notification"
  lazy val client: WSClient = app.injector.instanceOf[WSClient]
  val httpClient: HttpClient = app.injector.instanceOf[HttpClient]
  val appConfig: AppConfig = app.injector.instanceOf[AppConfig]

  implicit val hc: HeaderCarrier = HeaderCarrier()
  implicit val ec: ExecutionContext = app.injector.instanceOf[ExecutionContext]

  def servicesConfig: Map[String, String] = Map(
    "microservice.services.auth.host" -> mockHost,
    "microservice.services.auth.port" -> mockPort,
    "microservice.services.eis.url" -> mockUrl
  )

  def stubPutRequest(url: String, returnStatus: Int, returnBody: String): StubMapping =
    stubFor(put(url).willReturn(
      aResponse()
        .withStatus(returnStatus)
        .withBody(returnBody)
    ))

  override implicit lazy val app: Application = new GuiceApplicationBuilder()
    .in(Environment.simple(mode = Mode.Dev))
    .configure(servicesConfig)
    .build()

  override def beforeAll(): Unit = {
    super.beforeAll()
    startWireMock()
  }

  override def afterAll(): Unit = {
    stopWireMock()
    super.afterAll()
  }

  def post(path: String)(body: JsValue): WSResponse = await(
    client.url(s"http://localhost:$port$appRouteContext$path")
      .withHttpHeaders("Content-Type" -> "application/json")
      .post(body)
  )




}
