import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestRuner {
//POST
    @Test
    public void login() throws IOException, ConfigurationException {
        TestCase customer=new TestCase();
        customer.callingLoginAPI();
    }
//GET
    @Test
    public void Alldata() throws IOException, ConfigurationException {
        TestCase customer=new TestCase();
        customer.AlluserList();
    }
    //GET
    @Test
    public void OneUserdata() throws IOException, ConfigurationException {
        TestCase customer=new TestCase();
        customer.CheckOneUser();
    }
    //Post
    @Test
    public void NewUSer() throws IOException, ConfigurationException {
        TestCase customer=new TestCase();
        customer.createNewUser() ;
    }
    //put
    @Test
    public void PutUSer() throws IOException, ConfigurationException {
        TestCase customer=new TestCase();
        customer.updateUSER() ;
    }
    @Test
    public void DeleteUSer() throws IOException, ConfigurationException {
        TestCase customer=new TestCase();
        customer.deleteCustomer() ;
    }

}
