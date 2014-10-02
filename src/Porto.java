package jmarinasen.jmarinaspkg;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Cursor;
import java.awt.Toolkit;

import java.awt.Rectangle;
import java.awt.geom.*;

import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.MemoryImageSource;


public class Porto extends Editor implements MouseListener, ActionListener, MouseMotionListener {



/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



public funzioniPorto funzioniP = new funzioniPorto();



private Image image;
int imageWidth;
int imageHeight;
public String funzione = "confini";
public int schiarisci = 4;

int w;
int h;
int X;
int Y;

// parametri per la normalizzazione
double Nmin = 0;
double Nmax = 10;

//private Point massimo = new Point();
//private Point minimo = new Point();

/** Coefficienti peso Variabili da questionario*/
//l'eventuale coefficiente sommato è dato dalla calibrazione...

private double pesoVFonti = 9.09;
private double pesoVBocca = 9.09 + 4;
private double pesoVTortuosita = 6.36;
private double pesoVBatimetria = 5 - 4;
private double pesoVDistanza = 5;

private double pesoVTotale = pesoVFonti + pesoVBocca + pesoVTortuosita + pesoVBatimetria + pesoVDistanza;


/** harbor's bound*/
private Polygon body = new Polygon();
private Rectangle bound = new Rectangle();

private Vector ventoP = new Vector();
private Vector perimetro = new Vector();
int x1c, y1c, x2c, y2c;

double angleVento = 0;
double intVento = 0;

double[] mtxConfini;
int[] pixConfini;

public boolean tortuosita = false;
public String funzioneC = java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("harbor_border");

public Color coloreP;

private String valoreVento;
private String valoreVentoLabel;

// private boolean interP = false;

/** fonti inquinamento */

//Coefficienti disperisione
double disp1 = 6.36;
double disp2 = 6.36;
double disp3 = 5;
double disp4 = 5;
double disp5 = 7.73;
double disp6 = 3.64;
double disp7 = 3.64;
double disp8 = 9.09;
double disp9 = 9.09;

private Vector fonti = new Vector();
private Vector fontiL = new Vector();

public String tipoInq = java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico");

double disp = disp1; //Acque agricole
int riskT = 3; //rischio chimico e distrofico Acque agricole
String riskL = "1";

int[] intFonti = new int[100];
int[] dispFonti = new int[100];
int[] riskFonti = new int[100];

double[] mtxFontiD;
int[] pixFontiD;
double[] mtxFontiC;
int[] pixFontiC;
double[] mtxFontiIS;
int[] pixFontiIS;

double[] mtxFontiTot;
int[] pixFontiTot;

/** bocca di porto */

/** Coefficienti dal questionario*/
private double dispBD = 3.64;
private double dispBC = 9.09;
private double dispBIS = 9.09;


private Vector bocca = new Vector();
private Vector boccaPt = new Vector();

public String tipoInqB = java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distanza");

double tempIntD = 0;
double tempIntC = 0;
double tempIntIS = 0;

int x1b, y1b, x2b, y2b;

double[] intBoccaD = new double[100];
double[] intBoccaC = new double[100];
double[] intBoccaIS = new double[100];

double dispBoccaD = 0;
double dispBoccaC = 0;
double dispBoccaIS = 0;

double[] mtxBoccaTot;
int[] pixBoccaTot;

double[] mtxBoccaD;
int[] pixBoccaD;
double[] mtxBoccaC;
int[] pixBoccaC;
double[] mtxBoccaIS;
int[] pixBoccaIS;
double[] mtxBoccaDist;
int[] pixBoccaDist;

int cB = 0; //controllo esecuzione form bocca
boolean cB1 = false; //controllo disegno primo click
//private boolean controlloB;

/** moli */
private Vector moliPt = new Vector();
private Vector moli = new Vector();
private Vector moliT = new Vector();

// boolean over = false;
int x1m, y1m, x2m, y2m;
int xctrl = 0;
Double typeM = new Double(0.3);

double[] mtxMoli;
int[] pixMoli;

/** batimetria */
public String funzioneB = java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("rimescolato");

private Vector ptBati = new Vector();
double[] deptBati = new double[100];

double[] mtxBatimetria;
int[] pixBatimetria;


/** totale */
public String tipoTot = java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico");
public String tipoEsp = "00";


int[] pixTotaleD;
double[] mtxTotaleD;
int[] pixTotaleC;
double[] mtxTotaleC;
int[] pixTotaleIS;
double[] mtxTotaleIS;

int[] pixTotaleTot;
double[] mtxTotaleTot;

//public boolean patched = true;
public int sizeCel = 0;

/** Vulnerabilità */
int sup = 0; //superficie porto
int per = 0; //perimetro porto

int nF = 0; //numero fonti inquinamento (aggiungere un coeff di intensità)
int circ = 0; //circolarità

int lungM = 0; //lunghezza tutti moli
int lungB = 0; //lunghezza tutte bocche di porto

double rMP = 0; //rapporto (perimetro interno)/moli
double rBP = 0; //rapporto superifice/bocca

double sFrut = 0;

JPanel vPanel = new JPanel();

JLabel lSup = new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("V_superficie"));
JLabel vSup = new JLabel("");

JLabel lPer = new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("V_perimetro"));
JLabel vPer = new JLabel("");

JLabel lCirc = new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("V_circolarita"));
JLabel vCirc = new JLabel("");

JLabel lFonti = new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("V_number_sources"));
JLabel vFonti = new JLabel("");

JLabel lMoli0 = new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("V_lenght_wharfs"));
JLabel vMoli0 = new JLabel("");

JLabel lMoli = new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("V_irregolarita"));
JLabel vMoli = new JLabel("");

JLabel lBocca0 = new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("V_mouth_lenght"));
JLabel vBocca0 = new JLabel("");

JLabel lBocca = new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("V_capacita_rinnovo"));
JLabel vBocca = new JLabel("");

JLabel lSfrut = new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("V_sfruttamento"));
JLabel vSfrut = new JLabel("");


