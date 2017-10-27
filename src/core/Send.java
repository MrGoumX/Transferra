package core;
import java.io.*;
import java.net.Socket;
import java.util.List;

import static java.lang.System.exit;

public class Send implements Runnable{
    // Constants.
    private static final int size = 1024*1024;

    private byte[] buffer = new byte[size];
    private BufferedInputStream bfis;
    private List<File> files;
    private String ip,id;
    private int port;

    public Send(List<File> files, String ip, String id, int port ){
        this.files = files;
        this.ip = ip;
        this.id = id;
        this.port = port;
    }

    public void run(){
        try{
            sendString(id);
            openFile(files);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    private void openFile(List<File> files) throws IOException{

        for (int i = 0; i < files.size(); i++) {

            //send name of file
            String name = files.get(i).getName();
            sendString(name);

            //file.
            try {
                FileInputStream fis = new FileInputStream(files.get(i));
                bfis = new BufferedInputStream(fis);
                sendFiles();
                System.out.println("File: " + files.get(i).getAbsolutePath() + " is opened.");
            } catch (FileNotFoundException e) {
                System.err.println("File for transfer does'nt locate.\n\n");
                e.printStackTrace();
                exit(1);
            }
        }
    }

    private void sendFiles() throws IOException{
        Socket sock = new Socket(ip, port);
        OutputStream os = null;

        // read file from computer.
        while (true) {
            int i = 0;
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

    private void sendString(String str) throws IOException{

        Socket sock = new Socket(ip, port);
        OutputStream os = sock.getOutputStream();
        Writer sendName = new PrintWriter(os);
        sendName.write(str);
        sendName.flush();
        if (sendName != null) sendName.close();
        if (os != null) os.close();
        if (sock != null) sock.close();
    }

    private void sendNoFiles() throws IOException{

        Socket sock = new Socket(ip,port);
        OutputStream os = sock.getOutputStream();
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
        out.writeInt(files.size());
        out.flush();
        if(out!=null)out.close();
        if(os!=null)os.close();
        if(sock!=null)sock.close();
    }
}