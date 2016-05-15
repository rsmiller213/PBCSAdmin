/*
 File: PBCSFunc.java - Created on Feb 19, 2015
 Copyright (c) 2015 Oracle Corporation. All Rights Reserved.
 This software is the confidential and proprietary information of Oracle.
 */
package com.emtecinc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import com.emtecinc.PBCSAdmin;

import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/*
* PBCS Rest Samples.
*
 */
public class PBCSActions {

    private String userName; // PBCS user name
    private String pbcsDomain; //PBCS Domain
    private String password; // PBCS user password
    private String serverUrl; // PBCS server URL
    //private String apiVersion; // Version of the PBCS API that you are developing/compiling with.
    //private String applicationName; // PBCS application used in this sample

    public static void main(String[] args) {
        NewJFrame frame = new NewJFrame();
        //NewJPanel panel = new NewJPanel();
        pbcsFileEditor panel = new pbcsFileEditor();
        frame.add(panel);
        frame.setSize(1500, 800);
        panel.setSize(1500, 800);
        panel.setVisible(true);
        frame.setVisible(true);
    }

    //public PBCSActions(String userName, String pbcsDomain, String password, String serverUrl, String apiVersion, String applicationName) throws Exception {
    public PBCSActions(String userName, String pbcsDomain, String password, String serverUrl) throws Exception {
        this.userName = userName;
        this.pbcsDomain = pbcsDomain;
        this.password = password;
        this.serverUrl = serverUrl;
        //this.apiVersion = apiVersion;
        //this.applicationName = applicationName;
    }
//
// BEGIN - Integration scenarios.
//

    public void integrationScenarioImportMetadataIntoApplication() throws Exception {
        uploadFile("accounts.zip");
        executeJob("IMPORT_METADATA", "accountMetadata", "{importZipFileName:accounts.zip}");
        executeJob("CUBE_REFRESH", null, null);
    }

    public void integrationScenarioImportDataRunCalcCopyToAso() throws Exception {
        uploadFile("data.csv");
        executeJob("IMPORT_DATA", "loadingq1data", "{importFileName:data.csv}");
        executeJob("CUBE_REFRESH", null, null);
        executeJob("PLAN_TYPE_MAP", "CampaignToReporting", "{clearData:false}");
    }

    //public void integrationScenarioImportData(File file, String jobName) throws Exception {
    public void integrationScenarioImportData(String file, String jobName) throws Exception {

        //uploadFile(file.getAbsolutePath());
        //uploadFile(file.getAbsolutePath());
        //uploadFile(file);
        executeJob("IMPORT_DATA", jobName, "{importFileName:" + file + "}");
        //executeJob("CUBE_REFRESH", null, null);
        //executeJob("PLAN_TYPE_MAP", "CampaignToReporting", "{clearData:false}");
    }

    public void integrationScenarioExportMetadataAndDataAndDownloadFiles() throws Exception {
        executeJob("EXPORT_METADATA", "exportentitymetadata", "{exportZipFileName:entitydata.zip}");
        executeJob("EXPORT_DATA", "Forecastdata", "{exportFileName:forecastdata.zip}");
        listFiles();
//        downloadFile("entitydata.zip");
//        downloadFile("forecastdata.zip");
    }

    public void integrationScenarioRemoveUnnecessaryFiles() throws Exception {
        listFiles();
//        deleteFile("entitymetadata.csv");
//       deleteFile("forecastdata.csv");
    }

    public void integrationScenarioExportDataAndDownloadFiles() throws Exception {
        executeJob("EXPORT_DATA", "entitydata", "{exportFileName:entitydata.zip}");
        executeJob("EXPORT_DATA", "forecastdata", "{exportFileName:forecastdata.zip}");
        listFiles();
//        downloadFile("entitydata.zip");
//        downloadFile("forecastdata.zip");
    }

