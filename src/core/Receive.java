package core;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.exit;

public class Receive implements Runnable{

    // Constants.
    private static final int  port = 49900;
    private static final int size = 1024*1024;

    private ServerSocket server = null;
    private BufferedOutputStream bos;
    private FileOutputStream fos;
    private String storePath;
    private String ID,authID;

    public Receive(String ID, String file,String authID){
        this.ID=ID;
        this.storePath = file;
        this.authID=authID;
    }

    public void run(){
        try {
            startServer(port);
            if (receiveAuth()) {
                openFile(storePath);
                try {
                    readData();
                } catch (IOException e) {
                    System.err.println("Error reading data.\n\n");
                    e.printStackTrace();
                    exit(1);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void openFile(String path){

        try{
            fos = new FileOutputStream(path);
            bos = null;
            System.out.println("File: "+ path + " is opened.");
        }catch(FileNotFoundException e){
            System.err.println("Error opening file.\n\n");
            e.printStackTrace();
            exit(1);
        }
    }

    private void startServer(int port) throws IOException{
        server = new ServerSocket(port);
        System.out.println("Server socket is created");
    }

    private void readData() throws IOException{
        int current;
        byte buffer[] = new byte[size];
        Socket sock = null;
        sock = server.accept();
        System.out.println("Connect to server.");
        InputStream is = sock.getInputStream();
        bos = new BufferedOutputStream(fos);
        while((current = is.read(buffer))>0){
            bos.write(buffer,0,current);
        }
        bos.flush();
        System.out.println("Transfer Complete.");

        if(fos!=null)fos.close();
        if(bos!=null)bos.close();
        if(sock!=null)sock.close();
        if(server!=null)server.close();
    }

    private boolean receiveAuth() throws IOException {
        boolean check=false;
        Socket sock = null;
        sock = server.accept();
        BufferedReader receiveAuth = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        String recAuth = receiveAuth.readLine();
        if (recAuth.equals(authID)) {
            check = true;
        }
        receiveAuth.close();
        return check;
    }
}