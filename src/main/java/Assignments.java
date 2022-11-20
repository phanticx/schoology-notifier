import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class Assignments {
    private long id;
    private String title;
    private String description;
    private String due;
    private Date dueDate;
    private long grade_item_id;
    private Submissions[] submissions;
    private boolean submitted = false;

    public Assignments(long id, String title, String description, String due, long grade_item_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.due = due;
        this.grade_item_id = grade_item_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Assignments{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", due='" + due + '\'' +
                ", dueDate=" + dueDate +
                ", grade_item_id=" + grade_item_id +
                ", submissions=" + Arrays.toString(submissions) +
                ", submitted=" + submitted +
                '}';
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
        try {
            this.dueDate = new SimpleDateFormat().parse(due);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public long getGrade_item_id() {
        return grade_item_id;
    }

    public Submissions[] getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Submissions[] submissions) {
        this.submissions = submissions;
        if(submissions.length != 0) {
            submitted = true;
        }
    }

    public void setGrade_item_id(long grade_item_id) {
        this.grade_item_id = grade_item_id;
    }

}
