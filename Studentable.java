package hw1;

import java.util.Scanner;

public interface Studentable {
	public void viewNotFull();
	public void registerCourse(String coursename, int secNum);
	public void registerCourse(Scanner input);
	public void withdrawCourse(String coursename, int secNum);
	public void withdrawCourse(Scanner input);
	
}
