/**
 *
 * @author bmisr
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseList;
    private static PreparedStatement getCourseCodeList;
    private static PreparedStatement getCourseSeats;
    private static ResultSet resultSet;
    private static PreparedStatement clearData;
    private static PreparedStatement dropCourse;
    
    public static void clearAllData(){
        connection = DBConnection.getConnection();
        try
        {
            clearData = connection.prepareStatement("truncate table app.COURSE");
            clearData.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void addCourse(CourseEntry course){
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.COURSE "
                    + "(SEMESTER, COURSECODE, DESCRIPTION, SEATS) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<CourseEntry> getAllCourses(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try
        {
            getCourseList = connection.prepareStatement("select SEMESTER,COURSECODE,DESCRIPTION,SEATS"
                    + " from app.COURSE where SEMESTER=?");
            getCourseList.setString(1,semester);
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                courses.add(new CourseEntry(resultSet.getString(1), resultSet.getString(2),
                resultSet.getString(3),resultSet.getInt(4)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester){
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try
        {
            getCourseCodeList = connection.prepareStatement("select COURSECODE"
                    + " from app.COURSE where SEMESTER=? order by COURSECODE");
            getCourseCodeList.setString(1,semester);
            resultSet = getCourseCodeList.executeQuery();
            
            while(resultSet.next())
            {
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
    }
    
    public static int getCourseSeats(String semester, String courseCode){
        connection = DBConnection.getConnection();
        int numSeats=0;
        try
        {
            getCourseSeats = connection.prepareStatement("select SEATS"
            +" from app.COURSE where SEMESTER=? AND COURSECODE=?");
            getCourseSeats.setString(1, semester);
            getCourseSeats.setString(2, courseCode);
            resultSet = getCourseSeats.executeQuery();
            numSeats = resultSet.getInt(1);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return numSeats;
    }
    
    public static void dropCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            dropCourse = connection.prepareStatement("delete from app.Course where"
                    + " SEMESTER=? and COURSECODE=?");
            dropCourse.setString(1,semester);
            dropCourse.setString(2,courseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

}
