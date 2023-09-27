// server/Server4.java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server4 {
    public static void main(String[] args) {
        try {
            DataService Server4 = new DataServiceImpl();
            DataService stub = (DataService) UnicastRemoteObject.exportObject(Server4, 0);
            Registry registry = LocateRegistry.createRegistry(1102);
            registry.bind("Server4", stub);

            System.out.println("Server4 is ready.");
        } catch (Exception e) {
            System.err.println("Server4 exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
