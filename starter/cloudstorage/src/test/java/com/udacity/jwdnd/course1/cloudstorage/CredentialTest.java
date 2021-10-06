package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
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
        HomePage homePage = doSingupAndLogin();
        initCredential(url, userName, password, homePage);
        homePage.navigateToCredentialsTab();
        homePage = new HomePage(driver);
        deleteCredential(homePage);
        boolean isNoCredentials= homePage.noCredentials(driver);
        Assertions.assertTrue(isNoCredentials);
    }

    private void deleteCredential(HomePage homePage) {
        homePage.deleteCredential();
    }

    @Test
    public void testCreateAndDisplay() {
        String url = "localhost";
        String userName = "Admin";
        String password = "Admin12345";
        HomePage homePage = doSingupAndLogin();
        initCredential(url, userName, password, homePage);
        homePage.navigateToCredentialsTab();
        homePage = new HomePage(driver);
        Credential credential = homePage.getCredential();
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(userName, credential.getUsername());
        homePage.logout();
    }

    @Test
    public void testModify() {
        String url = "localhost";
        String userName = "Admin";
        String password = "Admin12345";
        String modifiedCredentialUrl = "My edit Credential URL";
        String modifiedCredentialUsername = "My edit Credential Usernam";
        String modifiedCredentialPassword = "My edit Credential Password";
        HomePage homePage = doSingupAndLogin();
        initCredential(url, userName, password, homePage);
        homePage.navigateToCredentialsTab();
        homePage = new HomePage(driver);
        homePage.clickEditCredentialBtn();
        homePage.editCredentialUrl(modifiedCredentialUrl);
        homePage.editCredentialUsername(modifiedCredentialUsername);
        homePage.editCredentialPassword(modifiedCredentialPassword);
        homePage.clickSaveCredentialChangesBtn();
        homePage.navigateToCredentialsTab();
        Credential credential = homePage.getCredential();
        Assertions.assertEquals(modifiedCredentialUrl, credential.getUrl());
        Assertions.assertEquals(modifiedCredentialUsername, credential.getUsername());
    }

    private void initCredential(String url, String userName, String password, HomePage homePage) {
        homePage.navigateToCredentialsTab();
        homePage.addNewCredential();
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(userName);
        homePage.setCredentialPassword(password);
        homePage.clickSaveCredentialChangesBtn();
        homePage.navigateToCredentialsTab();
    }
}
