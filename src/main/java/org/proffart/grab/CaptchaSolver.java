package org.proffart.grab;

import j.antigate.CapchaBypass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CaptchaSolver {

    public String solver(final BufferedImage image) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return CapchaBypass.CapchaAnswer(is, Constants.ANTI_CAPTCHA_KEY, null, null, null);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
