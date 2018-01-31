package com.justas.planuotojaspro.code;

import com.justas.planuotojaspro.global.Language;

import javax.swing.*;
import java.awt.*;

public class ComboBoxRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);
        Language language = (Language) value;
        label.setText(language.getLanguage());
        return label;
    }
}
