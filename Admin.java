package hw1;

import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User implements Adminable{

	Admin(){}
	
	Admin(String username,String password,String firstName, String lastName){
		this.firstName=firstName;
		this.lastName=lastName;
		this.username=username;
		this.password=password;
	}
	
	@Override
	//Reports:View all courses(The list of course name,course id, number of students registered, and the maximum number of students allowed to be registered
	void viewAllCourses(){
		for(int i=0; i<courseList.size(); i++) {
	    	  courseList.get(i).display();
	      }
	}
	
	//Overload
	void viewRegisteredCourses() {
		// TODO Auto-generated method stub
		
	}
	
	//Overload
	//Reports:View the list of courses that a given student is being registered on
	//get student list and for loop to check if this student exit
	//if exit: print(getCourseName)
	void viewRegisteredCourses(Student student){
		if (student == null) {
			System.out.println("Error: No such student in the database.");
		} else {
			student.viewRegisteredCourses();
		}
	}

	//Courses Management:Create a new course
	//Ask user to input information and then scanner input (must be valid inputs)
	//if repeated sectionNum on the same courseName/ID, print error
	//Constructor new object
	//addlist
	public void create(Scanner input) {
		String courseName=promptCourseName(input);
		input.next();
		String courseId=promptCourseId(input);
		System.out.println("Enter the maximum number of students: ");
		int maxNumOfStudents=input.nextInt();
		int currentNumOfStudents=0; //Default to be zero current student for new courses
		ArrayList<String> listOfStudents = new ArrayList<String>();
		listOfStudents=null;//Default to be null for new courses
		System.out.println("Enter the course instructor: ");
		input.next();
		String courseInstructor=input.nextLine();
		boolean repeated = true;
		int courseSectionNum=0;
		while(repeated) {
			courseSectionNum=promptSecNum(input);
			repeated = checkRepeatedCourse(courseName, courseId, courseSectionNum, courseList, input);
		}
		System.out.println("Enter the course location: ");
		String courseLocation=input.nextLine();
		Course courseObj = new Course(courseName,courseId,maxNumOfStudents,currentNumOfStudents,
				  listOfStudents,courseInstructor,courseSectionNum,courseLocation); 
		courseList.add(courseObj);
	}
	
	//Courses Management:Delete a course
	//Ask courseId and sectionNum to delete course (must be valid ID and SecNum)
	//check each line, if it finds the same one, delete it from arraylist
	//after checking the list and find no course, ask user to input again 
	public void delete(Scanner input) {
		Boolean noExistence  =  true;
		while(noExistence){
			String courseId=promptCourseId(input);
			int courseSectionNum=promptSecNum(input);
			for(int i=0; i<courseList.size(); i++) {
				if(courseList.get(i).getCourseId().equalsIgnoreCase(courseId)&&
				   courseList.get(i).getCourseSectionNum()==(courseSectionNum)){
					System.out.println("You successfully remove this course with ID: "+courseId
							+" and Section Number: "+courseSectionNum);
					courseList.remove(i);
					noExistence = false;
				}
			}
			if(noExistence){
				System.out.println("Error: There isn't a course with ID: "+courseId
						+" and Section Number: "+courseSectionNum+".");
			}
		}
	}
	
	//Courses Management:Edit a course(this will allow the admin to edit any information on the course except for course ID and name)
	//Ask which courseName/ID+sectionNum and which info want to edit 1,2,3,4,5,6
	//Assign new info based on the old one
	//if repeated sectionNum on the same courseName/ID, print error
	public void edit(Scanner input) {
		Boolean noExistence  =  true;
		Course newCourse;
		while(noExistence){
			String courseId=promptCourseId(input);
			int courseSectionNum=promptSecNum(input);
			for(int i=0; i<courseList.size(); i++) {
				if(courseList.get(i).getCourseId().equalsIgnoreCase(courseId)&&
				   courseList.get(i).getCourseSectionNum()==(courseSectionNum)){
					System.out.println("To edit the course with ID: "+courseId
							+" and Section Number: "+courseSectionNum);
					newCourse = courseList.get(i);
					editCourse(newCourse, input);
					noExistence = false;
				}
			}
			if(noExistence){
				System.out.println("Error: There isn't a course with ID: "+courseId
						+" and Section Number: "+courseSectionNum+".");
			}
		}
	}
	
	//For Admin to edit Course information
	public static void editCourse(Course newCourse, Scanner input){
		Boolean edit = true;
		while(edit){
			System.out.println("1.Max Number of Students.\n"
					+"2.Current Number of Students.\n"
					+"3.List of Student Names in this course.\n"
					+"4.Course Instructor.\n"
					+"5.Course Section Number.\n"
					+"6.Course Location.\n"
					+"Enter the number for editing: ");
			int choice = input.nextInt();
			switch(choice){
			case 1:
				System.out.println("Enter the new Max Number of Students: ");
				int newMax= input.nextInt();
				newCourse.setMaxNumOfStudents(newMax);
				break;
			case 2:
				Boolean exceed= true;
				int newCur=0;
				while(exceed){
					System.out.println("Enter the new Current Number of Students: ");
					newCur= input.nextInt();
					if(newCur<=newCourse.getMaxNumOfStudents()){
						newCourse.setCurrentNumOfStudents(newCur);
						exceed = false;
					}
				}
				break;
			case 3: //+++++TO BE IMPLEMENTED: to move students from the existing list
				System.out.println("1.Add Student Name to the list.\n"
							+"2.Remove Student Name from the list.\n"
							+"Enter the number: ");
				choice = input.nextInt();
				ArrayList<String> newListOfStudents = new ArrayList<String>();
				if(newCourse.getListOfStudents()!=null){
					newListOfStudents=newCourse.getListOfStudents();
				}
				if (choice==1){
					for(int i=0;i<(newCourse.getMaxNumOfStudents()-newCourse.getCurrentNumOfStudents());i++){
						System.out.println("Enter the student first name you want to add: ");
						String firstName = input.next();
						System.out.println("Enter the student last name you want to add: ");
						String lastNme = input.next();
						String addName = firstName+" "+lastNme;
						newCourse.setCurrentNumOfStudents(newCourse.getCurrentNumOfStudents()+1);
						newListOfStudents.add(addName);
					}
					newCourse.setListOfStudents(newListOfStudents);
				}
				else{
					System.out.println("Enter the student first name you want to remove: ");
					String firstName=input.next();
					System.out.println("Enter the student last name you want to remove: ");
					String lastName=input.next();
					String removeName=firstName+" "+lastName;
					newCourse.setCurrentNumOfStudents(newCourse.getCurrentNumOfStudents()-1);
					newListOfStudents.remove(removeName);	
				}
				
				break;
			case 4:
				System.out.println("Enter the new Course Instructor: ");
				String newInstructor=input.next();
				newCourse.setCourseInstrutor(newInstructor);
				break;
			case 5:
				System.out.println("Enter the new Course Section Number: ");
				int newSec= input.nextInt();
				newCourse.setCourseSecNum(newSec);
				break;
			case 6:
				System.out.println("Enter the new Course Location: ");
				String newLocation=input.nextLine();
				newCourse.setCourseLocation(newLocation);
				break;
			}
			System.out.println("Enter 1 to continue editing and enter 2 to finish editing.");
			int editing = input.nextInt();
			if(editing==2){
				edit = false;
			}
		}
	}
	
	//Courses Management:Display information for a given course (by course ID)
	public void displayCertainCourse(Scanner input){
		System.out.println("Enter the course ID to see the course information: ");
		String courseId = null;
		if(input.hasNext()){
			courseId=input.next();
		}
		for(int i=0; i<courseList.size(); i++) {
			if(courseList.get(i).getCourseId().equals(courseId)){
				courseList.get(i).display();
			}
		}
	}
	
	//Courses Management:Register a student
	//Ask the name of student to register
	//give user name and user password
	public void registerStudent(Scanner input) {
		Boolean addStu=true;
		while(addStu){
			System.out.print("Enter the student first name: ");
			String stuFirstName=input.next();
			System.out.print("Enter the student last name: ");
			String stuLastName=input.next();
			System.out.print("Enter the username for this student: ");
			String stuUsername=input.next();
			System.out.print("Enter the password for this student: ");
			String stuPassword=input.next();
			Student stuObj = new Student(stuFirstName,stuLastName,stuUsername,stuPassword);
			registeredStudents.add(stuObj);
			System.out.println("Enter 1 to continue adding student and enter 2 to finish adding student: ");
			int add=input.nextInt();
			if(add==2){
				addStu=false;
			}
		}
	}
	
	//Reports: View all courses that are FULL(reached the maximum number of students)
	//get num of current and max method in Course class
	//check if numCur==cumMax, FULL
	public void viewFullCourse() {
		for(int i=0; i<courseList.size(); i++) {
			if(courseList.get(i).getMaxNumOfStudents()==courseList.get(i).getCurrentNumOfStudents()){
				courseList.get(i).display();
				}
			}
	}

	//Reports:View the names of the students being registered in a specific course
	//create a print list of all students method in Course class
	//Ask user to input course name/id+sectionNum
	//for loop to check which course and within this loop use method course.printAllStudents()
	public void viewListOfStudent(Scanner input){
		Boolean noExistence  =  true;
		while(noExistence){
			String courseId=promptCourseId(input);
			int courseSectionNum=promptSecNum(input);
			for(int i=0; i<courseList.size(); i++) {
				if(courseList.get(i).getCourseId().equalsIgnoreCase(courseId)&&
				   courseList.get(i).getCourseSectionNum()==(courseSectionNum)){
					System.out.println(courseList.get(i).getListOfStudents());
					noExistence = false;
				}
			}
			if(noExistence){
				System.out.println("Error: There isn't a course with ID: "+courseId
						+" and Section Number: "+courseSectionNum+".");
			}
		}
	}
}
