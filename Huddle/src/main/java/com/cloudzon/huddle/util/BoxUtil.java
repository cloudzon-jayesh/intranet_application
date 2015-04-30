package com.cloudzon.huddle.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import com.box.sdk.BoxUser;
import com.dropbox.client2.session.AccessTokenPair;

public class BoxUtil 
{
	private static final String DEVELOPER_TOKEN = "n9qXxa2H6GgUGffKAJBakH1MS1eCM0Eg";
    private static final int MAX_DEPTH = 1;
    private static final String client_id ="et2qejowatfx3fn64h5v74v5iq21ga02";
    private static final String client_secret = "UYXOJUo4rAMd3mZDC2szOjt8jMx6INp9";
    private static final String auth_code = "71R61AI6JowyNTnsJcsRObTLmDr6dyQ3";
	public void authentication(MultipartFile file) throws IOException
	{
		Logger.getLogger("com.box.sdk").setLevel(Level.OFF);

        BoxAPIConnection api = new BoxAPIConnection(client_id,client_secret,auth_code);
	//	BoxAPIConnection api1 = new BoxAPIConnection(DEVELOPER_TOKEN);
		//String token = api1.getAccessToken();
		//System.out.println("access : "+ token);
		//String refresh_token = api1.getRefreshToken();
		//BoxAPIConnection api = new BoxAPIConnection(client_id,client_secret,token,refresh_token);
		//System.out.println("token : "+ refresh_token);
        BoxUser.Info userInfo = BoxUser.getCurrentUser(api).getInfo();
        System.out.format("Welcome, %s <%s>!\n\n", userInfo.getName(), userInfo.getLogin());

        BoxFolder rootFolder = BoxFolder.getRootFolder(api);
        listFolder(rootFolder, 0);
        String path = "C:/Users/CloudZon4/Downloads/";
        FileInputStream stream = new FileInputStream(path+file.getOriginalFilename());
        rootFolder.uploadFile(stream, file.getOriginalFilename());
        stream.close();
	}
	private static void listFolder(BoxFolder folder, int depth) {
        for (BoxItem.Info itemInfo : folder) {
            String indent = "";
            for (int i = 0; i < depth; i++) {
                indent += "  CoLS  ";
            }

            System.out.println(indent + itemInfo.getName());
            if (itemInfo instanceof BoxFolder.Info) {
                BoxFolder childFolder = (BoxFolder) itemInfo.getResource();
                if (depth < MAX_DEPTH) {
                    listFolder(childFolder, depth + 1);
                }
            }
        }
    }
}
