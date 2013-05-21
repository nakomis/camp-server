package io.brooklyn.camp.rest.resource;

import io.brooklyn.camp.dto.PlatformComponentTemplateDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/camp/v11/platform-component-templates")
@Produces("application/json")
public class PlatformComponentTemplateRestResource extends AbstractCampRestResource {

    @Path("/{id}")
    @GET
    public PlatformComponentTemplateDto get(
//            @ApiParam(value = "Application ID or name", required = true)
            @PathParam("id") String id) {
        return dto().adapt(lookup(camp().platformComponentTemplates(), id));
    }

}
