
package game;
import framework.GameController;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(GameController.getInstance().getPort());
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(socket.getOutputStream());
                } finally {
                    socket.close();
                }
            }
        } finally {
            listener.close();
        }
    }
}