public String path;

	public Porto()
	{

	GridLayout grid = new GridLayout(9,2);
	grid.setVgap(5);
	grid.setHgap(5);
	vPanel.setLayout(grid);

	vPanel.add(lSup);
	vPanel.add(vSup);
	vPanel.add(lPer);
	vPanel.add(vPer);
	vPanel.add(lCirc);
	vPanel.add(vCirc);
	vPanel.add(lFonti);
	vPanel.add(vFonti);
	vPanel.add(lBocca0);
	vPanel.add(vBocca0);
	vPanel.add(lBocca);
	vPanel.add(vBocca);
	vPanel.add(lMoli0);
	vPanel.add(vMoli0);
	vPanel.add(lMoli);
	vPanel.add(vMoli);
	vPanel.add(lSfrut);
	vPanel.add(vSfrut);
	add(vPanel);
	}

	public void DisegnaSfondo()
	{
		setBackground(Color.white);
		addMouseListener(this);
		addMouseMotionListener(this);
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

	}

	public void DisegnaPorto()
	{
		setBackground(Color.white);

		try
		{
		image = ImageIO.read(new File(path));
		imageWidth = image.getWidth(this);
		imageHeight = image.getHeight(this);
                }
		catch (IOException g)
		{
		g.printStackTrace();
		}
	}

   	public void actionPerformed(ActionEvent evt) {
		String com = evt.getActionCommand();

		if (com.equals("confini") || com.equals("fonti") || com.equals("moli") || com.equals("bocca") || com.equals("batimetria") || com.equals("totale") || com.equals("vuln"))
		funzione = com;

		/** parametri confini del porto */
		if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("harbor_borders"))) 		funzioneC = com;
		else if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("prevalent_wind")))  	funzioneC = com;


		/** Parametri fonti Inquinamento */
		//riskT: 1 = Distrofico, 2 = Chimico Fisico, 4 = Igienico Sanitario fare le somme
		if (funzione.equals("fonti")) {
		if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("1_acque_agricole")))              {disp = disp1; riskT = 3; riskL = "1";}
		else if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("2_acque_allevamenti")))		{disp = disp2; riskT = 5; riskL = "2";}
		else if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("3_acque_fluviali"))) 		{disp = disp3; riskT = 1; riskL = "3";}
		else if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("4_aree_lavaggio")))      	{disp = disp4; riskT = 2; riskL = "4";}
		else if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("5_distributori_carburante"))){disp = disp5; riskT = 2; riskL = "5";}
		else if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("6_impianti_acquacoltura"))) 	{disp = disp6; riskT = 5; riskL = "6";}
		else if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("7_presenza_alghe"))) 		{disp = disp7; riskT = 1; riskL = "7";}
		else if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("8_scarichi_fognari"))) 		{disp = disp8; riskT = 5; riskL = "8";}
		else if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("9_scarichi_industriali")))   {disp = disp9; riskT = 2; riskL = "9";}
		}

		/** Parametri batimetria */
		if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("rimescolato"))) 		funzioneB = com;
		else if(com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("stratificato")))	funzioneB = com;


		if (this.funzione.equals("confini"))
		{

                    /** nuovo Vento...
                        if (com.equals("prevalent wind"))
			{
                        Object[] possibleValuesVento = {"nullo","molto basso", "basso", "più o meno basso", "medio", "più o meno alto", "alto", "molto alto"};
                        Object selectedValueVento = JOptionPane.showInputDialog(null, "Intensità  Vento", "Valore di Intensità ", JOptionPane.INFORMATION_MESSAGE, null, possibleValuesVento, possibleValuesVento[0]);

                        double tempV = 0;

                        if(((String)selectedValueVento).equals("nullo"))		tempV = 0;
                        if(((String)selectedValueVento).equals( "molto basso"))		tempV = 0.91;
                        if(((String)selectedValueVento).equals("basso"))		tempV = 2.27;
                        if(((String)selectedValueVento).equals("più o meno basso"))    tempV = 3.64;
                        if(((String)selectedValueVento).equals("medio"))                tempV = 5;
                        if(((String)selectedValueVento).equals("più o meno alto"))     tempV = 6.36;
                        if(((String)selectedValueVento).equals("alto"))                 tempV = 7.73;
                        if(((String)selectedValueVento).equals("molto alto"))		tempV = 9.09;

                                if(selectedValueVento == null) {
                                Object last = ventoP.lastElement();
                                ventoP.removeElement(last);
                                repaint();
                                }

                        valoreVento = (String)selectedValueVento;
                        intVento = tempV;



                        Object[] possibleValuesVentoDir = {
                            "Nord","Nord Nord Est", "Nord Est",
                            "Est Nord Est", "Est", "Est Sud Est",
                            "Sud Est", "Sud Sud Est", "Sud","Sud Sud Ovest", "Sud Ovest",
                            "Ovest Sud Ovest", "Ovest", "Ovest Nord Ovest",
                            "Nord Ovest", "Nord Nord Ovest"};
                        Object selectedValueVentoDir = JOptionPane.showInputDialog(null, "Direzione  prevalent wind", "Direzione del vento prevalene", JOptionPane.INFORMATION_MESSAGE, null, possibleValuesVentoDir, possibleValuesVentoDir[0]);

                        double tempVDir = 0;

                        if(((String)selectedValueVentoDir).equals("Nord"))		tempVDir = 0;
                        if(((String)selectedValueVentoDir).equals("Nord Nord Est"))	tempVDir = 22.5;
                        if(((String)selectedValueVentoDir).equals("Nord Est"))		tempVDir = 45;
                        if(((String)selectedValueVentoDir).equals("Est Nord Est"))      tempVDir = 67.5;
                        if(((String)selectedValueVentoDir).equals("Est"))               tempVDir = 90;
                        if(((String)selectedValueVentoDir).equals("Est Sud Est"))       tempVDir = 112.5;
                        if(((String)selectedValueVentoDir).equals("Sud Est"))           tempVDir = 135;
                        if(((String)selectedValueVentoDir).equals("Sud Sud Est"))	tempVDir = 157.5;
                        if(((String)selectedValueVentoDir).equals("Sud"))		tempVDir = 180;
                        if(((String)selectedValueVentoDir).equals("Sud Sud Ovest"))	tempVDir = 202.5;
                        if(((String)selectedValueVentoDir).equals("Sud Ovest"))		tempVDir = 225;
                        if(((String)selectedValueVentoDir).equals("Ovest Sud Ovest"))   tempVDir = 247.5;
                        if(((String)selectedValueVentoDir).equals("Ovest"))             tempVDir = 270;
                        if(((String)selectedValueVentoDir).equals("Ovest Nord Ovest"))  tempVDir = 292.5;
                        if(((String)selectedValueVentoDir).equals("Nord Ovest"))        tempVDir = 315;
                        if(((String)selectedValueVentoDir).equals("Nord Nord Ovest"))	tempVDir = 337.5;

                                if(selectedValueVento == null) {
                                Object last = ventoP.lastElement();
                                ventoP.removeElement(last);
                                repaint();
                                }

                        valoreVentoLabel = (String)selectedValueVentoDir;

                        double angle = tempVDir * 2.0 * Math.PI/360.0;

                        float xVentoDir = (float)Math.cos(angle);
                        float yVentoDir = (float)Math.sin(angle);

                        Line2D.Float linea = new Line2D.Float(50, 50, xVentoDir, yVentoDir);
                        ventoP.addElement(linea);

                        }
                        */
			if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done")))
			{

			bound = body.getBounds();
			w = (int)bound.getWidth();
			h = (int)bound.getHeight();
			X = (int)bound.getX();
			Y = (int)bound.getY();

			int pixIn = 0;

			//misura il perimetro del poligono.
			int numDots = body.npoints;
			int[] xDots = body.xpoints;
			int[] yDots = body.ypoints;

			for(int i=0; i < numDots-1; i++)
			{
			double d = Math.hypot(xDots[i] - xDots[i+1], yDots[i] - yDots[i+1]);
			per = (int)d + per;
			//costruisco un vettore di line2D utile per successivi calcoli di appartenenza al poligono
                        Line2D.Double linea = new Line2D.Double((double)xDots[i], (double)yDots[i], (double)xDots[i+1], (double)yDots[i+1]);
                        perimetro.add(linea);
                        }
			//costruisco un vettore di line2D utile per successivi calcoli di appartenenza al poligono
                        Line2D.Double linea = new Line2D.Double((double)xDots[numDots-1], (double)yDots[numDots-1], (double)xDots[0], (double)yDots[0]);
                        perimetro.add(linea);

			per = per + (int)Math.hypot(xDots[numDots-1] - xDots[0], yDots[numDots-1] - yDots[0]);

			//misura l'area del poligono

				for (int y = 0; y < h; y++)
				{
					for (int x = 0; x < w; x++)
					{
					Point p = new Point(x+X,y+Y);
					if(body.contains(p))
					pixIn++;
					}
				}

			sup = pixIn; //pixIn: la misura della superficie.

			/* Calcolo la circolarita del poligono come la quantita di pixel di differenza tra il cerchio max circoscritto e l'area del poligono */

			int pixTot = (int)(Math.pow(bound.getWidth()/2, 2)*Math.PI);
			int pixTot2 = (int)(Math.pow(bound.getHeight()/2, 2)*Math.PI);

			pixTot = Math.max(pixTot, pixTot2);

			// int pixDif = Math.abs(pixTot - pixIn);

			circ = (int)((pixIn*100)/(pixTot));


			repaint();
			}

			if (com.equals("Cancel"))
			{
			body.reset();
			bound = body.getBounds();
			ventoP.clear();
            perimetro.clear();
            valoreVento = null;
            valoreVentoLabel = null;
			repaint();
			}

		}

		if (this.funzione.equals("fonti"))
		{

		if(fonti.isEmpty() == false)
		{
		w = (int)bound.getWidth();
		h = (int)bound.getHeight();
		int np = fonti.size();

			/** INIZIO RISCHIO DISTROFICO */
			if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done")))
			{

			pixFontiD = new int[w * h];
			mtxFontiD = new double[w * h];
			pixFontiC = new int[w * h];
			mtxFontiC = new double[w * h];
			pixFontiIS = new int[w * h];
			mtxFontiIS = new double[w * h];
			pixFontiTot = new int[w * h];
			mtxFontiTot = new double[w * h];

			double d = 0;
			double dtot = 0;
			double distm = 10000;
			double distM = 0;
			int index = 0;



			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
				Point t = new Point (x+X, y+Y);
				if(body.contains(t))
				{
				dtot = 0;
						for (int i=0; i < np; i++)
						{
						if (riskFonti[i] == 1 || riskFonti[i] == 3 || riskFonti[i] == 5 || riskFonti[i] == 7)
						{
                        Point p = (Point)fonti.elementAt(i);
                        d = (float)Math.hypot(x-(p.x-X), y-(p.y-Y));
                        dtot = dtot + intFonti[i]*Math.pow(2.7182,-Math.pow(d,2)/Math.pow(dispFonti[i],2));
                        }
                        }

				distM = Math.max(dtot, distM);
				distm = Math.min(dtot, distm);

				mtxFontiD[index++] = dtot;
				}
				else
				{
				mtxFontiD[index++] = 0;
				}
				}
			}

			index = 0;


				for (int i = 0; i < w*h; i++)
				{
				d = mtxFontiD[index];
				int alpha = funzioniP.normalizza(d, distM, distm);
				int j = alpha/10;
				pixFontiD[index] = (j*10 << 24) | (255 << 16) | (0 << 8) | (0);
				mtxFontiD[index++] = alpha;
				}


		repaint();

			/** FINE RISCHIO DISTROFICO */
			/** INIZIO RISCHIO CHIMICO FISICO */
	distM = 0;
	distm = 10000;

			index = 0;
			dtot = 0;



			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
				Point t = new Point(x+X, y+Y);
				if(body.contains(t))
				{
				dtot = 0;
						for (int i=0; i < np; i++)
						{
						if (riskFonti[i] == 2 || riskFonti[i] == 3 || riskFonti[i] == 6 || riskFonti[i] == 7)
						{
						Point p = (Point)fonti.elementAt(i);
						d = (float)Math.hypot(x-(p.x-X), y-(p.y-Y));
						dtot = dtot + intFonti[i]*Math.pow(2.7182,-Math.pow(d,2)/Math.pow(dispFonti[i],2));
						}

						}
				distM = Math.max(dtot, distM);
				distm = Math.min(dtot, distm);

				mtxFontiC[index++] = dtot;
				}
				else
				{
				mtxFontiC[index++] = 0;
				}
				}
			}

			index = 0;


			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
				d = mtxFontiC[index];
				int alpha = funzioniP.normalizza(d, distM, distm);
				int j = alpha/10;
				pixFontiC[index] = (j*10 << 24) | (0 << 16) | (0 << 8) | (255);
				mtxFontiC[index++] = alpha;
				}
			}

			/** FINE RISCHIO CHIMICO_FISICO */
			/** INIZIO RISCHIO IGIENICO SANITARIO */
	distM = 0;
	distm = 10000;

			dtot = 0;
			index = 0;


			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
				Point t = new Point(x+X, y+Y);
				if(body.contains(t))
				{
				dtot = 0;
						for (int i=0; i < np; i++)
						{
						if (riskFonti[i] == 4 || riskFonti[i] == 5 || riskFonti[i] == 6 || riskFonti[i] == 7)
						{
						Point p = (Point)fonti.elementAt(i);
						d = (float)Math.hypot(x-(p.x-X), y-(p.y-Y));
						dtot = dtot + intFonti[i]*Math.pow(2.7182,-Math.pow(d,2)/Math.pow(dispFonti[i],2));
						}
						}
				distM = Math.max(dtot, distM);
				distm = Math.min(dtot, distm);

				mtxFontiIS[index++] = dtot;
				}
				else
				{
				mtxFontiIS[index++] = 0;
				}
				}
			}

			index = 0;


			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
				d = mtxFontiIS[index];
				int alpha = funzioniP.normalizza(d, distM, distm);
				int j = alpha/10;
				pixFontiIS[index] = (j*10 << 24) | (0 << 16) | (255 << 8) | (0);
				mtxFontiIS[index++] = alpha;
				}
			}

			/** FINE RISCHIO IGIENICO SANITARIO *(
			/** INIZIO RISCHIO totale */
	distM = 0;
	distm = 10000;


			dtot = 0;
			index = 0;


			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
				Point t = new Point(x+X, y+Y);
				if(body.contains(t))
				{
					Point v = (Point)fonti.elementAt(0);
					d = (float)Math.hypot(x-(v.x-X), y-(v.y-Y));
					dtot = intFonti[0] * Math.pow(2.7182, - Math.pow(d, 2) / Math.pow(dispFonti[0], 2) );

						for (int i=1; i < np; i++)
						{
						Point p = (Point)fonti.elementAt(i);
						d = (float)Math.hypot(x-(p.x-X), y-(p.y-Y));
						dtot = dtot + intFonti[i] * Math.pow(2.7182, - Math.pow(d, 2) / Math.pow(dispFonti[i], 2) );
						}
				distM = Math.max(dtot, distM);
				distm = Math.min(dtot, distm);

				mtxFontiTot[index++] = dtot;
				}
				else
				{
				mtxFontiTot[index++] = 0;
				}
				}
			}

			index = 0;


			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
				d = mtxFontiTot[index];
				int alpha = funzioniP.normalizza(d, distM, distm);
				int j = alpha/10;
				pixFontiTot[index] = (j*10 << 24) | (0 << 16) | (0 << 8) | (0);
				mtxFontiTot[index++] = alpha;
				}
			}
			}

			/** FINE RISCHIO totale */
		repaint();
		}

			if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("cancel")) && fonti.isEmpty() == false)
			{
			fonti.clear();
			fontiL.clear();
			intFonti = new int[100];
			dispFonti = new int[100];
			riskFonti = new int[100];

			pixFontiTot = new int[0];
			mtxFontiTot =  new double[0];
			pixFontiC = new int[0];
			mtxFontiC =  new double[0];
			pixFontiD = new int[0];
			mtxFontiD =  new double[0];
			pixFontiIS = new int[0];
			mtxFontiIS =  new double[0];
			repaint();
			}

       if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("elimina_fonte")) && fonti.isEmpty() == false)
			{
			Object last = fonti.lastElement();
			Object lastL = fontiL.lastElement();
			fonti.removeElement(last);
			fontiL.removeElement(lastL);
			--nF;
			repaint();
			}

		}

		if (this.funzione.equals("bocca"))
		{
			if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done")))
			{
			if(bocca.size() != 0)
			{


			w = (int)bound.getWidth();
			h = (int)bound.getHeight();

			double d = 0;
			double distm = 10000;
			double distM = 0;
			int index = 0;

			/** Seguono un po' di variabili da inizializzare per i 3 cicli seguenti */

			int sizeB = bocca.size();

			pixBoccaD = new int[w * h];
			mtxBoccaD = new double[w * h];
			pixBoccaC = new int[w * h];
			mtxBoccaC = new double[w * h];
			pixBoccaIS = new int[w * h];
			mtxBoccaIS = new double[w * h];
			pixBoccaDist = new int[w * h];
			mtxBoccaDist = new double[w * h];
			pixBoccaTot = new int[w * h];
			mtxBoccaTot = new double[w * h];

/** COME PER LE fonti DI INQUINAMENTO FACCIO 3 CICLI (MI SA 4) PER CISCUNA FONTE DI INQUINAMENTO E CONTROLLO TUTTE LE BOCCHE IN ARRAY, SU QUESTE CALCOLO LE VARIE DISTANZE */

/** INIZIO bocca RISCHIO D */
	distM = 0;
	distm = 10000;


			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
					Point t = new Point(x+X, y+Y);
					if(body.contains(t))
					{
						d = 0;
						for (int i=0; i < sizeB; i++)
						{
Line2D line = (Line2D)bocca.elementAt(i);
double lenght = Math.hypot(line.getX1()-line.getX2(),line.getY1()-line.getY2());
double dtemp = (Point2D.distance(line.getX1(), line.getY1(), (double)t.x,(double)t.y) + Point2D.distance(line.getX2(), line.getY2(), (double)t.x,(double)t.y) - lenght)/2;

d = d + intBoccaD[i]*Math.pow(2.7182,-Math.pow(dtemp,2)/Math.pow(dispBoccaD,2));
						}

						distM = Math.max(d, distM);
						distm = Math.min(d, distm);
						mtxBoccaD[index++] = d;
					}
					else
					{
						mtxBoccaD[index++] = 0;
					}
				}
			}
index = 0;
			for (int i = 0; i < h * w; i++)
			{
				d = mtxBoccaD[index];
				int alpha = funzioniP.normalizza(d, distM, distm);
				int j = alpha/10;
				pixBoccaD[index] = (j*10 << 24) | (255 << 16) | (0 << 8) | (0);
				mtxBoccaD[index++] = alpha;
			}



/** FINE bocca RISCHIO D */

/** INIZIO bocca RISCHIO C */
index = 0;

	distM = 0;
	distm = 10000;


			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
					Point t = new Point(x+X, y+Y);
					if(body.contains(t))
					{
						d = 0;
						for (int i=0; i < sizeB; i++)
						{
Line2D line = (Line2D)bocca.elementAt(i);
double lenght = Math.hypot(line.getX1()-line.getX2(),line.getY1()-line.getY2());
double dtemp = (Point2D.distance(line.getX1(), line.getY1(), (double)t.x,(double)t.y) + Point2D.distance(line.getX2(), line.getY2(), (double)t.x,(double)t.y) - lenght)/2;

d = d + intBoccaC[i]*Math.pow(2.7182,-Math.pow(dtemp,2)/Math.pow(dispBoccaC,2));
						}

						distM = Math.max(d, distM);
						distm = Math.min(d, distm);
						mtxBoccaC[index++] = d;
					}
					else
					{
						mtxBoccaC[index++] = 0;
					}
				}
			}
