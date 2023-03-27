package mySolution.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import mySolution.Constants;


/* class to demonstrate use of Gmail list labels API */
@Service
public class GmailService extends AbustractService{
	/**
	* Application name.
	*/
	private final String APPLICATION_NAME = "gmailApi";
	/**
	* Global instance of the JSON factory.
	*/
	@Autowired
	private GsonFactory JSON_FACTORY;
	/**
	* Directory to store authorization tokens for this application.
	*/
	private final String TOKENS_DIRECTORY_PATH = "tokens";

	/**
	* Global instance of the scopes required by this quickstart.
	* If modifying these scopes, delete your previously saved tokens/ folder.
	*/
	private final List<String> SCOPES = new ArrayList<String>() {{add(GmailScopes.GMAIL_LABELS);add(GmailScopes.GMAIL_MODIFY);add(GmailScopes.GMAIL_COMPOSE);add(GmailScopes.GMAIL_READONLY);add(GmailScopes.GMAIL_METADATA);add(GmailScopes.GMAIL_INSERT);add(GmailScopes.GMAIL_SEND);}};
	private final String CREDENTIALS_FILE_PATH = "/credentials.json";

	/**
	* Creates an authorized Credential object.
	*
	* @param HTTP_TRANSPORT The network HTTP Transport.
	* @return An authorized Credential object.
	* @throws IOException If the credentials.json file cannot be found.
	*/
	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
			throws IOException {
		// Load client secrets.
		InputStream in = GmailService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets =
				GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline")
				.build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8081).setCallbackPath("/callBack").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		//returns an authorized Credential object.
		return credential;
	}

	/**
	 * gmaill instance load
	 */
	public Gmail init() throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
			.setApplicationName(APPLICATION_NAME)
			.build();
		return service;
	}

	public void main(Map<String,String>mailMap) throws IOException, GeneralSecurityException,MessagingException  {

		String fromEmailAddress = mailMap.get(Constants.MailKey.FROM_ADDRESS);
		String toEmailAddress = mailMap.get(Constants.MailKey.SEND_ADDRESS);
		String messageSubject = mailMap.get(Constants.MailKey.SUBJECT);
		String bodyText = mailMap.get(Constants.MailKey.MESSAGE_BODY);

		Gmail service = init();
		// Create the email content


		// Encode as MIME message
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage email = new MimeMessage(session);
		email.setFrom(new InternetAddress(fromEmailAddress));
		email.addRecipient(javax.mail.Message.RecipientType.TO,
			new InternetAddress(toEmailAddress));
		email.setSubject(messageSubject);
		email.setText(bodyText);

		// Encode and wrap the MIME message into a gmail message
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		email.writeTo(buffer);
		byte[] rawMessageBytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
		Message message = new Message();
		message.setRaw(encodedEmail);

		try {
			// Create send message
			message = service.users().messages().send("me", message).execute();

		} catch (GoogleJsonResponseException e) {
			GoogleJsonError error = e.getDetails();
			if (error.getCode() == 403) {
				log.error("Unable to send message: " + e.getDetails());
			} else {
				throw e;
			}
		}

	}
}
