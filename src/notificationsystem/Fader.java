/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notificationsystem;

import com.sun.awt.AWTUtilities;
import java.util.TimerTask;
import javax.swing.JDialog;

/**
 *
 * @author robertpyke
 */
public class Fader extends TimerTask{

    private JDialog jDialog;

    public Fader(JDialog jDialog) {
        this.jDialog = jDialog;
    }
    
    //As Fader extends from Timer, it's the run() method which does the main job
    public void run() {
        //The opacity is reduced by 0,01f steps
        //Of course is this value equals 0 (invisible), then we close the JDialog with dispose()
        if (AWTUtilities.getWindowOpacity(jDialog) > 0.01f) {
            AWTUtilities.setWindowOpacity(jDialog, AWTUtilities.getWindowOpacity(jDialog) - 0.01f);
        } else {
            jDialog.dispose();
        }
    }
}
