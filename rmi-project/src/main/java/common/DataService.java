// common/DataService.java
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DataService extends Remote {
    String getPopulationOfCountry(String countryName) throws RemoteException;

    int getNumberofCities(String countryName, int minPopulation) throws RemoteException;

    int getNumberofCountries(int cityCount, int minPopulation) throws RemoteException;

    int getNumberofCountries(int citycount, int minpopulation) throws RemoteException;

    int getNumberofCountries(int cityCount, int minPopulation, int maxPopulation) throws RemoteException;
}
