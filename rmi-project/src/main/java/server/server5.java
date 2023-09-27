// server/Server5.java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server5 {
    public static void main(String[] args) {
        try {
            DataService server1 = new DataServiceImpl();
            DataService stub = (DataService) UnicastRemoteObject.exportObject(Server5, 0);
            Registry registry = LocateRegistry.createRegistry(1103);
            registry.bind("Server5", stub);

            System.out.println("Server5 is ready.");
        } catch (Exception e) {
            System.err.println("Server5 exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
