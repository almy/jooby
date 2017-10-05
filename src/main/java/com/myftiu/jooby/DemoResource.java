package com.myftiu.jooby;

import org.jooby.Response;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.internal.ResponseImpl;
import org.jooby.mvc.GET;
import org.jooby.mvc.Path;

@Path("/demo")
public class DemoResource {

   @GET
   @Path("/search")
   public Result demoInit() {
      return Results.ok("ok");
   }

}
