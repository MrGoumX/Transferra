package core;
import java.io.*;
import java.net.Socket;
import java.util.List;

import static java.lang.System.exit;

public class Send implements Runnable{
    // Constants.
    private static final int  port = 49900;
    private static final int size = 1024*1024;

    private byte[] buffer = new byte[size];
    private OutputStream os = null;
    private BufferedInputStream bfis;
    private List<File> files;
    private String ip,ID;

    public Send(List<File> files, String ip, String ID){
        this.files=files;
        this.ip=ip;
        this.ID=ID;
    }

    public void run(){
        sendAuthID(ID);
        openFile(files);
        try{
            send();
        }catch (IOException e){
            System.err.println("Error closing server socket.\n\n");
            e.printStackTrace();
            exit(1);
        }
    }

    private void openFile(List<File> files){
        for (int i = 0; i < files.size(); i++) {
            //send name of file
            try {
                Socket sock = new Socket(ip, port);
                OutputStream os = sock.getOutputStream();
                String name = files.get(i).getName();
                Writer sendName = new PrintWriter(os);
                sendName.write(name);
                sendName.flush();
                if (sendName != null) sendName.close();
                if (os != null) os.close();
                if (sock != null) sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //file.
            try {
                FileInputStream fis = new FileInputStream(files.get(i));
                bfis = new BufferedInputStream(fis);
                System.out.println("File: " + files.get(i).getAbsolutePath() + " is opened.");
            } catch (FileNotFoundException e) {
                System.err.println("File for transfer doesnt locate.\n\n");
                e.printStackTrace();
                exit(1);
            }
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

    private void sendAuthID(String ID){
        try {
            Socket sock = new Socket(ip, port);
            OutputStream os = sock.getOutputStream();
            Writer sendName = new PrintWriter(os);
            sendName.write(ID);
            sendName.flush();
            if (sendName != null) sendName.close();
            if (os != null) os.close();
            if (sock != null) sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}