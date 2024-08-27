import java.time.LocalDate;
import java.util.StringJoiner;

//this could almost have been a record. it is very close to a POJO
//but there are some extra functions available that a record doesn't support

//this object holds metadata about course engagement (aka, "attendance" and enrollment) for a student
//each Student object possesses a CourseEngagement object for each class they are enrolled in
public class CourseEngagement
{
    private Course course;
    private LocalDate enrollmentDate;
    private String engagementType;

    private final String courseCode;

    private final int enrollmentMonth;
    private final int enrollmentYear;

    private int lastLecture;
    private int lastActiveMonth;
    private int lastActiveYear;

    //a function that simulates a student watching a lecture
    //records the date they "watched" it and which lecture #
    public void watchLecture(int lectureNumber, LocalDate inputDate)
    {
        lastLecture = lectureNumber;
        if(lectureNumber > course.getLectureCount())
        {
            lastLecture = course.getLectureCount(); //set to max number
        }
        lastActiveMonth = inputDate.getMonthValue();
        lastActiveYear = inputDate.getYear();
    }

    public CourseEngagement(Course inputCourse, LocalDate inputEnrollmentDate,
                            String inputEngagementType, int inputLastLecture, LocalDate inputLastActivity)
    {
        course = inputCourse;
        courseCode = course.getCourseCode();
        enrollmentDate = inputEnrollmentDate;
        enrollmentMonth = enrollmentDate.getMonthValue();
        enrollmentYear = enrollmentDate.getYear();
        engagementType = inputEngagementType;
        lastLecture = inputLastLecture;
        lastActiveMonth = inputLastActivity.getMonthValue();
        lastActiveYear = inputLastActivity.getYear();
    }

    //getters and setters
    public String getCourseCode()
    { return course.getCourseCode(); }

    public int getEnrollmentYear()
    { return enrollmentDate.getYear(); }

    public int getLastActivityYear()
    { return lastActiveYear; }

    public int getLastActivityMonth()
    { return lastActiveMonth; }

    public void setCourse(Course course)
    { this.course = course; }

    public void setEnrollmentDate(LocalDate enrollmentDate)
    { this.enrollmentDate = enrollmentDate; }

    public void setEngagementType(String engagementType)
    { this.engagementType = engagementType; }

    public void setLastLecture(int lastLecture)
    { this.lastLecture = lastLecture; }

    @Override
    public String toString()
    {
        return "CourseEngagement{" +
                "course=" + course +
                ", enrollmentDate=" + enrollmentDate +
                ", engagementType='" + engagementType + '\'' +
                ", lastLecture=" + lastLecture +
                ", lastActiveMonth=" + lastActiveMonth +
                ", lastActiveYear=" + lastActiveYear +
                '}';
    }

    //method that formats the student's data and returns a String fit for output to a .json file
    //.json files have specific formatting requirements for compatibility
    public String toJSON()
    {
        return new StringJoiner(", ", "{", "}")
                .add("\"courseCode\":\"" + courseCode + "\"")
                .add("\"engagementType\":\"" + engagementType + "\"")
                .add("\"enrollmentMonth\":" + enrollmentMonth)
                .add("\"enrollmentYear\":" + enrollmentYear)
                .add("\"lastLecture\":" + lastLecture)
                .add("\"lastActiveMonth\":" + lastActiveMonth)
                .add("\"lastActiveYear\":" + lastActiveYear)
                .toString();
    }
}
