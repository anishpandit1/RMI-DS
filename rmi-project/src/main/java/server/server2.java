// server/Server2.java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server2 {
    public static void main(String[] args) {
        try {
            DataService Server2 = new DataServiceImpl();
            DataService stub = (DataService) UnicastRemoteObject.exportObject(Server2, 0);
            Registry registry = LocateRegistry.createRegistry(1100);
            registry.bind("Server2", stub);

            System.out.println("Server2 is ready.");
        } catch (Exception e) {
            System.err.println("Server2 exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
