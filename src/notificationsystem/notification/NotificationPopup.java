/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notificationsystem.notification;

import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import notificationsystem.Fader;

public class NotificationPopup extends JDialog {

    private final LinearGradientPaint lpg;
    
    
    private final static long FADE_OUT_TIME = 10000;
    private final static long FADE_OUT_INCREMENT = 20;
    public final static int HEIGHT = 50;
    public final static int WIDTH = 300;
    private final boolean fade;
    
    public NotificationPopup(String noteText, int x, int y, boolean in_fade) throws IOException {
        final NotificationPopup f = this;
        fade = in_fade;
        
        Container con = this.getContentPane();
        setUndecorated(true);
        setSize(WIDTH, HEIGHT);
        
        con.setLayout(new GridBagLayout());

        // size of the screen
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // height of the task bar
        final Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(
                getGraphicsConfiguration());
        final int taskBarSize = scnMax.bottom;

        
        setLocation(x, y);
        // background paint
        lpg = new LinearGradientPaint(0, 0, 0, getHeight() / 2, new float[]{0f,
                    0.3f, 1f}, new Color[]{new Color(0.8f, 0.8f, 1f),
                    new Color(0.75f, 0.7f, 1f), new Color(0.65f, 0.6f, 1f)});

        // blue background panel
        setContentPane(new BackgroundPanel());


        final Container c = this.getContentPane();
        c.setLayout(new GridBagLayout());

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 15, 5, 5);
        constraints.anchor = GridBagConstraints.BASELINE;
        constraints.fill = GridBagConstraints.BOTH;
        //constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        //constraints.anchor = GridBagConstraints.NORTH;


        final JEditorPane l = new JEditorPane("text/html", noteText);
        l.setEditable(false);
        l.setOpaque(false);
        l.addHyperlinkListener(new HyperlinkListener() {

            @Override
            public void hyperlinkUpdate(HyperlinkEvent he) {
                // If the user clicks the link..
                if (HyperlinkEvent.EventType.ACTIVATED.equals(he.getEventType())) {
                    try {
                        Desktop desktop = Desktop.getDesktop();
                        desktop.browse(URI.create(he.getURL().toString()));
                    } catch (Throwable t) {
                        //If the user default browser is not found, or it fails to be launched, or the default handler application failed to be launched
                        System.out.println("Tried to open a link, but browser was not found.");
                    }
                }
            }
        });

        c.add(l, constraints);

        constraints.gridx++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.BOTH;
        //constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;


        final JButton b = new JButton(new AbstractAction("x") {

            @Override
            public void actionPerformed(final ActionEvent e) {
                f.dispose();
            }
        });

        b.setOpaque(false);
        b.setFocusable(false);

        c.add(b, constraints);
        this.setAlwaysOnTop(true);
        
    }

    @Override
    public void show() {
        AWTUtilities.setWindowOpacity(this, 0.95f);
        
        if (fade) {
            Timer timer = new Timer();

            //And then we launch the fade-out, waiting 500ms before starting
            //Then we gradually fade out every 5ms
            // timer.schedule(new Fader(this), 500, 5);
            timer.schedule(new Fader(this), FADE_OUT_TIME, FADE_OUT_INCREMENT);
        }
    
        super.show();
    }

    private class BackgroundPanel extends JPanel {

        public BackgroundPanel() {
            setOpaque(true);
        }

        @Override
        protected void paintComponent(final Graphics g) {
            final Graphics2D g2d = (Graphics2D) g;
            // background
            g2d.setPaint(lpg);
            g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
            g2d.setColor(Color.BLACK);

            // border
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

}