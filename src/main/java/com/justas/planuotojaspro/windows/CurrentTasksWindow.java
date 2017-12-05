package com.justas.planuotojaspro.windows;

import javax.swing.*;

public class CurrentTasksWindow {

    private JPanel panel;
    private JTextField searchbar;
    private JLabel datesjobs;
    private JLabel datepopup;

    public CurrentTasksWindow() {
        datesjobs.setText("Šiandienos darbai");
        datepopup.setText("Keisti datą?");
    }

    public JPanel returnPanel() {
        return this.panel;
    }
}
