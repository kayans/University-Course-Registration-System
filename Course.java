package hw1;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Comparable<Course>, Serializable{
	private String courseName;
	private String courseId;
	private int maxNumOfStudents;
	private int currentNumOfStudents;
	ArrayList<String> listOfStudents = new ArrayList<String>();
	private String courseInstructor;
	private int courseSectionNum;
	private String courseLocation; 
	
	Course(String courseName,String courseId,int maxNumOfStudents,
		   int currentNumOfStudents,ArrayList<String> listOfStudents,String courseInstructor,
	       int courseSectionNum,String courseLocation){
		this.courseName=courseName;
		this.courseId=courseId;
		this.maxNumOfStudents=maxNumOfStudents;
		this.currentNumOfStudents=currentNumOfStudents;
		this.listOfStudents=listOfStudents;
		this.courseInstructor=courseInstructor;
		this.courseSectionNum=courseSectionNum;
		this.courseLocation=courseLocation;
	}

	//Display course in a nice way:)
	public void display(){
		System.out.println("Course Name: "+courseName+"\n"
							+"Course ID: "+courseId+"\n"
							+"Max Number of Students: "+maxNumOfStudents+"\n"
							+"Current Number of Students: "+currentNumOfStudents+"\n"
							+"List of Students: "+listOfStudents+" \n"
							+"Course Instructor: "+courseInstructor+"\n"
							+"Course Section Number: "+courseSectionNum+"\n"
							+"Course Location: "+courseLocation);
	}
	
	public String getCourseName(){
		return courseName;
	}
	
	public void setCourseName(String courseName){
		this.courseName=courseName;
	}
	
	public String getCourseId(){
		return courseId;
	}
	
	public void setCourseId(String courseId){
		this.courseId=courseId;
	} 
	
	public int getMaxNumOfStudents(){
		return maxNumOfStudents;
	}
	
	public void setMaxNumOfStudents(int maxNum){
		this.maxNumOfStudents=maxNum;
	}
	
	public int getCurrentNumOfStudents(){
		return currentNumOfStudents;
	}
	
	public void setCurrentNumOfStudents(int curNum){
		this.currentNumOfStudents=curNum;
	}
	
	public ArrayList<String> getListOfStudents(){
		return listOfStudents;
	}
	
	public void setListOfStudents(ArrayList<String> listOfStudents){
		this.listOfStudents=listOfStudents;
	}
	
	public String getCourseInstructor(){
		return courseInstructor;
	} 
	
	public void setCourseInstrutor(String courseInstructor){
		this.courseInstructor=courseInstructor;
	}
	
	public int getCourseSectionNum(){
		return courseSectionNum;
	}
	
	public void setCourseSecNum(int secNum){
		this.courseSectionNum=secNum;
	}
	public String getCourseLocation(){
		return courseLocation;
	} 
	
	public void setCourseLocation(String location){
		this.courseLocation=location;
	}
	
	//Reports: For sorting courses based on the current number of student registers
	public int compareTo(Course o) {
		if(currentNumOfStudents==o.currentNumOfStudents)
			return 0;    
		else if(currentNumOfStudents<o.currentNumOfStudents)
			return 1;    
		else
			return -1;
	}  
	
	public void appendStudent(String stuName) {
		listOfStudents.add(stuName);
	}
	
	public void removeStudent(String stuName) {
		listOfStudents.remove(stuName);
	}
}
