package com.justas.planuotojaspro.global;

import com.justas.planuotojaspro.code.UTF8Control;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.justas.planuotojaspro.global.GlobalVariables.*;
import static com.justas.planuotojaspro.global.Settings.language;


public class GlobalMethods {

    private static Preferences prefs;

    public GlobalMethods() {
        prefs = Preferences.userNodeForPackage(GlobalMethods.class);
    }
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

    public static String getTranslation(String transname) {
        return ResourceBundle.getBundle("lang/Resources", new Locale(language), new UTF8Control()).getString(transname);
    }

    public static Image getResImage(String imgname) {
        Image image;
        try {
            image = ImageIO.read(new File(GlobalMethods.class.getResource("/images/" + imgname + ".png")
                    .getPath())).getScaledInstance(45, -1, Image.SCALE_SMOOTH);
        } catch (IOException | NullPointerException e) {
            image = getFallbackImage();
        }
        return image;
    }

    public static void saveSettings(String setName, String setValue) {
        prefs.put(setName, setValue);
    }

    public static Object getSettings(String setName) {
        return prefs.get(setName, "");
    }

}
