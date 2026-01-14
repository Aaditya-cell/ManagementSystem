package controller;

import java.util.*;
import model.Medicine;
import model.Pharmacist; // Ensure this class exists in your model package

public class PharmacyController {
    // Shared data lists (static so they persist across different screens)
    private static ArrayList<Medicine> medicineList = new ArrayList<>();
    private static ArrayList<Pharmacist> pharmacistList = new ArrayList<>();
    
    // Task A: Data Structure Requirements
    private static Queue<Medicine> recentlyAddedQueue = new LinkedList<>();
    private static Stack<String> actionHistory = new Stack<>();

    // =========================================================================
    // ADMIN DASHBOARD METHODS (Pharmacist Management)
    // =========================================================================

    public void addPharmacist(String name, String email, String pass) {
        pharmacistList.add(new Pharmacist(name, email, pass));
        actionHistory.push("Admin added pharmacist: " + name);
    }

    public ArrayList<Pharmacist> getAllPharmacists() {
        return pharmacistList;
    }

    public boolean isEmailDuplicate(String email) {
        for (Pharmacist p : pharmacistList) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidEmail(String email) {
        // Basic validation: must contain @ and a dot
        return email != null && email.contains("@") && email.contains(".");
    }

    public void updatePharmacist(String email, String newName, String newPass) {
        for (Pharmacist p : pharmacistList) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                p.setName(newName);
                p.setPassword(newPass);
                actionHistory.push("Updated pharmacist: " + email);
                break;
            }
        }
    }

    public boolean deletePharmacist(String email) {
        boolean removed = pharmacistList.removeIf(p -> p.getEmail().equalsIgnoreCase(email));
        if (removed) {
            actionHistory.push("Deleted pharmacist: " + email);
        }
        return removed;
    }

    // =========================================================================
    // PHARMACIST DASHBOARD METHODS (Medicine Management)
    // =========================================================================

    public String addMedicine(String id, String name, String category, int qty, double price) {
        for (Medicine m : medicineList) {
            if (m.getId().equalsIgnoreCase(id)) return "DUPLICATE";
        }
        
        Medicine newMed = new Medicine(id, name, category, qty, price);
        medicineList.add(newMed);
        
        // Manage Queue: Keep only last 5
        if (recentlyAddedQueue.size() >= 5) {
            recentlyAddedQueue.poll();
        }
        recentlyAddedQueue.add(newMed);
        
        actionHistory.push("Added medicine: " + name);
        return "SUCCESS";
    }

    public ArrayList<Medicine> searchMedicine(String query) {
        if (query == null || query.isEmpty()) return medicineList;
        ArrayList<Medicine> results = new ArrayList<>();
        for (Medicine m : medicineList) {
            if (m.getName().toLowerCase().contains(query.toLowerCase()) || m.getId().contains(query)) {
                results.add(m);
            }
        }
        return results;
    }

    public boolean updateMedicine(String id, String name, String category, int qty, double price) {
        for (int i = 0; i < medicineList.size(); i++) {
            if (medicineList.get(i).getId().equals(id)) {
                medicineList.set(i, new Medicine(id, name, category, qty, price));
                actionHistory.push("Updated medicine: " + name);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMedicine(String id) {
        return medicineList.removeIf(m -> {
            if (m.getId().equals(id)) {
                actionHistory.push("Deleted ID: " + id);
                return true;
            }
            return false;
        });
    }

    // =========================================================================
    // ALGORITHMS & UTILITIES
    // =========================================================================

    // SELECTION SORT Implementation
    public ArrayList<Medicine> sortMedicines(int criteria) {
        ArrayList<Medicine> sorted = new ArrayList<>(medicineList);
        int n = sorted.size();
        for (int i = 0; i < n - 1; i++) {
            int targetIdx = i;
            for (int j = i + 1; j < n; j++) {
                boolean shouldSwap = false;
                switch (criteria) {
                    case 0 -> shouldSwap = sorted.get(j).getPrice() < sorted.get(targetIdx).getPrice();
                    case 1 -> shouldSwap = sorted.get(j).getPrice() > sorted.get(targetIdx).getPrice();
                    case 2 -> shouldSwap = sorted.get(j).getName().compareToIgnoreCase(sorted.get(targetIdx).getName()) < 0;
                    case 3 -> shouldSwap = sorted.get(j).getName().compareToIgnoreCase(sorted.get(targetIdx).getName()) > 0;
                }
                if (shouldSwap) targetIdx = j;
            }
            Collections.swap(sorted, i, targetIdx);
        }
        return sorted;
    }

    public Stack<String> getActionHistory() {
        return actionHistory;
    }
}
