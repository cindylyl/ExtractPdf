package extractpdf;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.*;
import java.util.*;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;


public class ExtractPDF {

	public static void main(String[] args) {
		String pdf_file="/Users/cindy_liao/Downloads/60-656_outlineW2017_v1.pdf";
		String url_="http://robertfeldt.net/publications/petersen_ease08_sysmap_studies_in_se.pdf";
		String keyword="process";
		System.out.println();
	    System.out.println(extract_pdf(keyword,url_));
		
	}
	
	@SuppressWarnings("finally")
	static String extract_pdf(String keyword,String url_){
		//File pdfFile = new File(pdf_file);
		
		PDDocument document=null;
		String pattern="( |^)(\\W)*"+keyword+"(\\W)*( |$)";
		String result="";
		Pattern p=Pattern.compile(pattern);
		try{
			URL url=new URL(url_);
			//document=PDDocument.load(pdfFile);
			InputStream urlStream = url.openStream();
			document=PDDocument.load(urlStream);
			
			// get page number
			int pages= document.getNumberOfPages();
			PDFTextStripper stripper=new PDFTextStripper();
			stripper.setSortByPosition(true);
			//read text content page by page
			for(int i=0;i<=pages;i++){
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				String content = stripper.getText(document);
				System.out.println(content);
				String[] strs=content.split("\n");
				int line_number=0;
				for (String str:strs){
					Matcher match= p.matcher(str);
					while(match.find()){
				    	  System.out.println("Found keyword: " +match.group(0)+" in line: "+line_number+" page: "+i+" at position "+ match.start(0));
				    	  result=result+strs[line_number]+"(page: "+i+" line: "+line_number+")"+"\n";
				    	  System.out.println(str);
				    	  
				      }
					line_number++;
					
				}
			}

			document.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
			return result;
		}
		
	}

}
