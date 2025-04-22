package io.github.jagodevreede.demo.nohassle.image;

import java.awt.image.BufferedImage;

public record MemeImage(
        String id,
        BufferedImage imageData
) {
}