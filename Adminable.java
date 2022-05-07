package hw1;

import java.util.Scanner;

public interface Adminable {
	public void create(Scanner input);
	public void delete(Scanner input);
	public void edit(Scanner input);
	public void displayCertainCourse(Scanner input);
	public void registerStudent(Scanner input);
	public void viewFullCourse();
	public void viewListOfStudent(Scanner input);
}
