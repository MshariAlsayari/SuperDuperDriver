package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest extends CloudStorageApplicationTests {


    @Test
    public void testDelete() {
        String url = "localhost";
        String userName = "Admin";
        String password = "Admin12345";
        HomePage homePage = signUpAndLogin();
        createCredential(url, userName, password, homePage);
        homePage.navToCredentialsTab();
        homePage = new HomePage(driver);
        deleteCredential(homePage);
        Assertions.assertTrue(homePage.noCredentials(driver));
    }

    private void deleteCredential(HomePage homePage) {
        homePage.deleteCredential();
    }

    @Test
    public void testCreateAndDisplay() {
        String url = "localhost";
        String userName = "Admin";
        String password = "Admin12345";
        HomePage homePage = signUpAndLogin();
        createCredential(url, userName, password, homePage);
        homePage.navToCredentialsTab();
        homePage = new HomePage(driver);
        Credential credential = homePage.getFirstCredential();
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(userName, credential.getUsername());
        homePage.logout();
    }

    @Test
    public void testModify() {
        String url = "localhost";
        String userName = "Admin";
        String password = "Admin12345";
        HomePage homePage = signUpAndLogin();
        createCredential(url, userName, password, homePage);
        homePage.navToCredentialsTab();
        homePage = new HomePage(driver);
        homePage.editCredential();
        String modifiedCredentialUrl = "My Modified Credential URL";
        String modifiedCredentialUsername = "My Modified Credential Usernam";
        String modifiedCredentialPassword = "My Modified Credential Password";
        homePage.modifyCredentialUrl(modifiedCredentialUrl);
        homePage.modifyCredentialUsername(modifiedCredentialUsername);
        homePage.modifyCredentialPassword(modifiedCredentialPassword);
        homePage.saveCredentialChanges();
        homePage.navToCredentialsTab();
        Credential credential = homePage.getFirstCredential();
        Assertions.assertEquals(modifiedCredentialUrl, credential.getUrl());
        Assertions.assertEquals(modifiedCredentialUsername, credential.getUsername());
    }

    private void createCredential(String url, String userName, String password, HomePage homePage) {
        homePage.navToCredentialsTab();
        homePage.addNewCredential();
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(userName);
        homePage.setCredentialPassword(password);
        homePage.saveCredentialChanges();
        homePage.navToCredentialsTab();
    }
}
