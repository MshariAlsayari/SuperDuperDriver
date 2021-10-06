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
	public void testModify() {
		String noteTitle = "test Note";
		String noteDescription = "This is test note.";
		String editedNoteTitle = "My edit Note";
		String editedNoteDescription = "This is my edit note.";
		HomePage homePage = doSingupAndLogin();
		initNote(noteTitle, noteDescription, homePage);
		homePage.navigateToNotesTab();
		homePage = new HomePage(driver);
		homePage.clickEdtNoteBtn();
		homePage.editNoteTitle(editedNoteTitle);
		homePage.editNoteDescription(editedNoteDescription);
		homePage.clickSaveNoteChangesBtn();
		homePage.navigateToNotesTab();
		Note note = homePage.getNote();
		Assertions.assertEquals(editedNoteTitle, note.getNoteTitle());
		Assertions.assertEquals(editedNoteDescription, note.getNoteDescription());
	}





	@Test
	public void testCreateAndDisplay() {
		String noteTitle = "test Note";
		String noteDescription = "This is test note.";
		HomePage homePage = doSingupAndLogin();
		initNote(noteTitle, noteDescription, homePage);
		homePage.navigateToNotesTab();
		homePage = new HomePage(driver);
		Note note = homePage.getNote();
		Assertions.assertEquals(noteTitle, note.getNoteTitle());
		Assertions.assertEquals(noteDescription, note.getNoteDescription());
		homePage.logout();
	}

	@Test
	public void testDelete() {
		String noteTitle = "test Note";
		String noteDescription = "This is test note";
		HomePage homePage = doSingupAndLogin();
		initNote(noteTitle, noteDescription, homePage);
		homePage.navigateToNotesTab();
		homePage = new HomePage(driver);
		deleteNote(homePage);
		boolean isNoNote = homePage.noNotes(driver);
		Assertions.assertTrue(isNoNote);
	}

	private void deleteNote(HomePage homePage) {
		homePage.deleteNote();
	}




	private void initNote(String noteTitle, String noteDescription, HomePage homePage) {
		homePage.navigateToNotesTab();
		homePage.addNewNote();
		homePage.setNoteTitle(noteTitle);
		homePage.setNoteDescription(noteDescription);
		homePage.clickSaveNoteChangesBtn();
		homePage.navigateToNotesTab();
	}
}
