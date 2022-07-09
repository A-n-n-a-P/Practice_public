package study.anna.themes.Files;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class FilesSearch {

    public static void main(String[] args) {
        Object a;
  //      a.hashCode();
        File dirs = new File("Way");

        listFiles(dirs);
    }

    public static void listFiles(File folder) {
        File[] files = folder.listFiles();
        for (int i =0; i < files.length; i++) {
            File currentFolder = files[i];
            if(files[i].isDirectory()) {
                System.out.println("it's folder " + currentFolder.getName());
                listFiles(currentFolder);
            }
            else {
                System.out.println("file " + currentFolder.getName());
            }
        }
    }
}
