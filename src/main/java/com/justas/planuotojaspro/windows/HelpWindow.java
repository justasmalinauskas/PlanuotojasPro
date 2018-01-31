package com.justas.planuotojaspro.windows;

import sun.misc.Launcher;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HelpWindow {
    private JPanel panel;
    private JEditorPane help;

    HelpWindow() {
        help.setEditable(false);
        help.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    JEditorPane pane = (JEditorPane) e.getSource();
                    if (e instanceof HTMLFrameHyperlinkEvent) {
                        HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
                        HTMLDocument doc = (HTMLDocument)pane.getDocument();
                        doc.processHTMLFrameHyperlinkEvent(evt);
                    } else {
                        try {
                            pane.setPage(e.getURL());
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                }
            }
        });
        File file = new File("Pagalba.htm");
        URL helpURL = null;
        try {
            helpURL = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (helpURL != null) {
            try {
                help.setPage(helpURL);
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + helpURL);
            }
        } else {
            System.err.println("Couldn't find file: Pagalba.htm");
        }

        JScrollPane editorScrollPane = new JScrollPane(help);

        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JEditorPane pane = (JEditorPane) e.getSource();
            if (e instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
                HTMLDocument doc = (HTMLDocument)pane.getDocument();
                doc.processHTMLFrameHyperlinkEvent(evt);
            } else {
                try {
                    pane.setPage(e.getURL());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    public JPanel returnPanel() {
        return panel;
    }
}
