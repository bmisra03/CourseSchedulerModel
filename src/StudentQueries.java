/**
 *
 * @author bmisr
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getStudentList;
    private static ResultSet resultSet;
    private static PreparedStatement clearData;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    
    //method for clearning Student database file
    public static void clearAllData(){
        connection = DBConnection.getConnection();
        try
        {
            clearData = connection.prepareStatement("truncate table app.STUDENT");
            clearData.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    //method for adding student entry into students database
    public static void addStudent(StudentEntry student){
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.STUDENT (STUDENTID, FIRSTNAME, LASTNAME) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    //method for returning an arraylist of all students 
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getStudentList = connection.prepareStatement("select STUDENTID,FIRSTNAME,LASTNAME from app.student order by LASTNAME");
            resultSet = getStudentList.executeQuery();
            
            while(resultSet.next())
            {
                students.add(new StudentEntry(resultSet.getString(1),
                        resultSet.getString(2),resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        try
        {
            getStudent = connection.prepareStatement("select FIRSTNAME, LASTNAME from"
                    + " app.STUDENT where STUDENTID=?");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            while(resultSet.next()){
                return new StudentEntry(studentID,resultSet.getString(1),resultSet.getString(2));
            }  
        } 
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return null;
    }
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.STUDENT where"
                    + " STUDENTID=?");
            dropStudent.setString(1,studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
