package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for Note Creation, Viewing, Editing, and Deletion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests extends CloudStorageApplicationTests {


	@Test
	public void test_init_note() {
		String noteTitle = "test Note";
		String noteDescription = "This is test note.";
		HomePage homePage = doSingupAndLogin();
		initNote(noteTitle, noteDescription, homePage);
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickHomeButton();
		homePage = new HomePage(driver);
		homePage.navigateToNotesTab();
		Note note = homePage.getNote();
		Assertions.assertEquals(noteTitle, note.getNoteTitle());
		Assertions.assertEquals(noteDescription, note.getNoteDescription());
		deleteNote(homePage);
		resultPage = new ResultPage(driver);
		resultPage.clickHomeButton();
		homePage = new HomePage(driver);
		homePage.clickLogoutBtn();
	}


	@Test
	public void test_edit_note() {
		String noteTitle = "test Note";
		String noteDescription = "This is test note.";
		String editedNoteTitle = "My edit Note";
		String editedNoteDescription = "This is my edit note.";
		HomePage homePage = doSingupAndLogin();
		initNote(noteTitle, noteDescription, homePage);
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickHomeButton();
		homePage = new HomePage(driver);
		homePage.navigateToNotesTab();
		homePage.clickEdtNoteBtn();
		homePage.editNoteTitle(editedNoteTitle);
		homePage.editNoteDescription(editedNoteDescription);
		homePage.clickSaveNoteChangesBtn();
		resultPage = new ResultPage(driver);
		resultPage.clickHomeButton();
		homePage = new HomePage(driver);
		homePage.navigateToNotesTab();
		Note note = homePage.getNote();
		Assertions.assertEquals(editedNoteTitle, note.getNoteTitle());
		Assertions.assertEquals(editedNoteDescription, note.getNoteDescription());
	}


	@Test
	public void test_delete_note() {
		String noteTitle = "test Note";
		String noteDescription = "This is test note";
		HomePage homePage = doSingupAndLogin();
		initNote(noteTitle, noteDescription, homePage);
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickHomeButton();
		homePage = new HomePage(driver);
		homePage.navigateToNotesTab();
		deleteNote(homePage);
		resultPage = new ResultPage(driver);
		resultPage.clickHomeButton();
		homePage = new HomePage(driver);
		homePage.navigateToNotesTab();
		boolean isNoNote = homePage.isNotesEmpty(driver);
		Assertions.assertTrue(isNoNote);
	}



	private void deleteNote(HomePage homePage) {
		homePage.clickDeletNoteBtn();
	}

	private void initNote(String noteTitle, String noteDescription, HomePage homePage) {
		homePage.navigateToNotesTab();
		homePage.addNewNote();
		homePage.setNoteTitle(noteTitle);
		homePage.setNoteDescription(noteDescription);
		homePage.clickSaveNoteChangesBtn();
	}
}
