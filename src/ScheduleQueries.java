/**
 *
 * @author bmisr
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addSchedule;
    private static PreparedStatement getStudentSchedule;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduledStudentByCourse;
    private static PreparedStatement getWaitlistedStudentByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static PreparedStatement clearData;
    private static ResultSet resultSet;
    
    //method for clearing Schedule database
    public static void clearAllData(){
        connection = DBConnection.getConnection();
        try
        {
            clearData = connection.prepareStatement("truncate table app.SCHEDULE");
            clearData.executeUpdate();

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void addScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("insert into app.SCHEDULE "
                    + "(SEMESTER, COURSECODE, STUDENTID, STATUS, TIMESTAMP) values (?,?,?,?,?)");
            addSchedule.setString(1, entry.getSemester());
            addSchedule.setString(2, entry.getCourseCode());
            addSchedule.setString(3, entry.getStudentID());
            addSchedule.setString(4, entry.getStatus());
            addSchedule.setTimestamp(5, entry.getTimestamp());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> entries = new ArrayList<ScheduleEntry>();
        try
        {
            getStudentSchedule = connection.prepareStatement("select SEMESTER,COURSECODE,STUDENTID,STATUS,TIMESTAMP"
                    + " from app.SCHEDULE where SEMESTER=? and STUDENTID=?");
            getStudentSchedule.setString(1,semester);
            getStudentSchedule.setString(2,studentID);
            resultSet = getStudentSchedule.executeQuery();
            
            while(resultSet.next())
            {
                entries.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2),
                resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entries;
        
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode){
        connection = DBConnection.getConnection();
        int studentCount=0;
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select STUDENTID"
            +" from app.SCHEDULE where SEMESTER=? AND COURSECODE=?");
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            while(resultSet.next()){
                studentCount+=1;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentCount;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> entries = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduledStudentByCourse = connection.prepareStatement("select SEMESTER,COURSECODE,STUDENTID,STATUS,TIMESTAMP"
                    + " from app.SCHEDULE where SEMESTER=? and COURSECODE=? and STATUS=? order by TIMESTAMP");
            getScheduledStudentByCourse.setString(1,semester);
            getScheduledStudentByCourse.setString(2,courseCode);
            getScheduledStudentByCourse.setString(3,"S");
            resultSet = getScheduledStudentByCourse.executeQuery();
            while(resultSet.next())
            {
                entries.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2),
                resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entries;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> entries = new ArrayList<ScheduleEntry>();
        try
        {
            getWaitlistedStudentByCourse = connection.prepareStatement("select SEMESTER,COURSECODE,STUDENTID,STATUS,TIMESTAMP"
                    + " from app.SCHEDULE where SEMESTER=? and COURSECODE=? and STATUS=? order by TIMESTAMP");
            getWaitlistedStudentByCourse.setString(1,semester);
            getWaitlistedStudentByCourse.setString(2,courseCode);
            getWaitlistedStudentByCourse.setString(3,"W");
            resultSet = getWaitlistedStudentByCourse.executeQuery();
            while(resultSet.next())
            {
                entries.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2),
                resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entries;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.SCHEDULE where"
                    + " SEMESTER=? and STUDENTID=? and COURSECODE=?");
            dropStudentScheduleByCourse.setString(1,semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3,courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.SCHEDULE where"
                    + " SEMESTER=? and COURSECODE=?");
            dropScheduleByCourse.setString(1,semester);
            dropScheduleByCourse.setString(2,courseCode);
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try
        {
            updateScheduleEntry = connection.prepareStatement("update app.SCHEDULE "
                    + "set STATUS='S' where SEMESTER=? and COURSECODE=? and STUDENTID=?");
            updateScheduleEntry.setString(1,semester);
            updateScheduleEntry.setString(2,entry.getCourseCode());
            updateScheduleEntry.setString(3,entry.getStudentID());
            updateScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
