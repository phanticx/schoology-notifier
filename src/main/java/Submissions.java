public class Submissions {
    private int revision_id = 0;
    private long created;
    private int late;

    public Submissions(int revision_id, long created, int late) {
        this.revision_id = revision_id;
        this.created = created;
        this.late = late;
    }

    public int getRevision_id() {
        return revision_id;
    }

    public void setRevision_id(int revision_id) {
        this.revision_id = revision_id;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }

    @Override
    public String toString() {
        return "Submissions{" +
                "revision_id=" + revision_id +
                ", created=" + created +
                ", late=" + late +
                '}';
    }
}
