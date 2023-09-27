// server/DataServiceImpl.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class DataServiceImpl extends UnicastRemoteObject implements DataService {
    private Map<String, Integer> populationData = new HashMap<>();
    private Map<String, Integer> cityData = new HashMap<>();

    public DataServiceImpl() throws RemoteException {
        // Constructor required by UnicastRemoteObject
        loadDataset();
    }

    private void loadDataset() {
        // Read data from a CSV dataset file and populate the maps
        try (BufferedReader br = new BufferedReader(new FileReader("exercise_dataset.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parse the CSV line and extract relevant information
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String countryName = parts[3].trim(); // 4th column for country name
                    int population = Integer.parseInt(parts[4].trim()); // 5th column for population
                    populationData.put(countryName, population);

                    String city = parts[5].trim();
                    // Check if the cityData map already contains entries for the country
                    if (cityData.containsKey(countryName)) {
                        // If it does, increment the city count
                        int existingCityCount = cityData.get(countryName);
                        cityData.put(countryName, existingCityCount + 1);
                    } else {
                        // If not, add a new entry with a city count of 1
                        cityData.put(countryName, 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPopulationOfCountry(String countryName) throws RemoteException {
        Integer population = populationData.get(countryName);
        if (population != null) {
            return "Population of " + countryName + ": " + population;
        } else {
            return "Country not found";
        }
    }

    @Override
    public int getNumberofCities(String countryName, int minPopulation) throws RemoteException {
        Integer cityCount = cityData.get(countryName);
        if (cityCount != null) {
            return cityCount;
        } else {
            return 0; // Country not found or no city count data available
        }
    }

    @Override
    public int getNumberofCountries(int cityCount, int minPopulation) throws RemoteException {
        // Implement logic to count countries meeting the criteria based on the dataset
        int count = 0;
        for (String countryName : populationData.keySet()) {
            int population = populationData.get(countryName);
            int cities = getNumberofCities(countryName, minPopulation);
            if (cities >= cityCount && population >= minPopulation) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getNumberofCountries(int cityCount, int minPopulation, int maxPopulation) throws RemoteException {
        // Implement logic to count countries meeting the criteria based on the dataset
        int count = 0;
        for (String countryName : populationData.keySet()) {
            int population = populationData.get(countryName);
            int cities = getNumberofCities(countryName, minPopulation);
            if (cities >= cityCount && population >= minPopulation && population <= maxPopulation) {
                count++;
            }
        }
        return count;
    }
}
