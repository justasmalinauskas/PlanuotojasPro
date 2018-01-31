package com.justas.planuotojaspro.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;


import static com.justas.planuotojaspro.global.GlobalMethods.*;
import static com.justas.planuotojaspro.global.GlobalVariables.locale;
import static com.justas.planuotojaspro.global.Settings.getSettingsFromFile;
import static com.justas.planuotojaspro.global.Settings.language;
import static com.justas.planuotojaspro.global.TasksList.StopTask;

import com.justas.planuotojaspro.code.*;

public class MainWindow {
    private static JFrame frame;
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
        // default locale stuff

        getSettingsFromFile();
        Locale.setDefault(new Locale(language));


        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (menuButton.getText() == null) {
                    showMenuText();
                } else {
                    hideMenuText();
                }
            }
        });
        currenttasks.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                changeVisableWindow("currenttasks");

            }
        });
        tasks.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                changeVisableWindow("tasks");

            }
        });
        statistics.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                changeVisableWindow("statistics");

            }
        });
        settings.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                changeVisableWindow("settings");

            }
        });
        about.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                changeVisableWindow("about");

            }
        });
        /*frame.addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                JFrame frame = (JFrame)e.getSource();

                StopTask();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.dispose();
            }
        });*/
    }

    private void showMenuText() {

        menuButton.setText(getTranslation("t_menu"));
        settings.setText(getTranslation("t_settings"));
        currenttasks.setText(getTranslation("t_currenttasks"));
        about.setText(getTranslation("t_help"));
        statistics.setText(getTranslation("t_statistics"));
        tasks.setText(getTranslation("t_newtask"));
    }

    private void hideMenuText() {
        for (Component component : menu.getComponents()) {
            if (component instanceof JButton) {

                ((JButton) component).setText(null);

            }
        }
    }

    private void changeVisableWindow(String window) {
        CardLayout cl = (CardLayout) (content.getLayout());
        cl.show(content, window);
    }

    public void start() {
        SwingUtilities.invokeLater(new Thread(this::guiStart));
    }

    public void close() {
        frame.dispose();
    }


    private void guiStart() {
        frame = new JFrame("PlanuotojasPro");
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(mainPanel);
        frame.setResizable(true);
        frame.setVisible(true);
        DatabaseActions.createDB();
        initialMenuSettings();
        setIcons();
        setCardPanelItems();
        changeVisableWindow("currenttasks");
    }

    private void initialMenuSettings() {
        for (Component component : menu.getComponents()) {
            if (component instanceof JButton) {

                ((JButton) component).setOpaque(false);
                ((JButton) component).setContentAreaFilled(false);
                ((JButton) component).setBorderPainted(false);

            }
        }
        menu.setBorder(BorderFactory.createLineBorder(Color.black));
        menuButton.setText(null);
    }

    private void setCardPanelItems() {
        content.add(new CurrentTasksWindow().returnPanel(), "currenttasks");
        content.add(new TasksWindow().returnPanel(), "tasks");
        content.add(new StatisticsWindow().returnPanel(), "statistics");
        content.add(new SettingsWindow().returnPanel(), "settings");
        content.add(new HelpWindow().returnPanel(), "about");
    }

    private void setIcons() {
        //frame.setIconImage(ImageIO.read(new File(getClass().getClassLoader().getResource("src/main/resources/images/mainicon.png").getFile())).getScaledInstance(45, -1, Image.SCALE_SMOOTH));
        //frame.setIconImage(ImageIO.read(new File(MainWindow.class.getResource("/images/mainicon.png").getPath())).getScaledInstance(45, -1, Image.SCALE_SMOOTH));
        frame.setIconImage(getResImage("mainicon"));
        menuButton.setIcon(new ImageIcon(getResImage("mainicon")));
        currenttasks.setIcon(new ImageIcon(getResImage("currenttasks")));
        tasks.setIcon(new ImageIcon(getResImage("tasks")));
        statistics.setIcon(new ImageIcon(getResImage("statistics")));
        settings.setIcon(new ImageIcon(getResImage("settings")));
        about.setIcon(new ImageIcon(getResImage("about")));
    }


    private void createUIComponents() {
        content = new JPanel(new CardLayout());
        // TODO: place custom component creation code here
    }


}
