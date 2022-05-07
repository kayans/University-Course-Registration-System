package hw1;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;



public class Main {
	private static User theOnlyAdmin = new Admin("Admin","Admin001","Kayan","Shih");

	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		User currentUser = null;
		User.deserial();//deserialization
		
		int userType = getUserType(input);//prompt user type
		currentUser = logIn(userType, input);
		
		
		if (userType==1){//Admin
			Boolean goOn= true;
			Admin currentAdmin = (Admin) currentUser;
			while(goOn){
				int menuNum = menu(userType, input);
				switch(menuNum){
				case 0://Exit
					goOn=false;
					break;
				case 1://Courses Management:Create a new course
					currentAdmin.create(input);
					break;
				case 2://Courses Management:Delete a new course
					currentAdmin.delete(input);
					break;
				case 3://Courses Management:Edit a course(this will allow the admin to edit any information on the course except for course ID and name)
					currentAdmin.edit(input);
					break;
				case 4: //Courses Management:Display information for a given course (by course ID)
					currentAdmin.displayCertainCourse(input);
					break;
				case 5://Courses Management:Register a student
					currentAdmin.registerStudent(input);
					break;
				case 6://Reports:View all courses(The list of course name,course id, number of students registered, and the maximum number of students allowed to be registered
					currentAdmin.viewAllCourses();
					break;
				case 7://Reports: View all courses that are FULL(reached the maximum number of students)
					currentAdmin.viewFullCourse();
					break;
				case 8://Reports:Write to a file the list of course that are Full
					writeToFile(User.courseList);
					break;
				case 9://Reports:View the names of the students being registered in a specific course
					currentAdmin.viewListOfStudent(input);
					break;
				case 10://Reports:View the list of courses that a given student is being registered on
					String name = User.promptStuName(input);
					Student student = findStudent(name);
					currentAdmin.viewRegisteredCourses(student);
					break;
				case 11://Reports:Sort courses based on the current number of student registers
					Collections.sort(User.courseList);
					break;
				}
			}
		} else{ //if student (userType == 2)
			Student currentStudent = (Student) currentUser;
			Boolean goOn=true;
			while(goOn) {
				int menuNum = menu(userType, input);
				switch(menuNum){
				case 0: //Exit
					goOn=false;
					break;
				case 1: //View all courses.
					currentStudent.viewAllCourses();
					break;
				case 2: //View all courses that are not FULL
					currentStudent.viewNotFull();
					break;
				case 3: //Register on a course.
					currentStudent.registerCourse(input);
					break;
				case 4: //Withdraw from a course.
					currentStudent.withdrawCourse(input);
					break;
				case 5: //View all courses that the current student is being registered in
					currentStudent.viewRegisteredCourses();
					break;
				}
			}
		}
		
		input.close();
		System.out.println("Reach the end of the program.");
		currentUser.serial();//Serialization
	}
	
	//Prompt user for user type (Admin or User)
	public static int getUserType(Scanner input){
		Boolean incorrectUser = true;
		while(incorrectUser) {		
			System.out.println("Enter 1 if you are Admin and enter 2 if you are Student: ");
			int num = input.nextInt();	
			if(num==1){ //administer
				return 1;
			} 
			else if(num==2){ //student
				return 2;
			}
		}
		return 0;
	}
	
	//Prompt user to log in
	public static User logIn(int userType, Scanner input){
		Boolean incorrectLogIn = true;	
		String username = null;
		String password = null;
		while(incorrectLogIn) {
			System.out.println("Enter your username: ");
			if(input.hasNext()) {
				username = input.next();
			}
			System.out.println("Enter your password: ");
			if(input.hasNext()) {
				password = input.next();
			}
			
			if (userType==1){//check admin's username and password to log in
				if (username.equals("Admin") && password.equals("Admin001")) {
					return theOnlyAdmin;
				}
			}else {//check student's username & password
				for(int i=0;i<User.registeredStudents.size();i++){
					Student thisStu = User.registeredStudents.get(i);
					String stuUsername = thisStu.getUserName();
					String stuPassword = thisStu.getPassword();
					if(username.equals(stuUsername)&&password.equals(stuPassword)){
						return thisStu;
					}
				}
			}
		}
		return null;
	}
	
	//Prompt user to make choice
	public static int menu(int userType, Scanner input){
		if(userType==1){//Print menu for Admin to choose
			System.out.println("Enter 1 for Courses Management and enter 2 for Reports: ");
			int num = input.nextInt();
			if(num==1){ //Courses Management for Admin
				System.out.println("1.Create a new course\n"
						+ "2.Delete a course\n"
						+ "3.Edit a course\n"
						+ "4.Display information for a given course(by Course ID)\n"
						+ "5.Register a student\n"
						+ "0.Exit\n"
						+ "Enter the number to make your choice: ");
				
			} else{ //Reports for Admin
				System.out.println("6.View all courses\n"
						+ "7.View all courses that are FULL\n"
						+ "8.Write to a file the list of course that are Full\n"
						+ "9.View the names of the students being registered in a specific course\n"
						+ "10.View the list of courses that a given student is being registered on\n"
						+ "11.Sort courses based on the current number of student registers\n"
						+ "0.Exit\n"
						+ "Enter the number to make your choice: ");
			}
			int menuNum = input.nextInt();
			return menuNum;
		}
		else{//Print Course Management menu for Student to choose
			System.out.println("1.View all courses\n"
					+ "2.View all courses that are not FULL\n"
					+ "3.Register on a course\n"
					+ "4.Withdraw from a course\n"
					+ "5.View all courses that you are being registered in\n"
					+ "0.Exit\n"
					+ "Enter the number to make your choice: ");
			int menuNum = input.nextInt();
			return menuNum;
		}
	} 
	
	//Write FULL course in a file.
	public static void writeToFile(ArrayList<Course> courseList){ 
		try {
			File fullCourse = new File("src/full_course.txt"); //Create the file.
			if (fullCourse.createNewFile()) {
				System.out.println("File created: " + fullCourse.getName());
				} 
			else {
				System.out.println("File already exists.");
				}
			FileWriter myWriter = new FileWriter("src/full_course.txt");
			for(int i=0; i<courseList.size(); i++) { //If the course is full, it will be written to the file.
				if(courseList.get(i).getMaxNumOfStudents()==courseList.get(i).getCurrentNumOfStudents()){
					myWriter.write(courseList.get(i).getCourseName()+" with ID "
							+courseList.get(i).getCourseId()+" and section number "
							+courseList.get(i).getCourseSectionNum());
					}
				}
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
				}
	}

	//Find the specific student Admin is looking for
	public static Student findStudent(String name) {
		Scanner input=new Scanner(System.in);
		for (int i=0; i<User.registeredStudents.size(); i++) {
			Student thisStu = User.registeredStudents.get(i);
			if(thisStu.getRealName().equals(name)) {
				return thisStu;
			}
		}
		return null;
	}
}

