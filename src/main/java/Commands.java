import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Commands {

    private User user;
    public String check(String message) {
        String reply = "";
        switch(message.replace("/", "")) {
            case "help":
                System.out.println("Calling /help");
                reply = help();
                break;
            case "init":
            case "initialize":
                System.out.println("Calling /initialize");
                reply = initialize();
                break;
            case "overdue":
                System.out.println("Calling /overdue");
                reply = overdue();
                break;
            case "settings":
                System.out.println("Calling /settings");
                reply = "geg3rt3r34yheue";
                break;
            case "temp":
                System.out.println("Calling /temp");
                reply = temp();
                break;
            default:
                reply = "Unknown command. Please try again.";
                break;
        }

        return reply;
    }

    public String help() {
        return "This is an automated Schoology Notifier to remind you when upcoming assignments are due.";
    }

    public String initialize() {
        final int userID = ;
        final String userDomain = "";
        final String userAPIKey = "";
        final String userAPISecret = "";
        User user = new Schoology().initializeUser(userID, userDomain, userAPIKey, userAPISecret);
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

    public String temp() {
        User user = queryDatabase();
        System.out.println(user);
        return user.getUserDomain();
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
        System.out.println("Connection to SQL database established");
        return conn;
    }

    private void updateDatabase(int userID, User user) {
        tableExists();
        try (Connection conn = this.connect();) {
            if (userExists(userID)) {
                PreparedStatement statement = conn.prepareStatement(
                        "UPDATE users " +
                            "SET user = ? " +
                            "WHERE userid = ?;" );
                statement.setBytes(1, makeBytes(user));
                statement.setInt(2, userID);
                statement.executeUpdate();
            } else {
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO users (userid, user) " +
                            "VALUES (?, ?);");
                statement.setInt(1, userID);
                statement.setBytes(2, makeBytes(user));
                statement.executeUpdate();
            }
            conn.close();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void tableExists() {
        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS users (" +
                         "userid INTEGER NOT NULL UNIQUE,"  +
                         "user MEDIUMBLOB NOT NULL UNIQUE);")) {
            statement.executeUpdate();
            conn.close();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(int userID) {
        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(
                     "SELECT EXISTS (" +
                             "SELECT * " +
                             "FROM users " +
                             "WHERE userid = ?" +
                             "LIMIT 1 )")) {
            statement.setInt(1, userID);
            ResultSet result = statement.executeQuery();

            if (!result.isBeforeFirst()) {
                conn.close();
                return false;
            } else {
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User queryDatabase(int userID) {
        try(Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT user " +
                        "FROM users " +
                        "WHERE userid = ?;")) {
            statement.setInt(1, userID);
            ResultSet result = statement.executeQuery();
            byte[] byteArray = result.getBytes("user");
            User user = readBytes(byteArray);
            conn.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public byte[] makeBytes(User user) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(user);
            byte bytes[] = bos.toByteArray();
            oos.close();
            return bytes;
           } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User readBytes(byte[] data) {
        try {
            ByteArrayInputStream bias = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bias);
            User user = (User) ois.readObject();
            ois.close();
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
