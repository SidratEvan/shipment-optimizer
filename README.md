# Shipment Optimizer (Potash/Uranium Logistics)

A Java console application that simulates logistics optimization for potash and uranium shipments.  
Designed to reflect real trade-offs in the mining and logistics industry .

---

## Features
- Destination selection with distance presets (e.g., Vancouver Port, Thunder Bay, Regina).
- User inputs: destination, shipment weight (tonnes), and priority (Normal or Urgent).
- Cost & time modeling for Rail, Truck, and Ship:
  - Rail: $0.04/tonne-km, 1.5 days overhead + distance/700
  - Truck: $0.07/tonne-km, 0.8 days overhead + distance/350
  - Ship: $0.02/tonne-km, 4 days overhead + distance/1200
- Automatic recommendation:
  - If Normal → cheapest option.
  - If Urgent → fastest option.
- Robust input validation: prevents invalid numbers or letters.
- CSV logging: Every shipment is saved in `history.csv` with headers for easy analysis in Excel.

---

## Example Run
=== Shipment Optimizer (Potash/Uranium) ===
Choose destination:

Vancouver Port (1600 km)

Prince Rupert Port (2100 km)
...
Enter a number (1-7): 2
You selected: Prince Rupert Port
Enter shipment weight in tonnes (>0): 100
Priority? (1 = Normal, 2 = Urgent): 2
Priority: Urgent

--- Shipment Options to Prince Rupert Port (2100 km) ---
Rail -> Cost: $8,400.00 | Time: 4.5 days
Truck -> Cost: $14,700.00 | Time: 6.8 days
Ship -> Cost: $4,200.00 | Time: 5.8 days

Best Option: Rail -> Reason: Fastest (urgent priority)
Estimated Cost: $8,400.00 | Time: 4.5 days

yaml

---

## CSV Output Example
Destination,Distance(km),Weight(t),Priority,BestMode,Cost,Time(days)
Prince Rupert Port,2100,100.0,Urgent,Rail,8400.00,4.5
North Portal (US Border, SK),370,3000.0,Normal,Ship,22200.00,4.3

yaml
---

## How to Run
``bash
Compile
javac Main.java
Run
java Main

## Why This Project?
Mining companies rely on logistics optimization every day.
This project demonstrates:

Practical decision-making logic (cost vs. time).

Use of Java fundamentals (loops, input validation, file handling).

A unique application area (mining logistics), making it stand out from typical student projects.

Tech
Java 17

Console-based UI

File handling (CSV logging)
