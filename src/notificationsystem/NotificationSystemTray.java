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
import notificationsystem.notification.NotificationQueue;

/**
 * Notification System Tray
 * 
 * @author Robert Pyke
 */
public class NotificationSystemTray {

    public static final Image TRAY_IMAGE = Toolkit.getDefaultToolkit().getImage("images/TrayIcon.gif");
    private final NotificationQueue notificationQueue;
    
    /**
     * Constructor for the NotificationSystemTray
     */
    NotificationSystemTray(NotificationQueue inNotificationQueue) {        
        // Only do the following if the system tray is supported on this system
        if (SystemTray.isSupported()) {
            
            // assign the notification queue
            notificationQueue = inNotificationQueue;
                                
            // Create the popup menu for the system tray
            PopupMenu popup = new PopupMenu();
            
            // Add the menu-items to the popup menu
            // Add hide-notifications menu item
            addHideNotificationsListener(popup);
            // Add exit menu item
            addExitListener(popup);
            
            // Create the tray icon from the TRAY_IMAGE and popup
            final TrayIcon trayIcon = new TrayIcon(TRAY_IMAGE, "Notification System", popup);
            
            // Auti-resize the tray icon image as necessary
            trayIcon.setImageAutoSize(true);
            
            // Try and add the tray icon to the system tray
            try {
                SystemTray tray = SystemTray.getSystemTray();
                tray.add(trayIcon);
            } catch (AWTException e) {
                throw new RuntimeException("TrayIcon could not be added.");
            }
        } else {
            throw new RuntimeException("This system does not support the system " +
                    "tray. I'm sorry, you can't use this application.");
        }
    }
    
    /**
     * Add the hide notifications menu-item to the system-tray menu
     * Takes the system-tray menu as the argument 
     * 
     * @param popup the system-tray menu
     */
    private void addHideNotificationsListener(PopupMenu popup) {
        // Create the hide notification listener
        ActionListener hideNotificationsListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {                
                // Hide all the notifications when the listener is activated
                notificationQueue.hideAllNotifications();                
            }
        };        
        
        // Create a hide menu-item "Hide All Notifications"
        MenuItem hideItem = new MenuItem("Hide All Notifications");
        // Add the hide notification listener to the menu item
        hideItem.addActionListener(hideNotificationsListener);
        // Add the hide notification menu-item to the system-tray menu 
        popup.add(hideItem);
    }

    /**
     * Add the exit menu-item to the system-tray menu
     * Takes the system-tray menu as the argument 
     * 
     * @param popup the system-tray menu
     */
    private void addExitListener(PopupMenu popup) {
        // Create the exit listener
        ActionListener exitListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit when the listener is activated
                System.out.println("User Selected Exit from the System Tray");
                System.out.println("Exiting...");
                System.exit(0);
            }
        };

        // Create a exit menu-item "Exit"
        MenuItem exitItem = new MenuItem("Exit");
        // Add the exit notification listener to the menu item
        exitItem.addActionListener(exitListener);
        // Add the exit menu-item to the system-tray menu 
        popup.add(exitItem);
    }

}
