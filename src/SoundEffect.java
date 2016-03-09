
import javax.sound.sampled.*;

import java.io.*;

import sun.audio.*;
import java.net.*;

public class SoundEffect
{
 URL url;
 FileInputStream in;
 AudioInputStream ais;
 Clip clip;
 String path;
 
 
public SoundEffect(String fn) {
	 try
	   {
		 path = new File("Sounds/").getPath();
		 //System.out.println("path is: " + path);
		 //System.out.println(this.getClass().getName());
	     url = this.getClass().getResource(fn);
	     ais = AudioSystem.getAudioInputStream(url);
	     clip = AudioSystem.getClip();
	     clip.open(ais);
	     clip.start(); 
	   } 
	   
	   catch(Exception e){e.printStackTrace();}
	}
}
