package com.justas.planuotojaspro.code;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;


/*
*   Kuriant šią funkciją buvo pasiremta šiais pavyzdžiais:
*   https://stackoverflow.com/questions/8515601/java-swing-rounded-border-for-jtextfield
*   https://stackoverflow.com/questions/16213836/java-swing-jtextfield-set-placeholder
 */
public class JRoundedTextField extends JTextField {
    private Shape shape;
    private String placeHolder;

    public JRoundedTextField(int size) {
        super(size);
        setOpaque(false);
    }

    public JRoundedTextField(int size, String placeholder) {
        super(size);
        placeHolder = placeholder;
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        super.paintComponent(g);
        if (placeHolder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D gD = (Graphics2D) g;
        gD.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        gD.setColor(getDisabledTextColor());
        gD.drawString(placeHolder, getInsets().right, g.getFontMetrics()
                .getMaxAscent() + getInsets().top);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }
        return shape.contains(x, y);
    }
}
