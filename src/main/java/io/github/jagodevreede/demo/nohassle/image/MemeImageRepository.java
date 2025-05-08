package io.github.jagodevreede.demo.nohassle.image;

import io.github.jagodevreede.demo.nohassle.text.MemeTextRepository;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is filled in memory for demo purpose only.
 */
@Component
public class MemeImageRepository {
    private static final Map<String, MemeImage> DATABASE = initDB();

    public List<MemeImage> findAll() {
        return new ArrayList<>(DATABASE.values());
    }

    public List<String> findAllIds() {
        return new ArrayList<>(DATABASE.values()).stream().map(MemeImage::id).toList();
    }

    public MemeImage findById(String id) {
        return DATABASE.get(id);
    }

    private static Map<String, MemeImage> initDB() {
        List<MemeImage> db = new ArrayList<>();
        db.add(createMemeImage("Bernie.png"));
        db.add(createMemeImage("Disaster-Girl.jpg"));
        db.add(createMemeImage("Waiting-Skeleton.jpg"));
        db.add(createMemeImage("X-X-Everywhere.jpg"));
        return db.stream().collect(Collectors.toMap(MemeImage::id, Function.identity()));
    }

    private static MemeImage createMemeImage(String imageName) {
        BufferedImage imageData = loadImage(imageName);
        String id = imageName.substring(0, imageName.lastIndexOf('.'));
        return new MemeImage(id, imageData);
    }

    private static BufferedImage loadImage(String imageName) {
        try (var inputStream = MemeTextRepository.class.getResourceAsStream("/images/" + imageName)) {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + imageName, e);
        }
    }
}
