package study.anna.themes.Files.ForExperiments;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Experiments {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

//        addFiles();
        File dir1 = new File("WAY");
        HashCalculation(dir1);
//        System.out.println("\n" + Digest());
    }

    public static void addFiles() {
        HashMap<Integer, String> pr = new HashMap<>();
        pr.put(1, "Hello");
        System.out.println(pr + "\tpr.hashCode()\t" + pr.hashCode());
    }

    public static boolean Digest() throws NoSuchAlgorithmException, IOException {
        File dir1 = new File("WAY");
        File dir2 = new File("WAY");

        MessageDigest md1 = MessageDigest.getInstance("SHA-256");
        MessageDigest md2 = MessageDigest.getInstance("SHA-256");

        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();

        FileInputStream fin1 = new FileInputStream(dir1);
        FileInputStream fin2 = new FileInputStream(dir2);

        byte[] buffer1 = new byte[fin1.available()];
        byte[] buffer2 = new byte[fin2.available()];

        fin1.read(buffer1, 0, fin1.available());
        fin2.read(buffer2, 0, fin2.available());

        md1.update(buffer1);
        md1.digest(buffer1);

        md2.update(buffer2);
        md2.digest(buffer2);

        for (byte b1 : buffer1) {
            sb1.append(String.format("%02x", b1));
        }
        System.out.println("\nMessageDigest: " + sb1.toString());

        System.out.print("File data:\t");
        for (byte b1 : buffer1) {
            System.out.print((char)b1);
        }
        System.out.println();
        fin1.close();

        for (byte b2 : buffer2) {
            sb2.append(String.format("%02x", b2));
        }
        System.out.println("\nMessageDigest: " + sb2.toString());
        System.out.print("File data:\t");
        for (byte b2 : buffer2) {
            System.out.print((char)b2);
        }
        fin2.close();

        boolean result = MessageDigest.isEqual(md1.digest(), md2.digest());
//        boolean result2 = sb1.equals(sb2);
        return result;
    }

    public static void HashCalculation(File file) throws NoSuchAlgorithmException, IOException {
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
        System.out.println(hashFile);
    }
}
