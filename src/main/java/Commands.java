import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
        final int userID = ;
        final String userDomain = "";
        final String userAPIKey = "";
        final String userAPISecret = "";
        this.user = new Schoology().initializeUser(userID, userDomain, userAPIKey, userAPISecret);

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
}
