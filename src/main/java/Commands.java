import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Commands {

    private User user;
    public String check(String message) {
        String reply = "";
        switch(message) {
            case "/help":
                System.out.println("Calling /help");
                reply = help();
                break;
            case "/init":
            case "/initialize":
                System.out.println("Calling /initialize");
                reply = initialize();
                break;
            case "/overdue":
                System.out.println("Calling /overdue");
                reply = overdue();
                break;
            case "/settings":
                System.out.println("Calling /settings");
                reply = "geg3rt3r34yheue";
                break;
            default:
                reply = "Unknown command. Please try again.";
                break;
        }

        return reply;
    }

    public String help() {
        return "This is an automated Schoology Notifier to remind you wen upcoming assignments are due.";
    }
    public String initialize() {
        this.user = new Schoology().initializeUser(userID, userDomain, userAPIKey, userAPISecret);
        updateDatabase(userID, user);

        return "Initialization complete. First course: " + user.getCourses()[0].getCourse_title();
    }

    public String overdue() {
        String overdueAssignments ="";
        if (this.user == null) {
            return "Please run /initialize first.";
        }
        for (Course course : this.user.getCourses()) {
            for (Assignments assignment : course.getAssignments()) {
                if (!assignment.isSubmitted()) {
                    // overdueAssignments = overdueAssignments.concat("Course Title: " + course.getCourse_title() +", Assignment Name: "+ assignment.getTitle() + ", Due Date: " + assignment.getDue().substring(0,9) + "\n");
                    overdueAssignments = overdueAssignments.concat("" + course.getCourse_title() +", "+ assignment.getTitle() + ", "+ assignment.getDue() + "\n");
                }
            }
        }
        return overdueAssignments;
    }
    public void settings() {

    }

    private Connection connect() {
        String url = "jdbc:sqlite:users.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void updateDatabase(int userID, User user) {
        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS users (" +
                         "userid INTEGER NOT NULL UNIQUE,"  +
                         "user BLOB NOT NULL UNIQUE);"
             )) {
            statement.executeUpdate();

            PreparedStatement statement2 = conn.prepareStatement(
                    "SELECT EXISTS (" +
                        "SELECT 1 " +
                        "FROM users " +
                        "WHERE userid = ?" +
                        "LIMIT 1 )");
            statement2.setInt(1, userID);
            ResultSet result = statement2.executeQuery();

            if (!result.isBeforeFirst()) {
                    PreparedStatement statement3 = conn.prepareStatement(
                            "UPDATE users " +
                                "SET user = ?, " +
                                "WHERE userid = ?" );
                    statement3.setBytes(1, makeBytes(user));
                    statement3.setInt(2, userID);
                    statement3.executeUpdate();
            } else {
                    PreparedStatement statement3 = conn.prepareStatement(
                            "INSERT INTO users (userid, user)" +
                                "VALUES(?,?);");
                    statement3.setInt(1, userID);
                    statement3.setBytes(2, makeBytes(user));
                    statement3.executeUpdate();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public byte[] makeBytes(User user) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(user);
            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User readBytes(byte[] data) {
        try {
            ByteArrayInputStream baip = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(baip);
            User user = (User) ois.readObject();
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
