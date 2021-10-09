package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id = "btnLogout")
    private WebElement logoutButton;

    @FindBy(id = "btnAddNewNote")
    private WebElement btnAddNewNote;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "btnSaveChanges")
    private WebElement btnSaveNoteChanges;

    @FindBy(id = "ancDeleteNote")
    private WebElement btnDeleteNoteButton;

    @FindBy(id = "btnEditNote")
    private WebElement btnEditNote;

    @FindBy(id = "tableNoteTitle")
    private WebElement tableNoteTitle;

    @FindBy(id = "tableNoteDescription")
    private WebElement tableNoteDescription;

    @FindBy(id = "btnAddNewCredential")
    private WebElement btnAddNewCredential;

    @FindBy(id = "btnEditCredential")
    private WebElement btnEditCredential;

    @FindBy(id = "aDeleteCredential")
    private WebElement btnDeleteCredential;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "btnCredentialSaveChanges")
    private WebElement btnCredentialSaveChanges;

    @FindBy(id = "tblCredentialUrl")
    private WebElement tableCredentialUrl;

    @FindBy(id = "tblCredentialUsername")
    private WebElement tableCredentialUsername;

    @FindBy(id = "tblCredentialPassword")
    private WebElement tablCredentialPassword;

    private final JavascriptExecutor js;

    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 500);
    }

    public void clickLogoutBtn() {
        js.executeScript("arguments[0].click();", logoutButton);
    }

    public void clickEdtNoteBtn() {
        js.executeScript("arguments[0].click();", btnEditNote);
    }

    public void clickEditCredentialBtn() {
        js.executeScript("arguments[0].click();", btnEditCredential);
    }

    public void clickDeletNoteBtn() {
        js.executeScript("arguments[0].click();", btnDeleteNoteButton);
    }

    public void clickDeleteCredentialBtn() {
        js.executeScript("arguments[0].click();", btnDeleteCredential);
    }


    public void addNewNote() {
        js.executeScript("arguments[0].click();", btnAddNewNote);
    }

    public void addNewCredential() {
        js.executeScript("arguments[0].click();", btnAddNewCredential);
    }

    public void setNoteTitle(String noteTitle) {
        js.executeScript("arguments[0].value='" + noteTitle + "';", this.noteTitle);
    }

    public void setCredentialUrl(String url) {
        js.executeScript("arguments[0].value='" + url + "';", credentialUrl);
    }

    public void setCredentialUsername(String username) {
        js.executeScript("arguments[0].value='" + username + "';", credentialUsername);
    }

    public void setCredentialPassword(String password) {
        js.executeScript("arguments[0].value='" + password + "';", credentialPassword);
    }

    public void editNoteTitle(String newNoteTitle) {
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys(newNoteTitle);
    }

    public void editNoteDescription(String newNoteDescription) {
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).sendKeys(newNoteDescription);
    }

    public void editCredentialUrl(String newCredentialUrl) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).sendKeys(newCredentialUrl);
    }

    public void editCredentialUsername(String newUsername) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsername)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsername)).sendKeys(newUsername);
    }

    public void editCredentialPassword(String newPassword) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).sendKeys(newPassword);
    }

    public void navigateToNotesTab() {
        js.executeScript("arguments[0].click();", noteTab);
    }

    public void navigateToCredentialsTab() {
        js.executeScript("arguments[0].click();", credentialTab);
    }

    public void setNoteDescription(String noteDescription) {
        js.executeScript("arguments[0].value='"+ noteDescription +"';", this.noteDescription);
    }

    public void clickSaveNoteChangesBtn() {
        js.executeScript("arguments[0].click();", btnSaveNoteChanges);
    }

    public void clickSaveCredentialChangesBtn() {
        js.executeScript("arguments[0].click();", btnCredentialSaveChanges);
    }

    public boolean isNotesEmpty(WebDriver driver) {
        return !isElementPresent(By.id("tableNoteTitle"), driver) && !isElementPresent(By.id("tableNoteDescription"), driver);
    }

    public boolean isCredentialsEmpty(WebDriver driver) {
        return !isElementPresent(By.id("tblCredentialUrl"), driver) &&
                !isElementPresent(By.id("tblCredentialUsername"), driver) &&
                !isElementPresent(By.id("tblCredentialPassword"), driver);
    }

    public boolean isElementPresent(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);

            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public Note getNote() {
        String title = wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
        String description = tableNoteDescription.getText();
        return new Note(title, description);
    }

    public Credential getCredential() {
        String url =  wait.until(ExpectedConditions.elementToBeClickable(tableCredentialUrl)).getText();
        String username = wait.until(ExpectedConditions.elementToBeClickable(tableCredentialUsername)).getText();
        String password = tablCredentialPassword.getText();
        return new Credential(url, username, password);
    }


}
