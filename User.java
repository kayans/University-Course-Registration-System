package hw1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class User implements Serializable{
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected static ArrayList<Student> registeredStudents = new ArrayList<Student>(); 
	protected static ArrayList<Course> courseList = new ArrayList<Course>();
	
	User(){} 
	
	abstract void viewAllCourses();

	abstract void viewRegisteredCourses();
	
	public String getUserName(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getRealName(){
		return firstName+" "+lastName;
	}
	
	//Prompt User for course name 
	public static String promptCourseName(Scanner input){
		Boolean repeated = true;
		String courseName = null;
		while(repeated) {
			System.out.print("Enter the course name: ");
			input.nextLine();
			courseName=input.nextLine();
			Boolean condition = (courseName.charAt(0) == ' ') //The start of a course name cannot be space.
					|| (courseName.length()<2) //The shortest words I can think about courses are PE.
					|| (courseName.matches(".*\\d.*"));//If a course name need to contain number, it should use Roman numerals
			if (! condition) {
				repeated = false;
			} else {
				System.out.println("Error: Please Enter Valid Course Name.");
			}
		}
		return courseName;
	}
	
	//Prompt User for course id 
	public static String promptCourseId(Scanner input){
		Boolean repeated = true;
		String courseId = null;
		while(repeated) {
			System.out.println("Enter the course ID: ");
			courseId=input.next();
			Boolean condition = (courseId.matches("[A-Z]+-[A-Z]+\\.[0-9]+"));//Course ID needs to be in the format: "4 upper cases" + "-" + "2 upper cases" + "." + "4 number" 
			if(condition){
				repeated = false;
			}
			else{
				System.out.println("Error: Please Enter Valid Course ID.");
			}
		}
		return courseId;
	}
	
	//Prompt User for course section number 
	public static int promptSecNum(Scanner input){
		Boolean repeated =  true;
		String courseSecNum = null;
		int courseSectionNum = 0;
		while(repeated){
			System.out.println("Enter the course section number: ");
			courseSecNum=input.next();
			if(courseSecNum.matches("[0-9]+")){
				courseSectionNum=Integer.parseInt(courseSecNum);
				repeated = false;
			}
			else{
				System.out.println("Error: Please Enter Valid Integer.");
			}
		}
		return courseSectionNum;
	}
	
	//Check there is not repeated course when you try to create new course.
	public static Boolean checkRepeatedCourse(String courseName, String courseId, int courseSectionNum, ArrayList<Course> courseList, Scanner input){
		for(int i=0; i<courseList.size(); i++) {
			if(courseList.get(i).getCourseName().equalsIgnoreCase(courseName)&&
					courseList.get(i).getCourseId().equalsIgnoreCase(courseId)&&
					courseList.get(i).getCourseSectionNum()==(courseSectionNum)){
			System.out.println("Error: This section number is used by another course with same name and ID.");
			return true;
			}
		}
		return false;
	}
		
	//Prompt user for student name
	public static String promptStuName(Scanner input){
		Boolean repeated = true;
		String stuName = null;
		while(repeated) {
			System.out.print("Enter the student first name: ");
			String firstName=input.next();
			System.out.print("Enter the student last name: ");
			String lastName=input.next();
			stuName=firstName + " " + lastName;
			Boolean condition = (stuName.charAt(0) == ' ') //The start of a student name cannot be space.
					|| (stuName.matches(".*\\d.*"));//A student name cannot contain number.
			if (! condition) {
				repeated = false;
			} else {
				System.out.println("Error: Please Enter Valid Student Name.");
			}
		}
		return stuName;
	}
	
	//Serialization
	public void serial() {
		try{
			FileOutputStream fis1 = new FileOutputStream("src/courseList");
            ObjectOutputStream ois1 = new ObjectOutputStream(fis1);
            FileOutputStream fis2 = new FileOutputStream("src/registerStudent");
            ObjectOutputStream ois2 = new ObjectOutputStream(fis2);
            ois1.writeObject(User.courseList);
            ois2.writeObject(User.registeredStudents);
            ois1.close();
            fis1.close();
            ois2.close();
            fis2.close();
		}catch(IOException ioe){}
	}
	
	//Deserialization
	public static void deserial() {
		try{
			FileInputStream fis1 = new FileInputStream("src/courseList");
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            FileInputStream fis2 = new FileInputStream("src/registerStudent");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            User.courseList = (ArrayList<Course>) ois1.readObject();
            User.registeredStudents = (ArrayList<Student>) ois2.readObject();
            ois1.close();
            fis1.close();
            ois2.close();
            fis2.close();
		}catch(IOException ioe){
			readCourse(User.courseList);
		}catch(ClassNotFoundException c){}
	}
	
	//store information of MyUniversityCourses.csv file
	public static void readCourse(ArrayList<Course> courseList){
		//  first attempt to read csv file
		try {
		      Scanner courseReader = new Scanner(new File("/Users/kayanshih/eclipse-workspace/HW1_OOP/src/hw1/MyUniversityCourses.csv"));
		      // skip the first line
		      courseReader.nextLine();

	    	  //main loop to create course objects
		      while (courseReader.hasNextLine()){
		    	  String line = courseReader.nextLine();
		    	  String[] Line = line.split(",");
		    	  String courseName = Line[0];
		    	  String courseId = Line[1];
		    	  int maxNumOfStudents = Integer.parseInt(Line[2]);
		    	  int currentNumOfStudents = Integer.parseInt(Line[3]);
		    	  ArrayList<String> listOfStudents = null;
		    	  String courseInstructor = Line[5];
		    	  int courseSectionNum = Integer.parseInt(Line[6]);
		    	  String courseLocation = Line[7];
		    	  Course courseObj = new Course(courseName,courseId,maxNumOfStudents,currentNumOfStudents,
		    				  listOfStudents,courseInstructor,courseSectionNum,courseLocation); 
		   		  courseList.add(courseObj);
		      }
		      courseReader.close();
		    } catch (FileNotFoundException e) {
		    	System.out.println("An error occurred.");
		    	e.printStackTrace();
		    }
	}
}
