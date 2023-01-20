package com.andreick;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.AttributedString;

public class MovieRatingDrawer {

    public void saveImage(InputStream inputStream, String title, String rating) throws IOException {

        BufferedImage inputImage = ImageIO.read(inputStream);

        int outputImageHeightOffset = (int) Math.ceil(inputImage.getHeight() * 0.1);
        int outputImageHeight = inputImage.getHeight() + outputImageHeightOffset;
        var outputImage = new BufferedImage(inputImage.getWidth(), outputImageHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = outputImage.createGraphics();
        graphics.drawImage(inputImage, 0, 0, null);

        String ratingText = "IMDb rating: " + rating;

        int fontSize = (int) Math.ceil(outputImageHeightOffset * 0.5);
        var font = new Font(Font.SANS_SERIF, Font.BOLD, fontSize);

        var attRating = new AttributedString(ratingText);
        attRating.addAttribute(TextAttribute.FONT, font);
        attRating.addAttribute(TextAttribute.FOREGROUND, Color.ORANGE);

        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int posX = (outputImage.getWidth() - fontMetrics.stringWidth(ratingText)) / 2;
        int posY = (outputImageHeightOffset - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent() + inputImage.getHeight();

        graphics.drawString(attRating.getIterator(), posX, posY);
        graphics.dispose();

        var imagesPath = Paths.get("images");

        if (!Files.exists(imagesPath)) {
            Files.createDirectory(imagesPath);
        }

        String imageFileName = removeInvalidFileNameCharacters(title) + ".png";
        ImageIO.write(outputImage, "png", Files.newOutputStream(imagesPath.resolve(imageFileName)));
        System.out.println(imageFileName);
    }

    private String removeInvalidFileNameCharacters(String fileName) {
        String valid = fileName.trim();
        return valid.replaceAll("[<>:\"/\\\\|?*]", "");
    }
}
