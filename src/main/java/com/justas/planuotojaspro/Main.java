package com.justas.planuotojaspro;


import com.justas.planuotojaspro.windows.MainWindow;

public class Main {
    private static MainWindow window;

    public static void main(String[] args) {
        window = new MainWindow();
        window.start();
    }

    public static void restartApplication() {
        window.close();
        main(new String[] {});
    }
}
