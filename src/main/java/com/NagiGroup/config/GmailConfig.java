package com.NagiGroup.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
@Configuration
public class GmailConfig {
	
	    private static final String APPLICATION_NAME = "Load Management Chat";
	    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	    private static final String TOKENS_DIRECTORY_PATH = "tokens";
	    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
	    //private static  String CREDENTIALS_FILE_PATH = "C:/Users/ADMIN/Downloads/Credentials.json";
	    private static final  String  CREDENTIALS_FILE_PATH = System.getenv("gmailThread"); 
	   
	    public static Credential getCredentials(final com.google.api.client.http.HttpTransport HTTP_TRANSPORT)
	            throws IOException {

	        if (CREDENTIALS_FILE_PATH == null || CREDENTIALS_FILE_PATH.isEmpty()) {
	            throw new FileNotFoundException("Environment variable 'gmailThread' is not set or empty!");
	        }

	        try (InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH)) {
	            GoogleClientSecrets clientSecrets =
	                    GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

	            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
	                    .setDataStoreFactory(new com.google.api.client.util.store.FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
	                    .setAccessType("offline")
	                    .build();

	            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
	            return new com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp(flow, receiver)
	                    .authorize("user");
	        }
	    }
	    @Bean
	    public static Gmail getService() throws GeneralSecurityException, IOException {
	        com.google.api.client.http.HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
	                .setApplicationName(APPLICATION_NAME)
	                .build();
	    }

}
