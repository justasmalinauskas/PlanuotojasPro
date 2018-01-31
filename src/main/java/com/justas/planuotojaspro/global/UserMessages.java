package com.justas.planuotojaspro.global;

import javax.swing.JOptionPane;

public class UserMessages {

    public void infoMessage(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public void errorMessage(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.ERROR_MESSAGE);
    }

    public boolean confirmMessage(String infoMessage, String titleBar) {
        int confirm = JOptionPane.showConfirmDialog(null, infoMessage, titleBar, JOptionPane.YES_NO_OPTION);
        return confirm == JOptionPane.YES_OPTION;
    }
}
