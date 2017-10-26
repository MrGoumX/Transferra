package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ReceiveWindows {

    public void initialize(){
        try{
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            String ip = in.readLine(); //you get the IP as a String
           //ipTextField.setText(ip);
        }catch (Exception e){}
    }
}
