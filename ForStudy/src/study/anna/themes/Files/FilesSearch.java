package study.anna.themes.Files;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class FilesSearch {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        File dirs = new File("WAY");

        listFiles(dirs);
    }

    public static void listFiles(File folder) throws NoSuchAlgorithmException, IOException {
        File[] files = folder.listFiles();
        for (int i =0; i < files.length; i++) {
            File currentFolder = files[i];
            if(files[i].isDirectory()) {
                listFiles(currentFolder);
            }
            else {
                Map<String, File> fileHashMap = new HashMap<>();

//                for (Map.Entry<String, File> hash : fileHashMap.entrySet()) {   // не заходит в цикл
//                  System.out.println(hash.getKey());
                String keyHash = HashCalculation(currentFolder);
                    if(!(fileHashMap.containsKey(keyHash))) {
                        fileHashMap.put(HashCalculation(currentFolder), currentFolder);
//                        System.out.println(fileHashMap);
                    }
                    else {
                        System.out.println("совпадениe");
                    }
                    System.out.println(fileHashMap);
//               }
//                System.out.println(fileHashMap);
//                System.out.println("file " + currentFolder.getName());
            }
        }
    }

    public static String HashCalculation(File file) throws NoSuchAlgorithmException, IOException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[1024];
        try(InputStream instream = new FileInputStream(file)) {
            int lengthFile;
            while((lengthFile = instream.read(buffer)) != -1) {
                md.update(buffer, 0, lengthFile);
            }
        }
        byte[] digestFile = md.digest();
        StringBuffer stringDigest = new StringBuffer();
        for (byte b : digestFile) {
            stringDigest.append(String.format("%02x", b));
        }
        String hashFile = stringDigest.toString();
        return hashFile;
    }
}
