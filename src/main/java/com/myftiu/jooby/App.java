package com.myftiu.jooby;

import org.jooby.Jooby;
import org.jooby.RequestLogger;
import org.jooby.rx.Rx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

/**
 * @author jooby generator
 */
public class App extends Jooby {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  {
    http2();
    securePort(8443);
    lifeCycle(DemoService.class);
    use("*", new RequestLogger().extended().log(e -> System.out.println(e)));
    use(new Rx());
    use(DemoResource.class);

    before("/", (req, resp) -> {
      LOGGER.info("This is before");

    });

    complete("/", (req, resp, cause) -> {
      LOGGER.info("This is complete " + cause.isPresent());
    });

    get("/", (request, response, chain) -> {
      response.send("Hello World! " + request.protocol());
    });

    get("/async", deferred(() -> "aaa"));

    get("/rx", () -> Observable.from("reactive programming in jooby!".split
       ("a")));



  }



  public static void main(final String[] args) {
    run(App::new, args);
  }

}
