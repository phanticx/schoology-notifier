public class Course {
    private long id;
    private String course_title;
    private String course_code;
    private long course_id;
    private String section_title;
    private String location;
    private Assignments[] assignments;

    public Course(long id, String course_title, String course_code, long course_id, String section_title, String location) {
        this.id = id;
        this.course_title = course_title;
        this.course_code = course_code;
        this.course_id = course_id;
        this.section_title = section_title;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public Assignments[] getAssignments() {
        return assignments;
    }

    public void setAssignments(Assignments[] assignments) {
        this.assignments = assignments;
    }

    public long getCourse_id() {
        return course_id;
    }

    public String getSection_title() {
        return section_title;
    }

    public String getLocation() {
        return location;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    public void setSection_title(String section_title) {
        this.section_title = section_title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", course_title='" + course_title + '\'' +
                ", course_code='" + course_code + '\'' +
                ", course_id=" + course_id +
                ", section_title='" + section_title + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