    public void integrationScenarioRefreshTheApplication() throws Exception {
        //uploadFile("accounts.zip");
        //executeJob("IMPORT_METADATA", "accountMetadata", "{importZipFileName:accounts.zip}");
        executeJob("CUBE_REFRESH", null, null);
    }

//
// END - Integration scenarios.
//
//
    // BEGIN - Methods that invoke REST API
    //
//
// Common Helper Methods
//

    private String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    private String executeRequest(String urlString, String requestMethod, String payload, String contentType) throws
            Exception {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setInstanceFollowRedirects(false);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", "Basic " + new sun.misc.BASE64Encoder().encode((pbcsDomain + "." + userName + ":"
                    + password).getBytes()));
            connection.setRequestProperty("Content-Type", contentType);
            if (payload != null) {
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(payload);
                writer.flush();
            }
            int status = connection.getResponseCode();
            if (status == 200 || status == 201) {
                return getStringFromInputStream(connection.getInputStream());
            }
            throw new Exception("Http status code: " + status);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    private void getJobStatus(String pingUrlString, String methodType) throws Exception {
        boolean completed = false;
        while (!completed) {
            String pingResponse = executeRequest(pingUrlString, methodType, null, "application/x-wwwform-urlencoded");
            JSONObject json = new JSONObject(pingResponse);
            int status = json.getInt("status");
            if (status == -1) {
                try {
                    System.out.println("Please wait...");
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    completed = true;
                    throw e;
                }
            } else {
                if (status > 0) {
                    System.out.println("Error occurred: " + json.getString("details"));
                } else {
                    System.out.println("Completed");
                }
                completed = true;
            }
        }
    }

    public String fetchPingUrlFromResponse(String response) throws Exception {
        String pingUrlString = null;
        JSONObject jsonObj = new JSONObject(response);
        int resStatus = jsonObj.getInt("status");
        if (resStatus == -1) {
            JSONArray lArray = jsonObj.getJSONArray("links");
            for (int i = 0; i < lArray.length(); i++) {
                JSONObject arr = lArray.getJSONObject(i);
                if (arr.get("rel").equals("self")) {
                    pingUrlString = (String) arr.get("href");
                }
            }
        }
        return pingUrlString;
    }
