package security;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.security.KeyStoreException;
import java.security.cert.CertificateException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sosInterface.SOSServer;

// TODO: Auto-generated Javadoc
/**
 * The Class TransferManagerTest.
 */
class TransferManagerTest {

	/** The Constant certificate. */
	static final String certificate = "-----BEGIN CERTIFICATE-----MIIDjzCCAnegAwIBAgIEJO+EZTANBgkqhkiG9w0BAQsFADB4MQswCQYDVQQGEwJVUzELMAkGA1UECBMCRkwxDjAMBgNVBAcTBU1pYW1pMRcwFQYDVQQKEw5TY2hvb2wgUHJvamVjdDEbMBkGA1UECxMSU09TIFNjaG9vbCBQcm9qZWN0MRYwFAYDVQQDEw1Bcm1hbmRvIE9jaG9hMB4XDTE5MTEyODIyMDcyN1oXDTIwMDIyNjIyMDcyN1oweDELMAkGA1UEBhMCVVMxCzAJBgNVBAgTAkZMMQ4wDAYDVQQHEwVNaWFtaTEXMBUGA1UEChMOU2Nob29sIFByb2plY3QxGzAZBgNVBAsTElNPUyBTY2hvb2wgUHJvamVjdDEWMBQGA1UEAxMNQXJtYW5kbyBPY2hvYTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJTxxWinkDbcZp3DHWuUG978Z+dNZnkVvwC3cYksj0E5WG+FzaFQ3le6n3KmMElITxe4yc20tCVoEmnzq5ErgnzC7e63BExGREOh3C8fNPDGZJQ62lRL1ZTTZQAHUZNFAAlKQMvOGlM+j9ppPhbMlxI7z7GQKGvNUGoTqqteZZeSR7oG6yoQ7iwlCPGDDEUDldBOYRh1UU7tLEmhre/MNY7QPmY+tm1CyBsxNJec6wWmOlEbydWDtbUlO91BGs9SriD/E6iI/3H65QWqZ1pT9LXfsnSZidhSf9Etiftr5d76l16lJNM0BZYDRSEAvS2a7RPQLa4/+OTC494ezE6ufncCAwEAAaMhMB8wHQYDVR0OBBYEFG4qCzoVs4Xh1TD2zuLwYKW06sXvMA0GCSqGSIb3DQEBCwUAA4IBAQCM5dLyTqrkqMwBm2zRrt+yNRsYWotMhk4r8rNa5pzfrwLC+edDQ1otxzBxI6afecVlcTM7PQLIDgTdMN0ERpSZkFU9EtNBec3gg6cdnsKDLfybPy/bl4tXd8iC3gDGt5kv6H0vbhO7vu4F7xTb+p9F6N0Ge9Wbi6levW6mO61WVQoba8zWlEDo5LuSdObSaeET4CxgIikYHg/04OhMbPHVCzStWTZg0rne9lgGv+Gp+jeXcgR4lnrXQV11qwiTUP3oP9eA0/orLR3t2XkNQ0C3YLfa+drbZbclUcG5hAceau7rKiJX8+y2FJySkc+w6aL0a3wBNxu/79MkKiCJ2OWr-----END CERTIFICATE-----";
	
