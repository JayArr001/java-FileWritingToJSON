import java.util.StringJoiner;

//a POJO with extra functionality beyond a record
//this object stores information about any particular course
//including a course code, name of the course, and how many lectures ith as
public final class Course
{
    private String courseCode;
    private String title;
    private int lectureCount;

    //default constructor to create a new Course object
    public Course(String inputCode, String inputTitle, int InputLect)
    {
        courseCode = inputCode;
        title = inputTitle;
        lectureCount = InputLect;
    }

    //overloaded constructor which allows omission of a maximum lecture count, defaults to 1
    public Course(String inputCode, String inputTitle)
    {
        this(inputCode, inputTitle, 1);
    }

    //method that formats the student's data and returns a String fit for output to a .json file
    //.json files have specific formatting requirements for compatibility
    public String toJSON()
    {
        return new StringJoiner(", ", "{", "}")
                .add("\"courseCode\":\"" + courseCode + "\"")
                .add("\"title\":\"" + title + "\"")
                .toString();
    }

    //getters and setters
    public String getCourseCode()
    { return courseCode; }

    public String getTitle()
    { return title; }

    public int getLectureCount()
    { return lectureCount; }

}
