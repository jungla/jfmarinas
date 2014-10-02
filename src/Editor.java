package jmarinasen.jmarinaspkg;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;

import java.io.*;

import java.net.URL;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JToolBar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.border.*;
/**
 * 
 * @author Jean Alberto Mensa
 * 
 */
public class Editor extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] arguments) {
        new EditorFrame();
    }
}

class EditorFrame extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel tastiConfini = new JPanel();
    JPanel tastiBocca = new JPanel();
    JPanel tastiFonti = new JPanel();
    JPanel tastiMoli = new JPanel();
    JPanel tastiBatimetria = new JPanel();
    JPanel tastiTotale = new JPanel();
    JPanel tastiVuln = new JPanel();
    
    JPanel pane = new JPanel();
    
    Porto porto = new Porto();
    //Editor editor = new Editor();
    funzioniEditor funzioniE = new funzioniEditor();
    
    
    Border etched = BorderFactory.createEtchedBorder();
    
    JFileChooser chooser = new JFileChooser();
    
    
    
    public EditorFrame() {
        super(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_title"));
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        ActionListener navigazione = new CambiaMenu();
        ActionListener scegliFile = new ScegliImmagine();
//        ItemListener chkDati = new ChkDati();
        
        porto.DisegnaSfondo();
        
        
        /** pannello tastiConfini */
        pannelloF(tastiConfini, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_harbor_borders"));
        JEditorPane area = new JEditorPane("text/html",
                java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("<HTML>Operations_borders</HTML>"));
        area.setPreferredSize(new Dimension(170,400));
        area.setEditable(false);
        area.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        tastiConfini.add(area);
        
        ButtonGroup groupC0 = new ButtonGroup();
        
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("harbor_borders"), tastiConfini, groupC0, true);
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("prevalent_wind"), tastiConfini, groupC0, false);
        
        //addButton("Vento Prevalente", tastiConfini, 150);
        tastiConfini.add(new JLabel(" "));
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done"), tastiConfini, 100);
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("cancel"), tastiConfini, 100);
        
        /** pannello tastiFonti */
        pannelloF(tastiFonti, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_pollutant_sources"));
        
        JEditorPane areaF = new JEditorPane("text/html",
        java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("<HTML>Operations_sources</HTML>"));
        
        areaF.setPreferredSize(new Dimension(180,200));
        areaF.setEditable(false);
        areaF.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        tastiFonti.add(areaF);
        
        ButtonGroup groupF = new ButtonGroup();
        
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("1_acque_agricole"), tastiFonti, groupF, true);
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("2_acque_allevamenti"), tastiFonti, groupF, false);
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("3_acque_fluviali"), tastiFonti, groupF, false);
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("4_aree_lavaggio"), tastiFonti, groupF, false);
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("5_distributori_carburante"), tastiFonti, groupF, false);
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("6_impianti_acquacoltura"), tastiFonti, groupF, false);
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("7_presenza_alghe"), tastiFonti, groupF, false);
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("8_scarichi_fognari"), tastiFonti, groupF, false);
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("9_scarichi_industriali"), tastiFonti, groupF, false);
        
        tastiFonti.add(new JLabel(" "));
        
        tastiFonti.add(new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("calcola_rischio")));
        
        menuTendina = new JComboBox();
        menuTendina.setEditable(false);
        menuTendina.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico"));
        menuTendina.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("chimico"));
        menuTendina.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("igienico_sanitario"));
        menuTendina.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("totale"));
        
	menuTendina.addActionListener(
	new AbstractAction()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public final void actionPerformed(ActionEvent event)
		{
		porto.tipoInq = (String)menuTendina.getSelectedItem();
		porto.repaint();
		}
	}
	);
        
        tastiFonti.add(menuTendina);
        
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("elimina_fonte"), tastiFonti, 140);
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done"), tastiFonti, 100);
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("cancel"), tastiFonti, 100);
        
        
        
        /** pannello tastiBocca */
        pannelloF(tastiBocca, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_harbor_mouth"));
        
        
        JEditorPane areaB = new JEditorPane("text/html", java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("<HTML>Operation_mouth</HTML>"));
        areaB.setPreferredSize(new Dimension(180,500));
        areaB.setEditable(false);
        areaB.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        tastiBocca.add(areaB);
        
        new ButtonGroup();
        
        menuTendinaB = new JComboBox();
        menuTendinaB.setEditable(false);
        menuTendinaB.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distanza"));
        menuTendinaB.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico"));
        menuTendinaB.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("chimico"));
        menuTendinaB.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("igienico_sanitario"));
        menuTendinaB.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("totale"));
        
        menuTendinaB.addActionListener(
                new AbstractAction() {
            /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			public final void actionPerformed(ActionEvent event) {
                porto.tipoInqB = (String)menuTendinaB.getSelectedItem();
                porto.repaint();
            }
        }
        );
        tastiBocca.add(menuTendinaB);
        
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done"), tastiBocca, 100);
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("cancel"), tastiBocca, 100);
        
        /** pannello tastiMoli */
        pannelloF(tastiMoli, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_wharf"));
        JEditorPane areaM = new JEditorPane("text/html",
                java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("<HTML>Operation_wharf</HTML>"));
        areaM.setPreferredSize(new Dimension(170,200));
        areaM.setEditable(false);
        areaM.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        tastiMoli.add(areaM);
        
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("nuovo_molo"), tastiMoli, 140);
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("elimina_molo"), tastiMoli, 140);
        
        tastiMoli.add(new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("tipologia_pontile")));
        menuTendinaM = new JComboBox();
        menuTendinaM.setEditable(false);
        menuTendinaM.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("palafittato"));
        menuTendinaM.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("floating_deep"));
        menuTendinaM.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("floating_sup"));
        
        menuTendinaM.addActionListener(
                new AbstractAction() {
            /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			public final void actionPerformed(ActionEvent event) {
                if(((String)menuTendinaM.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("palafittato")))		porto.typeM = new Double(0.3);
                if(((String)menuTendinaM.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("floating_deep")))	porto.typeM = new Double(0.5);
                if(((String)menuTendinaM.getSelectedItem()).equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("floating_sup")))	porto.typeM = new Double(0.8);
                porto.repaint();
            }
        }
        );
        
        tastiMoli.add(menuTendinaM);
        
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done"), tastiMoli, 100);
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("cancel"), tastiMoli, 100);
        
        
        /** pannello tastiBatimetria */
        pannelloF(tastiBatimetria, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_bathymetry"));
        JEditorPane areaBat = new JEditorPane("text/html",
                java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("<HTML>Opertaions_bath</HTML>"));
        areaBat.setPreferredSize(new Dimension(170,400));
        areaBat.setEditable(false);
        areaBat.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        tastiBatimetria.add(areaBat);
        
        ButtonGroup groupBat = new ButtonGroup();
        
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("rimescolato"), tastiBatimetria, groupBat, true);
        addRButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("stratificato"), tastiBatimetria, groupBat, false);
        
