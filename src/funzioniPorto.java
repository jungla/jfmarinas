package jmarinasen.jmarinaspkg;

import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JFileChooser;

/*
* funzioniPorto.java
*
* Created on 2 aprile 2007, 9.11
*
*/

/**
*
* @author Jean Alberto Mensa
 * @param   Descrizione
*/
public class funzioniPorto {

/**
 * @throws diag la diagonale del porto 
 * @throws px posizione x dell'angolo basso sx
 * @throws py posizione y dell'angolo basso sx
 * @throws body Polygon del confine del porto
 * @throws bocca vettore delle line2D delle bocche di porto
 * @throws moli vettore delle line2D dei moli
 * @throws moliT tipo di moli
 * @throws w larghezza della matrice porto
 * @throws h altezza della matrice porto
 * @throws angle angolo del vettore vento
 * @throws intVento valore dell'intensità del vento
 */

public static double tortuositaMoli(double diag, double px, double py, Polygon body, Vector bocca, Vector moli, Vector moliT, double w, double h, double angle, double intVento)
{

int scala = 20;

/** Il vettore vento va normalizzato sulla diagonale del porto; Devo poi moltiplicare tale valore per un valore di fetch efficace (da bibliografia). */

if(angle < 0)
angle = angle+2*Math.PI;

int passo = (int)(diag/scala);

// costruisco un fattore di correzione per le direttrici...

double Tmoli = 0;

double Totmoli = 0;

//quanto conta il molo? Lo devo leggere dal tipo di molo nel vettore moliT.
//moliT.elementAt(j)

//mi servono le distanze tra i moli....nomino una variabile che ogni volta riassegno....
double d0;
// il numero dei moli incontrati lungo il tracciato
//int NUMmoli;
double TrMoli;
// il valore iniziale di capacit√É  di spostamento
//double v = 1;
/**
costruisco un segmento di direttrice da incrementare lungo ogni direzione....
dovrei mettere tutto in un solo loop che mi cambi la direzione di volta in volta...
faccio somme di x ed y calcolate dal seno e dal coseno dell'angolo che voglio calcolare...
devo uscire con somme di interi, per farlo scelgo che ogni pi/4 rad faccio una direttrice.
*/

Line2D.Double segmento = new Line2D.Double();

/** Per il vento dovrei calcolare la somma vettoriale con il segmento in questione, e poi calcolo tutto con il nuovo segmento; Faccio una correzione sul segmento.*/

for(double alpha = 0; alpha <= 2*Math.PI; alpha = alpha + Math.PI/12)
{
TrMoli = 0;
//NUMmoli = 0;
d0 = 0;
boolean in = true;
Tmoli = 0;

for(int i = 0; i < scala; i++) 
{
if(in)
{
//per calcolare il vento devo lavorare sulla definizione del segmento...
segmento.setLine(px + i*passo*Math.cos(alpha), py + i*passo*Math.sin(alpha),
px + (i+1)*passo*Math.cos(alpha), py + (i+1)*passo*Math.sin(alpha));

if(body.contains((int)(px + i*passo*Math.cos(alpha)), (int)(py + i*passo*Math.sin(alpha))))
{
for (int j=0; j < moli.size(); j++)
{
    if (segmento.intersectsLine((Line2D.Double)moli.elementAt(j)))
    {
//il coeff e' pari al valore preso dal vettore moliT (moliT.elementAt(j)) per i coefficenti di effetto del molo
    Tmoli = Tmoli + (i*passo - d0)*TrMoli;
    TrMoli = TrMoli * ((Double)moliT.elementAt(j)).doubleValue();
    d0 = i*passo; //assegno il valore della distanza dell'ultimo molo....
    }
}
// per considerare la bocca di porto nella funzione:
// ciclo da eliminare se la cosa non funziona... e per ora non funziona 
/*
for (int j=0; j < bocca.size(); j++)
{
    if (segmento.intersectsLine((Line2D.Double)bocca.elementAt(j)))
    {

 se intercetta la bocca di porto esce dal ciclo ed assegna il valore massimo al rasto della traiettoria
 il valore massimo is equal alla distanza che deve ancora percorrere per arrivare alla lunghezza del raggio
 costruito. Prende la distanza percorsa e la distanza totale, fa la differenza e la somma a Tmoli. Esce.
   
    double dmax = diag - (i*passo - d0);
    Tmoli = Tmoli + dmax;
    j = bocca.size(); //esce, non serve che ne incontri 2...
    in = false; //esce dalla traiettoria
    }
}
*/ /////////////////////////////

}
else
{
Tmoli = Tmoli + (i*passo - d0);
in = false;
}
//correzione calcolata come una percentuale del valore ottenuto in funzione dell'accordo con la direzione del vento.

//Normalizza l'angolo (in JAVA sono negativi sopra l'orizzone e positivi sotto crescendo da dx a sx)


double delta = 0;
double delta1 = 0;


double angle1 = Math.min(alpha, angle);
double angle2 = Math.max(alpha, angle);

delta = Math.abs(angle2 - angle1);
delta1 = Math.abs(2*Math.PI - (angle2 - angle1));

delta = Math.min(delta, delta1);


//Totmoli = Totmoli + Tmoli*(intVento - (delta/Math.PI) * (2*intVento - 1));

// -1 < (delta/Math.PI - 0.5)*2 < 1 --> Totale accordo, totale disaccordo //devo introdurre il segno meno per valori contro corrente
// 0 < intVento < 10 e' la defuzzificata

// Il contributo deve valere al massimo per lo (0.8 del delphi [intVento]) * (0.5 Tmoli)

Totmoli = Totmoli + Tmoli - Tmoli * 0.9 * (delta/Math.PI - 0.5)*2 * (intVento/10);


}
}

}

return Totmoli;
}

public void esportaMatrix(double[] matrix, int h, int w) {

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
                for(int i=0; i < h; i++)
                        {
                                for(int j=0; j < w; j++)
                                {
                                stringB.append(Double.toString(matrix[j+i*w]) + "\t");
                                }
                                stringB.append(System.getProperty("line.separator"));
                        }

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

public int normalizza(double x, double xmax, double xmin)
{
double valore = (- xmin + x )*255 / (xmax - xmin);
//if (valore < 0)
//valore = 0;
return (int)valore;
}


public static void filtra(int X, int Y, double[] matrix, double[] matrixR)
{
    int passo = 5;
    for (int i = 0; i < matrix.length - passo; i++) {
        for (int j = 0; j < 5; j++) {
        matrixR[i] = matrixR[i] + matrix[i+j]; //somma i 5 valori dell'intervallo    
        }
        matrixR[i] = matrixR[i] / 5; //media i valori
    }

    //per gli ultimi 5 valori, ma che brutto...
        double temp = 0;

        for (int i = matrix.length - passo - 1; i < matrix.length; i++) {
        temp = temp + matrix[i]; //somma i 5 valori dell'intervallo    
        }

        temp = temp / 5;
        for (int j = matrix.length - passo- 1; j < matrix.length; j++) {
        matrixR[j] = temp; //media i valori   
        }
}

public double[][] vectorToMatrix(int X, int Y, double[] vector)
{
    double[][] matrix = new double[X][Y];
    for (int j = 0; j < X; j++) {
        for (int i = 0; i < Y; i++) {
        matrix[j][i] = vector[j + i*X];
        }
    }
return matrix;
}

public double[] matrixToVector(int X, int Y, double[][] matrix)
{
double[] vector = new double[X*Y]; 
    for (int i = 0; i < Y; i++) {
        for (int j = 0; j < X; j++) {
        vector[j + i*X] = matrix[j][i];
        }
    }
return vector;
}


public double[] patchM(int X, int Y, double[] vectorIn, Polygon body, int a, int b, int sizeCel)
{
//all in one operations
double[][] matrixIn = vectorToMatrix(X,Y,vectorIn);
    
//per la localizzazione del punto
int xdot = 0;
int ydot = 0;

double[][] matrixOut = new double[X][Y];

double temp = 0;           
double count = 0;

int step = sizeCel;
//mi servono celle di Y/10 ed X/10 di lato. l'ultima di lato Y%10 e X%10
int y = Y/step;
int x = X/step;

int yf = Y%step;
int xf = X%step;

//questi due cicli scorrono le celle

    for (int i = 0; i < y; i++) {
        for (int j = 0; j < x; j++) {
            temp = 0;
            count = 0;
        //seguono due cicli che mediano i valori interni
            //di ogni cella sommo i valori interni (no dovrebbe servire farne la media)
            for (int k = 0; k < step; k++) {
                for (int l = 0; l < step; l++) {
                temp = temp + matrixIn[l+j*step][k+i*step];
                xdot = l+j*step+a;
                ydot = k+i*step+b;
                if(body.contains(xdot,ydot))
                count++;
                }
            }

            temp = temp/(count);
            //ho definito temp e riempio la matrice di uscita 
            for (int k = 0; k < step; k++) {
                for (int l = 0; l < step; l++) {
                matrixOut[l+j*step][k+i*step] = temp;
                }
            }


        }
        
    }
//ogni fine riga e fine colonna devo calcolare anche i punti che avanzano...

/** ciclo dell'ULTIMA COLONNA */
for (int i = 0; i < y; i++) {
            temp = 0;
            count = 0;
        //seguono due cicli che mediano i valori interni
            //di ogni cella sommo i valori interni (no dovrebbe servire farne la media)
            for (int k = 0; k < step; k++) {
                for (int l = 0; l < xf; l++) {
                temp = temp + matrixIn[l+x*step][k+i*step];
                xdot = l+x*step+a;
                ydot = k+i*step+b;
                if(body.contains(xdot,ydot))
                count++;
                }
            }

            temp = temp/(count);
            //ho definito temp e riempio la matrice di uscita 
            for (int k = 0; k < step; k++) {
                for (int l = 0; l < xf; l++) {
                matrixOut[l+x*step][k+i*step] = temp;
                }
            }
}

/** ciclo dell'ULTIMA RIGA */
for (int i = 0; i < x; i++) {
            temp = 0;
            count = 0;
        //seguono due cicli che mediano i valori interni
            //di ogni cella sommo i valori interni (no dovrebbe servire farne la media)
            for (int k = 0; k < yf; k++) {
                for (int l = 0; l < step; l++) {
                temp = temp + matrixIn[l+i*step][k+y*step];
                xdot = l+i*step+a;
                ydot = k+y*step+b;
                if(body.contains(xdot,ydot))
                count++;
                }
            }

            temp = temp/(count);
            //ho definito temp e riempio la matrice di uscita 
            for (int k = 0; k < yf; k++) {
                for (int l = 0; l < step; l++) {
                matrixOut[l+i*step][k+y*step] = temp;
                }
            }
}

/** ULTIMA CELLA basso-sx */

            temp = 0;
            count = 0;
        //seguono due cicli che mediano i valori interni
            //di ogni cella sommo i valori interni (no dovrebbe servire farne la media)
            for (int k = 0; k < yf; k++) {
                for (int l = 0; l < xf; l++) {
                temp = temp + matrixIn[l+x*step][k+y*step];
                xdot = l+x*step+a;
                ydot = k+y*step+b;
                if(body.contains(xdot,ydot))
                count++;
                }
            }

            temp = temp/(count);
            //ho definito temp e riempio la matrice di uscita 
            for (int k = 0; k < yf; k++) {
                for (int l = 0; l < xf; l++) {
                matrixOut[l+x*step][k+y*step] = temp;
                }
            }



//conversione  vettore della matrice
double[] vectorOut = matrixToVector(X,Y,matrixOut);
//NON va rinormalizzato....il dato non deve essere rinormalizzato...al massimo bisogna normalizzare la rappresentazione grafica...


return vectorOut; 
}


//DEVE riordinare dal più profondo ai punti via via più vicini...

public void sortBati(double[] prof, Vector punti) {

double[] temp = new double[prof.length]; //Vector con profondità double e punto Point2D
Vector tempV = new Vector();

for(int i = 0; i < punti.size(); i++)
   tempV.add(punti.get(i));



for (int j = 0; j < punti.size() - 1; j++) {

for (int i = 0; i < punti.size() - 1; i++) {

    if (prof[i + 1] > prof[i])
  {
    temp[i] = prof[i];
    prof[i] = prof[i + 1];
    prof[i + 1] = temp[i];
    
    tempV.setElementAt(punti.elementAt(i), i);
    punti.setElementAt(punti.elementAt(i + 1), i);
    punti.setElementAt(tempV.elementAt(i), i + 1);
  }
    
}
}

//prendo il primo punto che è il più pronfondo ed ordino dal + vicino al pi + lontano...



Point2D.Double p0 = (Point2D.Double)punti.elementAt(0);

for (int k = 0; k < punti.size() - 1; k++)
{

for (int i = 1; i < punti.size() - 1; i++)
{

    Point2D.Double pN = (Point2D.Double)punti.elementAt(i);
    Point2D.Double pN1 = (Point2D.Double)punti.elementAt(i + 1);
    
  if (p0.distance(pN) > p0.distance(pN1))
  {
    temp[i] = prof[i];
    prof[i] = prof[i + 1];
    prof[i + 1] = temp[i];
    
    tempV.setElementAt(punti.elementAt(i), i);
    punti.setElementAt(punti.elementAt(i + 1), i);
    punti.setElementAt(tempV.elementAt(i), i + 1);
  }

}
}

}

public boolean isNum(String s) {
try {
Double.parseDouble(s);
}
catch (NumberFormatException nfe) {
return false;
}
return true;
}


//
//public void done() {
//    Toolkit.getDefaultToolkit().beep();
////            startButton.setEnabled(true);
//setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
////            taskOutput.append("Done!\n");
//}
//
//private void setCursor(Cursor cursor) {
//throw new UnsupportedOperationException("Not yet implemented");
//}

}
