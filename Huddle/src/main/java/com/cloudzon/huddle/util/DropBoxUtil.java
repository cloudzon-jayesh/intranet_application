package com.cloudzon.huddle.util;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

import javax.swing.JOptionPane;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.RequestTokenPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.WebAuthSession.WebAuthInfo;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

public class DropBoxUtil 
{
	final static private String APP_KEY = "gvec9pdm4nuzzgl";
	final static private String APP_SECRET = "15z3ghfr7f0i4jh";
	
	// Define AccessType for DropboxAPI object
	final static private AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
	private static DropboxAPI<WebAuthSession> mDBApi;
	
	
	private DbxClient dbxClient;
	public String authDropbox(String dropBoxAppKey, String dropBoxAppSecret)
			throws IOException, DbxException {
		final String APP_KEY = dropBoxAppKey;
        final String APP_SECRET = dropBoxAppSecret;

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
            Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

        // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = "qGwrBtKUVQAAAAAAAAAAJQ5HUtJHaoB0L71El8Cd4W8";
        				
        // This will fail if the user enters an invalid authorization code.
        DbxAuthFinish authFinish = webAuth.finish(code);
        String accessToken = authFinish.accessToken;

        DbxClient client = new DbxClient(config, accessToken);

        System.out.println("Linked account: " + client.getAccountInfo().displayName);
        String displayName = client.getAccountInfo().displayName;
        
		File inputFile = new File("C:\\Users\\CloudZon4\\Downloads\\hd\\unique-hd-wallpapers.jpg");
		FileInputStream inputStream = new FileInputStream(inputFile);
		try {
			DbxEntry.File uploadedFile = client.uploadFile("/test.png",
					DbxWriteMode.add(), inputFile.length(), inputStream);
			System.out.println("Uploaded: " + uploadedFile.toString());
		} finally {
			inputStream.close();
		}
		return displayName;
	}

	public String  authentication() throws DropboxException
	{
		/*AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
		
		mDBApi = new DropboxAPI<WebAuthSession>(session);
		AccessTokenPair tokenPair = mDBApi.getSession().getAccessTokenPair();
		RequestTokenPair tokens = new RequestTokenPair(tokenPair.key, tokenPair.secret);
		mDBApi.getSession().retrieveWebAccessToken(tokens);
		// String accessToken = mDBApi.getSession().getOAuth2AccessToken ();
		String keyToken = session.getAccessTokenPair().key;
		String secretToken = session.getAccessTokenPair().secret;
		String dName = mDBApi.accountInfo().displayName;
		return keyToken+" ---- "+secretToken+" ---- "+dName;*/
		
		AppKeyPair appKeys = new AppKeyPair("ohzbs7u4z53s522", "ohzbs7u4z53s522"); //Both from Dropbox developer website
        AccessTokenPair accessTokenPair = new  AccessTokenPair("ohzbs7u4z53s522", "ohzbs7u4z53s522");
		WebAuthSession session = new WebAuthSession(appKeys, Session.AccessType.DROPBOX, accessTokenPair);
        WebAuthInfo webAuthInfo = session.getAuthInfo();
        RequestTokenPair pair = webAuthInfo.requestTokenPair;
        session.retrieveWebAccessToken(pair);
        AccessTokenPair tokens = session.getAccessTokenPair();
        DropboxAPI<WebAuthSession> mDBApi = new DropboxAPI<WebAuthSession>(session);
        System.out.println(mDBApi.getSession().getAuthInfo().url);
        return tokens.toString();
	}
	
	public String auth() throws DropboxException, MalformedURLException, IOException, URISyntaxException
	{
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);  
	     WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);  
	     WebAuthInfo authInfo = session.getAuthInfo();  
	     RequestTokenPair pair = authInfo.requestTokenPair;  
	     String url = authInfo.url;  
	     Desktop.getDesktop().browse(new URL(url).toURI());  

	     JOptionPane.showMessageDialog(null, "Press ok to continue once you have authenticated.");  
	     session.retrieveWebAccessToken(pair);  
	     AccessTokenPair tokens = session.getAccessTokenPair();  
	     System.out.println("Use this token pair in future so you don't have to re-authenticate each time:");  
	     System.out.println("Key token: " + tokens.key);  
	     System.out.println("Secret token: " + tokens.secret);  
	     mDBApi = new DropboxAPI<WebAuthSession>(session);  
	     System.out.println();  
	     System.out.print("Uploading file...");  
	     String fileContents = "Hello World!";  
	     ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContents.getBytes());  
	     Entry newEntry = mDBApi.putFile("/testing.txt", inputStream, fileContents.length(), null, null);  
	     System.out.println("Done. \nRevision of file: " + newEntry.rev);  
	     FileOutputStream outputStream = null;  
	     File file = new File("C:\\Users\\CloudZon4\\Downloads\\hd\\unique-hd-wallpapers.jpg");  
	     outputStream = new FileOutputStream(file);  
	     DropboxFileInfo info = mDBApi.getFile("/testing.png", null, outputStream, null);  
	     return "success";
	}
	
}
