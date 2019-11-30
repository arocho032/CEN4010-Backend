package security;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONObject;

import io.netty.handler.codec.base64.Base64Encoder;

/**
 * A Singleton that handles secure data exchange between the front end and the back end. 
 */
public class TransferManager {

	private Cipher cipher;
	private KeyStore keystore;
	
	/**
	 * Creates the unique instance of TransferManager.
	 */
	private TransferManager() {
		try {
			this.cipher = Cipher.getInstance("RSA");
			InputStream ins = new FileInputStream(new File("resources/keystore.jks"));
			this.keystore = KeyStore.getInstance("JCEKS");
			this.keystore.load(ins, "s3cr3t".toCharArray());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

 	/**
  	 * A handle to the unique PasswordManager
  	* instance.
 	*/
 	static private TransferManager _instance = null;
 	
	/**
	 * @return The unique instance of this
	 * class.
	 */
		 static public TransferManager instance( ) {
		 if ( null == _instance) {
			 _instance = new TransferManager( );
		 }
		 return _instance;
	 }
		
	/**
	 * Produces an Base64 version of the encrypted ciphertext for the 
	 * input given in msg. 
	 * @param msg
	 * 		the input to be encrypted and encoded.
	 * @return
	 * 		the Base64 encoding for the given input.
	 */
	public String encryptMessage(String msg, String alias) {
		String ret = null;
		try {
			this.cipher.init(Cipher.ENCRYPT_MODE, this.keystore.getCertificate(alias));
			byte[] plaintext = msg.getBytes("UTF-8");
			byte[] ciphertext = this.cipher.doFinal(plaintext);
			ret = Base64.getEncoder().encodeToString(ciphertext);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String decryptMessage(String msg) {
		String ret = null;
		try {
			this.cipher.init(Cipher.DECRYPT_MODE, this.getPrivateKey());
			byte[] ciphertext = Base64.getDecoder().decode(msg);
			byte[] plaintext = this.cipher.doFinal(ciphertext);
			ret = new String(plaintext);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private PrivateKey getPrivateKey() {
		PrivateKey ret = null;
		try {
		    KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("s3cr3t".toCharArray());
		    KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) this.keystore.getEntry("mykey", keyPassword);
			ret = privateKeyEntry.getPrivateKey();
		} catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException e) {
			e.printStackTrace();
		}	
	    return ret;
	}
	
	private PublicKey getPublicKey() {
		PublicKey ret = null;
		try {
			Certificate cert = this.keystore.getCertificate("mykey");
			ret = cert.getPublicKey();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return ret;
	}
		 
	public String getSharableCertificate() {
		String ret = null;
		try {
			X509Certificate cert = (X509Certificate) this.keystore.getCertificate("mykey");
						
			ret  = "-----BEGIN CERTIFICATE-----";  
			ret += new String(Base64.getEncoder().encode(cert.getEncoded()));
			ret += "-----END CERTIFICATE-----";
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public void setCertificateEntry(String certS, String alias) {
		try {
			CertificateFactory fact = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(certS.getBytes()));
			this.keystore.setCertificateEntry(alias, cert);
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws KeyStoreException {
		TransferManager tm = TransferManager.instance();
		String alias = "mykey";
		String plain = "Test plain text";
		String tc = tm.encryptMessage(plain, alias);
		System.out.println(tc);
		System.out.println(tm.decryptMessage(tc));
		
		String cipher = "Ovns33F7qJ6UUVqTtbUdpcaxmRFm5nakUA4tk4kgu5xpoe+3o9qSBdXnKqcs90a4SRMFiyWDAlwkk9CoGKkCF9Nz6433dcteG0QTFTf1DI3BjYgl7dN7k8BPa4SbiGIgXnIf2gYmx6kKoJRFbH29HWYNW1yrnAHKctLXjX0pL4Ma4G5n4PayxpgC2pLVD4MOEc9dfHIDKXoD0XnK/b5HNVARxSGvtRuJm6XXZeoEIiaU7o7TehpeL82xixT+dnW4v0jAptEH1tpk3aZHVZh+hMDLur9WyYUT9Q+zaPE6L3lEtiOaaKblpKpDw/Nprb+wcRu21HMr297y/vnEirbQdg==";
		System.out.println(tm.keystore.aliases().toString());
		
		System.out.println(tm.decryptMessage(cipher));
		
		
	}
	
}
		 