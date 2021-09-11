package tech.idawud.bloggy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

	private static final int HTTP_PORT = Integer.parseInt(System.getenv().getOrDefault("HTTP_PORT", "8888"));

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		vertx.createHttpServer().requestHandler(req -> {
			req.response().putHeader("content-type", "text/plain").end("Hello from Vert.x!");
		}).listen(HTTP_PORT, http -> {
			if (http.succeeded()) {
				startPromise.complete();
				log.info("HTTP server started on port {}", HTTP_PORT);
			} else {
				startPromise.fail(http.cause());
			}
		});
	}
}
