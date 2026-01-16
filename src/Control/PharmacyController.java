package controller;

import java.util.*;
import model.Medicine;
import model.Pharmacist;

public class PharmacyController {
    // Shared data lists (static to persist across different screens)
    private static ArrayList<Medicine> medicineList = new ArrayList<>();
    
    // Task: Using LinkedList for Pharmacist List
    private static LinkedList<Pharmacist> pharmacistList = new LinkedList<>();
    
    // Task: Queue for Carousel (Last 5 added)
    private static Queue<Medicine> recentlyAddedQueue = new LinkedList<>(); 
    
    // Task: Stack for System History (LIFO)
    private static Stack<String> actionHistory = new Stack<>();

    // =========================================================================
    // PHARMACIST DASHBOARD METHODS (Medicine Management)
    // =========================================================================

    public String addMedicine(int id, String name, String category, int qty, double price) {
        for (Medicine m : medicineList) {
            if (m.getId() == id) return "DUPLICATE";
        }
        
        Medicine newMed = new Medicine(id, name, category, qty, price);
        medicineList.add(newMed);
        
        // Queue Logic: Maintain only 5 most recent items
        if (recentlyAddedQueue.size() >= 5) {
            recentlyAddedQueue.poll(); 
        }
        recentlyAddedQueue.add(newMed); 
        
        // Stack Push for Undo
        actionHistory.push("Added Medicine: " + id);
        return "SUCCESS";
    }

    public boolean updateMedicine(int id, String name, String category, int qty, double price) {
        for (Medicine m : medicineList) {
            if (m.getId() == id) {
                m.setName(name);
                m.setCategory(category);
                m.setQuantity(qty);
                m.setPrice(price);
                actionHistory.push("Updated Medicine: " + id);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMedicine(int id) {
        boolean removed = medicineList.removeIf(m -> m.getId() == id);
        if (removed) {
            recentlyAddedQueue.removeIf(m -> m.getId() == id);
            actionHistory.push("Deleted Medicine: " + id);
        }
        return removed;
    }

    public ArrayList<Medicine> searchMedicine(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(medicineList);
        }
        ArrayList<Medicine> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase().trim();
        for (Medicine m : medicineList) {
            if (m.getName().toLowerCase().contains(lowerQuery)) {
                results.add(m);
            }
        }
        return results;
    }

    public ArrayList<Medicine> getRecentMedicines() {
        return new ArrayList<>(recentlyAddedQueue);
    }

    // =========================================================================
    // SORTING LOGIC
    // =========================================================================

    public ArrayList<Medicine> sortMedicines(int criteria) {
        ArrayList<Medicine> sortedList = new ArrayList<>(medicineList);
        switch (criteria) {
            case 0: Collections.sort(sortedList, Comparator.comparing(Medicine::getName, String.CASE_INSENSITIVE_ORDER)); break;
            case 1: Collections.sort(sortedList, (m1, m2) -> m2.getName().compareToIgnoreCase(m1.getName())); break;
            case 2: Collections.sort(sortedList, Comparator.comparingDouble(Medicine::getPrice)); break;
            case 3: Collections.sort(sortedList, (m1, m2) -> Double.compare(m2.getPrice(), m1.getPrice())); break;
        }
        return sortedList;
    }

    // =========================================================================
    // LOGIN & ADMIN METHODS (Using LinkedList & Stack)
    // =========================================================================

    public void addPharmacist(String name, String email, String pass) {
        pharmacistList.add(new Pharmacist(name, email, pass));
        
        // CRITICAL: Format "Added: email" so AdminDashboard Undo can parse it
        actionHistory.push("Added Pharmacist: " + email);
    }

    public LinkedList<Pharmacist> getAllPharmacists() {
        return pharmacistList;
    }

    public boolean isEmailDuplicate(String email) {
        for (Pharmacist p : pharmacistList) {
            if (p.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    public void updatePharmacist(String email, String newName, String newPass) {
        for (Pharmacist p : pharmacistList) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                p.setName(newName);
                p.setPassword(newPass);
                actionHistory.push("Updated Pharmacist: " + email);
                break;
            }
        }
    }

    public void deletePharmacist(String email) {
        pharmacistList.removeIf(p -> p.getEmail().equalsIgnoreCase(email));
        // We don't necessarily want to push "Deleted" to stack during an UNDO operation
        // to avoid infinite loops, but for general history:
        actionHistory.push("Deleted Pharmacist: " + email);
    }

    public Stack<String> getActionHistory() {
        return actionHistory;
    }
}