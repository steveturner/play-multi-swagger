package controllers;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponses;
import controllers.common.JavaBaseApiController;
import controllers.common.ScalaBaseApiController;
import com.wordnik.swagger.annotations.*;
import play.api.mvc.Rendering;
import play.mvc.Http;
import play.mvc.Result;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Api(value = "/server", description = "Operations about the server")
public class Application extends JavaBaseApiController {

    @GET
    @Path("/server/firstMethod")
    @ApiOperation(value = "Return woot hopefully!", response = Boolean.class, httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = Http.Status.BAD_REQUEST, message = "Invalid status value")})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public static Result firstMethod(
            @ApiParam(value = "Status value for true or false", required = true, defaultValue = "false", allowableValues = "true,false") @PathParam("status") String status) {
        if (status.equals("true")) {
            if (request().accepts(MediaType.TEXT_HTML)) {
                return ok(views.html.firstMethod.render(new value.ApiResponse(Http.Status.OK, "SUCCESS!")));
            } else {
                return returnValue(new value.ApiResponse(Http.Status.OK, "SUCCESS!"));
            }
        } else {
            if (request().accepts(MediaType.TEXT_HTML)) {
                    return badRequest(views.html.firstMethod.render(new value.ApiResponse(Http.Status.BAD_REQUEST, "error")));
            } else {

                return returnValue(new value.ApiResponse(Http.Status.BAD_REQUEST, "error"));
            }
        }
    }
}
