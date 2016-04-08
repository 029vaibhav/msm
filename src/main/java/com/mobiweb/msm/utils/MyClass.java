package com.mobiweb.msm.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.drive.model.Permission;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Component
public class MyClass {

    /**
     * Application name.
     */
    private final String APPLICATION_NAME =
            "Drive API Java Quickstart";

    /**
     * Directory to store user credentials for this application.
     */
    private final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/drive-java-quickstart.json");
    /**
     * Global instance of the JSON factory.
     */
    private final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();
    /**
     * Global instance of the scopes required by this quickstart.
     * <p>
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart.json
     */
    private final List<String> SCOPES =
            Lists.newArrayList(DriveScopes.all());
    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private FileDataStoreFactory DATA_STORE_FACTORY;
    /**
     * Global instance of the HTTP transport.
     */
    private HttpTransport HTTP_TRANSPORT;

    {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                MyClass.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     *
     * @return an authorized Drive client service
     * @throws IOException
     */
    public Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public String insertFile(String title, String description,
                             String parentId, String mimeType, String multipartFile) throws IOException {

        Drive service = getDriveService();

        // File's metadata.
        File body = new File();
        body.setTitle(title);
        body.setDescription(description);
        body.setMimeType(mimeType);

        // Set the parent folder.
        if (parentId != null && parentId.length() > 0) {
            body.setParents(
                    Arrays.asList(new ParentReference().setId(parentId)));
        }

        // File's content.
        try {
            java.io.File fileContent = convertString(multipartFile);
            FileContent mediaContent = new FileContent(mimeType, fileContent);
            File file = service.files().insert(body, mediaContent).execute();
            System.out.println(file.getWebContentLink());
            Permission permission = new Permission();
            permission.setRole("reader");
            permission.setType("anyone");
            Drive.Permissions.Insert insert = service.permissions().insert(file.getId(), permission);
            insert.execute();
            String fileUrl = getFileUrl(file.getId());
            System.out.println(fileUrl);
            if (file.getWebContentLink().equals(fileUrl)) {
                System.out.println("file " + true);

            }
            return fileUrl;
        } catch (IOException e) {
            return null;
        }
    }

    private String getFileUrl(String fileId) {

        try {
            Drive service = getDriveService();
            File file = service.files().get(fileId).execute();
            return file.getWebContentLink();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public java.io.File convert(MultipartFile file) throws IOException {


        java.io.File convFile = new java.io.File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;


    }

    public java.io.File convertString(String file) throws IOException {

        byte[] bytes = Base64.decodeBase64(file);
        java.io.File convFile = new java.io.File("image.jpg");
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(bytes);
        fos.close();
        return convFile;


    }

}


