import java.io.Serializable;

public class User implements Serializable {
    private int userID;
    private Course[] courses;
    private String firstName;
    private String lastName;
    private String userDomain;
    private String userAPIKey;
    private String userAPISecret;

    public User(int userID, Course[] courses) {
        this.userID = userID;
        this.courses = courses;
    }

    public User(int userID, Course[] courses, String firstName, String lastName, String userDomain, String userAPIKey, String userAPISecret) {
        this.userID = userID;
        this.courses = courses;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userDomain = userDomain;
        this.userAPIKey = userAPIKey;
        this.userAPISecret = userAPISecret;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(String userDomain) {
        this.userDomain = userDomain;
    }

    public String getUserAPIKey() {
        return userAPIKey;
    }

    public void setUserAPIKey(String userAPIKey) {
        this.userAPIKey = userAPIKey;
    }

    public String getUserAPISecret() {
        return userAPISecret;
    }

    public void setUserAPISecret(String userAPISecret) {
        this.userAPISecret = userAPISecret;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }
}
