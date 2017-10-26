package core;
import java.io.*;
import java.net.Socket;

import static java.lang.System.exit;

public class Send implements Runnable{
    // Constants.
    private static final int  port = 49900;
    private static final int size = 1024*1024;

    private byte[] buffer = new byte[size];
    private OutputStream os = null;
    private BufferedInputStream bfis;
    private File file;
    private String ip;

    public Send(File file, String ip){
        this.file=file;
        this.ip=ip;
    }

    public void run(){
        openFile(file);
        try{
            send();
        }catch (IOException e){
            System.err.println("Error closing server socket.\n\n");
            e.printStackTrace();
            exit(1);
        }
    }

    private void openFile(File file){
        try{
            //file.
            FileInputStream fis = new FileInputStream(file);
            bfis = new BufferedInputStream(fis);
            System.out.println("File: "+ file.getAbsolutePath() + " is opened.");
        }catch(FileNotFoundException e){
            System.err.println("File for transfer doesnt locate.\n\n");
            e.printStackTrace();
            exit(1);
        }
    }

    private void send() throws IOException{
        Socket sock = new Socket(ip, port);
        //sock = server.accept();
        System.out.println("Client socket accepted");
        // read file from computer.
        while (true) {
            int i=0;
            i = bfis.read(buffer, 0, size);
            if (i == -1) {
                break;
            }
            // write and send file to client.
            os = sock.getOutputStream();
            os.write(buffer,0,i);
            os.flush();
        }
        System.out.println("Transfer Complete");

        if (bfis != null) bfis.close();
        if (os != null) os.close();
        if (sock != null) sock.close();
    }
}