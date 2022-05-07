package hw1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User implements Studentable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<String[]> listOfRegisteredCourses= new ArrayList<String[]>();
	
	Student(){}
	
	Student(String firstName, String lastName, String username,String password){
		this.firstName=firstName;
		this.lastName=lastName;
		this.username=username;
		this.password=password;
		this.listOfRegisteredCourses=null;
	}
	
	@Override
	//Student Course Management: View all courses
	void viewAllCourses(){
		for(int i=0; i<courseList.size(); i++) {
	    	  courseList.get(i).display();
	      }
	}
	

	@Override
	//Student Course Management: View all courses that the current student is being registered in
	void viewRegisteredCourses() {
		if(listOfRegisteredCourses==null) {
			System.out.println("This student hasn't register any course.");
		}
		else {
			for (int i=0; i < listOfRegisteredCourses.size(); i++) {
				String[] theCourse = listOfRegisteredCourses.get(i);
				System.out.println("Course name: " + theCourse[0] + "; Course Section Number: " + theCourse[1]);
			}
		}
	}
	
	//Student Course Management: View all courses that are not FULL
	public void viewNotFull(){
		for(int i=0; i<courseList.size(); i++) {
			if(courseList.get(i).getMaxNumOfStudents()>courseList.get(i).getCurrentNumOfStudents()){
				courseList.get(i).display();
			}
		}
	}
	
	//Overload
	//Student Course Management: Register on a course
	public void registerCourse(String coursename, int secNum){
		String[] arr = {coursename, (secNum+"")};
		listOfRegisteredCourses.add(arr);
	}
		
	//Overload
	//Student Course Management: Register on a course
	public void registerCourse(Scanner input){
		//Register on a course.
		String courseName=null;
		int secNum=0;
		Boolean noExistence  =  true;
		while(noExistence){
			courseName=promptCourseName(input);
			secNum=promptSecNum(input); 
			//add student to the identified course
			for(int i=0; i<courseList.size(); i++) { 
				Course thisCourse = courseList.get(i);
				if(thisCourse.getCourseName().equalsIgnoreCase(courseName)&&
						thisCourse.getCourseSectionNum()==(secNum)){
					if(thisCourse.getListOfStudents()==null){
						thisCourse.setListOfStudents(new ArrayList <String>());
					}thisCourse.appendStudent(firstName+" "+lastName);
					if(listOfRegisteredCourses==null){
						listOfRegisteredCourses=new ArrayList<String[]>();
					}
					registerCourse(courseName, secNum);//update student object
					noExistence = false;
				}
			}
			if(noExistence){
				System.out.println("Error: There isn't a course named "+courseName
						+" with Section Number: "+secNum+".");
			}
		}
	}
	
	//Overload
	//Student Course Management: Withdraw from a course
	public void withdrawCourse(String coursename, int secNum) {
		for(int i=0;i<listOfRegisteredCourses.size();i++) {
			if(listOfRegisteredCourses.get(i)[0].equals(coursename)&&Integer.parseInt(listOfRegisteredCourses.get(i)[1])==secNum) {
				listOfRegisteredCourses.remove(i);
			}
		}
	}
	
	//Overload
	//Student Course Management: Withdraw from a course
	public void withdrawCourse(Scanner input) {
		 //Withdraw from a course.
		String courseName=null;
		int secNum=0;
		Boolean noExistence  =  true;
		while(noExistence){
			courseName=promptCourseName(input);
			secNum=promptSecNum(input); 
			//remove student from the course list
			for(int i=0; i<courseList.size(); i++) {
				Course thisCourse = courseList.get(i);
				if(thisCourse.getCourseName().equalsIgnoreCase(courseName)&&
						thisCourse.getCourseSectionNum()==(secNum)){
					thisCourse.removeStudent(firstName+" "+lastName);
					withdrawCourse(courseName, secNum); //remove course from student object
					noExistence = false;
				}
			}
			if(noExistence){
				System.out.println("Error: There isn't a course named "+courseName
						+" with Section Number: "+secNum+".");
				}
			}
	}
}
