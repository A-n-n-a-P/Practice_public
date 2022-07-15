package study.anna.themes.Files;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class FilesSearch2 {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        File dirs = new File("C:\\Users\\ania_\\Desktop\\Антохино — копия");
        Map<String, List<File>> fileHashMap = new HashMap<>();
        listFiles(dirs, fileHashMap);
        addToFile(fileHashMap);
    }

    public static void listFiles(File folder, Map<String, List<File>> fileHashMap) throws NoSuchAlgorithmException, IOException {
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            File currentFolder = files[i];
            if(currentFolder.isDirectory()) {
                listFiles(currentFolder, fileHashMap);
            }
            else {
                String keyHash = HashCalculation(currentFolder);
                if(fileHashMap.containsKey(keyHash)) {
                    fileHashMap.get(keyHash).add(currentFolder);
                }
                else {
                    List<File> fileList = new ArrayList<>();
                    fileList.add(currentFolder);
                    fileHashMap.put(keyHash, fileList);
                }
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

    public static void addToFile(Map<String, List<File>> fileHashMap) throws IOException {
        PrintWriter duplicates = new PrintWriter(new BufferedWriter(new FileWriter("duplicatesFiles.txt", true)));
        float sizeDublicates = 0.0f;
        for (List<File> value : fileHashMap.values()) {
            if(value.size() > 1) {
                for (File file : value) {
                    float sizeFile;
                    sizeFile = file.length();
                    sizeDublicates += sizeFile;
                    try {
                        duplicates.println("Файл: " + file.getName() + "\nПуть файла: " + file + "\nРазмер файла: " + sizeFile + " байт\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if((sizeDublicates / 1024) > 1024) {
            sizeDublicates /= Math.pow(1024, 2);
            duplicates.println("\nРазмер всех дубликатов: " + sizeDublicates + " мб");
        }
        else {
            sizeDublicates /= 1024;
            duplicates.println("\nРазмер всех дубликатов: " + sizeDublicates + " кб");
        }
        duplicates.close();
    }
}
