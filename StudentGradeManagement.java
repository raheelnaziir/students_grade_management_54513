
package studentgrademanagement;

import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String name;
    private int rollNo;
    private int[] marks = new int[3];

    public Student(String name, int rollNo, int[] marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.marks = marks;
    }

    public int getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public int[] getMarks() {
        return marks;
    }

    public void setMarks(int[] marks) {
        this.marks = marks;
    }

    public int getTotal() {
        int sum = 0;
        for (int m : marks) sum += m;
        return sum;
    }

    public double getAverage() {
        return getTotal() / 3.0;
    }

    @Override
    public String toString() {
        return String.format("%-10d %-15s %-5d %-5d %-5d %-7d %-7.2f",
                rollNo, name, marks[0], marks[1], marks[2], getTotal(), getAverage());
    }
}

public class StudentGradeManagement {
    private static final int MAX_STUDENTS = 50;
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = getInt("Choose an option: ");
            switch (choice) {
                case 1 : 
                    addStudent(); break;
                case 2 : 
                    updateMarks();break;
                case 3 : 
                    removeStudent(); break;
                case 4 : 
                    viewAllStudents(); break;
                case 5 : 
                    searchStudent(); break;
                case 6 : 
                    highestScorer(); break;
                case 7 : 
                    classAverage(); break;
                case 8 : 
                    exitSummary(); break;
                default : 
                    System.out.println("Invalid choice. Try again!");
            }
        } while (choice != 8);
    }

    private static void printMenu() {
        System.out.println("\n--- Student Grade Management System ---");
        System.out.println("1. Add Student");
        System.out.println("2. Update Marks");
        System.out.println("3. Remove Student");
        System.out.println("4. View All Students");
        System.out.println("5. Search Student");
        System.out.println("6. Highest Scorer");
        System.out.println("7. Class Average");
        System.out.println("8. Exit");
    }

    private static void addStudent() {
        if (students.size() >= MAX_STUDENTS) {
            System.out.println("Cannot add more students! Limit reached.");
            return;
        }

        int rollNo = getInt("Enter Roll No: ");
        if (findStudent(rollNo) != null) {
            System.out.println("Roll number already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        int[] marks = new int[3];
        for (int i = 0; i < 3; i++) {
            marks[i] = getMarks("Enter Marks in Subject " + (i + 1) + ": ");
        }

        students.add(new Student(name, rollNo, marks));
        System.out.println("Student added successfully!");
    }

    private static void updateMarks() {
        int rollNo = getInt("Enter Roll No to update: ");
        Student s = findStudent(rollNo);
        if (s == null) {
            System.out.println("Student not found!");
            return;
        }
        int[] marks = new int[3];
        for (int i = 0; i < 3; i++) {
            marks[i] = getMarks("Enter new Marks in Subject " + (i + 1) + ": ");
        }
        s.setMarks(marks);
        System.out.println("Marks updated successfully!");
    }

    private static void removeStudent() {
        int rollNo = getInt("Enter Roll No to remove: ");
        Student s = findStudent(rollNo);
        if (s == null) {
            System.out.println("Student not found!");
            return;
        }
        students.remove(s);
        System.out.println("Student removed successfully!");
    }

    private static void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        System.out.printf("%-10s %-15s %-5s %-5s %-5s %-7s %-7s\n",
                "RollNo", "Name", "M1", "M2", "M3", "Total", "Average");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void searchStudent() {
        int rollNo = getInt("Enter Roll No to search: ");
        Student s = findStudent(rollNo);
        if (s == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.printf("%-10s %-15s %-5s %-5s %-5s %-7s %-7s\n",
                "RollNo", "Name", "M1", "M2", "M3", "Total", "Average");
        System.out.println(s);
    }

    private static void highestScorer() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        Student top = students.get(0);
        for (Student s : students) {
            if (s.getTotal() > top.getTotal()) top = s;
        }
        System.out.println("Highest Scorer:");
        System.out.printf("%-10s %-15s %-5s %-5s %-5s %-7s %-7s\n",
                "RollNo", "Name", "M1", "M2", "M3", "Total", "Average");
        System.out.println(top);
    }

    private static void classAverage() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        double total = 0;
        for (Student s : students) total += s.getAverage();
        double avg = total / students.size();
        System.out.printf("Class Average: %.2f\n", avg);
    }

    private static void exitSummary() {
        System.out.println("Exiting... Summary:");
        System.out.println("Total Students: " + students.size());
        classAverage();
    }

    private static Student findStudent(int rollNo) {
        for (Student s : students) {
            if (s.getRollNo() == rollNo) return s;
        }
        return null;
    }

    private static int getInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter an integer.");
            }
        }
    }

    private static int getMarks(String msg) {
        while (true) {
            int m = getInt(msg);
            if (m >= 0 && m <= 100) return m;
            System.out.println("Marks must be between 0 and 100.");
        }
    }
}
