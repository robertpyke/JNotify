package notificationsystem.notification;

import com.sun.awt.AWTUtilities;
import java.util.TimerTask;
import javax.swing.JWindow;

/**
 *
 * @author Robert Pyke
 */
public class Fader extends TimerTask {

    private JWindow jWindow;

    public Fader(JWindow jWindow) {
        this.jWindow = jWindow;
    }
    
    // As Fader extends from Timer, it's the run() method which does the main job
    @Override
    public void run() {
        // The opacity is reduced by 0.01f per step
        // If this value equals 0 (invisible), then dispose the JDialog
        if (AWTUtilities.getWindowOpacity(jWindow) > 0.005f) {
            AWTUtilities.setWindowOpacity(jWindow, AWTUtilities.getWindowOpacity(jWindow) - 0.005f);
        } else {
            jWindow.dispose();
        }
    }
}
