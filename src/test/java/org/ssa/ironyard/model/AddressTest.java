package org.ssa.ironyard.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ssa.ironyard.model.Address;
import org.ssa.ironyard.model.Address.ZipCode;

public class AddressTest {

    @Test
    public void zippy() {
        ZipCode zipCode = new ZipCode("89078-0987");
        assertEquals("", "890780987", zipCode.datafy());
    }

}
