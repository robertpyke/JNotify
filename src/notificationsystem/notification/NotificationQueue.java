/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notificationsystem.notification;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author robertpyke
 */
public class NotificationQueue extends LinkedList<NotificationPopup> {

    private static final int NOTIFICATION_MARGIN_PIXELS_TOP_BOTTOM = 5;
    private static final int NOTIFICATION_MARGIN_PIXELS_LEFT_RIGHT = 2;

    public NotificationQueue() {
    }

    public void addNotification(String notificationHTML) throws IOException {
        clearInvisible();
        Integer lastX = null;
        Integer lastY = null;
        if (size() > 0) {
            NotificationPopup lastNotification = getLast();
            lastX = lastNotification.getX();
            lastY = lastNotification.getY();
        }

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration dc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        // size of the screen
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // height of the task bar
        final Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(dc);
        final int screenBottomEdge = scnMax.bottom;
        final int screenTopEdge = scnMax.top;
        final int screenLeftEdge = scnMax.left;
        final int screenRightEdge = scnMax.right;

        int x = 0;
        int y = 0;

        // The maxX is the most right side of the screen, taking into consideration the notification margin.
        int maxX = (screenSize.width - NotificationPopup.WIDTH - screenRightEdge - NOTIFICATION_MARGIN_PIXELS_LEFT_RIGHT);

        // The maxY is the bottom of the screen, taking into consideration both the 
        // notification height and the bottom screen panels/dock.
        int maxY = screenSize.height - screenBottomEdge - NotificationPopup.HEIGHT;

        // If this is the first notification, use the right size of the screen as the X position
        if (lastX == null) {
            x = maxX;
        // Else, use the X position of the most recent notification.
        } else {
            x = lastX;
        }

        // If this is the first notification, start in the top of the screen.
        if (lastY == null) {
            y = screenTopEdge;
        } else {
            // Otherwise, set y to be the down from the last notification
            y = (lastY + NotificationPopup.HEIGHT + NOTIFICATION_MARGIN_PIXELS_TOP_BOTTOM);
        }

        // If y is too large...
        // wrap around, and start putting notifications at the top
        // Move left of the last notification position.
        if (y > maxY) {
            y = screenTopEdge;
            x = lastX - NotificationPopup.WIDTH;
        }

        // If we have gone all the way left,
        // go back to the right.
        if ( x < ( ( screenLeftEdge + maxX ) / 2 ) ) {
            x = maxX;
        }

        NotificationPopup newNotification = new NotificationPopup(notificationHTML, x, y, true);        
        
        this.add(newNotification);
        newNotification.setVisible(true);
        
    }

    public void hideAllNotifications() {

        for (NotificationPopup n : this) {
            n.dispose();
        }
        clear();

    }

    // Delete out all the non_visible NotificationPopups
    public void clearInvisible() {

        Collection<NotificationPopup> syncList = Collections.synchronizedCollection(this);
        
        synchronized(syncList) {
            Collection del = new LinkedList();
            for (NotificationPopup n : syncList) {
                if (n.isVisible() == false) {
                    del.add(n);
                }
            }

            syncList.removeAll(del);
        }

    }
}
