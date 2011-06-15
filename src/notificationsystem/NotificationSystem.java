package notificationsystem;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import notificationsystem.notification.NotificationQueue;
 
/**
 * Notification System
 * 
 * @author Robert Pyke
 */
public class NotificationSystem {
    public static final int LISTEN_PORT = 43596;
    public static final int EXIT_PORT_BIND_FAILED = 1;
    public static final int EXIT_PORT_RECEIVER_FAILED_IO = 2;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Set the look and feel of the system to the default look-and-feel.
            try {            
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (final Exception e1) {
                System.err.println("Failed to set look and feel.");
            }
            
            NotificationQueue notificationQueue = new NotificationQueue(); 

            DatagramSocket socket = new DatagramSocket(LISTEN_PORT);
            
            System.out.println("Listening for notifications on UDP Port: " + LISTEN_PORT);
           
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
        } catch (SocketException ex) {            
            Logger.getLogger(NotificationSystem.class.getName()).log(Level.SEVERE, 
                    "Failed to bind to listen socket on UDP port: " + LISTEN_PORT, ex);
            
            JOptionPane.showMessageDialog(null, "Failed to bind to listen socket on UDP port: " 
                    + LISTEN_PORT, "Fatal Error", JOptionPane.ERROR_MESSAGE);
            
            System.exit(EXIT_PORT_BIND_FAILED);        
        } catch (IOException ex) {
            Logger.getLogger(NotificationSystem.class.getName()).log(Level.SEVERE, 
                    "IO Error occurred while listening for notifications on UDP port: " + LISTEN_PORT, ex);
            
            JOptionPane.showMessageDialog(null, "IO Error occurred while listening " +
                    "for notifications on UDP port: " + LISTEN_PORT, "Fatal Error", JOptionPane.ERROR_MESSAGE);
            
            System.exit(EXIT_PORT_RECEIVER_FAILED_IO);
        }        
    }
}
