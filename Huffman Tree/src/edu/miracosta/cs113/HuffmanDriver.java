package edu.miracosta.cs113;



import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class HuffmanDriver {
    private static final String MENU_CHOICES = "1) View Translation Table.\n2) Decode From File.\n3) Decode From Console.\n4) Exit Program.";
    private HuffmanTree huffmanTree;
    private Scanner key;
    private boolean running;

    public static void main(String[] args){
        HuffmanDriver driver = new HuffmanDriver();
        driver.menu();
    }
    public HuffmanDriver(){
        running = true;
        key = new Scanner(System.in);
    }

    public void menu(){
        PrintWriter writer;
        BufferedReader reader;
        String input, outputFileName;
        int originalSize = 0;
        int compressedSize = 0;
        int decodedSize = 0;
        StringBuilder sb = new StringBuilder();

        do{
            input = getStringInput("Please enter the name of the website you would like to compress.");
            try{
                outputFileName = getStringInput("Please enter the name of the file you would like to save the website's text to.");
                TextFileGenerator.makeCleanFile(input, outputFileName);
                huffmanTree = new HuffmanTree(new File(outputFileName));

                reader = new BufferedReader(new FileReader(new File(outputFileName)));
                writer = new PrintWriter(new File(outputFileName + "Encoded"));
                int current;
                current = reader.read();
                while (current != -1){
                    originalSize += 16;
                    writer.write(huffmanTree.characterEncodings[current]);
                    current = reader.read();
                }
                writer.close();
                reader.close();

                reader = new BufferedReader(new FileReader(new File(outputFileName + "Encoded")));
                writer = new PrintWriter(new File(outputFileName + "Decoded"));
                String line = reader.readLine();
                while (line != null){
                    compressedSize += line.length();
                    sb.append(line);
                    line = reader.readLine();
                }
                reader.close();

                writer.write(huffmanTree.decode(sb.toString()));
                writer.close();

                decodedSize = getBytesInCharFile(new File(outputFileName + "Decoded"));

                reader.close();

                System.out.println("# of bytes in original text: " + originalSize);
                System.out.println("# of bytes in decoded text: " + decodedSize);
                System.out.println("# of bytes in encoded text: " + compressedSize);

                double compressionRatio = ((double)compressedSize  /(double) originalSize) * 100;

                System.out.printf("%.2f", compressionRatio);
                System.out.println("% Compression Ratio");
            }catch (IOException e){
                e.printStackTrace();
                System.exit(1);
            }

        }while (running);
    }
    public String getStringInput(String message){
        System.out.println(message);
        return key.nextLine();
    }

    public void exit(){
        running = false;
        key.close();
    }

    public int getBytesInCharFile(File file){
        BufferedReader reader;
        int current, bytes;
        current = 0;
        bytes = 0;
        try{
            reader = new BufferedReader(new FileReader(file));
            current = reader.read();
            while(current != -1){
                bytes += 16;
                current = reader.read();
            }

            reader.close();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(0);
        }
        return bytes;
    }
}