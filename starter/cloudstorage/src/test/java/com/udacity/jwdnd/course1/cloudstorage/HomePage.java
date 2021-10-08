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

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "btnAddNewNote")
    private WebElement btnAddNewNote;

    @FindBy(id = "btnAddNewCredential")
    private WebElement btnAddNewCredential;

    @FindBy(id = "note-title")
    private WebElement txtNoteTitle;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "note-description")
    private WebElement txtNoteDescription;

    @FindBy(id = "btnSaveChanges")
    private WebElement btnSaveChanges;

    @FindBy(id = "tableNoteTitle")
    private WebElement tableNoteTitle;

    @FindBy(id = "tableNoteDescription")
    private WebElement tableNoteDescription;

    @FindBy(id = "btnEditNote")
    private WebElement btnEditNote;

    @FindBy(id = "btnEditCredential")
    private WebElement btnEditCredential;

    @FindBy(id = "note-description")
    private WebElement txtModifyNoteDescription;

    @FindBy(id = "ancDeleteNote")
    private WebElement deleteNoteBtn;

    @FindBy(id = "aDeleteCredential")
    private WebElement deleteCredentialBtn;

    @FindBy(id = "credential-url")
    private WebElement txtCredentialUrl;

    @FindBy(id = "credential-username")
    private WebElement txtCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement txtCredentialPassword;

    @FindBy(id = "btnCredentialSaveChanges")
    private WebElement btnCredentialSaveChanges;

    @FindBy(id = "tblCredentialUrl")
    private WebElement tblCredentialUrl;

    @FindBy(id = "tblCredentialUsername")
    private WebElement tblCredentialUsername;

    @FindBy(id = "tblCredentialPassword")
    private WebElement tblCredentialPassword;

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
        js.executeScript("arguments[0].click();", deleteNoteBtn);
    }

    public void clickDeleteCredentialBtn() {
        js.executeScript("arguments[0].click();", deleteCredentialBtn);
    }


    public void addNewNote() {
        js.executeScript("arguments[0].click();", btnAddNewNote);
    }

    public void addNewCredential() {
        js.executeScript("arguments[0].click();", btnAddNewCredential);
    }

    public void setNoteTitle(String noteTitle) {
        js.executeScript("arguments[0].value='" + noteTitle + "';", txtNoteTitle);
    }

    public void setCredentialUrl(String url) {
        js.executeScript("arguments[0].value='" + url + "';", txtCredentialUrl);
    }

    public void setCredentialUsername(String username) {
        js.executeScript("arguments[0].value='" + username + "';", txtCredentialUsername);
    }

    public void setCredentialPassword(String password) {
        js.executeScript("arguments[0].value='" + password + "';", txtCredentialPassword);
    }

    public void editNoteTitle(String newNoteTitle) {
        wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).sendKeys(newNoteTitle);
    }

    public void editNoteDescription(String newNoteDescription) {
        wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription)).sendKeys(newNoteDescription);
    }

    public void editCredentialUrl(String newCredentialUrl) {
        wait.until(ExpectedConditions.elementToBeClickable(txtCredentialUrl)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtCredentialUrl)).sendKeys(newCredentialUrl);
    }

    public void editCredentialUsername(String newUsername) {
        wait.until(ExpectedConditions.elementToBeClickable(txtCredentialUsername)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtCredentialUsername)).sendKeys(newUsername);
    }

    public void editCredentialPassword(String newPassword) {
        wait.until(ExpectedConditions.elementToBeClickable(txtCredentialPassword)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtCredentialPassword)).sendKeys(newPassword);
    }

    public void navigateToNotesTab() {
        js.executeScript("arguments[0].click();", navNotesTab);
    }

    public void navigateToCredentialsTab() {
        js.executeScript("arguments[0].click();", navCredentialsTab);
    }

    public void setNoteDescription(String noteDescription) {
        js.executeScript("arguments[0].value='"+ noteDescription +"';", txtNoteDescription);
    }

    public void clickSaveNoteChangesBtn() {
        js.executeScript("arguments[0].click();", btnSaveChanges);
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
        String url =  wait.until(ExpectedConditions.elementToBeClickable(tblCredentialUrl)).getText();
        String username = wait.until(ExpectedConditions.elementToBeClickable(tblCredentialUsername)).getText();
        String password = tblCredentialPassword.getText();
        return new Credential(url, username, password);
    }


}
