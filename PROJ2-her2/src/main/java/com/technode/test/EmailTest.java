package com.technode.test;
import com.technode.Login.LoginCred;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;



import static com.technode.Login.CredentialManager.getCredentialsByUsername;

public class EmailTest {
    @Test
    public void Emailtest() {
        String user = "JUnit";

        LoginCred userCred = getCredentialsByUsername(user);

        Assertions.assertEquals(userCred.getEmail(), "Junit@email.com");
        Assertions.assertNotEquals(userCred.getEmail(), "Wrong@email.com");
        Assertions.assertNotNull(userCred.getEmail());

    }

}
