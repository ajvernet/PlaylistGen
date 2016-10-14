package org.ssa.ironyard.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;

public class DirectionsServiceTest {

    @Ignore
    @Test
    public void directions() {
	GeoApiContext context = new GeoApiContext();
	ByteSource byteSource = Resources.asByteSource(Resources.getResource("secrets.properties"));
	Properties p = new Properties();
	try (InputStream inputStream = byteSource.openStream()) {
	    p.load(inputStream);
	} catch (IOException e) {
	    throw new RuntimeException();
	}
	String apiKey = p.getProperty("mapkey");
	context.setApiKey(apiKey);
	String origin = "westminster";
	String destination = "columbia";
	DirectionsResult result = new DirectionsResult();
	DirectionsApiRequest req = DirectionsApi.newRequest(context).origin(origin).destination(destination);
	try {
	    result = req.await();
	    long duration = 0;
	    System.out.format("%d routes returned\n", result.routes.length);
	    System.out.format("Route 0 has %d legs\n", result.routes[0].legs.length);
	    for (DirectionsLeg d : Arrays.asList(result.routes[0].legs)) {
		System.out.format("Duration: %d\n", duration += d.duration.inSeconds);
		System.out.println(d.duration.humanReadable);
	    }

	    System.out.format("Summary: %s\n", result.routes[0].summary);
	} catch (Exception e) {
	    throw new RuntimeException();
	}
    }
}