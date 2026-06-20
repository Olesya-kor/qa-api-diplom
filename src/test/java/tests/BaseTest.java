package tests;


import client.OrderClient;
import client.UserClient;


public class BaseTest {


    protected UserClient userClient = new UserClient();


    protected OrderClient orderClient = new OrderClient();


    protected String accessToken;



    protected void clearToken() {

        accessToken = null;

    }

}