//        tastiBatimetria.add(new JLabel(" "));
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("elimina_punto"), tastiBatimetria, 140);
        tastiBatimetria.add(new JLabel(" "));
        
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done"), tastiBatimetria, 100);
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("cancel"), tastiBatimetria, 100);
        
        
        /** pannello tastiTotale */
        pannelloF(tastiTotale, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_portuality"));
        JEditorPane areaP = new JEditorPane("text/html",
                java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("<HTML>Operations_portuality</HTML>"));
        areaP.setPreferredSize(new Dimension(170,400));
        areaP.setEditable(false);
        areaP.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        tastiTotale.add(areaP);
        
        tastiTotale.add(new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("calcola_rischio")));
        menuTendinaTot = new JComboBox();
        menuTendinaTot.setEditable(false);
        menuTendinaTot.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico"));
        menuTendinaTot.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("chimico"));
        menuTendinaTot.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("igienico_sanitario"));
        menuTendinaTot.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("totale"));
        
        menuTendinaTot.addActionListener(
                new AbstractAction() {
            /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			public final void actionPerformed(ActionEvent event) {
                porto.tipoTot = (String)menuTendinaTot.getSelectedItem();
                porto.repaint();
            }
        }
        );
        
        tastiTotale.add(menuTendinaTot);
        
        //filtered = new JCheckBox("filtered");
        //filtered.setSelected(true);
       
        tastiTotale.add(new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("patch_size")));

        menuTendinaPatch = new JComboBox();
        menuTendinaPatch.setEditable(false);
        menuTendinaPatch.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_nullo"));
        menuTendinaPatch.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("molto_piccola"));
        menuTendinaPatch.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("piccola"));
        menuTendinaPatch.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("media"));
        menuTendinaPatch.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("grande"));
        menuTendinaPatch.addItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("molto_grande"));
        
        
        menuTendinaPatch.addActionListener(
                new AbstractAction() {
            /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			public final void actionPerformed(ActionEvent event) {
                
                if(menuTendinaPatch.getSelectedItem().equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("F_nullo")))
                porto.sizeCel = 0;
                if(menuTendinaPatch.getSelectedItem().equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("molto_piccola")))
                porto.sizeCel = 10;
                if(menuTendinaPatch.getSelectedItem().equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("piccola")))
                porto.sizeCel = 20;
                if(menuTendinaPatch.getSelectedItem().equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("media")))
                porto.sizeCel = 30;
                if(menuTendinaPatch.getSelectedItem().equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("grande")))
                porto.sizeCel = 40;
                if(menuTendinaPatch.getSelectedItem().equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("molto_grande")))
                porto.sizeCel = 50;
                
                
            }
        }
        );
        
        tastiTotale.add(menuTendinaPatch);
        
