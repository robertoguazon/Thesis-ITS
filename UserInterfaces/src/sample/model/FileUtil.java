package sample.model;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Yves on 10/16/2016.
 */
public class FileUtil {

    private Scanner in;
    private FileWriter out;
    private final String path = "UserInterfaces/src/sample/style/stylePath.txt";

    public String readFile(){
        String str = null;
        try {
            File file = new File(path);
            if (!file.exists()){
                System.out.println("created file");
                file.createNewFile();
            }
            in = new Scanner(file);
            if (in.hasNextLine()){
                str = in.nextLine();
            }else {
                //str = "sample/style/css/dark.css";
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return str;
    }

    public void writeFile(String source){
        try {
            out = new FileWriter(new File(path), false);
            out.write(source);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