//
// END - Common Helper Methods
//
//
// BEGIN - List all the versions in PBCS
//

    public void getLCMVersions() throws Exception {
        String urlString = String.format("%s/interop/rest", serverUrl);
        String response = executeRequest(urlString, "GET", null, "application/x-www-form-urlencoded");
        JSONObject json = new JSONObject(response);
        int resStatus = json.getInt("status");
        if (resStatus == 0) {
            JSONArray fileList = json.getJSONArray("items");
            System.out.println("List of files are :");
            JSONObject jObj = null;
            for (int i = 0; i < fileList.length(); i++) {
                jObj = (JSONObject) fileList.get(i);
                System.out.println("Version :" + jObj.getString("version"));
                System.out.println("Lifecycle :" + jObj.getString("lifecycle"));
                System.out.println("Latest :" + jObj.getString("latest"));
                System.out.println("Link :" + ((JSONObject) ((JSONArray) jObj.getJSONArray("links")).get(0)).getString("href") + "\n");
            }
        }
    }
    
    /**
     * Gets the current details for the app depending on request
     * @param App "HP" or "LCM"
     * @param request "URL", "Version", "AppName", "AppType"
     * 
     * URLs come out as such:
     * HP: https://<serverURL>/HyperionPlanning/rest/<version>
     * LCM: https://<serverURL>/interop/rest/<version>
     * @throws Exception 
     */  
    public String getCurrentDetails(String App, String request) throws Exception {
        String res = "";
        String url = "";
        
        if (App.equals("HP")){
            url = String.format("%s/HyperionPlanning/rest/%s", serverUrl, getCurrentVersion("HP"));
        } else if (App.equals("LCM")){
            url = String.format("%s/interop/rest/%s", serverUrl, getCurrentVersion("LCM"));
        }
        
        if (request.equals("URL")){
            res = url;
        } else if (request.equals("Version")){
            res = getCurrentVersion(App);
        } else if (request.equals("AppName")){
            res = getAppDetails("Name");
        } else if (request.equals("AppType")){
            res = getAppDetails("Type");
        }
        return res;
    }
    
    /**
     * Gets the current details for the app depending on request
     * @param App "HP" or "LCM"
     * @param request "URL", "Version"
     * 
     * URLs come out as such:
     * HP: https://<serverURL>/HyperionPlanning/rest/<version>
     * LCM: https://<serverURL>/interop/rest/<version>
     * @throws Exception 
     */ 
    
    public String getCurrentVersion(String App) throws Exception {
        String res = "";
        String urlString = "";
        String latest = "";
        
        if (App.equals("HP")){
            urlString = String.format("%s/HyperionPlanning/rest/", serverUrl);
            latest = "isLatest";
        } else if (App.equals("LCM")){
            urlString = String.format("%s/interop/rest/", serverUrl);
            latest = "latest";
        }

        String response = executeRequest(urlString, "GET", null, "application/x-www-form-urlencoded");
        JSONObject json = new JSONObject(response);
        JSONArray fileList = json.getJSONArray("items");
        JSONObject jObj = null;
        for (int i = 0; i < fileList.length(); i++) {
            jObj = (JSONObject) fileList.get(i);
            if (jObj.getBoolean(latest)){
                res = jObj.getString("version");
            }
        }

        return res;
    }
    
    /**
    * Get App Name or Type 
    *
    * @param request "Name" or "Type"
    */
    public String getAppDetails(String request) throws Exception {
        String res = "";
        //String urlString = String.format("https://emtec-emtec.pbcs.us2.oraclecloud.com/HyperionPlanning/rest/v3/applications");
        String urlString = String.format("%s/applications", getCurrentDetails("HP","URL"));
        String response = executeRequest(urlString, "GET", null, "application/x-www-form-urlencoded");
        JSONObject json = new JSONObject(response);
         if (json.get("items").equals(JSONObject.NULL)) {
                System.out.println("No Applications Found");
            } else {
             JSONArray itemsArray = json.getJSONArray("items");
             JSONObject jObj = null;
             for (int i = 0; i < itemsArray.length(); i++) {
                    jObj = (JSONObject) itemsArray.get(i);
                    if (request.equals("Type")){
                        res = jObj.getString("type");
                    } else if (request.equals("Name")){
                        res = jObj.getString("name");
                    }
                }
         }
         
         return res;
    }

//
// BEGIN - List version details
//

    public void getLCMVersionDetails() throws Exception {
        //String urlString = String.format("%s/interop/rest/%s", serverUrl, apiVersion);
        String urlString = String.format("%s", getCurrentDetails("LCM","URL"));
        String response = executeRequest(urlString, "GET", null, "application/x-www-form-urlencoded");
        JSONObject json = new JSONObject(response);
        int resStatus = json.getInt("status");
        if (resStatus == 0) {
            JSONArray linksArray = json.getJSONArray("links");
            System.out.println("Version " + getCurrentDetails("LCM","Version") + " details :");
            JSONObject jObj = null;
            for (int i = 0; i < linksArray.length(); i++) {
                jObj = (JSONObject) linksArray.get(i);
                System.out.println("Service :" + jObj.getString("rel"));
                System.out.println("URL :" + jObj.getString("href"));
                System.out.println("Action :" + jObj.getString("action") + "\n");
            }
        }
    }
