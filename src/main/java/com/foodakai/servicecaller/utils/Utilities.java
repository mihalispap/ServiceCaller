package com.foodakai.servicecaller.utils;

import com.foodakai.servicecaller.utils.config.Configuration;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class Utilities {

    private Utilities(){}

    private static volatile Utilities instance = null;

    public static Utilities getInstance(){
        if (instance == null) {
            synchronized(Utilities.class) {
                if (instance == null) {
                    instance = new Utilities();
                }
            }
        }
        return instance;
    }

    public byte[] readContentIntoByteArray(File file)
    {
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length()];
        try
        {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
            for (int i = 0; i < bFile.length; i++)
            {
                System.out.print((char) bFile[i]);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return bFile;
    }

    public static Configuration loadConfiguration(String yaml_file){

        Configuration configuration = new Configuration();

        Yaml yaml = new Yaml();
        try( InputStream in = Files.newInputStream( Paths.get( yaml_file ) ) ) {
            configuration = yaml.loadAs( in, Configuration.class );
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return configuration;
    }

    /*public static Object sendGET(String url) throws Exception{

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        System.out.println("Content Type : " + con.getContentType());

        if(con.getContentType().contains("pdf")) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream is = null;
            try {
                is = con.getInputStream();
                byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
                int n;

                while ((n = is.read(byteChunk)) > 0) {
                    baos.write(byteChunk, 0, n);
                }
            } catch (IOException e) {
                //System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
                e.printStackTrace();
                // Perform any other exception handling that's appropriate.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
            return baos.toByteArray();
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response;

    }*/

    public static Object sendGET(String url, Configuration config) throws Exception{

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        try {
            if (config != null && config.getMediatype().equals("json"))
                con.setRequestProperty("Accept", "application/json");
        }
        catch(Exception e){}

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        System.out.println("Content Type : " + con.getContentType());
        //System.out.println("Content Encoding : " + con.getContentEncoding());

//        String cookiesHeader = con.getHeaderField("Set-Cookie");
//        List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);
//
//        CookieManager cookieManager = new CookieManager();
//        cookies.forEach(cookie -> cookieManager.getCookieStore().add(null, cookie));

        if(con.getContentType().contains("pdf")) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream is = null;
            try {
                is = con.getInputStream();
                byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
                int n;

                while ((n = is.read(byteChunk)) > 0) {
                    baos.write(byteChunk, 0, n);
                }
            } catch (IOException e) {
                //System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
                e.printStackTrace();
                // Perform any other exception handling that's appropriate.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
            return baos.toByteArray();
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response;

    }

    public static void save_file(String filename, MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();
        BufferedOutputStream stream =
            new BufferedOutputStream(new FileOutputStream(
                            new File(filename)));
        stream.write(bytes);
        stream.close();
    }

    public static String pdf2text(String pdf_file) throws Exception{

        PDFParser parser;
        PDFTextStripper pdfStripper;
        PDDocument pdDoc ;
        COSDocument cosDoc ;

        pdDoc = PDDocument.load(new File(pdf_file));
        pdfStripper = new PDFTextStripper();

        pdDoc.getNumberOfPages();

        // reading text from page 1 to 10
        // if you want to get text from full pdf file use this code
        // pdfStripper.setEndPage(pdDoc.getNumberOfPages());

        String text = pdfStripper.getText(pdDoc);

        pdDoc.close();

        System.out.println(text.trim());

        return text.trim();
    }
}
