package org.ssa.ironyard.crypto;

import static org.junit.Assert.*;
import org.junit.Test;
import org.ssa.ironyard.crypto.BCryptSecurePassword;
import org.ssa.ironyard.crypto.SecurePassword;
import org.ssa.ironyard.model.Password;
/**
 *
 * @author thurston
 */
public class BCryptSecureTests 
{
    final SecurePassword security = new BCryptSecurePassword();
    
    @Test
    public void good()
    {
        final String raw = "s3cr3t";
        Password pass = this.security.secureHash(raw);
        System.out.println(pass.getSalt());
        System.out.println(pass.getHash());
        assertTrue("", this.security.verify(raw, pass));
    }
    
    @Test
    public void hacker()
    {
        final String raw = "p0wned";
        Password pass = this.security.secureHash(raw);
        assertFalse("", this.security.verify("powned", pass));
    }
    
    @Test
    public void strong()
    {
        final String raw = "+lTyf3hqs";
        Password pass = this.security.secureHash(raw);
        assertTrue("", this.security.verify(raw, pass));
        assertFalse("of course should be case sensitive", this.security.verify(raw.toLowerCase(), pass));
    }
}