//
// END - List version details
//
//
// BEGIN - Get services
//

    public void getServices() throws Exception {
        //String urlString = String.format("%s/interop/rest/%s/services", serverUrl, getCurrentLCMVer());
        String urlString = String.format("%s/services", getCurrentDetails("LCM","URL"));
        String response = executeRequest(urlString, "GET", null, "application/x-www-form-urlencoded");
        JSONObject json = new JSONObject(response);
        int resStatus = json.getInt("status");
        if (resStatus == 0) {
            JSONArray linksArray = json.getJSONArray("links");
            System.out.println("Services list :");
            JSONObject jObj = null;
            for (int i = 0; i < linksArray.length(); i++) {
                jObj = (JSONObject) linksArray.get(i);
                System.out.println("Service :" + jObj.getString("rel"));
                System.out.println("URL :" + jObj.getString("href"));
                System.out.println("Action :" + jObj.getString("action") + "\n");
            }
        }
    }
//
// END - Get services
//
//
// BEGIN - List all the files in PBCS
//

    public void listFiles() throws Exception {
        //String urlString = String.format("%s/interop/rest/%s/applicationsnapshots", serverUrl, apiVersion);
        String urlString = String.format("%s/applicationsnapshots", getCurrentDetails("LCM","URL"));
        String response = executeRequest(urlString, "GET", null, "application/x-www-form-urlencoded");
        JSONObject json = new JSONObject(response);
        int resStatus = json.getInt("status");
        if (resStatus == 0) {
            if (json.get("items").equals(JSONObject.NULL)) {
                System.out.println("No files found");
            } else {
                System.out.println("List of files :");
                JSONArray itemsArray = json.getJSONArray("items");
                JSONObject jObj = null;
                for (int i = 0; i < itemsArray.length(); i++) {
                    jObj = (JSONObject) itemsArray.get(i);
                    System.out.println(jObj.getString("name"));
                }
            }
        }
    }

    public ArrayList<String> listFilesReturn() throws Exception {
        ArrayList<String> strArray = new ArrayList<String>();
        //String urlString = String.format("%s/interop/rest/%s/applicationsnapshots", serverUrl, apiVersion);
        String urlString = String.format("%s/applicationsnapshots", getCurrentDetails("LCM","URL"));
        String response = executeRequest(urlString, "GET", null, "application/x-www-form-urlencoded");
        JSONObject json = new JSONObject(response);
        int resStatus = json.getInt("status");
        if (resStatus == 0) {
            if (json.get("items").equals(JSONObject.NULL)) {
                System.out.println("No files found");
            } else {
                System.out.println("List of files :");
                JSONArray itemsArray = json.getJSONArray("items");
                JSONObject jObj = null;
                for (int i = 0; i < itemsArray.length(); i++) {
                    jObj = (JSONObject) itemsArray.get(i);
                    strArray.add(jObj.getString("name"));
                    System.out.println(jObj.getString("name"));
                }
            }
        }
        return strArray;
    }
    
    public ArrayList<String> listPlanningFiles() throws Exception {
        ArrayList<String> strArray = new ArrayList<String>();
        //String urlString = String.format("https://emtec-emtec.pbcs.us2.oraclecloud.com/interop/rest/11.1.2.3.600/applicationsnapshots");
        String urlString = String.format("%s/applicationsnapshots", getCurrentDetails("LCM","URL"));
        String response = executeRequest(urlString, "GET", null, "application/x-www-form-urlencoded");
        JSONObject json = new JSONObject(response);
        int resStatus = json.getInt("status");
        if (resStatus == 0) {
            if (json.get("items").equals(JSONObject.NULL)) {
                System.out.println("No files found");
            } else {
                JSONArray itemsArray = json.getJSONArray("items");
                JSONObject jObj = null;
                for (int i = 0; i < itemsArray.length(); i++) {
                    jObj = (JSONObject) itemsArray.get(i);
                    if (jObj.getString("type").equals("EXTERNAL")) {
                        strArray.add(jObj.getString("name"));
                    }
                }
            }
        }
        return strArray;
    }
    
    
    public ArrayList<String> listJobs() throws Exception {
        ArrayList<String> strArray = new ArrayList<String>();
        //String urlString = String.format("https://emtec-emtec.pbcs.us2.oraclecloud.com/HyperionPlanning/rest/v3/applications/POC_CITA/jobdefinitions");
        String urlString = String.format("%s/applications/%s/jobdefinitions", getCurrentDetails("HP","URL"), getAppDetails("Name"));
        String response = executeRequest(urlString, "GET", null, "application/x-www-form-urlencoded");
        JSONObject json = new JSONObject(response);
            if (json.get("items").equals(JSONObject.NULL)) {
                System.out.println("No files found");
            } else {
                //System.out.println("List of jobs:");
                JSONArray itemsArray = json.getJSONArray("items");
                JSONObject jObj = null;
                for (int i = 0; i < itemsArray.length(); i++) {
                    jObj = (JSONObject) itemsArray.get(i);
                    if(jObj.getString("jobType").equals("IMPORT_DATA")){
                        strArray.add(jObj.getString("jobName"));
                    }
                }
            }
        //}
        return strArray;
    }
