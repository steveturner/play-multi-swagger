package controllers.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.core.util.JsonUtil;
import com.wordnik.swagger.model.ResourceListing;
import play.Logger;

import play.mvc.*;
import util.RestResourceUtil;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


public class JavaBaseApiController extends Controller {
    static final JavaRestResourceUtil ru = new JavaRestResourceUtil();
    protected static final ObjectMapper mapper = JsonUtil.mapper();

    private static final Map<Class,JAXBContext> jaxbContexts = new HashMap<Class, JAXBContext>();



    protected static Result JsonResponse(Object obj) {
        return JsonResponse(obj, Http.Status.OK);
    }

    //Todo properly handle response code.
    protected static Result JsonResponse(Object obj, int code) {
        StringWriter w = new StringWriter();

        response().setContentType(MediaType.APPLICATION_JSON);
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response().setHeader("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization");

        try {
            mapper.writeValue(w, obj);
        } catch (Exception e) {

            Logger.error(e.toString());
            return internalServerError(e.getMessage());
        }


        return returnWithCorrectStatus(w.toString(),code);
    }


    protected static Result XmlResponse(Object obj) {
        return XmlResponse(obj, Http.Status.OK);
    }

    //TODO properly handle response code.
    protected static Result XmlResponse(Object obj, int code) {
        StringWriter w = new StringWriter();

        JAXBContext jaxbContext;
        synchronized (jaxbContexts)
        {
            jaxbContext = jaxbContexts.get(obj.getClass());
                    if(jaxbContext == null)
                    {
                        try {
                            jaxbContext = JAXBContext.newInstance(obj.getClass());
                            jaxbContexts.put(obj.getClass(),jaxbContext);
                        } catch (JAXBException e) {
                            Logger.error(e.toString());
                            return internalServerError(e.getMessage());
                        }
                    }
        }

        response().setContentType(MediaType.APPLICATION_XML);
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response().setHeader("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization");


        try {
            jaxbContext.createMarshaller().marshal(obj, w);
        } catch (JAXBException e) {
            Logger.error(e.toString());
            return internalServerError(e.getMessage());
        }

        return returnWithCorrectStatus(w.toString(),code);
    }

    protected static Result returnValue(Object obj, int code) {
        if (request().accepts(MediaType.APPLICATION_JSON)) {
            return JsonResponse(obj, code);
        } else if (request().accepts(MediaType.APPLICATION_XML) || request().accepts(MediaType.TEXT_XML)) {
            return XmlResponse(obj, code);
        } else {
            return badRequest("Invalid mime/accepts type");
        }
    }

    protected static Result returnValue(Object obj) {
        return returnValue(obj, Http.Status.OK);
    }

    private static Result returnWithCorrectStatus( String xmlOrJson, int code)
    {
        switch (code) {

            case Http.Status.OK: ok(xmlOrJson);
            case Http.Status.CREATED: created(xmlOrJson);
            case Http.Status.BAD_REQUEST: return badRequest(xmlOrJson);
            case Http.Status.UNAUTHORIZED: return unauthorized(xmlOrJson);
            case Http.Status.FORBIDDEN: forbidden(xmlOrJson);
            case Http.Status.NOT_FOUND: notFound(xmlOrJson);
            case Http.Status.INTERNAL_SERVER_ERROR: internalServerError(xmlOrJson);
            default: return internalServerError("Invalid response code.");
        }
    }
}
