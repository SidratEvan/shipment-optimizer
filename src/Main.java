import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\n=== Shipment Optimizer (Potash/Uranium) ===");

            // ---- Destination choice with validation ----
            int choice = 0;
            while (choice < 1 || choice > 7) {
                System.out.println("Choose destination:");
                System.out.println("  1) Vancouver Port (1600 km)");
                System.out.println("  2) Prince Rupert Port (2100 km)");
                System.out.println("  3) Thunder Bay (1800 km)");
                System.out.println("  4) North Portal (US Border, SK) (370 km)");
                System.out.println("  5) Montreal Port (3000 km)");
                System.out.println("  6) Regina (Yards) (260 km)");
                System.out.println("  7) Moose Jaw (Terminal) (70 km)");
                System.out.print("Enter a number (1-7): ");
                choice = sc.nextInt();
                if (choice < 1 || choice > 7) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            }

            String destination = "";
            int km = 0;
            switch (choice) {
                case 1: destination = "Vancouver Port"; km = 1600; break;
                case 2: destination = "Prince Rupert Port"; km = 2100; break;
                case 3: destination = "Thunder Bay"; km = 1800; break;
                case 4: destination = "North Portal (US Border, SK)"; km = 370; break;
                case 5: destination = "Montreal Port"; km = 3000; break;
                case 6: destination = "Regina (Yards)"; km = 260; break;
                case 7: destination = "Moose Jaw (Terminal)"; km = 70; break;
            }

            System.out.println("You selected: " + destination);

            // ---- Weight input with validation ----
            double weight = 0;
            while (weight <= 0) {
                System.out.print("Enter shipment weight in tonnes (>0): ");
                weight = sc.nextDouble();
                if (weight <= 0) {
                    System.out.println("Invalid weight. Please enter a number greater than 0.");
                }
            }
            System.out.println("Shipment weight: " + weight + " tonnes");

            // ---- Priority input with validation ----
            int priority = 0;
            while (priority != 1 && priority != 2) {
                System.out.print("Priority? (1 = Normal, 2 = Urgent): ");
                priority = sc.nextInt();
                if (priority != 1 && priority != 2) {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            }
            String priorityText = (priority == 2) ? "Urgent" : "Normal";
            System.out.println("Priority: " + priorityText);

            // ---- Calculate shipping options ----
            double railCost = weight * km * 0.04;
            double railTime = 1.5 + (km / 700.0);

            double truckCost = weight * km * 0.07;
            double truckTime = 0.8 + (km / 350.0);

            double shipCost = weight * km * 0.02;
            double shipTime = 4.0 + (km / 1200.0);

            // ---- Print report ----
            System.out.println("\n--- Shipment Options to " + destination + " (" + km + " km) ---");
            System.out.printf("Rail  -> Cost: $%,.2f | Time: %.1f days%n", railCost, railTime);
            System.out.printf("Truck -> Cost: $%,.2f | Time: %.1f days%n", truckCost, truckTime);
            System.out.printf("Ship  -> Cost: $%,.2f | Time: %.1f days%n", shipCost, shipTime);

            // ---- Decide best option ----
            String bestMode = "";
            double bestCost = 0;
            double bestTime = 0;
            String reason = "";

            if (priority == 1) { // Normal → cheapest
                if (railCost <= truckCost && railCost <= shipCost) {
                    bestMode = "Rail"; bestCost = railCost; bestTime = railTime;
                } else if (truckCost <= railCost && truckCost <= shipCost) {
                    bestMode = "Truck"; bestCost = truckCost; bestTime = truckTime;
                } else {
                    bestMode = "Ship"; bestCost = shipCost; bestTime = shipTime;
                }
                reason = "Cheapest (normal priority)";
            } else { // Urgent → fastest
                if (railTime <= truckTime && railTime <= shipTime) {
                    bestMode = "Rail"; bestCost = railCost; bestTime = railTime;
                } else if (truckTime <= railTime && truckTime <= shipTime) {
                    bestMode = "Truck"; bestCost = truckCost; bestTime = truckTime;
                } else {
                    bestMode = "Ship"; bestCost = shipCost; bestTime = shipTime;
                }
                reason = "Fastest (urgent priority)";
            }

            System.out.println("\nBest Option: " + bestMode + " -> Reason: " + reason);
            System.out.printf("Estimated Cost: $%,.2f | Time: %.1f days%n", bestCost, bestTime);

            // ---- Save to CSV ----
            try {
                File file = new File("history.csv");
                boolean newFile = !file.exists();

                try (FileWriter fw = new FileWriter(file, true)) {
                    if (newFile) {
                        fw.write("Destination,Distance(km),Weight(t),Priority,BestMode,Cost,Time(days)\n");
                    }
                    fw.write(String.format("%s,%d,%.1f,%s,%s,%.2f,%.1f%n",
                            destination, km, weight, priorityText, bestMode, bestCost, bestTime));
                }
            } catch (IOException e) {
                System.out.println("Error writing to CSV: " + e.getMessage());
            }

            // ---- Run again? with validation ----
            String again = "";
            while (!again.equalsIgnoreCase("y") && !again.equalsIgnoreCase("n")) {
                System.out.print("\nDo you want to optimize another shipment? (y/n): ");
                again = sc.next();
                if (!again.equalsIgnoreCase("y") && !again.equalsIgnoreCase("n")) {
                    System.out.println("Invalid input. Please type 'y' or 'n'.");
                }
            }

            if (again.equalsIgnoreCase("n")) {
                running = false;
                System.out.println("Exiting Shipment Optimizer. Goodbye!");
            }
        }
    }
}



