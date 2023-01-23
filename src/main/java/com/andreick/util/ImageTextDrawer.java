package com.andreick.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.AttributedString;

public class ImageTextDrawer {

    private static BufferedImage toBufferedImage(InputStream inputStream) {
        BufferedImage inputImage;
        try {
            inputImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inputImage;
    }

    public RenderedImage drawText(InputStream inputStream, String text) {

        BufferedImage inputImage = toBufferedImage(inputStream);

        // Create new image
        int outputImageHeightOffset = (int) Math.ceil(inputImage.getHeight() * 0.1);
        int outputImageHeight = inputImage.getHeight() + outputImageHeightOffset;
        var outputImage = new BufferedImage(inputImage.getWidth(), outputImageHeight, BufferedImage.TYPE_INT_RGB);

        // Draw input image
        Graphics2D graphics = outputImage.createGraphics();
        graphics.drawImage(inputImage, 0, 0, null);

        // Create font
        int fontSize = (int) Math.ceil(outputImageHeightOffset * 0.5);
        var font = new Font(Font.SANS_SERIF, Font.BOLD, fontSize);

        // Set font text and color
        var attRating = new AttributedString(text);
        attRating.addAttribute(TextAttribute.FONT, font);
        attRating.addAttribute(TextAttribute.FOREGROUND, Color.ORANGE);

        // Calculate font position
        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int posX = (outputImage.getWidth() - fontMetrics.stringWidth(text)) / 2;
        int posY = (outputImageHeightOffset - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent() + inputImage.getHeight();

        // Draw text
        graphics.drawString(attRating.getIterator(), posX, posY);
        graphics.dispose();

        return outputImage;
    }
}
