import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import lib280.list.LinkedList280;

public class PerformanceByRank {

    public static LinkedList280<Crew> readCrewData(String path) {
        Scanner infile = null;

        try {
            infile = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
        }

        // Initialize output list.
        LinkedList280<Crew> pirateCrew = new LinkedList280<Crew>();

        // While there is more stuff to read...
        while (infile.hasNext()) {
            // Read the three values for a Crew record
            int rank = infile.nextInt();
            double pay = infile.nextDouble();
            int sacks = infile.nextInt();

            // Create a crew object from the data
            Crew c = new Crew(rank, pay, sacks);

            // Place the new Crew instance in the linked list.
            pirateCrew.insertFirst(c);
        }

        // Close the input file like a good citizen. :)
        infile.close();

        // Return the list of Crew objects.
        return pirateCrew;
    }


    public static void main(String args[]) {

        LinkedList280<Crew> pirateCrew = readCrewData("E:\\Studies Related\\USASK\\Subjects (Winter 2019)\\CMPT 280\\Assignments\\A1 (rsp502)\\A1Q1\\src\\piratecrew.txt");

        LinkedList280<Crew>[] piratesByRank = new LinkedList280[10];
        for (int i = 0; i < 10; i++) {
            piratesByRank[i] = new LinkedList280<>();
        }
        //Create a iterator to travel throught the list and give the position to the crew members in array according to their Rank
        while (!pirateCrew.isEmpty()) {
            Crew n = pirateCrew.firstItem();
            piratesByRank[n.getRank()].insert(n);
            pirateCrew.deleteFirst();
        }
        for (int i = 0; i < piratesByRank.length; i++) {
            if (!piratesByRank[i].isEmpty()) {
                piratesByRank[i].goFirst();

                double totalPay = 0;
                int totalSacksPlundered = 0;

                while (piratesByRank[i].itemExists()) {
                    totalSacksPlundered += piratesByRank[i].item().getGrainSacks();
                    totalPay += piratesByRank[i].item().getPay();
                    piratesByRank[i].goForth();
                }

                System.out.println("Jack's rank " + i + " crew members were paid of " +
                        totalPay / totalSacksPlundered + " guineas per sack of grain plundered.");
            } else if (piratesByRank[i].isEmpty()) {
                System.out.println("Jack has no pirates that are in rank " + i);
            }
        }
    }

}
