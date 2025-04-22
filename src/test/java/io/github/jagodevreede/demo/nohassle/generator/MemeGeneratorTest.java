package io.github.jagodevreede.demo.nohassle.generator;

import io.github.jagodevreede.demo.nohassle.image.MemeImageRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MemeGeneratorTest {

    MemeImageRepository memeImageRepository = new MemeImageRepository();

    MemeGenerator memeGenerator = new MemeGenerator();

    @ParameterizedTest
    @CsvSource({
            "Top text, Bottom text, small-meme",
            "This is a very long text that should be smaller to still fit on a line, Even the bottom text is long and should also be smaller to make it fit, big-meme",
    })
    void generateMeme(String topText, String bottomText, String outputName) throws IOException {
        // Given
        BufferedImage image = memeImageRepository.findById("Bernie").imageData();

        // When
        BufferedImage memeImage = memeGenerator.generateMeme(image, topText, bottomText);

        // Then
        assertNotNull(memeImage);
        // Write to target directory so we can see the result
        ImageIO.write(memeImage, "png", new File("target/" + outputName + ".png"));
    }
}