index = 0;
			for (int i = 0; i < h * w; i++)
			{
				d = mtxBoccaC[index];
				int alpha = funzioniP.normalizza(d, distM, distm);
				int j = alpha/10;
				pixBoccaC[index] = (j*10 << 24) | (0 << 16) | (0 << 8) | (255);
				mtxBoccaC[index++] = alpha;
			}

/** FINE bocca RISCHIO C */

/** INIZIO bocca RISCHIO IS */
index = 0;

	distM = 0;
	distm = 10000;


			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
					Point t = new Point(x+X, y+Y);

					if(body.contains(t))
					{
						d = 0;
						for (int i=0; i < sizeB; i++)
						{
Line2D line = (Line2D)bocca.elementAt(i);
double lenght = Math.hypot(line.getX1()-line.getX2(),line.getY1()-line.getY2());
double dtemp = (Point2D.distance(line.getX1(), line.getY1(), (double)t.x,(double)t.y) + Point2D.distance(line.getX2(), line.getY2(), (double)t.x,(double)t.y) - lenght)/2;

d = d + intBoccaIS[i]*Math.pow(2.7182,-Math.pow(dtemp,2)/Math.pow(dispBoccaIS,2));
						}

						distM = Math.max(d, distM);
						distm = Math.min(d, distm);
						mtxBoccaIS[index++] = d;
					}
					else
					{
						mtxBoccaIS[index++] = 0;
					}
				}
			}
   index = 0;
			for (int i = 0; i < h * w; i++)
			{
				d = mtxBoccaIS[index];
				int alpha = funzioniP.normalizza(d, distM, distm);
				int j = alpha/10;
				pixBoccaIS[index] = (j*10 << 24) | (0 << 16) | (255 << 8) | (0);
				mtxBoccaIS[index++] = alpha;
			}


