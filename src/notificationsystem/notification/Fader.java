package notificationsystem.notification;

import com.sun.awt.AWTUtilities;
import java.util.TimerTask;
import javax.swing.JDialog;

/**
 *
 * @author Robert Pyke
 */
public class Fader extends TimerTask {

    private JDialog jDialog;

    public Fader(JDialog jDialog) {
        this.jDialog = jDialog;
    }
    
    // As Fader extends from Timer, it's the run() method which does the main job
    @Override
    public void run() {
        // The opacity is reduced by 0.01f per step
        // If this value equals 0 (invisible), then dispose the JDialog
        if (AWTUtilities.getWindowOpacity(jDialog) > 0.01f) {
            AWTUtilities.setWindowOpacity(jDialog, AWTUtilities.getWindowOpacity(jDialog) - 0.01f);
        } else {
            jDialog.dispose();
        }
    }
}
