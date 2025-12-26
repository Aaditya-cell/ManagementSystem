package controller;

import model.Pharmacist;
import model.Medicine;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class PharmacyController {
    // Static lists ensure data persists while the app is running
    private static ArrayList<Pharmacist> pharmacistList = new ArrayList<>();
    private static ArrayList<Medicine> medicineList = new ArrayList<>();

    // --- SEARCH LOGIC ---
    public ArrayList<Medicine> searchMedicine(String query) {
        ArrayList<Medicine> filteredList = new ArrayList<>();
        for (Medicine m : medicineList) {
            if (m.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(m);
            }
        }
        return filteredList;
    }

    // --- VALIDATION LOGIC ---
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return email != null && pat.matcher(email).matches();
    }

    public boolean isEmailDuplicate(String email) {
        for (Pharmacist p : pharmacistList) {
            if (p.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    // --- LOGIN LOGIC ---
    public String login(String email, String password) {
        if (email.equals("admin@gmail.com") && password.equals("123")) return "ADMIN";
        for (Pharmacist p : pharmacistList) {
            if (p.getEmail().equalsIgnoreCase(email) && p.getPassword().equals(password)) {
                return "PHARMACIST";
            }
        }
        return "INVALID";
    }

    // --- PHARMACIST METHODS ---
    public void addPharmacist(String name, String email, String pass) {
        pharmacistList.add(new Pharmacist(name, email, pass));
    }

    public ArrayList<Pharmacist> getAllPharmacists() { return pharmacistList; }

    // --- MEDICINE METHODS ---
    public void addMedicine(String id, String name, int qty, double price) {
        medicineList.add(new Medicine(id, name, qty, price));
    }

    public ArrayList<Medicine> getAllMedicines() { return medicineList; }

    public void updateMedicine(String id, String name, int qty, double price) {
        for (int i = 0; i < medicineList.size(); i++) {
            if (medicineList.get(i).getId().equals(id)) {
                medicineList.set(i, new Medicine(id, name, qty, price));
                break;
            }
        }
    }

    public void deleteMedicine(String id) {
        medicineList.removeIf(m -> m.getId().equals(id));
    }
}