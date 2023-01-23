package com.andreick.util.image;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageFileHandler {

    public static void saveImage(RenderedImage image, String fileName) {
        try {
            var imagesPath = Paths.get("images");

            if (!Files.exists(imagesPath)) {
                Files.createDirectory(imagesPath);
            }

            String imageFileName = removeInvalidFileNameCharacters(fileName) + ".png";
            ImageIO.write(image, "png", Files.newOutputStream(imagesPath.resolve(imageFileName)));
            System.out.println(imageFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String removeInvalidFileNameCharacters(String fileName) {
        String valid = fileName.trim();
        return valid.replaceAll("[<>:\"/\\\\|?*]", "");
    }
}
