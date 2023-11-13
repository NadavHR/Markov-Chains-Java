import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import javax.print.DocFlavor.STRING;

class Main {
    public static void main(String[] args) {
        // testCharacters("hello", 10);
        testWords(new String[]{"hello"}, 100);
    }


    public static void testWords(String[] kernel, int length){
        MarkovChain<String> mark = new MarkovChain<>();
        File f = new File("text.txt");
        Scanner s;
        String str = "";
        try {
            s = new Scanner(f);
            while (s.hasNextLine()) {
                str += " \n" + s.nextLine();    
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        

        mark.fit(str.split(" "));

        String genStr = "";
        for (String string : mark.forward(kernel, length)){
            genStr += " " + string;
        }

        System.out.println(genStr);
    }


    public static void testCharacters(String kernel, int length){
        MarkovChain<Character> mark = new MarkovChain<>();
        File f = new File("text.txt");
        Scanner s;
        String str = "";
        try {
            s = new Scanner(f);
            while (s.hasNextLine()) {
                str += " \n" + s.nextLine();    
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        

        mark.fit(str.chars().mapToObj(c -> (char)c).toArray(Character[]::new));

        String genStr = "";
        for (Character c : mark.forward(kernel.chars().mapToObj(c -> (char)c).toArray(Character[]::new), length)){
            genStr += c;
        }

        System.out.println(genStr);
    }
}