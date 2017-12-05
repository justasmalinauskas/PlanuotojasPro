package com.justas.planuotojaspro.global;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static com.justas.planuotojaspro.global.GlobalVariables.fallbackIcon;


public class GlobalMethods {
    private static Image getFallbackImage() {
        Image image = null;
        try {
            String base64Image = fallbackIcon.split(",")[1];
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
            image = ImageIO.read(new ByteArrayInputStream(imageBytes)).getScaledInstance(45, -1, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.getStackTrace();
        }
        return image;
    }

    public static Image getResImage(String imgname) {
        Image image;
        try {
            image = ImageIO.read(new File(GlobalMethods.class.getResource("/images/" + imgname + ".png").getPath())).getScaledInstance(45, -1, Image.SCALE_SMOOTH);
        } catch (IOException | NullPointerException e) {
            image = getFallbackImage();
        }
        return image;
    }
}
