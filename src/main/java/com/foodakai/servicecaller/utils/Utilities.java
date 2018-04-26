package com.foodakai.servicecaller.utils;

import com.foodakai.servicecaller.responses.internal.OntologyRecommenderEntity;
import com.foodakai.servicecaller.utils.config.Configuration;
import com.foodakai.servicecaller.utils.pdf.PDFBoldParser;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.manager.RemoteRepositoryManager;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Utilities {

    /*
    *   TODO:
    *       add in resources
    * */
    private static String[] stopwords_for_important_sentences={"et al"};

    /*
    *   TODO:
    *       add in resources
    * */
    private static String[] irrelevant_sections={"ACKNOWLEDGMENTS", "REFERENCES"};

    /*
    *   TODO:
    *       add in resources
    * */
    private static String[] keywords={"keywords"};


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

        return text.trim();
    }

    public static String pdf2title(String pdf_file) throws Exception{

        PDFParser parser;
        PDFBoldParser pdfStripper;
        PDDocument pdDoc ;
        COSDocument cosDoc ;

        pdDoc = PDDocument.load(new File(pdf_file));
        pdfStripper = new PDFBoldParser();

        pdDoc.getNumberOfPages();

        // reading text from page 1 to 10
        // if you want to get text from full pdf file use this code
        // pdfStripper.setEndPage(pdDoc.getNumberOfPages());

        String text = pdfStripper.getText(pdDoc);

        pdDoc.close();

        return text.trim();


//        PDDocument pdDoc ;
//        pdDoc = PDDocument.load(new File(pdf_file));
//        //for (int i = 0; i < pdDoc.getNumberOfPages(); i++)
//        for (int i = 0; i < 1; i++)
//        {
//            PDPage page = pdDoc.getPage(i);
//            PDResources res = page.getResources();
//            for (COSName fontName : res.getFontNames())
//            {
//                PDFont font = res.getFont(fontName);
//                // do stuff with the font
//                System.out.println(font.getName());
//                System.out.println(font.getAverageFontWidth());
//                System.out.println(font.getType());
//                System.out.println(font.getFontDescriptor().getFontName());
//                System.out.println(font.getFontDescriptor().getFontFamily());
//                System.out.println(font.getFontDescriptor().getFontWeight());
//            }
//        }
//
//        return "";
    }

    public static String toCamelCase(final String init) {
        if (init==null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length()==init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }

    public static boolean is_important_sentence(String s){

        for(String el : stopwords_for_important_sentences){
            if(s.contains(el))
                return false;
        }
        return true;
    }

    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }

    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }

    public static boolean graphDB_caller(String input){
        try
        {
            //String dbaasURL = "http://83.212.115.164:7200";///repositories/agris";
            String dbaasURL = "http://94.130.68.90:7200/";
            String repositoryId="geonames";
            String ApiKey = "";
            String ApiPass = "";
            String queryString = "PREFIX geo-ont: <http://www.geonames.org/ontology#> "+
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
                    "select ?s ?c ?n "+
                    "where{ "+
                    "?s ?d \""+toCamelCase(input)+"\". "+
                    "}";
            RemoteRepositoryManager manager = RemoteRepositoryManager.getInstance(
                    dbaasURL, ApiKey, ApiPass);

            System.out.println("Calling gdb with:"+toCamelCase(input));

            // Get the repository to use
            Repository repository = manager.getRepository(repositoryId);

            RepositoryConnection con = repository.getConnection();

            TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL,
                    queryString);

            TupleQueryResult result = tupleQuery.evaluate();

            if(result.hasNext()){
                result.close();
                con.close();
                return true;
            }

            result.close();
            con.close();
        }
        catch(java.lang.Exception e)
        {
            e.printStackTrace();
        }

        return false;

    }

    public static String remove_irrelevant_sections(String input){

        String relevant="";
        for(String v : input.split("\\.")){
            for(String irr : irrelevant_sections){
                if(v.contains(irr))
                    return relevant;
            }
            relevant+=v+".";
        }

        return relevant;
    }

    public static ArrayList<String> extract_keywords(String input){

        ArrayList<String> ekeywords = new ArrayList<>();

        for(String v : input.split("\n"))
        {
            for(String k : keywords)
            {
                if(v.toLowerCase().contains(k))
                {
                    for(String key : v.split(","))
                        ekeywords.add(remove_punctuation(key.toLowerCase().replace(k, "")).trim());
                    return ekeywords;
                }
            }
        }
        return ekeywords;
    }

    public static String cleanse_string(String input){
        return input.replace("\r", "").replace("\n", " ");
    }

    public static String remove_punctuation(String s) {
        String res = "";
        for (Character c : s.toCharArray()) {
            if(Character.isLetterOrDigit(c) || c =='-' || c==' ' || c=='/')
                res += c;
        }
        return res;
    }
}
