package projetJavaS5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class ParserTests {
	@AfterEach
	public void cleanup() {
	    // Suppression du fichier "test.ca"
	    new File("test.ca").delete();
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"banane", 
			"ville()\nville()\nroute(A,B)", 
			"route(A,B)\nville()\nville()\nrecharge(A)", 
			"ville\nville\nrecharge(A)", 
			"\n", 
			"()", 
			"ville(A)\nville(B)\nroute()", 
			"ville(A)\nville(B)\nroute(A,)", 
			"ville(A)\nville(B)\nroute(,)", 
			"ville(A)\nville(B)\nroute(","ville(A)\nville(B)\nroute)", 
			"ville(A)\nville(B)\nrecharge(A)", 
			"recharge(A)\nville(A)\nville(B)", 
			"ville(A)\nville(B)\nville(C)\nroute(A,B)\nroute(C,B)\nrecharge(A)", 
			"ville(A)\nville(B)\nville(C)\nville(D)\nville(E)\nroute(A,B)\nroute(A,C)\nroute(C,B)\nroute(C,E)\nroute(D,E)\nrecharge(E)"
	})
	public void parserTest(String fileContent) {
		String file = "test.ca";
		boolean assertThrows = false;
		
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(fileContent);
			writer.close();
			
			ParserCommunaute.parser(file);

		}catch(ParserException e){
			assertThrows = true;
			System.out.println("ParserException thrown: " + e.getMessage());
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		Assertions.assertTrue(assertThrows);
	}
	
}