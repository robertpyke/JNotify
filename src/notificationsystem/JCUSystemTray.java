/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notificationsystem;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.Exception;
import java.lang.RuntimeException;
import java.util.Collections;
import notificationsystem.notification.NotificationQueue;

/**
 *
 * @author robertpyke
 */
public class JCUSystemTray {

    static Image trayImage = Toolkit.getDefaultToolkit().getImage("images/TrayIcon.gif");
    public final NotificationQueue notificationQueue;
    
    JCUSystemTray() {
        
        if (SystemTray.isSupported()) {
            notificationQueue = new NotificationQueue();
        
            final TrayIcon trayIcon;
            SystemTray tray = SystemTray.getSystemTray();

            PopupMenu popup = new PopupMenu();
            
            addHideNotificationsListener(popup);
            addExitListener(popup);
            
            trayIcon = new TrayIcon(trayImage, "Notification System", popup);

            trayIcon.setImageAutoSize(true);
            
            
            
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                throw new RuntimeException("TrayIcon could not be added.");
            }

        } else {
            throw new RuntimeException("This system does not support the system tray. I'm sorry, you can't use this application.");
        }
    }
    
    private void addHideNotificationsListener(PopupMenu popup) {
        ActionListener hideNotificationsListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {                            
                System.out.println("User Selected hide all notifications");
                notificationQueue.hideAllNotifications();
                
            }
        };
        
        MenuItem hideItem = new MenuItem("Hide All Notifications");
        hideItem.addActionListener(hideNotificationsListener);
        popup.add(hideItem);
    }

    private void addExitListener(PopupMenu popup) {
        ActionListener exitListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("User Selected Exit");
                System.out.println("Exiting...");
                System.exit(0);
            }
        };

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(exitListener);
        popup.add(exitItem);
    }

    NotificationQueue getNotificationQueue() {
        return notificationQueue;
    }
}
