/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jmarinasen.jmarinaspkg;

import java.awt.Polygon;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author jean
 */
public class funzioniEditor {

/**
 * 
 * ci vogliono tutti gli oggetti di tutti i passaggi...comincio con quelli del poligono (poligono, mappa, vento)
 * 
 * 
 */
    
    /**
     * 
     * @param polygon
     * @param path
     * @param ventoP
     * @param intVento
     */
    
    public void saveFile(Polygon polygon, String path, double intVento) {
 
        for(int i = 0; i < 100; i++)
            {System.out.println("suca");}
        
JFileChooser chooser = new JFileChooser();
chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
int result = chooser.showSaveDialog(null);

if (result == JFileChooser.APPROVE_OPTION)
{
        String filename = chooser.getSelectedFile().getAbsolutePath();
        BufferedWriter bw = null;
        StringBuffer stringB = new StringBuffer();

        try {
                bw = new BufferedWriter(new FileWriter(filename, true));

                //ora deve costruire la stringona...
                /**
                for(int i=0; i < h; i++)
                        {
                                for(int j=0; j < w; j++)
                                {
                                stringB.append(Double.toString(matrix[j+i*w]) + "\t");
                                }
                                stringB.append(System.getProperty("line.separator"));
                        }
*/
                bw.write(stringB.toString());
                bw.newLine();
                bw.flush();
        } catch (IOException ioe) {
                ioe.printStackTrace();
        } finally {                       // always close the file
                if (bw != null) try {
                bw.close();
                } catch (IOException ioe2) {
                // just ignore it
                }
        } // end try/catch/finally

} // end test()
}
}
