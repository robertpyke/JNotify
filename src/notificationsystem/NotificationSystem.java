/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notificationsystem;


import notificationsystem.notification.NotificationPopup;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.UIManager;
import notificationsystem.notification.NotificationQueue;
 
public class NotificationSystem {
    public static final int LISTEN_PORT = 7070;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final Exception e1) {
            System.err.println("Failed to set look and feel.");
        }

        
        DatagramSocket socket = new DatagramSocket(LISTEN_PORT);
        DatagramPacket packet = new DatagramPacket(new byte[512], 512);
        
        
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();        
        
        JCUSystemTray tray = new JCUSystemTray();
    
        while (true) {
            socket.receive(packet);
            
            String noteText = new String(packet.getData(), 0, packet.getLength()); 
            
            tray.getNotificationQueue().addNotification(noteText);
            tray.getNotificationQueue().clearInvisible();

            System.out.println("New notification: " + noteText);            
        }
    }
}
