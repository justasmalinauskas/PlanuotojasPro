package com.justas.planuotojaspro;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class MainWindow {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel menu;
    private JPanel content;
    private JButton menuButton;
    private JButton settings;
    private JButton currenttasks;
    private JButton statistics;
    private JButton tasks;
    private JButton about;

    public MainWindow() {
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (menuButton.getText() == null) {
                    menuButton.setText(ResourceBundle.getBundle("com.justas.planuotojaspro.resources.lang.Resources").getString("menu"));
                    settings.setText(ResourceBundle.getBundle("com.justas.planuotojaspro.resources.lang.Resources").getString("settings"));
                    currenttasks.setText(ResourceBundle.getBundle("com.justas.planuotojaspro.resources.lang.Resources").getString("currenttasks"));
                    about.setText(ResourceBundle.getBundle("com.justas.planuotojaspro.resources.lang.Resources").getString("about"));
                    statistics.setText(ResourceBundle.getBundle("com.justas.planuotojaspro.resources.lang.Resources").getString("statistics"));
                    tasks.setText(ResourceBundle.getBundle("com.justas.planuotojaspro.resources.lang.Resources").getString("tasks"));
                }
                else {
                    for (Component component : menu.getComponents()) {
                        if (component instanceof JButton) {

                            ((JButton) component).setText(null);

                        }
                    }
                }
            }
        });
        about.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                AboutWindow aboutWindow = new AboutWindow();
                content = aboutWindow.getPanel();

            }
        });
    }

    public void start() {
        SwingUtilities.invokeLater(new Thread(() -> {
            try {
                guiStart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    public void guiStart() throws IOException {
        frame = new JFrame("PlanuotojasPro");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setResizable(true);
        frame.setVisible(true);
        for (Component component : menu.getComponents()) {
            if (component instanceof JButton) {

                ((JButton) component).setOpaque(false);
                ((JButton) component).setContentAreaFilled(false);
                ((JButton) component).setBorderPainted(false);

            }
        }

        float dash1[] = {10.0f};
        Border blackline = BorderFactory.createLineBorder(Color.black);
        menu.setBorder(blackline);
        frame.setIconImage(ImageIO.read(new File(getClass().getClassLoader().getResource("com/justas/planuotojaspro/resources/images/mainicon.png").getFile())).getScaledInstance(45, -1, Image.SCALE_SMOOTH));
        menuButton.setText(null);
        menuButton.setIcon(new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("com/justas/planuotojaspro/resources/images/mainicon.png").getFile())).getScaledInstance(45, -1, Image.SCALE_SMOOTH)));
        currenttasks.setIcon(new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("com/justas/planuotojaspro/resources/images/currenttasks.png").getFile())).getScaledInstance(45, -1, Image.SCALE_SMOOTH)));
        tasks.setIcon(new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("com/justas/planuotojaspro/resources/images/tasks.png").getFile())).getScaledInstance(45, -1, Image.SCALE_SMOOTH)));
        statistics.setIcon(new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("com/justas/planuotojaspro/resources/images/statistics.png").getFile())).getScaledInstance(45, -1, Image.SCALE_SMOOTH)));
        settings.setIcon(new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("com/justas/planuotojaspro/resources/images/settings.png").getFile())).getScaledInstance(45, -1, Image.SCALE_SMOOTH)));
        about.setIcon(new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("com/justas/planuotojaspro/resources/images/about.png").getFile())).getScaledInstance(45, -1, Image.SCALE_SMOOTH)));

    }
}
