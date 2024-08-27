import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

//the Student object represents any one particular student in a school
//it holds demographic information about the student, such as age/location/gender
//plus contains a collection of their classes and engagement in each of those classes

//demographic information is required to construct an instance of the Student
//a Student can theoretically have no classes as well
public class Student
{
    private long studentID;
    private List<Course> coursesEnrolled;
    private Map<String, CourseEngagement> engagementMap;
    private final StudentDemographics demographics;

    public static int lastStudentID = 0;

    //enrolls the Student into a course, with today as the enrollment date
    //it also adds the corresponding information to their engagement map
    public void addCourse(Course inputCourse)
    {
        if(engagementMap == null)
        {
            engagementMap = new HashMap<>();
        }
        String courseID = inputCourse.getCourseCode();
        LocalDate now = LocalDate.now();
        CourseEngagement ce = new CourseEngagement(inputCourse, now, inputCourse.getTitle(),
                0, now);
        engagementMap.put(courseID, ce);
    }

    //overloaded method which allows function call to specify an enroll date
    //the first lecture watched is recorded as the same input date
    public void addCourse(Course inputCourse, LocalDate enrollDate)
    {
        if(engagementMap == null)
        {
            engagementMap = new HashMap<>();
        }
        String courseID = inputCourse.getCourseCode();
        LocalDate now = LocalDate.now();
        CourseEngagement ce = new CourseEngagement(inputCourse, enrollDate, inputCourse.getTitle(),
                0, now);
        engagementMap.put(courseID, ce);
    }

    //function that simulates a student "watching" the lecture, records date of when it was watched
    //puts that data to their personalized map
    public void watchLecture(String inputCourseCode, int lectureNumber, LocalDate watchTime)
    {
        if(engagementMap.get(inputCourseCode) == null)
        {
            System.out.println("course not found in watchLecture() call");
            return;
        }
        engagementMap.get(inputCourseCode).watchLecture(lectureNumber, watchTime);
    }

    //populates a new random student
    //for the sake of demonstration, a random array of values is coded in
    //a more dynamic experience might allow args for country codes, genders, or other demographics
    //the only argument is a varying amount of courses to enroll a student in
    public static Student populateNewRandomStudent(Course... courseList)
    {
        Random random = new Random();

        //example demographics used
        String[] countryCodes = {"US", "CA", "UK", "CN", "KR", "JP", "AU"}; //using big countries for simplicity
        String[] genders = {"Male", "Female"}; //only using major 2 genders for simplicity
        int randomCountryId = random.nextInt(countryCodes.length);
        int randomEnrollmentMonth = random.nextInt(1,13);
        int randomEnrollmentYear = LocalDate.now().getYear() - random.nextInt(10);
        int randomAge = random.nextInt(17, 70);
        int randomGender = random.nextInt(genders.length);
        Student newStudent = new Student(lastStudentID++, countryCodes[randomCountryId],
                randomEnrollmentMonth, randomEnrollmentYear, randomAge,
                genders[randomGender], random.nextBoolean());

        //for all courses passed in by the method argument
        //enroll the student with a random enrollment date bounded by their initial enrollment in school
        //additionally, sets their first watched lecture to that date
        for(Course c : courseList)
        {
            int yearsToSub = LocalDate.now().getYear() - randomEnrollmentYear;
            LocalDate enrollDate = LocalDate.now().minusYears(yearsToSub);
            newStudent.addCourse(c, enrollDate);

            //set the last watched lecture time somewhere between 0 and 2100 days ago
            LocalDate randomDate = LocalDate.now().minusDays(random.nextLong(2100));
            newStudent.watchLecture(c.getCourseCode(), random.nextInt(1, c.getLectureCount()), randomDate);
        }
        return newStudent;
    }

    //constructor with optional varying argument to pass a course list
    public Student(long studentID, String countryCode, int monthEnrolled, int yearEnrolled,
                   int ageEnrolled, String gender, boolean programmingExperience, Course... courseList)
    {
        this.studentID = studentID;
        coursesEnrolled = List.of(courseList);
        engagementMap = new HashMap<>();
        Random random = new Random();
        demographics = new StudentDemographics(countryCode, monthEnrolled, yearEnrolled,
                ageEnrolled, gender, programmingExperience);

        for(Course c : courseList)
        {
            addCourse(c);

            //set the last watched lecture time somewhere between 0 and 2100 days ago
            LocalDate randomDate = LocalDate.now().minusDays(random.nextLong(2100));
            watchLecture(c.getCourseCode(), random.nextInt(30),randomDate);
        }
    }

    //helper function to select a random value from string[] of data
    public static String getRandomValue(String...data)
    {
        Random random = new Random();
        return data[random.nextInt(data.length)];
    }

    //getters and setters
    public long getStudentID() {
        return studentID;
    }

    public StudentDemographics getDemographics()
    { return demographics; }

    //Returns defensive copy of the map, changes made to this do not affect source
    public Map<String, CourseEngagement> getMapCopy()
    {
        Map<String, CourseEngagement> copy = new HashMap<>(engagementMap);
        return copy;
    }

    //Returns the real map, and changes to the returned var mutate source map
    public Map<String, CourseEngagement> getMapSource()
    {
        return engagementMap;
    }

    //method that formats the student's data and returns a String fit for output to a .json file
    //.json files have specific formatting requirements for compatibility
    public String toJSON()
    {
        StringJoiner courses = new StringJoiner(",", "[", "]");
        for(Course c : coursesEnrolled)
        {
            courses.add(c.toJSON());
        }
        String engagement = engagementMap.values().stream()
                .map(CourseEngagement::toJSON)
                .collect(Collectors.joining(",", "[","]"));
        return new StringJoiner(",", "{", "}")
                .add("\"studentId\":" + studentID)
                .add("\"demographics\":" + demographics.toJSON())
                .add("\"coursesEnrolled\":" + courses)
                .add("\"engagement\":" + engagement)
                .toString();
    }

    @Override
    public String toString()
    {
        return "Student{" +
                "studentID=" + studentID +
                ", demographics=" + demographics.toString() +
                ", engagementMap=" + engagementMap +
                '}';
    }
}
