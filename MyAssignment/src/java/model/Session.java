/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author MANH
 */
public class Session {

    private int id;
    private Group group;
    private Room room;
    private Lecturer lecturer;
    private Date date;
    private int index;
    private boolean attandated;
    private TimeSlot timeslot;
    private ArrayList<Attendance> attandances = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public TimeSlot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(TimeSlot timeslot) {
        this.timeslot = timeslot;
    }

    public java.util.Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Boolean isAttandated() {
        return attandated;
    }

    public void setAttandated(Boolean attandated) {
        this.attandated = attandated;
    }

    public ArrayList<Attendance> getAttandances() {
        return attandances;
    }

    public void setAttandances(ArrayList<Attendance> attandances) {
        this.attandances = attandances;
    }

}
