import net.rvanasa.schoology.exception.SchoologyException;

import java.io.*;
import java.sql.*;

import static java.lang.Integer.parseInt;

public class Commands {



    public String help() {
        return "This is an automated Schoology Notifier to remind you when upcoming assignments are due.";
    }

    public String initialize(String[] args, long telegramId) throws InvalidUserInputException {
        try {
            final int userID = parseInt(args[0]);
            final String userDomain = args[1];
            final String userAPIKey = args[2];
            final String userAPISecret = args[3];
            User user = new SchoologyRequestHandler().initializeUser(userID, userDomain, userAPIKey, userAPISecret);
            updateDatabase(userID, user, telegramId);
            return "Initialization complete. First course: " + user.getCourses()[0].getCourse_title();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new InvalidUserInputException("bad User id");
        } catch (SchoologyException e) {
            e.printStackTrace();
            throw new InvalidUserInputException("");
        }

    }

    public String overdue(long telegramID) {
        String overdueAssignments = "";
        User user = queryDatabase(telegramID);
        if (user != null) {
            for (Course course : user.getCourses()) {
                for (Assignments assignment : course.getAssignments()) {
                    if (!assignment.isSubmitted()) {
                        // overdueAssignments = overdueAssignments.concat("Course Title: " + course.getCourse_title() +", Assignment Name: "+ assignment.getTitle() + ", Due Date: " + assignment.getDue().substring(0,9) + "\n");
                        overdueAssignments = overdueAssignments.concat("" + course.getCourse_title() +", "+ assignment.getTitle() + ", "+ assignment.getDue() + "\n");
                    }
                }
            }
            return overdueAssignments;
        }

        return "Please run /initialize before checking for overdue assignments!";
    }

    public String temp(long telegramID) {
        User user = queryDatabase(telegramID);
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

    private void updateDatabase(int userID, User user, long telegramID) {
        tableExists();
        System.out.println("Updating database..");
        try (Connection conn = this.connect();) {
            if (userExists(telegramID)) {
                PreparedStatement statement = conn.prepareStatement(
                        "UPDATE data " +
                            "SET user = ?, userid = ? " +
                            "WHERE telegramid = ?;" );
                statement.setBytes(1, makeBytes(user));
                statement.setInt(2, userID);
                statement.setLong(3, telegramID);
                statement.executeUpdate();
            } else {
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO data (telegramid, userid, user) " +
                            "VALUES (?, ?, ?);");
                statement.setLong(1, telegramID);
                statement.setInt(2, userID);
                statement.setBytes(3, makeBytes(user));
                statement.executeUpdate();
            }
            System.out.println("Updated database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void tableExists() {
        System.out.println("Checking if table data exists in users.db and creating if it does not...");
        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS data (telegramid INTEGER, userid INTEGER, user MEDIUMBLOB);")) {
            statement.executeUpdate();
            System.out.println("Finished checking for users.db.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(long telegramID) {
        System.out.println("Checking if user exists in database...");
        User user = queryDatabase(telegramID);
        if (user != null) {
            System.out.println("User exists.");
            return true;
        }
        System.out.println("User does not exist.");
        return false;
    }

    public User queryDatabase(long telegramID) {
        System.out.println("Querying database...");
        try(Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(
                            "SELECT * " +
                            "FROM data " +
                            "WHERE telegramid = ?")) {
            statement.setLong(1, telegramID);
            ResultSet result = statement.executeQuery();
            if (result.next() == false) {
                conn.close();
                return null;
            } else {
                byte[] byteArray = result.getBytes("user");
                User user = readBytes(byteArray);
                conn.close();
                System.out.println("Query completed.");
                return user;
            }
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
