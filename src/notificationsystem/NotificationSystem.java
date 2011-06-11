package notificationsystem;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.UIManager;
import notificationsystem.notification.NotificationQueue;
 
/**
 * Notification System
 * 
 * @author Robert Pyke
 */
public class NotificationSystem {
    public static final int LISTEN_PORT = 43596;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // Set the look and feel of the system to the default look-and-feel.
        try {            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final Exception e1) {
            System.err.println("Failed to set look and feel.");
        }
        
        NotificationQueue notificationQueue = new NotificationQueue(); 

        DatagramSocket socket = new DatagramSocket(LISTEN_PORT);
        DatagramPacket packet = new DatagramPacket(new byte[512], 512);        
        
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();        
        
        NotificationSystemTray tray = new NotificationSystemTray(notificationQueue);
    
        while (true) {
            socket.receive(packet);
            
            String noteText = new String(packet.getData(), 0, packet.getLength()); 
            
            notificationQueue.addNotification(noteText);
            notificationQueue.clearInvisible();

            System.out.println("New notification: " + noteText);            
        }
    }
}
