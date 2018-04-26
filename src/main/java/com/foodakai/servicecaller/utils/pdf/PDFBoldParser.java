package com.foodakai.servicecaller.utils.pdf;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PDFBoldParser extends PDFTextStripper {

    PrintWriter writer;
    boolean wasBold=false;

    private ArrayList<String> bold_text = new ArrayList<>();

    public PDFBoldParser(String filepath) throws IOException {
        writer=new PrintWriter(filepath,"UTF-8");
        writer.println("Dictionary Source:");
    }

    public PDFBoldParser() throws IOException {
    }

    public ArrayList<String> getBold_text() {
        return bold_text;
    }

    public void setBold_text(ArrayList<String> bold_text) {
        this.bold_text = bold_text;
    }

    @Override
    protected void processTextPosition(TextPosition text){
        if(text.getFont().getFontDescriptor()!=null){
            if (text.getFont().getName().contains("Bold")) {
                //writer.print(text.toString().toUpperCase());
                bold_text.add(text.toString());
                wasBold=true;
            }
            else if (text.getFont().getName().contains("Bold")){
                //writer.println();
                //writer.print(text.toString().toUpperCase());

                bold_text.add(text.toString());
                wasBold=true;
            }
            else{
                //writer.print(text.toString());
                wasBold=false;
            }
        }
    }

    public void closeParser(){
        writer.close();
    }
}