/** FINE bocca RISCHIO IS */

/** INIZIO bocca DISTANZA */
index = 0;
	distM = 0;
	distm = 10000;


			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
					Point t = new Point(x+X, y+Y);

					if(body.contains(t))
					{
						d = 0;
						for (int i=0; i < sizeB; i++)
						{
		Line2D line = (Line2D)bocca.elementAt(i);
		double dtemp = (Point2D.distance(line.getX1(), line.getY1(), (double)t.x,(double)t.y) + Point2D.distance(line.getX2(), line.getY2(), (double)t.x,(double)t.y))/2;
		d = d + dtemp;

						}

						distM = Math.max(d, distM);
						distm = Math.min(d, distm);
						mtxBoccaDist[index++] = d;
					}
					else
					{
						mtxBoccaDist[index++] = 0;
					}
				}
			}
            index = 0;
			for (int i = 0; i < h * w; i++)
			{
				d = mtxBoccaDist[index];
				int alpha = funzioniP.normalizza(d, distM, distm);
				int j = alpha/10;
				pixBoccaDist[index] = (j*10 << 24) | (0 << 16) | (0 << 8) | (0);
				mtxBoccaDist[index++] = alpha;
			}

/** FINE bocca DISTANZA */

/** INIZIO bocca RISCHIO totale */
			distM = 0;
			distm = 10000;

			index = 0;

			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
				mtxBoccaTot[index] = (mtxBoccaD[index] + mtxBoccaC[index] + mtxBoccaIS[index])/3;

				distM = Math.max(mtxBoccaTot[index], distM);
				distm = Math.min(mtxBoccaTot[index], distm);
				index++;
				}
			}

            index = 0;
			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
				d = mtxBoccaTot[index];
				int alpha = funzioniP.normalizza(d, distM, distm);
				int j = alpha/10;
				pixBoccaTot[index] = (j*10 << 24) | (0 << 16) | (0 << 8) | (0);
				mtxBoccaTot[index++] = alpha;
				}
			}

/** FINE bocca RISCHIO totale */

/** VUNERABILITA': lunghezza bocca di porto/superficie */

			for (int i=0; i < sizeB; i++)
			{
			Line2D line = (Line2D)bocca.elementAt(i);
			double lung0 = Math.hypot(line.getX1() - line.getX2(), line.getY1() - line.getY2());
			lungB = lungB + (int)lung0;
			}
			rBP = 1 - (double)lungB/(double)per;
			rBP = (int)(rBP*100);
            rBP = rBP/100;
			//rBP = Math.round((float)(rBP*10))/10;
		repaint();
		}
		}
			if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("cancel")))
			{
			bocca.clear();
			mtxBoccaD = new double[0];
			mtxBoccaC = new double[0];
			mtxBoccaIS = new double[0];
			mtxBoccaDist = new double[0];
                        mtxBoccaTot = new double[0];
			pixBoccaD = new int[0];
			pixBoccaC = new int[0];
			pixBoccaIS = new int[0];
			pixBoccaDist = new int[0];
                        pixBoccaTot = new int[0];
			cB1 = false;
			repaint();
                        }


		}//chiude sezione bocca

		if (this.funzione.equals("moli"))
		{
			if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("nuovo_molo")))
			{
			xctrl = 0;
			}

			if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("elimina_molo")))
			{
			//elimina l'ultimo punto dell'ultimo molo inserito...e poi passa al successivo...
			int nm = moli.size();
			int nmPt = moliPt.size();
			int nmT = moliT.size();

			moli.removeElementAt(nm - 1);

			moliPt.removeElementAt(nmPt - 1);
			moliPt.removeElementAt(nmPt - 2);

			moliT.removeElementAt(nmT - 1);

			repaint();
			}

			if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done")))
			{
//                      editorFrame.jPBarM(true);

			double xmax = 0;
			double xmin = 1000000;

			pixMoli = new int[w * h];
			mtxMoli = new double[w * h];


			Line2D vp = (Line2D)ventoP.elementAt(0);

			int index = 0;
                        double diag = Math.hypot(w, h);
                        double angle = Math.atan2(vp.getY2() - vp.getY1(), vp.getX2() - vp.getX1());

			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
				if(body.contains(x+X,y+Y))
				{
				double iout = funzioniPorto.tortuositaMoli(diag, x+X,y+Y, body, bocca, moli, moliT, w, h, angle, intVento);
				mtxMoli[index++] = iout;
				xmax = Math.max(xmax, iout);
				xmin = Math.min(xmin, iout);
				}
				else
				{
				mtxMoli[index++] = 0;
				}
				}
			}


			double xmax2 = 0;
			double xmin2 = 1000000;

			index = 0;

			for (int y = 0; y < h; y++)
			{
				for (int x = 0; x < w; x++)
				{
				if(body.contains(x+X,y+Y))
				{
			int Totout = funzioniP.normalizza(mtxMoli[index], xmax, xmin);
                                double iout = Totout;
				xmax2 = Math.max(xmax2, iout);
				xmin2 = Math.min(xmin2, iout);

				pixMoli[index] = (255 - Totout << 24) | (255 << 16) | (0 << 8) | (0);
				mtxMoli[index++] = 255 - Totout;
				}
				else
				{
				pixMoli[index] = (0 << 24) | (255 << 16) | (0 << 8) | (0);
				mtxMoli[index++] = 0;
				}
				}
			}



/** VUNERABILITA': lunghezza moli/superficie */
// lunghezza moli totale lungM

	for (int j=0; j < moli.size(); j++)
	{
		Line2D line = (Line2D.Double)moli.elementAt(j);
		double lung0 = Math.hypot(line.getX1() - line.getX2(), line.getY1() - line.getY2());
		lungM = lungM + (int)lung0;
	}

	rMP = -1*(3.5*(Math.sqrt(sup)/(per+lungM))-1);
        rMP = (int)(rMP*100);
        rMP = rMP / 100;