//
// END - List all the files in PBCS
//
// BEGIN - Upload a file to PBCS
//

    public void uploadFile(String fileName) throws Exception {
        final int DEFAULT_CHUNK_SIZE = 50 * 1024 * 1024;
        InputStream fis = null;
        byte[] lastChunk = null;
        long totalFileSize = new File(fileName).length(), totalbytesRead = 0;
        boolean isLast = false, status = true;
        Boolean isFirst = true;
        int packetNo = 1, lastPacketNo = (int) (Math.ceil(totalFileSize / (double) DEFAULT_CHUNK_SIZE));
        try {
            fis = new BufferedInputStream(new FileInputStream(fileName));
            while (totalbytesRead < totalFileSize && status) {
                int nextChunkSize = (int) Math.min(DEFAULT_CHUNK_SIZE, totalFileSize
                        - totalbytesRead);
                if (lastChunk == null) {
                    lastChunk = new byte[nextChunkSize];
                    totalbytesRead += fis.read(lastChunk);
                    if (packetNo == lastPacketNo) {
                        isLast = true;
                    }
                    status = sendFileContents(isFirst, isLast, lastChunk, fileName);
                    isFirst = false;
                    packetNo = packetNo + 1;
                    lastChunk = null;
                }
            }
            System.out.println("Uploaded successfully");
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    public void uploadFile(File file) throws Exception {
        final int DEFAULT_CHUNK_SIZE = 50 * 1024 * 1024;
        InputStream fis = null;
        byte[] lastChunk = null;
        long totalFileSize = file.length(), totalbytesRead = 0;
        boolean isLast = false, status = true;
        Boolean isFirst = true;
        int packetNo = 1, lastPacketNo = (int) (Math.ceil(totalFileSize / (double) DEFAULT_CHUNK_SIZE));
        try {
            fis = new BufferedInputStream(new FileInputStream(file));
            while (totalbytesRead < totalFileSize && status) {
                int nextChunkSize = (int) Math.min(DEFAULT_CHUNK_SIZE, totalFileSize
                        - totalbytesRead);
                if (lastChunk == null) {
                    lastChunk = new byte[nextChunkSize];
                    totalbytesRead += fis.read(lastChunk);
                    if (packetNo == lastPacketNo) {
                        isLast = true;
                    }
                    status = sendFileContents(isFirst, isLast, lastChunk, file.getName());
                    isFirst = false;
                    packetNo = packetNo + 1;
                    lastChunk = null;
                }
            }
            System.out.println("Uploaded successfully");
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

    }
    
    

    private boolean sendFileContents(Boolean isFirst, boolean isLast, byte[] lastChunk, String fileName) throws Exception {
        HttpURLConnection connection = null;
        try {
            //URL url = new URL(String.format("https://emtec-emtec.pbcs.us2.oraclecloud.com/interop/rest/11.1.2.3.600/applicationsnapshots/%s/contents?q={chunkSize:%d,isFirst:%b,isLast:%b}",
            //        fileName, lastChunk.length, isFirst, isLast));
            URL url = new URL(String.format("%s/applicationsnapshots/%s/contents?q={chunkSize:%d,isFirst:%b,isLast:%b}",
                    getCurrentDetails("LCM","URL"),fileName, lastChunk.length, isFirst, isLast));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setInstanceFollowRedirects(false);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", "Basic " + new sun.misc.BASE64Encoder().encode((pbcsDomain + "." + userName + ":" + password).getBytes()));
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(lastChunk);
            wr.flush();
            int statusCode = connection.getResponseCode();
            String status = getStringFromInputStream(connection.getInputStream());
            if (statusCode == 200 && status != null) {
                if (getCommandStatus(status) == 0) {
                    isFirst = false;
                    return true;
                }
            }
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public int getCommandStatus(String response) throws Exception {
        JSONObject json = new JSONObject(response);
        if (!JSONObject.NULL.equals(json.get("status"))) {
            return json.getInt("status");
        } else {
            return Integer.MIN_VALUE;
        }
    }
//
// END - Upload a file to PBCS
//
    
    public void downloadFile(String fileName, String saveFile) throws Exception {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            //URL url = new URL(String.format("%s/interop/rest/%s/applicationsnapshots/%s/contents", serverUrl, apiVersion, fileName));
            URL url = new URL(String.format("%s/applicationsnapshots/%s/contents", getCurrentDetails("LCM","URL"), fileName));
            System.out.println(url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(false);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", "Basic " + new sun.misc.BASE64Encoder().encode((pbcsDomain + "." + userName + ":" + password).getBytes()));
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            int status = connection.getResponseCode();
            if (status == 200) {
                if (connection.getContentType() != null
                        && connection.getContentType().equals("application/json")) {
                    JSONObject json = new JSONObject(getStringFromInputStream(connection.getInputStream()));
                    System.out.println("Error downloading file : " + json.getString("details"));
                } else {
                    inputStream = connection.getInputStream();
                    outputStream = new FileOutputStream(new File(saveFile));
                    int bytesRead = -1;
                    byte[] buffer = new byte[5 * 1024 * 1024];
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("File download completed.");
                }
            } else {
                throw new Exception("Http status code: " + status);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
    
    public void deleteFile(String fileName) throws Exception {
        String urlString = String.format("%s/applicationsnapshots/%s", getCurrentDetails("LCM","URL"), fileName);
        String response = executeRequest(urlString, "DELETE", null, "application/x-www-form-urlencoded");
        JSONObject json = new JSONObject(response);
        int resStatus = json.getInt("status");
        if (resStatus == 0) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Error deleting file : " + json.getString("details"));
        }
    }
    
    
    
// BEGIN - Execute a Job (EXPORT_DATA, EXPORT_METADATA, IMPORT_DATA, IMPORT_METADATA, CUBE_REFRESH, 
//...)
//
    public void executeJob(String jobType, String jobName, String parameters) throws Exception {
        //String urlString = String.format("%s/HyperionPlanning/rest/v3/applications/%s/jobs", serverUrl, applicationName);
        String urlString = String.format("%s/applications/%s/jobs", getCurrentDetails("HP","URL"), getAppDetails("Name"));
        System.out.println("URL: " + urlString);
        JSONObject payload = new JSONObject();
        payload.put("jobName", jobName);
        payload.put("jobType", jobType);
        payload.put("parameters", new JSONObject(parameters));
        String response = executeRequest(urlString, "POST", payload.toString(), "application/json");
        System.out.println("Job started successfully");
        getJobStatus(fetchPingUrlFromResponse(response), "GET");
    }
//
// END - Execute a Job (EXPORT_DATA, EXPORT_METADATA, IMPORT_DATA, IMPORT_METADATA, CUBE_REFRESH,
//...)
//
}
