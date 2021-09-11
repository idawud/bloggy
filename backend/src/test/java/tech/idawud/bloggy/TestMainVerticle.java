package tech.idawud.bloggy;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

	private static final String LOCALHOST = "localhost";
	private static final int HTTP_PORT = Integer.parseInt(System.getenv().getOrDefault("HTTP_PORT", "8888"));

	@BeforeEach
	void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
		vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
	}

	@Test
	void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
		testContext.completeNow();
	}

	@Test
	void http_server_response_check(Vertx vertx, VertxTestContext testContext) {
		WebClient webClient = WebClient.create(vertx);
		final String expectedBody = "Hello from Vert.x!";
		final String expectedContentTypeHeader = "text/plain";

		webClient.get(HTTP_PORT, LOCALHOST, "/").as(BodyCodec.string()).send(testContext.succeeding(response -> {
			testContext.verify(() -> {
				assertEquals(expectedBody, response.body());
				assertNotNull(response.getHeader("content-type"));
				assertEquals(expectedContentTypeHeader, response.getHeader("content-type"));
				testContext.completeNow();
			});
		}));
	}

}
