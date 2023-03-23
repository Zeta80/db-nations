import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

            String url = "jdbc:mysql://localhost/db_nations";
            String user = "root";
            String password = "root";

            // Chiediamo all'utente di inserire una stringa di ricerca
        Scanner input = new Scanner(System.in);
        System.out.print("Inserisci una stringa di ricerca: ");
        String search = input.nextLine();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT c.country_id, c.name AS country_name, r.name AS region_name, ct.name AS continent_name FROM countries c JOIN regions r ON c.region_id = r.region_id JOIN continents ct ON r.continent_id = ct.continent_id WHERE c.name LIKE ? ORDER BY c.name")) {

            // Aggiungiamo il parametro di ricerca alla query
            stmt.setString(1, "%" + search + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("country_id");
                String countryName = rs.getString("country_name");
                String regionName = rs.getString("region_name");
                String continentName = rs.getString("continent_name");

                System.out.println(countryId + "\t" + countryName + "\t" + regionName + "\t" + continentName);
            }

            rs.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
        }

