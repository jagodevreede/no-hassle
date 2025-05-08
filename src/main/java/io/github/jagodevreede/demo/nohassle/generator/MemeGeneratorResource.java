package io.github.jagodevreede.demo.nohassle.generator;

import io.github.jagodevreede.demo.nohassle.image.MemeImageRepository;
import io.github.jagodevreede.demo.nohassle.text.MemeTextRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Path("/image")
public class MemeGeneratorResource {

    @Inject
    MemeImageRepository memeImageRepository;

    @Inject
    MemeTextRepository memeTextRepository;

    @GET
    @Path("/{id}")
    @Produces("image/png")
    public Response getMemeImage(@PathParam("id") UUID id) {
        var memeText = memeTextRepository.findById(id);
        if (memeText == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        var memeImageEntry = memeImageRepository.findById(memeText.imageId());
        BufferedImage memeImage = MemeGenerator.generateMeme(
                memeImageEntry.imageData(),
                memeText.upperText(),
                memeText.lowerText()
        );

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(memeImage, "PNG", os);
            byte[] imageData = os.toByteArray();
            return Response.ok(imageData).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}