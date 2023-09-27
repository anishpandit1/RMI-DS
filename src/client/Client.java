import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class RMIClient {
    public static void main(String[] args) {
        try {
            // Define the RMI server URL
            String serverURL = "rmi://localhost:1099/DataService";

            // Open the input file for reading
            BufferedReader inputFile = new BufferedReader(new FileReader("input.txt"));

            // Create the output file for writing
            BufferedWriter outputFile = new BufferedWriter(new FileWriter("output.txt"));

            // Variables to store average times
            int totalTurnaroundTime = 0;
            int totalExecutionTime = 0;
            int totalWaitingTime = 0;
            int totalQueries = 0;

            // Read and process each line from the input file
            String line;
            while ((line = inputFile.readLine()) != null) {
                // Split the input line into tokens
                String[] tokens = line.split(" ");

                if (tokens.length >= 4) {
                    String methodName = tokens[0];
                    String arg1 = tokens[1];
                    String arg2 = tokens[2];
                    String arg3 = tokens[3];
                    String zone = tokens[4];

                    // Measure start time
                    long startTime = System.currentTimeMillis();

                    // Invoke the remote method based on methodName and arguments
                    String result = invokeRemoteMethod(serverURL, methodName, arg1, arg2, arg3);

                    // Measure end time
                    long endTime = System.currentTimeMillis();

                    // Calculate turnaround time, execution time, and waiting time
                    long turnaroundTime = endTime - startTime;
                    long executionTime = Long.parseLong(result);
                    long waitingTime = turnaroundTime - executionTime;

                    // Write the result and time measurements to the output file
                    String outputLine = result + " " + methodName + " (turnaround time: " +
                            turnaroundTime + " ms, execution time: " + executionTime +
                            " ms, waiting time: " + waitingTime + " ms, processed by Server " + zone + ")";
                    outputFile.write(outputLine);
                    outputFile.newLine();

                    // Update total times and query count for averaging
                    totalTurnaroundTime += turnaroundTime;
                    totalExecutionTime += executionTime;
                    totalWaitingTime += waitingTime;
                    totalQueries++;
                }
            }

            // Calculate average times
            int avgTurnaroundTime = totalTurnaroundTime / totalQueries;
            int avgExecutionTime = totalExecutionTime / totalQueries;
            int avgWaitingTime = totalWaitingTime / totalQueries;

            // Write average times to the end of the output file
            String avgOutput = "Averages: turn around time: " + avgTurnaroundTime +
                    " ms, execution time: " + avgExecutionTime + " ms, waiting time: " + avgWaitingTime + " ms";
            outputFile.write(avgOutput);

            // Close the input and output files
            inputFile.close();
            outputFile.close();

            System.out.println("Client requests completed. Results written to output.txt.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to invoke remote methods on the RMI server
    private static String invokeRemoteMethod(String serverURL, String methodName, String arg1, String arg2, String arg3) throws RemoteException {
        try {
            // Lookup the remote object from the RMI registry
            DataService dataService = (DataService) Naming.lookup(serverURL);

            // Invoke the appropriate method based on methodName
            if (methodName.equals("getPopulationofCountry")) {
                return String.valueOf(dataService.getPopulationofCountry(arg1));
            } else if (methodName.equals("getNumberofCities")) {
                return String.valueOf(dataService.getNumberofCities(arg1, Long.parseLong(arg2)));
            } else if (methodName.equals("getNumberofCountries")) {
                if (arg3 != null) {
                    return String.valueOf(dataService.getNumberofCountries(Integer.parseInt(arg1), Long.parseLong(arg2), Long.parseLong(arg3)));
                } else {
                    return String.valueOf(dataService.getNumberofCountries(Integer.parseInt(arg1), Long.parseLong(arg2)));
                }
            } else {
                return "Invalid method name";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error invoking remote method";
        }
    }
}