//	rMP = Math.round((float)(rMP*10))/10;

		repaint();

			}

			if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("cancel")))
			{
            pixMoli = new int[0];
			mtxMoli =  new double[0];
			moli.clear();
			moliPt.clear();
			moliT.clear();
			xctrl = 0;
			repaint();
			}
		}

		if (this.funzione.equals("batimetria"))
		{
		if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done")))
		{

		int sizeBt = ptBati.size();
		X = (int)bound.getX();
		Y = (int)bound.getY();

		/** deve controllare tra quali dei vari punti √Ø¬ø¬Ω compresa la distanza --> assegna il valore del punto + basso*/


		int index = 0;
		double deptBatiC = 0;

		double distM = 0;
		double distm = 10000;


		pixBatimetria = new int[w * h];
		mtxBatimetria = new double[w * h];

        Point2D.Double pP;

        Point2D.Double pN1;
        Point2D.Double pN;

        funzioniP.sortBati(deptBati, ptBati);

        Point2D.Double p0 = (Point2D.Double)ptBati.elementAt(0);
        Point2D.Double pL = (Point2D.Double)ptBati.elementAt(sizeBt-1);

        // ALTERNATIVA COSTRUTTIVA E SOLIDA: Dal punto + profondo ordino i punti sulla basa della distanza da questo e poi interpolo circonferenze...



		for(int i=0; i < h; i++)
		{
			for(int j=0; j < w; j++)
			{

                pP = new Point2D.Double((double)(j+X), (double)(i+Y));

                //controllo se sono fuori dall'ultimo punto...
                if(p0.distance(pP) > p0.distance(pL))
				{
                deptBatiC = deptBati[sizeBt-1];
                }

                else
                {

                    for(int k = 0; k < sizeBt-1 ; k++)
                    {
                //devo trovare un modo per scoprire tra quali punti sono...
                //devo minimizzare la distanza dai punti (o meglio le differenze delle distanze dai punti)


                    Point2D.Double pM = (Point2D.Double)ptBati.elementAt(k+1);
                    double dM = p0.distance(pM) - p0.distance(pP);

                    Point2D.Double pm = (Point2D.Double)ptBati.elementAt(k);
                    double dm = p0.distance(pm) - p0.distance(pP);

                    if(dM*dm < 0)
                    {
                    double Xb = p0.distance(pM) - p0.distance(pm);
                    double Yb = deptBati[k+1];
                    double Xa = 0;
                    double Ya = deptBati[k];
                    double Xp = p0.distance(pP) - p0.distance(pm);
                    double Yp = ((Yb-Ya)/(Xb-Xa))*Xp + Ya;
                    deptBatiC = Yp;

                    k = sizeBt-1; //faster...
                    }
                    }
                }

				mtxBatimetria[index] = deptBatiC;
				distM = Math.max(mtxBatimetria[index], distM);
				distm = Math.min(mtxBatimetria[index], distm);


			index++;
			}
		}



                            index = 0;
                            for (int i = 0; i < w*h; i++)
                            {
                            double d = mtxBatimetria[index];
                            int alpha;

                            if(!funzioneB.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("rimescolato")))
                                {
                                if(d < 5)
                                d = 5;

                                alpha = funzioniP.normalizza(d, distM, 5);
                                }

                            else
                            alpha = funzioniP.normalizza(d, distM, distm);

                            int j = alpha/10;
                            pixBatimetria[index] = (255 - j*10 << 24) | (0 << 16) | (0 << 8) | (0);
                            mtxBatimetria[index++] = alpha;
                            }



		repaint();
		}//chiude Done

                if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("elimina_punto")))
		{
			int nmT = ptBati.size();
			ptBati.removeElementAt(nmT - 1);



		repaint();
		}

		if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("cancel")))
		{
			deptBati = new double[100];
			ptBati.clear();

			pixBatimetria = new int[0];
			mtxBatimetria =  new double[0];

		repaint();
		}
		}//chiude batimetria

		if(this.funzione.equals("totale"))
		{
		/** CALCOLO DEI RISCHI: DISTROFICO CHIMICO FISICO E totale */

			if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done")))
			{
				X = (int)bound.getX();
				Y = (int)bound.getY();

				/** distingue 4 casi e li calcola tutti e quattro insieme*/

				//fa i conti solo con le matrici riempite. e solo se e stato disegnato il perimetro

				/** PRIMO: caso di inquinamento DISTROFICO */

				int index = 0;
				pixTotaleD = new int[w * h];
				mtxTotaleD = new double[w * h];

				double xmin = 300;
				double xmax = 0;
				int imin = 0;
				int imax = 0;

				for(int i=0; i < h; i++)
				{
				for(int j=0; j < w; j++)
				{
				if(body.contains(j + X, i + Y))  {
					if ((pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiD[index] + pesoVBocca*mtxBoccaD[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale < xmin) {
					xmin = (pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiD[index] + pesoVBocca*mtxBoccaD[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale;
					imin = index;
					}

					if((pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiD[index] + pesoVBocca*mtxBoccaD[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale  > xmax) {
					xmax = (pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiD[index] + pesoVBocca*mtxBoccaD[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale;
					imax = index;
					}
				}
				++index;
				}
				}
				// se patch operazione su matrice
                                for(int i=0; i < w * h; i++)
				{
                                mtxTotaleD[i] = funzioniP.normalizza((pesoVDistanza*mtxBoccaDist[i] + pesoVFonti*mtxFontiD[i] + pesoVBocca*mtxBoccaD[i] + pesoVTortuosita*mtxMoli[i]+ pesoVBatimetria*mtxBatimetria[i])/pesoVTotale, xmax, xmin);
                                }

                                if(sizeCel != 0)
                                mtxTotaleD = funzioniP.patchM(w, h, mtxTotaleD, body, X, Y, sizeCel);

                                for(int i=0; i < w * h; i++)
				{
                                int j = (int)mtxTotaleD[i];
				pixTotaleD[i] = (j << 24) | (255 << 16) | (0 << 8) | (0);
				}



				/** SECONDO: caso di inquinamento CHIMICO */
				index = 0;
				pixTotaleC = new int[w * h];
				mtxTotaleC = new double[w * h];

				xmin = 300;
				xmax = 0;

				imin = 0;
				imax = 0;

				for(int i=0; i < h; i++)
				{
				for(int j=0; j < w; j++)
				{
					if(body.contains(j + X, i + Y))  {
					if ((pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiC[index] + pesoVBocca*mtxBoccaC[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale < xmin) {
					xmin = (pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiC[index] + pesoVBocca*mtxBoccaC[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale;
					imin = index;
					}

					if ((pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiC[index] + pesoVBocca*mtxBoccaC[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale  > xmax){
					xmax = (pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiC[index] + pesoVBocca*mtxBoccaC[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale;
					imax = index;
					}
					}
				index++;
				}
				}


				// se patch operazione su matrice
                                for(int i=0; i < w * h; i++)
				{
                                mtxTotaleC[i] = funzioniP.normalizza((pesoVDistanza*mtxBoccaDist[i] + pesoVFonti*mtxFontiC[i] + pesoVBocca*mtxBoccaC[i] + pesoVTortuosita*mtxMoli[i]+ pesoVBatimetria*mtxBatimetria[i])/pesoVTotale, xmax, xmin);
                                }

                                if(sizeCel != 0)
                                mtxTotaleC = funzioniP.patchM(w, h, mtxTotaleC, body, X, Y, sizeCel);

                                for(int i=0; i < w * h; i++)
				{
                                int j = (int)mtxTotaleC[i];
				pixTotaleC[i] = (j << 24) | (0 << 16) | (0 << 8) | (255);
				}


				/** TERZO: caso di inquinamento IGIENICO SANITARIO */
				index = 0;

				pixTotaleIS = new int[w * h];
				mtxTotaleIS = new double[w * h];

				xmin = 300;
				xmax = 0;

				imin = 0;
				imax = 0;

				for(int i=0; i < h; i++)
				{
				for(int j=0; j < w; j++)
				{
				if(body.contains(j + X, i + Y))  {
				if ((pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiIS[index] + pesoVBocca*mtxBoccaIS[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale < xmin) {
				xmin = (pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiIS[index] + pesoVBocca*mtxBoccaIS[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale;
				imin = index;
				}

				if ((pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiIS[index] + pesoVBocca*mtxBoccaIS[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale  > xmax) {
				xmax = (pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiIS[index] + pesoVBocca*mtxBoccaIS[index] + pesoVTortuosita*mtxMoli[index] + pesoVBatimetria*mtxBatimetria[index])/pesoVTotale;
				imax = index;
				}
				}
				++index;
				}
				}


                                // se patch operazione su matrice
                                for(int i=0; i < w * h; i++)
				{
                                mtxTotaleIS[i] = funzioniP.normalizza((pesoVDistanza*mtxBoccaDist[i] + pesoVFonti*mtxFontiIS[i] + pesoVBocca*mtxBoccaIS[i] + pesoVTortuosita*mtxMoli[i]+ pesoVBatimetria*mtxBatimetria[i])/pesoVTotale, xmax, xmin);
                                }

                                if(sizeCel != 0)
                                mtxTotaleIS = funzioniP.patchM(w, h, mtxTotaleIS, body, X, Y, sizeCel);

                                for(int i=0; i < w * h; i++)
				{
                                int j = (int)mtxTotaleIS[i];
				pixTotaleIS[i] = (j << 24) | (0 << 16) | (255 << 8) | (0);
				}

				/** QUARTO: caso di inquinamento totale */
				index = 0;

				pixTotaleTot = new int[w * h];
				mtxTotaleTot = new double[w * h];

				xmin = 300;
				xmax = 0;

				imin = 0;
				imax = 0;

				for(int i=0; i < h; i++)
				{
				for(int j=0; j < w; j++)
				{
				if(body.contains(j + X, i + Y))  {
				if ((pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiTot[index] + pesoVBocca*mtxBoccaTot[index] + pesoVTortuosita*mtxMoli[index]+ pesoVBatimetria*mtxBatimetria[index])/pesoVTotale < xmin) {
				xmin = (pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiTot[index] + pesoVBocca*mtxBoccaTot[index] + pesoVTortuosita*mtxMoli[index]+ pesoVBatimetria*mtxBatimetria[index])/pesoVTotale;
				imin = index;
				}

				if ((pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiTot[index] + pesoVBocca*mtxBoccaTot[index] + pesoVTortuosita*mtxMoli[index]+ pesoVBatimetria*mtxBatimetria[index])/pesoVTotale  > xmax) {
				xmax = (pesoVDistanza*mtxBoccaDist[index] + pesoVFonti*mtxFontiTot[index] + pesoVBocca*mtxBoccaTot[index] + pesoVTortuosita*mtxMoli[index]+ pesoVBatimetria*mtxBatimetria[index])/pesoVTotale;
				imax = index;
				}
				}
				++index;
				}
				}


                               for(int i=0; i < w * h; i++)
				{
                                mtxTotaleTot[i] = funzioniP.normalizza((pesoVDistanza*mtxBoccaDist[i] + pesoVFonti*mtxFontiTot[i] + pesoVBocca*mtxBoccaTot[i] + pesoVTortuosita*mtxMoli[i]+ pesoVBatimetria*mtxBatimetria[i])/pesoVTotale, xmax, xmin);
                                }

                                if(sizeCel != 0)
                                mtxTotaleTot = funzioniP.patchM(w, h, mtxTotaleTot, body, X, Y, sizeCel);


                                xmax = 0;
                                xmin = 255;

                                for(int i=0; i < w * h; i++)
				{
                                int j = (int)mtxTotaleTot[i];
				if(j > xmax) xmax = j;
                                else
                                if(j < xmin) xmin = j;
                                }

                                for(int i = 0; i < w*h; i++)
				{
                                int j = (int)mtxTotaleTot[i];

//                                if(j == xmax)
//				pixTotaleTot[i] = (255 << 24) | (255 << 16) | (0 << 8) | (0);
//
//                                if(j == xmin)
//				pixTotaleTot[i] = (255 << 24) | (0 << 16) | (0 << 8) | (255);
//
//                                if(j != xmax && j != xmin)
                                pixTotaleTot[i] = (j << 24) | (0 << 16) | (0 << 8) | (0);
                                }



		repaint();
                }

                        /**

                if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("filtra")))
                {
                double[] matrixR = new double[w * h];

                funzioniP.filtra(X, Y, mtxTotaleD, matrixR);

                for(int i=0; i < w * h; i++)
                {
		int j = (int)matrixR[i]/10;
		pixTotaleD[i] = (j*10 << 24) | (255 << 16) | (0 << 8) | (0);
		}
                repaint();
                }
                         */


		}

		if(this.funzione.equals("vuln"))
		{
		if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("sfruttamento")))
		{
        new formSfrutt(new javax.swing.JFrame(), true).setVisible(true);
		System.out.println(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("P_sfruttamento") + sFrut);
		}
 		if (com.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done")))
 		{
        repaint();
 		}

            }
	} // chiude l'ActionPerformed alla riga 162

	public void mouseDragged(MouseEvent e) {
		e.consume();

                if (this.funzione.equals("confini") && funzioneC.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("prevalent_wind")))
		{
		x2c = e.getX();
		y2c = e.getY();
		}

	repaint();

	}

	public void mousePressed(MouseEvent e) {
		e.consume();

		if (this.funzione.equals("confini") && funzioneC.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("prevalent_wind")))
		{
			x1c = e.getX();
			y1c = e.getY();
			x2c = -1;
		}

	repaint();

                 }

	public void mouseReleased(MouseEvent e) {
		e.consume();

		if (this.funzione.equals("confini") && funzioneC.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("prevalent_wind")))
		{
	Line2D.Float linea = new Line2D.Float((float)x1c, (float)y1c, (float)e.getX(), (float)e.getY());


		ventoP.addElement(linea);
		x2c = -1;


Object[] possibleValuesVento = {java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_nullo"),java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_medio"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_alto"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_alto"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_alto")};
Object selectedValueVento = JOptionPane.showInputDialog(null, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("wind_intensity"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("intensity_value"), JOptionPane.INFORMATION_MESSAGE, null, possibleValuesVento, possibleValuesVento[0]);

double tempV = 0;
if((String)selectedValueVento != null)
    {
if(((String)selectedValueVento).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_nullo")))		tempV = 0;
if(((String)selectedValueVento).equals( java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_basso")))		tempV = 0.91;
if(((String)selectedValueVento).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_basso")))		tempV = 2.27;
if(((String)selectedValueVento).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_basso")))    tempV = 3.64;
if(((String)selectedValueVento).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_medio")))                tempV = 5;
if(((String)selectedValueVento).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_alto")))     tempV = 6.36;
if(((String)selectedValueVento).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_alto")))                 tempV = 7.73;
if(((String)selectedValueVento).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_alto")))		tempV = 9.09;
}
else
{
Object last = ventoP.lastElement();
ventoP.removeElement(last);
repaint();
}

valoreVento = (String)selectedValueVento;
intVento = tempV;

		}





	repaint();
	}

	public void mouseClicked(MouseEvent evt) {
		evt.consume();
		int but = evt.getButton();

		if (this.funzione.equals("confini"))
		{
			if (but == 1)
			body.addPoint(evt.getX(),evt.getY());
		}

		if (this.funzione.equals("fonti"))
		{
			if (but == 1)
			{

			Object[] possibleValues = {java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_medio"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_alto"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_alto"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_alto")};
			Object selectedValue = JOptionPane.showInputDialog(null, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("JOP_T_intensity_value"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("JOP_intensity_value"), JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);

			double tempI = 0;
                        if((String)selectedValue != null)
                        {

            if(((String)selectedValue).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_basso")))	tempI = 0.91;
            if(((String)selectedValue).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_basso")))		tempI = 2.27;
			if(((String)selectedValue).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_basso")))	tempI = 3.64;
			if(((String)selectedValue).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_medio")))		tempI = 5;
			if(((String)selectedValue).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_alto")))	tempI = 6.36;
			if(((String)selectedValue).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_alto")))		tempI = 7.73;
			if(((String)selectedValue).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_alto")))	tempI = 9.09;

			Point point = new Point(evt.getX(), evt.getY());
			fonti.addElement(point);
			fontiL.addElement(riskL);

			int size = fonti.size();


			double tempDInt = disp;

			tempI = funzioniP.normalizza(tempI, Nmax, Nmin);

			//Vettore intensità
			intFonti[size - 1] = (int)tempI;

			w = (int)bound.getWidth();
			h = (int)bound.getHeight();
			int diag = (int)Math.hypot(w,h);

			double tempD = (diag/Math.sqrt(5.54)) * tempDInt;

			//Vettore dispersione
			dispFonti[size - 1] = (int)tempD;

			//Vettore rischio
			riskFonti[size - 1] = riskT;

			++nF;

			}

			}

			else if (but == 3 && fonti.isEmpty() == false)
			{
			Object last = fonti.lastElement();
			Object lastL = fontiL.lastElement();
			fonti.removeElement(last);
			fontiL.removeElement(lastL);
			--nF;
			}
		}

        if (this.funzione.equals("bocca"))
		{
                    for (int i = 0; i < perimetro.size(); i++) {
                    Line2D.Double line = (Line2D.Double)perimetro.elementAt(i);
                    if(line.intersects(evt.getX()-1, evt.getY()-1,2,2))
                    {
                      i =   perimetro.size();


                if (cB1 == false)
		{
		Point2D.Double punto = new Point2D.Double((double)evt.getX(), (double)evt.getY());
		boccaPt.addElement(punto);
		cB1 = true;
		}
                else if(cB1 == true)
		{
		Point2D.Double punto = new Point2D.Double((double)evt.getX(), (double)evt.getY());
		boccaPt.addElement(punto);
		int sizeBPt = boccaPt.size();
		Point2D.Double punto0 = (Point2D.Double)boccaPt.elementAt(sizeBPt - 1);
		Point2D.Double punto1 = (Point2D.Double)boccaPt.elementAt(sizeBPt - 2);
		bocca.add(new Line2D.Double(punto0, punto1));

/** INQUINAMENTO DISTROFICO */



new formBocca(new javax.swing.JFrame(), true).setVisible(true);
//if(cB == 0)

int sizeB = bocca.size();

tempIntD = funzioniP.normalizza(tempIntD, Nmax, Nmin); //il valore minimo e lo zero del valore "nullo" ed il massimo il 9.09 del "molto alto"
intBoccaD[sizeB-1] = tempIntD;

tempIntC = funzioniP.normalizza(tempIntC, Nmax, Nmin);
intBoccaC[sizeB-1] = tempIntC;

tempIntIS = funzioniP.normalizza(tempIntIS, Nmax, Nmin);
intBoccaIS[sizeB-1] = tempIntIS;

/** Vettore dispersione */

w = (int)bound.getWidth();
h = (int)bound.getHeight();
int diag = (int)Math.hypot(w,h);

//coefficenti di dispersione delle varie tipologie di sostanze.

double tempD = (diag/ Math.sqrt(5.54)) * dispBD;
double tempC = (diag/ Math.sqrt(5.54)) * dispBC;
double tempIS = (diag/ Math.sqrt(5.54)) * dispBIS;


dispBoccaD = tempD;
dispBoccaC = tempC;
dispBoccaIS = tempIS;

                cB1 = false;
                }
   }

                }
		}



		if (this.funzione.equals("moli"))
		{
			if (but == 1)
			{
//controllo se è il primo punto di un segmento e nel caso lo inserisco due volte...a meno che non sia il primo in assoluto.
//vorrei anche fare che disegna un segmento blu quando si muove il mouse


			if (xctrl == 0)
			{
			Point2D.Double punto = new Point2D.Double((double)evt.getX(), (double)evt.getY());
			moliPt.addElement(punto);
			xctrl = 1;
			}

			if (xctrl == 1)
			{
			Point2D.Double punto = new Point2D.Double((double)evt.getX(), (double)evt.getY());
			moliPt.addElement(punto);
			moliPt.addElement(punto);
			int size = moliPt.size();
			Point2D.Double punto0 = (Point2D.Double)moliPt.elementAt(size - 2);
			Point2D.Double punto1 = (Point2D.Double)moliPt.elementAt(size - 3);
			moli.add(new Line2D.Double(punto0, punto1));

			moliT.addElement(typeM);
			}

			}

			if (but != 1 && moli.isEmpty() == false)
			{
			xctrl = 0;
			repaint();
			}
		}

		if (this.funzione.equals("batimetria"))
		{
			if (but == 1)
			{

			ptBati.addElement(new Point2D.Double((double)evt.getX(), (double)evt.getY()));
			int size = ptBati.size();

	String selectedValue = JOptionPane.showInputDialog(null, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("JOP_T_batimetria"));

        if(funzioniP.isNum(selectedValue) && selectedValue.length() > 0)
            {
			double tempI = Double.parseDouble(selectedValue);

			if(tempI >= 0 && tempI <= 10000)
			//Vettore profondita
			deptBati[size - 1] = tempI;
			else
			{
			Object last = ptBati.lastElement();
			ptBati.removeElement(last);
			repaint();
			}

            }
            else
            {
            Object last = ptBati.lastElement();
			ptBati.removeElement(last);
        }



                        }
			else if (but == 3 && ptBati.isEmpty() == false)
			{
			Object last = ptBati.lastElement();
			ptBati.removeElement(last);
			}
		}

		if (this.funzione.equals("totale"))
		{
			if (but == 1)
			{//deve darmi il valore della matrice nel punto del click

			int x = evt.getX();
			int y = evt.getY();

			w = (int)bound.getWidth();
			h = (int)bound.getHeight();
			X = (int)bound.getX();
			Y = (int)bound.getY();

			int xp = x - X;
			int yp = y - Y;
			int coor = w*yp + xp;

                        if(tipoTot.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico")))
                        {
                        int val = (int)mtxTotaleD[coor];
			JOptionPane.showMessageDialog(null,String.valueOf(val), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("JOP_T_port_distr"),JOptionPane.INFORMATION_MESSAGE);
                        }
                        if(tipoTot.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("chimico")))
                        {
                        int val = (int)mtxTotaleC[coor];
			JOptionPane.showMessageDialog(null,String.valueOf(val), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("JOP_T_port_chim"),JOptionPane.INFORMATION_MESSAGE);
                        }
                        if(tipoTot.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("igienico_sanitario")))
                        {
                        int val = (int)mtxTotaleIS[coor];
			JOptionPane.showMessageDialog(null,String.valueOf(val), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("JOP_T_port_san"),JOptionPane.INFORMATION_MESSAGE);
                        }
                        if(tipoTot.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("totale")))
                        {
                        int val = (int)mtxTotaleTot[coor];
			JOptionPane.showMessageDialog(null,String.valueOf(val), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("JOP_T_port_tot"),JOptionPane.INFORMATION_MESSAGE);
                        }

                        }
		}
repaint();
	}


	public void mouseExited(MouseEvent txt) {
	}

	public void mouseMoved(MouseEvent evt) {
        if (this.funzione.equals("bocca"))
		{
		x2b = evt.getX();
		y2b = evt.getY();
		repaint();
        }

		if (this.funzione.equals("moli"))
		{
		x2m = evt.getX();
		y2m = evt.getY();
		repaint();
                }

	}

	public void mouseEntered(MouseEvent txt) {
	}

	public void valori() {
		vSup.setText(Integer.toString(sup));
		vPer.setText(Integer.toString(per));
		vCirc.setText(Integer.toString(circ));
		vFonti.setText(Integer.toString(nF));
		vBocca0.setText(Integer.toString(lungB));
		vBocca.setText(Double.toString(rBP));
		vMoli0.setText(Integer.toString(lungM));
		vMoli.setText(Double.toString(rMP));
		vSfrut.setText(Double.toString(sFrut));
		repaint();
	}

    public void esporta(int tipoEsp) {
            switch (tipoEsp) {
            case 00:
            funzioniP.esportaMatrix(mtxFontiD, h, w);
            break;
            case 01:
            funzioniP.esportaMatrix(mtxFontiC, h, w);
            break;
            case 02:
            funzioniP.esportaMatrix(mtxFontiIS, h, w);
            break;
            case 03:
            funzioniP.esportaMatrix(mtxFontiTot, h, w);
            break;


            case 10:
            funzioniP.esportaMatrix(mtxBoccaD, h, w);
            break;
            case 11:
            funzioniP.esportaMatrix(mtxBoccaC, h, w);
            break;
            case 12:
            funzioniP.esportaMatrix(mtxBoccaIS, h, w);
            break;
            case 13:
            funzioniP.esportaMatrix(mtxBoccaTot, h, w);
            break;
            case 14:
            funzioniP.esportaMatrix(mtxBoccaDist, h, w);
            break;

            case 20:
            funzioniP.esportaMatrix(mtxMoli, h, w);
            break;


            case 30:
            funzioniP.esportaMatrix(mtxBatimetria, h, w);
            break;

            case 40:
            funzioniP.esportaMatrix(mtxTotaleC, h, w);
            break;
            case 41:
            funzioniP.esportaMatrix(mtxTotaleD, h, w);
            break;
            case 42:
            funzioniP.esportaMatrix(mtxTotaleIS, h, w);
            break;
            case 43:
            funzioniP.esportaMatrix(mtxTotaleTot, h, w);
            break;

            }


	}


    @Override
	public void paintComponent(Graphics comp)
	{
		super.paintComponent(comp);

		Graphics2D comp2D = (Graphics2D) comp;
		comp2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		w = (int)bound.getWidth();
		h = (int)bound.getHeight();

		X = (int)bound.getX();
		Y = (int)bound.getY();

		comp2D.setColor(Color.red);

		/** disegna Immagine sfondo */
		if (image != null)
		{

		comp2D.drawImage(image, 0, 0, null);

		int color = 0;
                switch (schiarisci) {
                case 04:
                color = 0;
                break;

                case 00:
                color = 20;
                break;

                case 01:
                color = 40;
                break;

                case 02:
                color = 60;
                break;

                case 03:
                color = 80;
                break;

		}
        comp2D.setColor(new Color(255, 255, 255, color));
		comp2D.fillRect(0, 0, imageWidth, imageHeight);
		comp2D.setColor(Color.red);
                }



		/** Disegna buffers */
		comp2D.setClip(body);

		if(this.funzione.equals("fonti"))
		{
			if (tipoInq.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico"))) {
			Image imgFontiD = createImage(new MemoryImageSource(w, h, pixFontiD, 0, w));
			comp2D.drawImage(imgFontiD, X, Y, null);
			}
			if (tipoInq.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("chimico"))) {
			Image imgFontiC = createImage(new MemoryImageSource(w, h, pixFontiC, 0, w));
			comp2D.drawImage(imgFontiC, X, Y, null);
			}
			if (tipoInq.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("igienico_sanitario"))) {
			Image imgFontiIS = createImage(new MemoryImageSource(w, h, pixFontiIS, 0, w));
			comp2D.drawImage(imgFontiIS, X, Y, null);
			}
			if (tipoInq.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("totale"))) {
			Image imgFontiTot = createImage(new MemoryImageSource(w, h, pixFontiTot, 0, w));
			comp2D.drawImage(imgFontiTot, X, Y, null);
			}
		}

		if(this.funzione.equals("bocca"))
		{

			if (tipoInqB.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distanza"))) {
			Image imgBoccaDist = createImage(new MemoryImageSource(w, h, pixBoccaDist, 0, w));
			comp2D.drawImage(imgBoccaDist, X, Y, null);
			}
			if (tipoInqB.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico"))) {
			Image imgBoccaD = createImage(new MemoryImageSource(w, h, pixBoccaD, 0, w));
			comp2D.drawImage(imgBoccaD, X, Y, null);
			}
			if (tipoInqB.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("chimico"))) {
			Image imgBoccaC = createImage(new MemoryImageSource(w, h, pixBoccaC, 0, w));
			comp2D.drawImage(imgBoccaC, X, Y, null);
			}
			if (tipoInqB.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("igienico_sanitario"))) {
			Image imgBoccaIS = createImage(new MemoryImageSource(w, h, pixBoccaIS, 0, w));
			comp2D.drawImage(imgBoccaIS, X, Y, null);
			}
			if (tipoInqB.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("totale"))) {
			Image imgBoccaTot = createImage(new MemoryImageSource(w, h, pixBoccaTot, 0, w));
			comp2D.drawImage(imgBoccaTot, X, Y, null);
			}
		}

		if(this.funzione.equals("moli"))
		{
		Image imgMoli = createImage(new MemoryImageSource(w, h, pixMoli, 0, w));
		comp2D.drawImage(imgMoli, X, Y, null);
		}

		if(this.funzione.equals("batimetria"))
		{
		Image imgBati = createImage(new MemoryImageSource(w, h, pixBatimetria, 0, w));
		comp2D.drawImage(imgBati, X, Y, null);
		}

		if(this.funzione.equals("totale"))
		{
			if (tipoTot.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico"))) {
			Image imgTotD = createImage(new MemoryImageSource(w, h, pixTotaleD, 0, w));
			comp2D.drawImage(imgTotD, X, Y, null);
			}
			if (tipoTot.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("chimico"))) {
			Image imgTotC = createImage(new MemoryImageSource(w, h, pixTotaleC, 0, w));
			comp2D.drawImage(imgTotC, X, Y, null);
			}
			if (tipoTot.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("igienico_sanitario"))) {
			Image imgTotIS = createImage(new MemoryImageSource(w, h, pixTotaleIS, 0, w));
			comp2D.drawImage(imgTotIS, X, Y, null);
			}
			if (tipoTot.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("totale"))) {
			Image imgTotTot = createImage(new MemoryImageSource(w, h, pixTotaleTot, 0, w));
			comp2D.drawImage(imgTotTot, X, Y, null);
			}
		}

		comp2D.setClip(null);


		/** disegna confini */

		if(this.funzione.equals("confini"))
		{
		Image imgConfini = createImage(new MemoryImageSource(w, h, pixConfini, 0, w));
		comp2D.drawImage(imgConfini, X, Y, null);
		}

		comp2D.setStroke(new BasicStroke(1.5f));
        comp2D.setColor(coloreP);
		comp2D.draw(body);

        comp2D.setStroke(new BasicStroke());
		comp2D.setColor(Color.blue);
		comp2D.draw(bound);



		/* disegna prevalent wind */

        comp2D.setColor(Color.blue);
		int vsize = ventoP.size();

		if(vsize != 0)
		{
		Line2D vp = (Line2D)ventoP.elementAt(0);
		comp2D.draw(vp);
		double beta = Math.atan2(vp.getY2() - vp.getY1(), vp.getX2() - vp.getX1());

		comp2D.drawLine((int)vp.getX2(), (int)vp.getY2(), (int)(vp.getX2() - 50 * Math.sin(Math.PI/4 - beta)), (int)(vp.getY2() - 50 * Math.cos(Math.PI/4 - beta)));
		if(valoreVento != null)
                comp2D.drawString(valoreVento,(int)(vp.getX1() - 20 * Math.sin(beta)), (int)(vp.getY1() - 20 * Math.cos(beta)));

		}
		else if(x2c != -1)
		{
		comp2D.drawLine(x1c, y1c, x2c, y2c);
		double beta = Math.atan2(y2c - y1c, x2c - x1c);
		comp2D.drawLine(x2c, y2c, (int)(x2c - 50 * Math.sin(Math.PI/4 - beta)), (int)(y2c - 50 * Math.cos(Math.PI/4 - beta)));
		}


        comp2D.setColor(Color.blue);
        comp2D.drawString("prevalent wind: " + valoreVento + ", " + valoreVentoLabel,5, 20);


		/** disegna fonti */
		int np = fonti.size();

		for (int i=0; i < np; i++)
		{
		Point p = (Point)fonti.elementAt(i);
		comp2D.drawOval(p.x-2, p.y-2, 4, 4);
		comp2D.drawString("type:" + (String)fontiL.elementAt(i),p.x-10, p.y-10);
		comp2D.drawString("int:" + Double.toString(intFonti[i]),p.x-10, p.y-25);
		comp2D.drawString("disp:" + Double.toString(dispFonti[i]),p.x-10, p.y-40);
        }

		/** Disegna bocca di Porto*/


		comp2D.setColor(Color.blue);
                comp2D.setStroke(new BasicStroke(3f));
		for(int i = 0; i < bocca.size(); i++)
		{
		comp2D.draw((Line2D.Double)bocca.elementAt(i));
		}

                if(cB1 == true && boccaPt.size() > 0)
		{
		//selezione l'ultimo punto inserito e da questo disegna una linea...sempre che i punti restino nel vettore
		int size = boccaPt.size();
		Point2D.Double punto0 = (Point2D.Double)boccaPt.elementAt(size - 1);
		comp2D.setColor(Color.gray);
		comp2D.drawLine((int)punto0.getX(), (int)punto0.getY(), x2b, y2b);
		}

                comp2D.setStroke(new BasicStroke(1f));

		/** Disegna moli*/

		comp2D.setColor(Color.black);

		for (int i=0; i < moli.size(); i++)
		{
		comp2D.draw((Line2D.Double)moli.elementAt(i));
		}

		if(xctrl == 1 && moliPt.size() > 0)
		{
		//selezione l'ultimo punto inserito e da questo disegna una linea...sempre che i punti restino nel vettore
		int size = moliPt.size();
		Point2D.Double punto0 = (Point2D.Double)moliPt.elementAt(size - 1);
		comp2D.setColor(Color.blue);
		comp2D.drawLine((int)punto0.getX(), (int)punto0.getY(), x2m, y2m);
		}

		/** disegna Punti Batimetrici */
		np = ptBati.size();

		for (int i=0; i < np; i++)
		{
		Point2D p = (Point2D)ptBati.elementAt(i);
		comp2D.setColor(Color.blue);
		comp2D.drawRect((int)p.getX() - 2, (int)p.getY() - 2, 4, 4);
        comp2D.drawString("deptBati:" + Double.toString(deptBati[i]),(int)p.getX()-10, (int)p.getY()-10);
		comp2D.drawString("ptBati:" + i,(int)p.getX()-10, (int)p.getY()-25);
		}
		/** Disegna Max e min Inquinamento */

		/** Stampa info Vulnerability */
		vPanel.setVisible(false);

		if(this.funzione.equals("vuln"))
		{
		vPanel.setVisible(true);
		}
	}

public class formSfrutt extends javax.swing.JDialog {

    /** Creates new form formSfrutt */
    public formSfrutt(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PT = new javax.swing.JTextField();
        PM = new javax.swing.JTextField();
        Done = new javax.swing.JButton();
        Cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("sfruttamento"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 0, 0)));
        jLabel1.setText(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("V_totale_presenze"));

        jLabel2.setText(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("V_totale_posti_barca"));

        Done.setText(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done"));
        Done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoneActionPerformed(evt);
            }
        });

        Cancel.setText(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("cancel"));
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(PM)
                    .add(PT, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .add(Cancel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(Done))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(PT, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(PM, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(19, 19, 19)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(Done)
                    .add(Cancel)))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        pack();
    }// </editor-fold>

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {
// TODO add your handling code here:
    setVisible(false);
    }

    private void DoneActionPerformed(java.awt.event.ActionEvent evt) {
// TODO add your handling code here:

        double tempPT = Integer.parseInt(PT.getText());
        double tempPM = Integer.parseInt(PM.getText());
        sFrut = (tempPT/365)/tempPM;

        setVisible(false);
    }

    // Variables declaration - do not modify
    private javax.swing.JTextField PM;
    private javax.swing.JTextField PT;
    private javax.swing.JButton Cancel;
    private javax.swing.JButton Done;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration


}

/*
 * formBocca.java
 *
 * Created on 30 marzo 2007, 9.14
 */

/**
 *
 * @author  Jean Alberto Mensa
 */
public class formBocca extends javax.swing.JDialog {

    /** Creates new form formBocca */
    public formBocca(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();


        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("JP_T_intensita_inq_esterno"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 0, 0)));
        jLabel1.setText(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("JP_inq_distr"));

        jLabel2.setText(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("JP_inq_chim"));

        jLabel3.setText(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("JP_inq_is"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_nullo"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_medio"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_alto"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_alto"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_alto") }));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_nullo"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_medio"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_alto"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_alto"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_alto") }));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_nullo"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_basso"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_medio"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_alto"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_alto"), java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_alto") }));

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 144, Short.MAX_VALUE)
                        .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 105, Short.MAX_VALUE)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jComboBox2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jComboBox3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBox2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBox3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                          .add(64, 64, 64))));

        jButton2.setText(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("cancel"));

        jButton1.setText(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(250, Short.MAX_VALUE)
                .add(jButton2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1)
                    .add(jButton2))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>
/*    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
// TODO add your handling code here:
    cB = 0;
    setVisible(false);
    }
*/
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
// TODO add your handling code here:

    if(((String)jComboBox1.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_nullo")))             tempIntD = 0;
    if(((String)jComboBox1.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_basso")))       tempIntD = 0.91;
    if(((String)jComboBox1.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_basso")))             tempIntD = 2.27;
    if(((String)jComboBox1.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_basso")))  tempIntD = 3.64;
    if(((String)jComboBox1.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_medio")))             tempIntD = 5;
    if(((String)jComboBox1.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_alto")))   tempIntD = 6.36;
    if(((String)jComboBox1.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_alto")))              tempIntD = 7.73;
    if(((String)jComboBox1.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_alto")))        tempIntD = 9.09;

    if(((String)jComboBox2.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_nullo")))             tempIntC = 0;
    if(((String)jComboBox2.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_basso")))       tempIntC = 0.91;
    if(((String)jComboBox2.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_basso")))             tempIntC = 2.27;
    if(((String)jComboBox2.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_basso")))  tempIntC = 3.64;
    if(((String)jComboBox2.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_medio")))             tempIntC = 5;
    if(((String)jComboBox2.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_alto")))   tempIntC = 6.36;
    if(((String)jComboBox2.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_alto")))              tempIntC = 7.73;
    if(((String)jComboBox2.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_alto")))        tempIntC = 9.09;

    if(((String)jComboBox3.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_nullo")))             tempIntIS = 0;
    if(((String)jComboBox3.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_basso")))       tempIntIS = 0.91;
    if(((String)jComboBox3.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_basso")))             tempIntIS = 2.27;
    if(((String)jComboBox3.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_basso")))  tempIntIS = 3.64;
    if(((String)jComboBox3.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_medio")))             tempIntIS = 5;
    if(((String)jComboBox3.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_piu_o_meno_alto")))   tempIntIS = 6.36;
    if(((String)jComboBox3.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_alto")))              tempIntIS = 7.73;
    if(((String)jComboBox3.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_molto_alto")))        tempIntIS = 9.09;

    cB = 1;
    setVisible(false);
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;

    private javax.swing.JPanel jPanel1;
    // End of variables declaration

}

}
