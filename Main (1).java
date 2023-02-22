import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String name;
    private int hoursPerWeek;
    private String date;

    public Course(String name, int hoursPerWeek, String date) {
        this.name = name;
        this.hoursPerWeek = hoursPerWeek;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return name + "," + hoursPerWeek + "," + date;
    }
}

class OnlineCourse extends Course {
    private String website;

    public OnlineCourse(String name, int hoursPerWeek, String date, String website) {
        super(name, hoursPerWeek, date);
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return super.toString() + "," + website;
    }
}

public class Main {
    private static final String FILENAME = "courses.csv";
    private static List<Course> courses = new ArrayList<>();

    public static void main(String[] args) {
        readFromFile();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Add Course");
            System.out.println("2. Update Course");
            System.out.println("3. Delete Course");
            System.out.println("4. Display Courses");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    updateCourse();
                    break;
                case 3:
                    deleteCourse();
                    break;
                case 4:
                    displayCourses();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }

        writeToFile();
    }
    private static void addCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();
        System.out.print("Enter hours per week: ");
        int hoursPerWeek = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter date (format: dd/MM/yyyy): ");
        String date = scanner.nextLine();

        System.out.print("Is this an online course? (y/n): ");
        char online = scanner.nextLine().charAt(0);
        Course course;
        if (online == 'y') {
            System.out.print("Enter website: ");
            String website = scanner.nextLine();
            course = new OnlineCourse(name, hoursPerWeek, date, website);
        } else {
            course = new Course(name, hoursPerWeek, date);
        }
        courses.add(course);
        System.out.println("Course added");
    }

    private static void updateCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter course name to update: ");
        String name = scanner.nextLine();
        Course course = findCourse(name);
        if (course == null) {
            System.out.println("Course not found");
            return;
        }
        System.out.print("Enter new hours per week: ");
        int hoursPerWeek = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new date (format: dd/MM/yyyy): ");
        String date = scanner.nextLine();

        course.setHoursPerWeek(hoursPerWeek);
        course.setDate(date);

        if (course instanceof OnlineCourse) {
            System.out.print("Enter new website: ");
            String website = scanner.nextLine();
            ((OnlineCourse) course).setWebsite(website);
        }
        System.out.println("Course updated");
    }

    private static void deleteCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter course name to delete: ");
        String name = scanner.nextLine();
        Course course = findCourse(name);
        if (course == null) {
            System.out.println("Course not found");
            return;
        }
        courses.remove(course);
        System.out.println("Course deleted");
    }

    private static Course findCourse(String name) {
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }
        return null;
    }

    private static void displayCourses() {
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private static void readFromFile() {
        File file = new File(FILENAME);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Course course;
                if (data.length == 4) {
                    course = new OnlineCourse(data[0], Integer.parseInt(data[1]), data[2], data[3]);
                } else {
                    course = new Course(data[0], Integer.parseInt(data[1]), data[2]);
                }
                courses.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile() {
        File file = new File(FILENAME);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Course course : courses) {
                bw.write(course.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/*-------------------------------------------------------the objectif of the code ------------------------------------------------
In this code,
 you can read the course information from a CSV file and write the course information to a CSV file.
 You can also add a new course, update an existing course, and delete a course. 
 The program uses inheritance to create two types of courses: regular courses and online courses.
 -------------------------------------------------------------------------------------------------------------------------------*/
/*================================================general overview of the code==================================================*/
/*
The code uses object-oriented programming (OOP) principles in Java to store and manage coursework information.

There are two classes defined in the code: Course and OnlineCourse. The Course class represents a regular course and the OnlineCourse class represents an online course. The OnlineCourse class extends the Course class and inherits its properties and methods.

The Course class has three properties: name, hoursPerWeek, and date. The OnlineCourse class has an additional property url.

The Course class has a toString method that returns a string representation of the course information in CSV format. The OnlineCourse class overrides the toString method to include the URL of the online course.

The main class CourseManager has a courses list to store the course information.

The CourseManager class has four methods:

addCourse method adds a new course to the courses list.
updateCourse method updates an existing course in the courses list.
deleteCourse method deletes a course from the courses list.
displayCourses method displays the course information stored in the courses list.
The readFromFile method reads the course information from a CSV file and creates instances of Course or OnlineCourse classes based on the data in the file.

The writeToFile method writes the course information stored in the courses list to a CSV file.

The program uses a try-with-resources block to handle exceptions when reading from or writing to the file.

======================================================================================================*/