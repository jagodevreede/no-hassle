package io.github.jagodevreede.demo.nohassle.text;

import java.util.UUID;

public record MemeText(
        UUID id,
        String upperText,
        String lowerText,
        String imageId
) {
}
