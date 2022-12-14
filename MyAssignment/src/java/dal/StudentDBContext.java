/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attendance;
import model.Group;
import model.Lecturer;
import model.Room;
import model.Session;
import model.Student;
import model.Subject;
import model.TimeSlot;

/**
 *
 * @author MANH
 */
public class StudentDBContext extends DBContext<Student> {

    @Override
    public void insert(Student model) {
        try {
            String sql = "INSERT INTO Student(stdid, stdname)\n"
                    + "VALUES (?, ?)"
                    + "INSERT into Student_Group values (?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getId());
            stm.setString(2, model.getName());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void update(Student model) {
        try {
            connection.setAutoCommit(false);
            String sql = "Update Student set stdname = ? where stdid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getId());
            stm.setString(1, model.getName());
            stm.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Student model) {
        try {
            String sql = "Delete Student_Group where stdid = ?"
                    + "Delete Student where stdid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Student get(int id) {
//        try {
//            String sql = "select s.stdid, s.stdname, s.stdgender, s.stddob, sg.gid, g.gname, g.lid from Student s\n"
//                    + "left join Student_Group sg on s.stdid = sg.stdid\n"
//                    + "left join [Group] g on g.gid = sg.gid"
//                    + "where s.stdid = ?";
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setInt(1, id);
//            ResultSet rs = stm.executeQuery();
//            Student s = null;
//            while (rs.next()) {
//                if (s == null) {
//                    s = new Student();
//                    int stdid = rs.getInt("stdid");
//                    String stdname = rs.getString("stdname");
//                    boolean stdgender = rs.getBoolean("stdgender");
//                    Date stddob = rs.getDate("stddob");
//                    s.setId(stdid);
//                    s.setName(stdname);
//                    s.setGender(stdgender);
//                    s.setDob(stddob);
//                    
//                }
//                
//            } 
//            return s;
//        } catch (SQLException ex) {
//            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
        Student s = new Student();
        try {
            String statement = "select stdname from student where stdid = ?";
            PreparedStatement stm = connection.prepareStatement(statement);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            String name = rs.getString(1);
            s.setId(id);
            s.setName(name);
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    @Override
    public ArrayList<Student> list() {
//        ArrayList<Student> students = new ArrayList<>();
//        String sql = "Select stdid, stdname, stdgender, stddob from Student";
//        try {
//            PreparedStatement stm = connection.prepareStatement(sql);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                Student s = new Student();
//                int stdid = rs.getInt("stdid");
//                String stdname = rs.getString("stdname");
//                boolean stdgender = rs.getBoolean("stdgender");
//                Date stddob = rs.getDate("stddob");
//                s.setId(stdid);
//                s.setName(stdname);
//                students.add(s);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return null;
    }

    public Student get(int id, int gid) {
        try {
            Student student = null;
            Group g = new Group();
            g.setId(gid);
            ArrayList<Group> groups = new ArrayList<>();
            groups.add(g);
            Attendance attend;
            ArrayList<Attendance> attendances = new ArrayList<>();
            String statement = "select s.stdname, ss.sesid, a.present, ss.rid, ss.date, ss.tid, ss.lid, ss.attanded\n"
                    + "						from Student s join Attandance a on s.stdid = a.stdid\n"
                    + "						join [Session] ss on ss.sesid = a.sesid\n"
                    + "						where s.stdid = ? and ss.gid = ?";
            PreparedStatement pstm = connection.prepareStatement(statement);
            pstm.setInt(1, id);
            pstm.setInt(2, gid);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                if (student == null) {
                    student = new Student();

                    student.setId(id);
                    student.setName(rs.getString(1));

                    attend = new Attendance();
                    Session ses = new Session();
                    ses.setId(rs.getInt(2));
                    attend.setPresent(rs.getBoolean(3));
                    
                    Room room = new Room();
                    room.setId(rs.getInt(4));
                    ses.setRoom(room);
                    ses.setDate(rs.getDate(5));
                    
                    TimeSlot timeSlot = new TimeSlot();
                    timeSlot.setId(rs.getInt(6));
                    ses.setTimeslot(timeSlot);
                    
                    Lecturer lecturer = new Lecturer();
                    lecturer.setId(rs.getInt(7));
                    ses.setLecturer(lecturer);
                    ses.setAttandated(rs.getBoolean(8));
                    attend.setSession(ses);
                    attendances.add(attend);
                } else {
                    attend = new Attendance();
                    Session ses = new Session();
                    ses.setId(rs.getInt(2));
                    attend.setPresent(rs.getBoolean(3));
                    
                    Room room = new Room();
                    room.setId(rs.getInt(4));
                    ses.setRoom(room);
                    ses.setDate(rs.getDate(5));
                    
                    TimeSlot timeSlot = new TimeSlot();
                    timeSlot.setId(rs.getInt(6));
                    ses.setTimeslot(timeSlot);
                    
                    Lecturer lecturer = new Lecturer();
                    lecturer.setId(rs.getInt(7));
                    ses.setLecturer(lecturer);
                    ses.setAttandated(rs.getBoolean(8));
                    attend.setSession(ses);
                    attendances.add(attend);
                }
            }
            student.setGroups(groups);
            student.setAttandances(attendances);
            return student;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Student> getByGroup(int grid) {
        ArrayList<Student> list = new ArrayList<>();
        Attendance att;
        Student s;
        Lecturer lec;
        Group gr;
        Subject sub;
        try {

            String statement = "select std.stdid, std.masv, std.stdname ,att.present, lec.lname, gr.sem, gr.[year], gr.class, sub.subname\n"
                    + "							from Student std \n"
                    + "							join Student_Group sg on std.stdid = sg.stdid\n"
                    + "							join [Group] gr on gr.gid = sg.gid\n"
                    + "							join Attandance att on att.stdid = std.stdid\n"
                    + "							join Lecturer lec on lec.lid = gr.lid\n"
                    + "							join Subject sub on sub.subid = gr.subid\n"
                    + "							where gr.gid = ?"
                    + "                                                 order by std.stdid";

            PreparedStatement pstm = connection.prepareStatement(statement);
            pstm.setInt(1, grid);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            s = new Student();
            s.setId(rs.getInt("stdid"));
            s.setCode(rs.getString("masv"));
            s.setName(rs.getString("stdname"));
            att = new Attendance();
            att.setPresent(rs.getBoolean("present"));
            s.getAttandances().add(att);
            gr = new Group();
            gr.setSemester(rs.getString("sem"));
            gr.setYear(rs.getInt("year"));
            gr.setClassname(rs.getString("class"));
            lec = new Lecturer();
            lec.setName(rs.getString("lname"));
            gr.setSupervisor(lec);
            sub = new Subject();
            sub.setName(rs.getString("subname"));
            gr.setSubject(sub);
            s.getGroups().add(gr);
            list.add(s);
            while (rs.next()) {
                int stdid = rs.getInt("stdid");
                if (s.getId() == stdid) {
                    att = new Attendance();
                    att.setPresent(rs.getBoolean("present"));
                    s.getAttandances().add(att);
                } else {
                    s = new Student();
                    s.setId(rs.getInt("stdid"));
                    s.setCode(rs.getString("masv"));
                    s.setName(rs.getString("stdname"));
                    att = new Attendance();
                    att.setPresent(rs.getBoolean("present"));
                    s.getAttandances().add(att);
                    gr = new Group();
                    gr.setSemester(rs.getString("sem"));
                    gr.setYear(rs.getInt("year"));
                    gr.setClassname(rs.getString("class"));
                    lec = new Lecturer();
                    lec.setName(rs.getString("lname"));
                    gr.setSupervisor(lec);
                    sub = new Subject();
                    sub.setName(rs.getString("subname"));
                    gr.setSubject(sub);
                    s.getGroups().add(gr);
                    list.add(s);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
