package pro1;

import com.google.gson.Gson;
import pro1.apiDataModel.SpecializationsList;
import pro1.apiDataModel.Specialization;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import java.util.HashSet;
import java.util.Set;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Main7 {
    public static String specializationDeadlines(int year) {
        String json = Api.getSpecializations(year);
        SpecializationsList specializationsList = new Gson().fromJson(json, SpecializationsList.class);

        Set<String> deadlines = new HashSet<>();

        // Loop through the list of specializations and collect deadlines
        for (Specialization specialization : specializationsList.items) {
            if (specialization.deadline != null && specialization.deadline.value != null) {
                deadlines.add(specialization.deadline.value);  // Add deadlines to set
            }
        }

        // Debugging: print the collected deadlines
        System.out.println("Collected Deadlines: " + deadlines);

        // DateTimeFormatter to parse and format date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

        // Sort deadlines chronologically, format and join into a single string
        String sortedDeadlines = deadlines.stream()
                .map(deadline -> {
                    try {
                        return LocalDate.parse(deadline, formatter);  // Parse date
                    } catch (Exception e) {
                        return null;  // Handle invalid date format
                    }
                })
                .filter(date -> date != null)  // Remove nulls (if any invalid dates were encountered)
                .sorted()  // Sort by date
                .map(date -> date.format(formatter))  // Convert back to string format
                .collect(Collectors.joining(","));

        // Debugging: print sorted deadlines to verify
        System.out.println("Sorted Deadlines: " + sortedDeadlines);

        return sortedDeadlines;
    }
}