	/** The Constant nonpem. */
	static final String nonpem = "MIIDjzCCAnegAwIBAgIEJO+EZTANBgkqhkiG9w0BAQsFADB4MQswCQYDVQQGEwJVUzELMAkGA1UECBMCRkwxDjAMBgNVBAcTBU1pYW1pMRcwFQYDVQQKEw5TY2hvb2wgUHJvamVjdDEbMBkGA1UECxMSU09TIFNjaG9vbCBQcm9qZWN0MRYwFAYDVQQDEw1Bcm1hbmRvIE9jaG9hMB4XDTE5MTEyODIyMDcyN1oXDTIwMDIyNjIyMDcyN1oweDELMAkGA1UEBhMCVVMxCzAJBgNVBAgTAkZMMQ4wDAYDVQQHEwVNaWFtaTEXMBUGA1UEChMOU2Nob29sIFByb2plY3QxGzAZBgNVBAsTElNPUyBTY2hvb2wgUHJvamVjdDEWMBQGA1UEAxMNQXJtYW5kbyBPY2hvYTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJTxxWinkDbcZp3DHWuUG978Z+dNZnkVvwC3cYksj0E5WG+FzaFQ3le6n3KmMElITxe4yc20tCVoEmnzq5ErgnzC7e63BExGREOh3C8fNPDGZJQ62lRL1ZTTZQAHUZNFAAlKQMvOGlM+j9ppPhbMlxI7z7GQKGvNUGoTqqteZZeSR7oG6yoQ7iwlCPGDDEUDldBOYRh1UU7tLEmhre/MNY7QPmY+tm1CyBsxNJec6wWmOlEbydWDtbUlO91BGs9SriD/E6iI/3H65QWqZ1pT9LXfsnSZidhSf9Etiftr5d76l16lJNM0BZYDRSEAvS2a7RPQLa4/+OTC494ezE6ufncCAwEAAaMhMB8wHQYDVR0OBBYEFG4qCzoVs4Xh1TD2zuLwYKW06sXvMA0GCSqGSIb3DQEBCwUAA4IBAQCM5dLyTqrkqMwBm2zRrt+yNRsYWotMhk4r8rNa5pzfrwLC+edDQ1otxzBxI6afecVlcTM7PQLIDgTdMN0ERpSZkFU9EtNBec3gg6cdnsKDLfybPy/bl4tXd8iC3gDGt5kv6H0vbhO7vu4F7xTb+p9F6N0Ge9Wbi6levW6mO61WVQoba8zWlEDo5LuSdObSaeET4CxgIikYHg/04OhMbPHVCzStWTZg0rne9lgGv+Gp+jeXcgR4lnrXQV11qwiTUP3oP9eA0/orLR3t2XkNQ0C3YLfa+drbZbclUcG5hAceau7rKiJX8+y2FJySkc+w6aL0a3wBNxu/79MkKiCJ2OWr";
	
	/** The Constant alias. */
	static final String alias = "test";
	
	/**
	 * Test instance.
	 */
	@Test
	void testInstance() {
		// Must return the same object if called twice.
		TransferManager s1 = TransferManager.instance();
		TransferManager s2 = TransferManager.instance();
		assertEquals("same instance", s1, s2);
	}

	/**
	 * Test encrypt message.
	 *
	 * @throws JSONException the JSON exception
	 */
	@Test
	void testEncryptMessage() throws JSONException {
		TransferManager tm = TransferManager.instance();

		// Because the key is randomized, we cannot expect a ciphertext
		// However, we can check that the ciphertext is decrypatable given
		// the parameters. 
		String plainText = "this is a plaintext";
		JSONObject ret = tm.encryptMessage(plainText, "mykey");
		// should contain a key, an iv, and a text
		assertTrue(ret.has("text"));
		assertTrue(ret.has("key"));
		assertTrue(ret.has("iv"));
		
		// Decrypting with the corresponding certificate should give the plaintext.
		assertTrue(plainText.equals(tm.decryptMessage(ret)));
	}

	/**
	 * Test decrypt message.
	 */
	@Test
	void testDecryptMessage() {
		TransferManager tm = TransferManager.instance();
				
		// Because the key is randomized, we cannot expect a ciphertext
		// However, we can check that the ciphertext is decrypatable given
		// the parameters. 
		String plainText = "this is a plaintext";
		JSONObject ret = tm.encryptMessage(plainText, "mykey");
		
		// Decrypting with the corresponding certificate should give the plaintext.
		assertTrue(plainText.equals(tm.decryptMessage(ret)));
	}

	/**
	 * Test get sharable certificate.
	 */
	@Test
	void testGetSharableCertificate() {
		// Sharable certificate is a constant
		assertEquals(certificate, TransferManager.instance().getSharableCertificate());
	}

	/**
	 * Test set certificate entry.
	 */
	@Test
	void testSetCertificateEntry() {
		TransferManager tm = TransferManager.instance();
		
		// An existent alias should fail
		assertThrows(KeyStoreException.class, () -> tm.setCertificateEntry(certificate, alias));
		assertThrows(KeyStoreException.class, () -> tm.setCertificateEntry(nonpem, alias));
		// An invalid certificate should fail
		assertThrows(CertificateException.class, () -> tm.setCertificateEntry("", alias));
		
	}

}
