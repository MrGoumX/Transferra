package core;

import java.io.*;
import java.net.Socket;
import java.util.List;

import static java.lang.System.exit;

public class Send implements Runnable{
    // Constants.
    private static final int size = 1024*1024;

    // Variables
    private byte[] buffer = new byte[size];
    private BufferedInputStream bfis = null;
    private List<File> files;
    private String ip,id;
    private int port;

    // Constructor.
    public Send(List<File> files, String ip, String id, int port ){
        this.files = files;
        this.ip = ip;
        this.id = id;
        this.port = port;
    }

    // run execute whenever a thread create.
    public void run(){
        try{
            sendString(id); // send authentication id.
            sendNoFiles(); // send number of files witch will send.
            openFile(files); // open files and send files.
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    // openFile open and sends all files.
    private void openFile(List<File> files) throws IOException{

        for (int i = 0; i < files.size(); i++) {

            //send name of file
            String name = files.get(i).getName();
            sendString(name);

            //file.
            try {
                FileInputStream fis = new FileInputStream(files.get(i));
                bfis = new BufferedInputStream(fis);
                System.out.println("File: " + files.get(i).getAbsolutePath() + " is opened and ready to send.");
                sendFiles();
            } catch (FileNotFoundException e) {
                System.err.println("File for transfer does'nt locate.\n\n");
                e.printStackTrace();
                exit(1);
            }
        }
    }

    // sendFiles send one file per time.
    private void sendFiles() throws IOException{
        Socket sock = new Socket(ip, port);
        OutputStream os = null;
        // read file from computer.
        while (true) {

            int i = bfis.read(buffer, 0, size);
            if (i == -1) {
                break;
            }
            // write and send file to client.
            os = sock.getOutputStream();
            os.write(buffer,0,i);
            os.flush();
        }
        System.out.println("Transfer Complete from client");

        bfis.close();
        os.close();
        sock.close();
    }

    // sendString sends a string message to server.
    private void sendString(String str) throws IOException{

        Socket sock = new Socket(ip, port);
        OutputStream os = sock.getOutputStream();
        Writer sendName = new PrintWriter(os);
        sendName.write(str);
        sendName.flush();

        sendName.close();
        os.close();
        sock.close();
    }

    // sendNoFiles send the number of files, that will be send to server.
    private void sendNoFiles() throws IOException{

        Socket sock = new Socket(ip, port);
        OutputStream os = sock.getOutputStream();
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
        out.writeInt(files.size());
        System.out.println("No of files for send: "+files.size());
        out.flush();

        out.close();
        os.close();
        sock.close();
    }

}