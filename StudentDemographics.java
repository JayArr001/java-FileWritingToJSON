import java.util.StringJoiner;

//record/POJO for holding demographic info about students in a convenient object
//each Student object creates a StudentDemographics on instantiation
public record StudentDemographics(String countryCode, int enrolledMonth, int enrolledYear,
                                  int ageAtEnrollment, String gender,
                                  boolean previousProgrammingExperience)
{

    @Override
    public String toString()
    {
        return "%s,%d,%d,%d,%s,%b".formatted(countryCode,
                enrolledMonth,enrolledYear, ageAtEnrollment,gender,
                previousProgrammingExperience);
    }

    //method that formats the student's data and returns a String fit for output to a .json file
    //.json files have specific formatting requirements for compatibility
    public String toJSON()
    {
        return new StringJoiner(", ", "{", "}")
                .add("\"countryCode\":\"" + countryCode + "\"")
                .add("\"enrolledMonth\":" + enrolledMonth)
                .add("\"enrolledYear\":" + enrolledYear)
                .add("\"ageAtEnrollment\":" + ageAtEnrollment)
                .add("\"gender\":\"" + gender + "\"")
                .add("\"previousProgrammingExperience\":" + previousProgrammingExperience)
                .toString();
    }
}
