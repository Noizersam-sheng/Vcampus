package ui.general;

/**
 * @author 牟倪
 * @version 2.2
 * 总体UI，直接run它就可以了。
 */

import model.personnel.Person;
import ui.academic_panel.AcademicUI;
import ui.library_manager_panel.LibraryManagerUI;
import ui.login_panel.LoginUI;
import ui.shop_manager_panel.ShopManagerUI;
import ui.student_panel.StudentUI;
import ui.support.MyType;
import ui.teacher_panel.TeacherUI;

public class GeneralUI {

	public volatile Person person = null;
	private LoginUI loginUI = new LoginUI(this);

	public GeneralUI() {

	}

	public void ui() {
		loginUI.display();
		while (person == null)
			; // spin waiting
		if (person.getJurisdiction() == 5) {
			StudentUI studentUI = new StudentUI(person);
			studentUI.display();
		} else if (person.getJurisdiction() == 4) {
			TeacherUI teacherUI = new TeacherUI(person);
			teacherUI.display();
		} else if (person.getJurisdiction() == 1) {
			AcademicUI acadamicUI = new AcademicUI();
			acadamicUI.display();
		} else if (person.getJurisdiction() == 2) {
			LibraryManagerUI libraryManagerUI = new LibraryManagerUI();
			libraryManagerUI.display();
		} else if (person.getJurisdiction() == 3) {
			ShopManagerUI shopManagerUI = new ShopManagerUI();
			shopManagerUI.display();
		}
	}

	public static void main(String args[]) {
		MyType.initGlobalFont();
		GeneralUI generalUI = new GeneralUI();
		generalUI.ui();
	}
}
