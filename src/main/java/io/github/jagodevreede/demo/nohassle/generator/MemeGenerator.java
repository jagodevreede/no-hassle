package io.github.jagodevreede.demo.nohassle.generator;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;

public class MemeGenerator {

    /**
     * Generates a meme image with top and bottom text.
     *
     * @param image the original image
     * @param upperText the text to be drawn at the top
     * @param lowerText the text to be drawn at the bottom
     * @return a new BufferedImage containing the meme
     */
    public static BufferedImage generateMeme(BufferedImage image, String upperText, String lowerText) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        // Create a copy of the image with an alpha channel.
        BufferedImage memeImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = memeImage.createGraphics();

        // Draw the original image onto the copy.
        g2.drawImage(image, 0, 0, null);

        // Enable anti-aliasing for text and shapes.
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define left/right margin in pixels.
        int margin = 20;
        int maxTextWidth = imageWidth - 2 * margin;
        int centerX = imageWidth / 2;

        // Draw top text.
        drawOutlinedText(g2, upperText, centerX, imageHeight, margin, maxTextWidth, true);
        // Draw bottom text.
        drawOutlinedText(g2, lowerText, centerX, imageHeight, margin, maxTextWidth, false);

        g2.dispose();
        return memeImage;
    }

    /**
     * Draws outlined text. The text is centered horizontally.
     *
     * @param g2 the Graphics2D context
     * @param text the text to draw
     * @param centerX the horizontal center of the image
     * @param imageHeight the image height
     * @param margin the margin from the top or bottom edge
     * @param maxTextWidth maximum width available for the text
     * @param isTop if true, the text is drawn at the top; otherwise at the bottom
     */
    private static void drawOutlinedText(Graphics2D g2, String text, int centerX, int imageHeight, int margin, int maxTextWidth, boolean isTop) {
        // Determine the scalar font that best fits the maxTextWidth.
        Font font = getFittingFont(g2, text, maxTextWidth);
        g2.setFont(font);
        FontMetrics metrics = g2.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);

        int x = centerX - textWidth / 2;
        int yPos;
        if (isTop) {
            // Position the top text so that the ascent is below the top margin.
            yPos = margin + metrics.getAscent();
        } else {
            // Position the bottom text so that the descent is above the bottom margin.
            yPos = imageHeight - margin - metrics.getDescent();
        }

        // Create a GlyphVector to get the text outline.
        GlyphVector gv = font.createGlyphVector(g2.getFontRenderContext(), text);
        Shape textShape = gv.getOutline(x, yPos);

        // Draw outline in black.
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLACK);
        g2.draw(textShape);

        // Fill the text in white.
        g2.setColor(Color.WHITE);
        g2.fill(textShape);
    }

    /**
     * Returns a font that will make the given text fit within the maxWidth.
     *
     * @param g2 the Graphics2D context
     * @param text the text to be measured
     * @param maxWidth the maximum allowed width
     * @return a Font instance using the Impact typeface in bold
     */
    private static Font getFittingFont(Graphics2D g2, String text, int maxWidth) {
        // The starting font size is set high.
        int fontSize = 100;
        Font font = new Font("Impact", Font.BOLD, fontSize);
        FontMetrics metrics = g2.getFontMetrics(font);

        // Reduce the font size until the text fits or a minimum font size is reached.
        while (metrics.stringWidth(text) > maxWidth && fontSize > 10) {
            fontSize--;
            font = new Font("Impact", Font.BOLD, fontSize);
            metrics = g2.getFontMetrics(font);
        }
        return font;
    }
}