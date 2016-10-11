package org.ssa.ironyard.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

public class AuthorizationServiceTest {

    @Test
    public void test() throws IOException  {
	AudiosearchAuthorizationService as = new AudiosearchAuthorizationService();
	as.getAccessToken();
    }

}
