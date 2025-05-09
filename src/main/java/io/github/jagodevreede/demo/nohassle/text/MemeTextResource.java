package io.github.jagodevreede.demo.nohassle.text;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.jboss.logging.BasicLogger;

import java.util.List;
import java.util.UUID;

import static io.quarkus.arc.ComponentsProvider.LOG;

@Path("/memes")
@Produces(MediaType.TEXT_HTML)
public class MemeTextResource {

    @Inject
    MemeTextRepository memeTextRepository;

    @GET
    @Path("")
    public TemplateInstance listAll() {
        List<MemeText> memes = memeTextRepository.findAll();
        return Templates.table(memes);
    }

    @GET
    @Path("/{id}")
    public TemplateInstance detail(@PathParam("id") UUID id) {
        MemeText meme = memeTextRepository.findById(id);
        if (meme == null) {
            // Optional: create a placeholder meme or redirect/return error view
            meme = new MemeText(id, "Not found", "No details", "unknown");
        }
        return Templates.detail(meme);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createMeme(@FormParam("upperText") String upperText,
                               @FormParam("lowerText") String lowerText,
                               @FormParam("imageId") String imageId) {
        MemeText meme = new MemeText(UUID.randomUUID(), upperText, lowerText, imageId);
        memeTextRepository.save(meme);
        final BasicLogger logger;
        LOG.info("Meme created: " + meme);
        // Redirect to the main page with a success message in the query params
        return Response.seeOther(UriBuilder.fromPath("index.html").queryParam("success", "true").build()).build();
    }

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance table(List<MemeText> memes);

        public static native TemplateInstance detail(MemeText meme);
    }
}
