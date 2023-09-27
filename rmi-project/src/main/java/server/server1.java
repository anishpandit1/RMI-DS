// server/Server1.java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server1 {
    public static void main(String[] args) {
        try {
            DataService server1 = new DataServiceImpl();
            DataService stub = (DataService) UnicastRemoteObject.exportObject(server1, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Server1", stub);

            System.out.println("Server1 is ready.");
        } catch (Exception e) {
            System.err.println("Server1 exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
