package core;

import sample.ReceiveWindows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import static java.lang.System.exit;

public class Receive implements Runnable{

    // Constants.
    private static final int size = 1024*1024;

    private ServerSocket server = null;
    private FileOutputStream fos;
    private String storePath;
    private String authID;
    private  int port;
    private int numberOfFiles;

    // Public Methods.

    // Constructor.
    public Receive(String storePath, String authID, int port){
        this.storePath = storePath;
        this.authID = authID;
        this.port = port;
    }

    // run execute whenever a thread create.
    public void run(){
        try {

            // start server.
            server = new ServerSocket(port);
            System.out.println("Server socket is created at port: " + port);

            // if authentication id is correct:
            if (receiveAuth()) {
                // for each file:
                numberOfFiles = receiveNoFiles();
                for(int i=0; i<numberOfFiles;i++){

                    if(Thread.currentThread().isInterrupted())break;
                    // open path for storing.
                    openFile(storePath, receiveName());
                    // receive and store this file.
                    readData();
                }
                // close server .
                server.close();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    // return total number of files, that will be received.
    public int getNumberOfFiles(){
        return numberOfFiles;
    }

    // Private Methods.

    // openFile create a FileOutptStream with the path andd name of file witch receive.
    private void openFile(String path, String name){

        try{
            String fullPath = path + File.separator + name;
            fos = new FileOutputStream(fullPath);
            System.out.println("File: "+ fullPath + " is opened for storing.");
        }catch(FileNotFoundException e){
            System.err.println("Error opening file at path: " + path + File.separator + name +".\n\n");
            e.printStackTrace();
            exit(1);
        }
    }

    // readData receive and store a file.
    private void readData() throws IOException{

        int current;
        byte buffer[] = new byte[size];
        Socket sock = server.accept();
        System.out.println("Connect with client for sending files.");
        InputStream is = sock.getInputStream();
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        while(((current = is.read(buffer))>0) && (!Thread.currentThread().isInterrupted())){
            bos.write(buffer,0,current);
        }
        bos.flush();
        System.out.println("Transfer Complete from server.");

        // Increase the number of sending files at progress bar.
        ReceiveWindows.increaseNoFiles();

        fos.close();
        bos.close();
    }

    // receiveAuth receive and check if authentication id is correct.
    private boolean receiveAuth() throws IOException {

        Socket sock = server.accept();
        System.out.println("Connect with client for sending auth id.");
        BufferedReader receiveAuth = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        String recAuth = receiveAuth.readLine();
        receiveAuth.close();
        sock.close();
        System.out.println("received auth id is: "+recAuth);
        return recAuth.equals(authID);
    }

    // receiveName receive the name and suffix of the file.
    private String receiveName()throws IOException {
        Socket sock = server.accept();
        System.out.println("Connect with client for sending name files.");
        BufferedReader receiveName = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        String name = receiveName.readLine();
        receiveName.close();
        sock.close();
        System.out.println("received name of files is: "+name);
        return name;
    }

    // receiveNoFiles receives the number of all files that will be receive.
    private int receiveNoFiles() throws IOException {
        Socket sock = server.accept();
        System.out.println("Connect with client for sending number files.");
        DataInputStream in = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
        int no = in.readInt();
        in.close();
        sock.close();
        System.out.println("received number of files is: "+no);
        return no;
    }

}