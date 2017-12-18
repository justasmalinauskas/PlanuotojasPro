package com.justas.planuotojaspro.windows;

import javax.swing.*;

public class SettingsWindow {
    private JPanel panel;
    private JComboBox langsetting;

    public JPanel returnPanel() {
        return this.panel;
    }


    public void SettingsWindow() {
        setLangSetting();
    }

    private void setLangSetting() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                langsetting.addItem("LT");
                langsetting.addItem("EN");
                Object cmboitem = langsetting.getSelectedItem();
            }
        });
    }
}