//        //Register a listener for the check boxes.
//        patched = new JCheckBox(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("patched"));
//        patched.setSelected(true);  
//        
//        //filtered.addItemListener(chkDati);
//        patched.addItemListener(chkDati);
//        
//        
//        
//        //tastiTotale.add(filtered);
//        tastiTotale.add(patched);
        
        
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done"), tastiTotale, 100);
        
        //addButton("Filtra", tastiTotale, 100);
        
        
        /** pannello tastiVuln */
        pannelloF(tastiVuln, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_vulnerability"));
        addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("sfruttamento"), tastiVuln, 150);
	    addButton(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("done"), tastiVuln, 100);
        
        /** pannello tastiSotto */
        JPanel tastiSotto = new JPanel();
        
        FlowLayout grid = new FlowLayout(2);
        tastiSotto.setLayout(grid);
        
        JLabel credits = new JLabel(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_signature"));
        
        tastiSotto.add(credits);
        
        
        
// 	JLabel coord = new JLabel();
// 	JLabel istruzioni = new JLabel();
        
        /**Menu generale alto */
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_file"));
        menuBar.add(fileMenu);
        
        JMenuItem nuovo = new JMenuItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_new_project"));
        nuovo.setEnabled(false);
        fileMenu.add(nuovo);
        
        JMenuItem apri = new JMenuItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_open_project"));
        apri.setEnabled(false);
        fileMenu.add(apri);
        
        JMenuItem salva = new JMenuItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_save"));
        salva.setEnabled(false);
        fileMenu.add(salva);
        
        fileMenu.addSeparator();
        
        new JMenuItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_exit"));
        fileMenu.add(
                new AbstractAction(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_exit")) {
            /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        JMenu editMenu = new JMenu(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_edit"));
        menuBar.add(editMenu);
        
        JMenuItem importa = new JMenuItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_import_image"));
        editMenu.add(importa);
        importa.addActionListener(scegliFile);
        
        JMenuItem esporta = new JMenuItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_export_image"));
        esporta.setEnabled(false);
        editMenu.add(esporta);
        
        
        JMenu toolMenu = new JMenu(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_export"));
        
        JMenu fontiMenu = new JMenu(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_source_matrix"));
        JMenu boccaMenu = new JMenu(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_mouth_matrix"));
        JMenu totaleMenu = new JMenu(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_portuality_matrix"));
        
        
        
        toolMenu.add(fontiMenu);
        addMenuItemEsp(00, fontiMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico"));
        addMenuItemEsp(01, fontiMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("chimico"));
        addMenuItemEsp(02, fontiMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("igienico_sanitario"));
        addMenuItemEsp(03, fontiMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("totale"));
        
        toolMenu.add(boccaMenu);
        addMenuItemEsp(10, boccaMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico"));
        addMenuItemEsp(11, boccaMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("chimico"));
        addMenuItemEsp(12, boccaMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("igienico_sanitario"));
        addMenuItemEsp(13, boccaMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("totale"));
        addMenuItemEsp(14, boccaMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("f_distanza"));
        
        addMenuItemEsp(20, toolMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_tortuosity_data"));
        addMenuItemEsp(30, toolMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_bathymetry_data"));
        
        toolMenu.add(totaleMenu);
        addMenuItemEsp(40, totaleMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("distrofico"));
        addMenuItemEsp(41, totaleMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("chimico"));
        addMenuItemEsp(42, totaleMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("igienico_sanitario"));
        addMenuItemEsp(43, totaleMenu, java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("totale"));
        
        menuBar.add(toolMenu);
        
        
        
        JMenu visualizzaMenu = new JMenu(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_view"));
        menuBar.add(visualizzaMenu);
        
        
        JMenu schiarisciMenu = new JMenu(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_schiarisci_img"));
        
        visualizzaMenu.add(schiarisciMenu);
        addMenuItemVis(04, schiarisciMenu, "0%");
        addMenuItemVis(00, schiarisciMenu, "20%");
        addMenuItemVis(01, schiarisciMenu, "40%");
        addMenuItemVis(02, schiarisciMenu, "60%");
        addMenuItemVis(03, schiarisciMenu, "80%");
        
        JMenu infoMenu = new JMenu(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_help"));
        menuBar.add(infoMenu);
        
        JMenuItem about = new JMenuItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_about"));
        infoMenu.add(about);
        
        infoMenu.addSeparator();
        
        JMenuItem help = new JMenuItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_help_contents"));
        infoMenu.add(help);
        
        JMenuItem support = new JMenuItem(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("M_online_support"));
        infoMenu.add(support);
        
        
        
        /** Pannello JToolBar Sx */
        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(1);
        new ButtonGroup();
        
        addButtonTB(toolBar, true, "confini", java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_harbor_borders"), navigazione);
        addButtonTB(toolBar, true, "fonti", java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_pollutant_sources"), navigazione);
        addButtonTB(toolBar, true, "bocca", java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_harbor_mouth"), navigazione);
        addButtonTB(toolBar, true, "moli", java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_wharf"), navigazione);
        addButtonTB(toolBar, true, "batimetria", java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_bathymetry"), navigazione);
        addButtonTB(toolBar, true, "totale", java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_portuality"), navigazione);
        addButtonTB(toolBar, true, "vuln", java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("T_vulnerability"), navigazione);
        
        /** LAYOUT PANNELLO CENTRALE */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BorderLayout border = new BorderLayout();
        pane.setLayout(border);
        
        pane.add(menuBar, BorderLayout.NORTH);
        pane.add(tastiSotto, BorderLayout.SOUTH);
        pane.add(toolBar, BorderLayout.WEST);
        pane.add(porto,  BorderLayout.CENTER);
        pane.add(tastiConfini, BorderLayout.EAST);
        
        setContentPane(pane);
        setVisible(true);
        
    }
    private JComboBox menuTendina;
    private JComboBox menuTendinaB;
    private JComboBox menuTendinaTot;
    private JComboBox menuTendinaPatch;
    private JComboBox menuTendinaM;
    // un po' di spec ai pannelli laterali
    private void pannelloF(JPanel panel, String titolo) {
        FlowLayout grid = new FlowLayout(0);
        
        panel.setLayout(grid);
//	panel.setMargin(new Insets(0, 0, 0, 40));
        panel.setPreferredSize(new Dimension(DXPANEL_WIDTH, DXPANEL_HEIGHT));
        panel.setAlignmentX(RIGHT_ALIGNMENT);
        
        Border titlePannello = BorderFactory.createTitledBorder(etched, titolo);
        panel.setBorder(titlePannello);
        
    }
    
//aggiunge listener per il porto...
    private void addButton(String label, JPanel panel, int width) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(width, 25));
        panel.add(button);
        button.addActionListener(porto);
//	button.setBorder(lined);
    }
    
//aggiunge listener per il porto...
    private void addRButton(String label, JPanel panel, ButtonGroup group, boolean selected ) {
        JRadioButton button = new JRadioButton(label, selected);
        panel.add(button);
        group.add(button);
        button.addActionListener(porto);
    }
    
//aggiunge listener alla navigazione, per cambiare il menu ed al porto per sapere cosa fare....
    private JButton addButtonTB(JToolBar toolBar, boolean bUseImage, String sButton, String sToolHelp, ActionListener listener) {
        JButton b;
        
        // Create a new button
        if( bUseImage )
        {
        URL url = this.getClass().getResource("/img/" + sButton + ".png");
        b = new JButton( new ImageIcon( url ) );
          }
        else
            b = (JButton)toolBar.add( new JButton() );
        
        // Add the button to the toolbar
        toolBar.add( b );
        
        // Add optional button text
        
        // Only a graphic, so make the button smaller
        b.setMargin( new Insets( 0, 0, 0, 0 ) );
        
        // Add optional tooltip help
        if( sToolHelp != null )
            b.setToolTipText( sToolHelp );
        
        // Make sure this button sends a message when the user clicks it
        b.setActionCommand(sButton);
        b.addActionListener(porto);
        b.addActionListener(listener);
        
        return b;
    }
    
    private void addMenuItemEsp(final int type, JMenu menu, String label) {
        menu.add(
                new AbstractAction(label) {
            /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
                porto.esporta(type);
            }
        });
    }
    
        
    private void addMenuItemVis(final int type, JMenu menu, String label) {
        menu.add(
                new AbstractAction(label) {
            /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
                porto.schiarisci = type;
            repaint();
        }
         });
    }
    
//    private class ChkDati implements ItemListener {
//        public void itemStateChanged(ItemEvent e) {
//            int c = -1;
//            Object source = e.getItemSelectable();
//            if (source.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("filtered"))) {
//                c = 0;
//            }
//            if (source.equals(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("patched"))) {
//                c = 1;
//            }
//            
//            if (e.getStateChange() == ItemEvent.DESELECTED) {
//                if(c == 0)
//                    System.out.println(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("filtered"));
//                else if (c == 1) {
//                    porto.patched = false;
//                }
//            }
//            
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                if(c == 0)
//                    System.out.println(java.util.ResourceBundle.getBundle("jmarinasen/jmarinaspkg/Bundle").getString("filtered"));
//                else if (c == 1) {
//                    porto.patched = true;
//                }
//            }
//        }
//        
//    }
    
    public class CambiaMenu implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String com = evt.getActionCommand();
            if (com.equals("confini")) {
                pane.remove(tastiBocca);
                pane.remove(tastiMoli);
                pane.remove(tastiFonti);
                pane.remove(tastiTotale);
                pane.remove(tastiBatimetria);
                pane.remove(tastiVuln);
                pane.add(tastiConfini, BorderLayout.EAST);
            } else if (com.equals("bocca")) {
                pane.remove(tastiMoli);
                pane.remove(tastiConfini);
                pane.remove(tastiFonti);
                pane.remove(tastiTotale);
                pane.remove(tastiBatimetria);
                pane.remove(tastiVuln);
                pane.add(tastiBocca, BorderLayout.EAST);
            } else if (com.equals("fonti")) {
                pane.remove(tastiBocca);
                pane.remove(tastiMoli);
                pane.remove(tastiConfini);
                pane.remove(tastiTotale);
                pane.remove(tastiBatimetria);
                pane.remove(tastiVuln);
                pane.add(tastiFonti, BorderLayout.EAST);
            } else if (com.equals("moli")) {
                pane.remove(tastiBocca);
                pane.remove(tastiConfini);
                pane.remove(tastiFonti);
                pane.remove(tastiTotale);
                pane.remove(tastiBatimetria);
                pane.remove(tastiVuln);
                pane.add(tastiMoli, BorderLayout.EAST);
            } else if (com.equals("batimetria")) {
                pane.remove(tastiBocca);
                pane.remove(tastiConfini);
                pane.remove(tastiFonti);
                pane.remove(tastiMoli);
                pane.remove(tastiTotale);
                pane.remove(tastiVuln);
                pane.add(tastiBatimetria, BorderLayout.EAST);
            } else if (com.equals("totale")) {
                pane.remove(tastiBocca);
                pane.remove(tastiConfini);
                pane.remove(tastiFonti);
                pane.remove(tastiMoli);
                pane.remove(tastiBatimetria);
                pane.remove(tastiVuln);
                pane.add(tastiTotale, BorderLayout.EAST);
            } else if (com.equals("vuln")) {
                porto.valori();
                pane.remove(tastiBocca);
                pane.remove(tastiConfini);
                pane.remove(tastiFonti);
                pane.remove(tastiMoli);
                pane.remove(tastiBatimetria);
                pane.remove(tastiTotale);
                pane.add(tastiVuln, BorderLayout.EAST);
            }
            setContentPane(pane);
            setVisible(true);
            
        }
    }
    
    
    private class ScegliImmagine implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            chooser.setCurrentDirectory(new File("."));
            int result = chooser.showOpenDialog(null);
            
            if(result == JFileChooser.APPROVE_OPTION) {
                porto.path = chooser.getSelectedFile().getPath();
                System.out.println(porto.path);
                porto.DisegnaPorto();
                repaint();
            }
        }
    }
    
    
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 700;
    
    private static final int DXPANEL_WIDTH = 220;
    private static final int DXPANEL_HEIGHT = 500;
    
}