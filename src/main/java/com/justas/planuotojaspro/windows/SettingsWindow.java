package com.justas.planuotojaspro.windows;

import com.justas.planuotojaspro.code.ComboBoxRenderer;
import com.justas.planuotojaspro.global.Language;
import com.justas.planuotojaspro.global.UserMessages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import static com.justas.planuotojaspro.Main.restartApplication;
import static com.justas.planuotojaspro.global.GlobalMethods.getTranslation;
import static com.justas.planuotojaspro.global.Settings.language;
import static com.justas.planuotojaspro.global.Settings.setLanguage;
import static com.justas.planuotojaspro.global.TasksList.StopTask;

public class SettingsWindow {
    private JPanel panel;
    private JComboBox langsetting;
    private int chl;

    public JPanel returnPanel() {
        return this.panel;
    }


    public SettingsWindow() {
    }

    private JComboBox createComboBox(List<Language> languages) {
        final JComboBox<? extends Object> comboBox = new JComboBox<>(languages.toArray());
        comboBox.setRenderer(new ComboBoxRenderer());
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Language language = (Language) comboBox.getSelectedItem();
                    assert language != null;
                    setLanguage(language.getLang());
                    if (chl > 0) {
                        new UserMessages().infoMessage(getTranslation("t_restartmessage"), getTranslation("t_restartmessage"));
                        StopTask();
                        restartApplication();
                    }
                }
            }
        });
        return comboBox;
    }

    public void restart() {
        StringBuilder cmd = new StringBuilder();
        cmd.append(System.getProperty("java.home")).append(File.separator).append("bin").append(File.separator).append("java ");
        for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            cmd.append(jvmArg).append(" ");
        }
        cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
        cmd.append(Window.class.getName()).append(" ");

        try {
            Runtime.getRuntime().exec(cmd.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void createUIComponents() {
        List<Language> languages = new ArrayList<>();
        languages.add(new Language("Lietuvių", "lt"));
        languages.add(new Language("English", "en"));
        langsetting = createComboBox(languages);
        for (Language lang : languages) {
            if (lang.getLang().equals(language)) {
                System.out.println("Setting language" + lang.getLanguage());
                langsetting.setSelectedIndex(languages.indexOf(lang));
                chl++;
            }

        }



    }
}
