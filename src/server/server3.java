// server/Server3.java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server3 {
    public static void main(String[] args) {
        try {
            DataService Server3 = new DataServiceImpl();
            DataService stub = (DataService) UnicastRemoteObject.exportObject(Server3, 0);
            Registry registry = LocateRegistry.createRegistry(1101);
            registry.bind("Server3", stub);

            System.out.println("Server3 is ready.");
        } catch (Exception e) {
            System.err.println("Server3 exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
