import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.rvanasa.schoology.impl.SchoologyResponseBody;

public class SchoologyRequestHandler {
    final Gson gson = new Gson();
    final JsonParser jsonParser = new JsonParser();
    public User initializeUser(int userID, String domain, String key, String secret) {
        // Get user names
        final net.rvanasa.schoology.impl.SchoologyRequestHandler schoology = new net.rvanasa.schoology.impl.SchoologyRequestHandler(domain, key, secret);
        SchoologyResponseBody response = schoology.get("users/" + String.valueOf(userID)).requireSuccess().getBody();
        JsonObject json = (JsonObject) jsonParser.parse(response.getRawData());
        String firstName = json.get("name_first").getAsString();
        System.out.println(firstName);
        String lastName = json.get("name_last").getAsString();
        System.out.println(lastName);

        // Initializes user courses
        response = schoology.get("users/"+ String.valueOf(userID) + "/sections").requireSuccess().getBody();
        json = (JsonObject) jsonParser.parse(response.getRawData());
        JsonArray courses = json.get("section").getAsJsonArray();
        Course[] sections = gson.fromJson(courses, Course[].class);


        for(int i = 0; i < sections.length; i++) {
            System.out.println("Getting course data for "+ "\""+ sections[i].getCourse_title()+ "\"");
            // Initializes user assignments for all courses, placing into an Assignments array within a Course object
            SchoologyResponseBody sectionResponse = schoology.get("sections/"+ sections[i].getId() + "/assignments").requireSuccess().getBody();
            JsonObject assignmentJson = (JsonObject) jsonParser.parse(sectionResponse.getRawData());
            JsonArray assignmentArray = assignmentJson.get("assignment").getAsJsonArray();
            Assignments[] assignments = gson.fromJson(assignmentArray, Assignments[].class);

            for(int j = 0; j < assignments.length; j++) {
                // Initialize user submissions/revisions for each assignment
                SchoologyResponseBody submissionResponse = schoology.get("sections/"+ sections[i].getId() +"/submissions/"+ assignments[j].getGrade_item_id()).requireSuccess().getBody();
                JsonObject submissionJson = (JsonObject) jsonParser.parse(submissionResponse.getRawData());
                JsonArray submissionArray = submissionJson.get("revision").getAsJsonArray();
                Submissions[] submissions = gson.fromJson(submissionArray, Submissions[].class);
                assignments[j].setSubmissions(submissions);
                System.out.println(j);
            }

            sections[i].setAssignments(assignments);

        }

        // Places all Course objects into a User object to return
        User user = new User(userID, sections, firstName, lastName, domain, key, secret);

        return user;
    }

}
