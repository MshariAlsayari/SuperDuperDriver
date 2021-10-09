package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest extends CloudStorageApplicationTests {



    @Test
    public void test_init_credential() {
        String url = "localhost";
        String userName = "Admin";
        String password = "Admin12345";
        HomePage homePage = doSingupAndLogin();
        initCredential(url, userName, password, homePage);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickHomeButton(driver);
        homePage = new HomePage(driver);
        homePage.navigateToCredentialsTab();
        Credential credential = homePage.getCredential();
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(userName, credential.getUsername());
        deleteCredential(homePage);
        resultPage = new ResultPage(driver);
        resultPage.clickHomeButton(driver);
        homePage = new HomePage(driver);
        homePage.clickLogoutBtn();
    }


    @Test
    public void test_edit_credential() {
        String url = "localhost";
        String userName = "Admin";
        String password = "Admin12345";
        String modifiedCredentialUrl = "My edit Credential URL";
        String modifiedCredentialUsername = "Admin";
        String modifiedCredentialPassword = "My edit Credential Password";
        HomePage homePage = doSingupAndLogin();
        initCredential(url, userName, password, homePage);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickHomeButton(driver);
        homePage = new HomePage(driver);
        homePage.navigateToCredentialsTab();
        homePage.clickEditCredentialBtn();
        homePage.editCredentialUrl(modifiedCredentialUrl);
        homePage.editCredentialUsername(modifiedCredentialUsername);
        homePage.editCredentialPassword(modifiedCredentialPassword);
        homePage.clickSaveCredentialChangesBtn();
        resultPage = new ResultPage(driver);
        resultPage.clickHomeButton(driver);
        homePage = new HomePage(driver);
        homePage.navigateToCredentialsTab();
        Credential credential = homePage.getCredential();
        Assertions.assertEquals(modifiedCredentialUsername, credential.getUsername());
        homePage.clickLogoutBtn();
    }

    @Test
    public void test_delete_credential() {
        String url = "localhost";
        String userName = "Admin";
        String password = "Admin12345";
        HomePage homePage = doSingupAndLogin();
        initCredential(url, userName, password, homePage);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickHomeButton(driver);
        homePage = new HomePage(driver);
        homePage.navigateToCredentialsTab();
        deleteCredential(homePage);
        resultPage = new ResultPage(driver);
        resultPage.clickHomeButton(driver);
        homePage = new HomePage(driver);
        homePage.navigateToCredentialsTab();
        boolean isNoCredentials= homePage.isCredentialsEmpty(driver);
        Assertions.assertTrue(isNoCredentials);
    }


    private void deleteCredential(HomePage homePage) {
        homePage.clickDeleteCredentialBtn();
    }

    private void initCredential(String url, String userName, String password, HomePage homePage) {
        homePage.navigateToCredentialsTab();
        homePage.addNewCredential();
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(userName);
        homePage.setCredentialPassword(password);
        homePage.clickSaveCredentialChangesBtn();
    }
}
