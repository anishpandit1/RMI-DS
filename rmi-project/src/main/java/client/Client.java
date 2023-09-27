import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.util.Date;

public class Client {
    public static void main(String[] args) {
        try {
            // Read the input file containing queries
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            FileWriter fw = new FileWriter("output.txt");

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String methodName = parts[0];
                String argument1 = parts[1];
                String argument2 = parts[2];
                String argument3 = parts[3];
                String zone = parts[4];

                // Resolve the server based on the zone number
                ServerService server = (ServerService) Naming.lookup("rmi://localhost/Server" + zone);

                // Measure the time before invoking the remote method
                long startTime = System.currentTimeMillis();

                // Invoke the remote method on the server
                String result = invokeRemoteMethod(server, methodName, argument1, argument2, argument3);

                // Measure the time after the remote method call
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;

                // Write the result to the output file
                String output = result + " " + line + " (turnaround time: " + executionTime + " ms)";
                System.out.println(output);
                fw.write(output + "\n");
            }

            br.close();
            fw.close();
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    private static String invokeRemoteMethod(ServerService server, String methodName, String arg1, String arg2, String arg3) throws RemoteException {
        // Implement the logic to invoke remote methods based on methodName and arguments
        // You can switch on methodName and call the appropriate method on the server
        // For example, if methodName is "getPopulationofCountry", call server.getPopulationofCountry(arg1);
        // Handle other methods in a similar way
        return server.requestService(); // Replace with actual remote method invocation
    }
}
