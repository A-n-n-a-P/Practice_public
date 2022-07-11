package study.anna.themes.Files;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class FilesSearch {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        File dirs = new File("Way");
        Map<String, File> fileHashMap = new HashMap<>();
        listFiles(dirs, fileHashMap);
    }

    public static void listFiles(File folder, Map<String, File> fileHashMap) throws NoSuchAlgorithmException, IOException {
        float sizeDublicates = 0.0f;
        File[] files = folder.listFiles();
        for (int i =0; i < files.length; i++) {
            File currentFolder = files[i];
            if(files[i].isDirectory()) {
                listFiles(currentFolder, fileHashMap);
            }
            else {
                String keyHash = HashCalculation(currentFolder);
                float sizeFile;
                if(fileHashMap.containsKey(keyHash)) {
                        sizeFile = currentFolder.length();
                        sizeDublicates += sizeFile;
                    PrintWriter duplicates = new PrintWriter(new BufferedWriter(new FileWriter("duplicatesFiles.txt", true)));
                        try {
                            duplicates.println("\nДУБЛИКАТ" + "Файл:  " + currentFolder.getName() + "\nПуть файла:  " + currentFolder + "\nРазмер файла: " + sizeFile + " байт" + "\nРазмер всех дубликатов: " + sizeDublicates / 1024 + " кб");
                            duplicates.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
                else {
                        fileHashMap.put(HashCalculation(currentFolder), currentFolder);
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
}
