package security;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
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
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;

import io.netty.handler.codec.base64.Base64Encoder;

// TODO: Auto-generated Javadoc
/**
 * A Singleton that handles secure data exchange between the front end and the back end. 
 */
public class TransferManager {

	/** The cipher. */
	private Cipher cipher;
	
	/** The keystore. */
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
	 * Instance.
	 *
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
	 *
	 * @param msg 		the input to be encrypted and encoded.
	 * @param alias the alias
	 * @return 		the JSONObject containing:
	 * 			'key'	the encrypted key parameter, decryptable with the target's private key.
	 * 			'iv'	the encrypted iv parameter, decryptable with the target's private key.
	 * 			'text'	the encrypted text, decrypateble with a AES/CBC/PKCS7Padding using the given key and iv.
	 */
	public JSONObject encryptMessage(String msg, String alias) {
		JSONObject retJSON = null;
		try {

			byte[] iv  = new byte[16];
			byte[] key = new byte[16];

			SecureRandom r = new SecureRandom();
			r.nextBytes(iv);
			r.nextBytes(key);
			IvParameterSpec ivPS = new IvParameterSpec(iv);
			
			// Encrypt Key using Pu/Pr
			this.cipher.init(Cipher.ENCRYPT_MODE, this.keystore.getCertificate(alias));
			byte[] keycipher = this.cipher.doFinal(key);
			byte[] ivcipher = this.cipher.doFinal(iv);
			
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");			
			Cipher sCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			sCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivPS);

			byte[] plaintext = msg.getBytes("UTF-8");
			byte[] ciphertext = sCipher.doFinal(plaintext);
			
			retJSON = new JSONObject();
			retJSON.put("key", Base64.getEncoder().encodeToString(keycipher));
			retJSON.put("iv", Base64.getEncoder().encodeToString(ivcipher));
			retJSON.put("text", Base64.getEncoder().encodeToString(ciphertext));
			
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retJSON;
	}
	
	/**
	 * Decrypts the message in the given JSONObject.
	 * @param msg
	 * 		a json object which must have:
	 *	 		key		a symmetric key encrypted with this TransferManager's public certificate.
	 * 			iv		an iv value encrypted with this TransferManager's public certificate.
	 * 			text	a cyphertext encrypted using AES/CBC/PKCS5Padding and the given key and iv. 
	 * @return
	 * 		the plaintext form of text.
	 */
	public String decryptMessage(JSONObject msg) {
		String ret = null;
		try {
			
			this.cipher.init(Cipher.DECRYPT_MODE, this.getPrivateKey());
			byte[] iv  = this.cipher.doFinal(Base64.getDecoder().decode(msg.getString("iv")));
			byte[] key = this.cipher.doFinal(Base64.getDecoder().decode(msg.getString("key")));
			
			Cipher sCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec sks = new SecretKeySpec(key, "AES");
	        IvParameterSpec ivp = new IvParameterSpec(iv);			
			sCipher.init(Cipher.DECRYPT_MODE, sks, ivp);
				
			byte[] ciphertext = Base64.getDecoder().decode(msg.getString("text"));
			byte[] plaintext = sCipher.doFinal(ciphertext);
			ret = new String(plaintext, "UTF-8");
		
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * Gets the private key of this object.
	 * @return
	 * 		the private key.
	 */
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
	
	/**
	 * Gets the public key of this object.
	 * @return
	 * 		the public key.
	 */
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
		 
	/**
	 * Gets the sharable (PEM) string version of this object's certificate. 
	 * @return
	 * 		a String containing the PEM version of the certificate. 
	 */
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

	/**
	 * Adds an external certificate to the KeyStore with the given alias. 
	 * @param certS
	 * 		the certificate, usually in a PEM format.
	 * @param alias
	 * 		the alias for the certificate.
	 */
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
	
}
		 