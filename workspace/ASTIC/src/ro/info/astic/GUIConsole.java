package ro.info.astic;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ro.info.asticlib.clustering.BaseClusteringService;
import ro.info.asticlib.clustering.InitialClusteringDaemon;
import ro.info.asticlib.conf.Conf;
import ro.info.asticlib.io.FileSystemWatcher;

import java.awt.Component;

import javax.swing.Box;

@SuppressWarnings("serial")
public class GUIConsole extends JFrame {

	private static final String KEY_CLUSTERIZED = "clusterized";
	
	private JPanel contentPane;
	private static FileSystemWatcher watcher;
	public static final String ON_ICON_PATH = "res/img/img_on.png";
	public static final String OFF_ICON_PATH = "res/img/img_off.png";
	private final String START ="Start";
	private final String STOP ="Stop";
	private JButton btnStartInitialClustering;
	private JLabel lblStateInitialClustering;
	private JProgressBar pbInitialClustering;
	private InitialClusteringDaemon initialClusteringDeamon;
	private JImageView onOffIconInitialClustering;
	private JImageView imgFsw;
	private JProgressBar pbFsw;
	private JButton btnFsw;
	private JLabel lblStateFsw;
	private JImageView imgServer;
	private JProgressBar pbServer;
	private JButton btnStartServer;
	private JLabel lblServerState;
	private Server queryServer;
	private boolean forcedStoped = false;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		watcher= FileSystemWatcher.getInstance();
		watcher.setClusteringService(BaseClusteringService.class);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIConsole frame = new GUIConsole();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIConsole() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 485, 408);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//Initial Clustering
		JPanel pnInitialClustering = new JPanel();
		pnInitialClustering.setBounds(10, 39, 449, 31);
		contentPane.add(pnInitialClustering);
		FlowLayout fl_panel = new FlowLayout(FlowLayout.LEFT, 5, 5);
		fl_panel.setAlignOnBaseline(true);
		pnInitialClustering.setLayout(fl_panel);
		
		onOffIconInitialClustering = new JImageView("res/img/img_off.png");
		onOffIconInitialClustering.setSize(40, 40);
		pnInitialClustering.add(onOffIconInitialClustering);
		
		JLabel lblInitialClustering = new JLabel("Gruparea Initiala");
		lblInitialClustering.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInitialClustering.setPreferredSize(new Dimension(140, 20));
		pnInitialClustering.add(lblInitialClustering);
		
		pbInitialClustering = new JProgressBar();
		pnInitialClustering.add(pbInitialClustering);
		
		btnStartInitialClustering = new JButton("Start");
		btnStartInitialClustering.setEnabled(false);
		pnInitialClustering.add(btnStartInitialClustering);
		
		lblStateInitialClustering = new JLabel("State");
		lblStateInitialClustering.setEnabled(false);
		pnInitialClustering.add(lblStateInitialClustering);
		//File System Watcher
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 81, 449, 31);
		contentPane.add(panel_1);
		FlowLayout fl_panel_1 = new FlowLayout(FlowLayout.LEFT, 5, 5);
		fl_panel_1.setAlignOnBaseline(true);
		panel_1.setLayout(fl_panel_1);
		
		imgFsw = new JImageView("res/img/img_off.png");
		panel_1.add(imgFsw);
		
		JLabel label = new JLabel("Monitor pe Fisiere");
		label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label.setPreferredSize(new Dimension(140, 20));
		panel_1.add(label);
		
		pbFsw = new JProgressBar();
		panel_1.add(pbFsw);
		
		btnFsw = new JButton("Start");
		btnFsw.setEnabled(false);
		panel_1.add(btnFsw);
		
		lblStateFsw = new JLabel("Stare");
		lblStateFsw.setEnabled(false);
		panel_1.add(lblStateFsw);
		//Query Server
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 123, 449, 31);
		contentPane.add(panel_2);
		FlowLayout fl_panel_2 = new FlowLayout(FlowLayout.LEFT, 5, 5);
		fl_panel_2.setAlignOnBaseline(true);
		panel_2.setLayout(fl_panel_2);
		
		imgServer = new JImageView(OFF_ICON_PATH);
		panel_2.add(imgServer);
		
		JLabel label_2 = new JLabel("Server de Intergogari");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_2.setPreferredSize(new Dimension(140, 20));
		panel_2.add(label_2);
		
		pbServer = new JProgressBar();
		panel_2.add(pbServer);
		
		btnStartServer = new JButton("Start");
		btnStartServer.setEnabled(false);
		panel_2.add(btnStartServer);
		
		lblServerState = new JLabel("State");
		lblServerState.setEnabled(false);
		panel_2.add(lblServerState);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textPane); 
		scrollPane.setBounds(10, 165, 449, 194);
		contentPane.add(scrollPane);
		try {
			Log.setup(textPane);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		JLabel lblAsticServerConsole = new JLabel("Astic Server Console");
		lblAsticServerConsole.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAsticServerConsole.setBounds(146, 11, 164, 14);
		contentPane.add(lblAsticServerConsole);
		
		//if(!PrefUtils.load(KEY_CLUSTERIZED)){
			btnStartInitialClustering.setEnabled(true);
			lblStateInitialClustering.setText("Asteapta");
			
		//}else{
			//btnStartInitialClustering.setEnabled(false);
			//lblStateInitialClustering.setText("Terminat");
			enableFileSystemWatcher();
			enableServerQuery();
		//}
		setListeners();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void startInitialClustering(){
		if(initialClusteringDeamon != null){
			return ;
		}
		initialClusteringDeamon = new InitialClusteringDaemon(Conf.ROOT_TEST,watcher) {
			public void onFinish(Exception e) {
				enableFileSystemWatcher();
				enableServerQuery();
				//if(!initialClusteringDeamon.isForcedStoped()){
					PrefUtils.save(KEY_CLUSTERIZED, true);
				//}
				initialClusteringDeamon = null;
				btnStartInitialClustering.setText(START);
				lblStateInitialClustering.setText("Oprit");
				pbInitialClustering.setIndeterminate(false);
				onOffIconInitialClustering.setImagePath(OFF_ICON_PATH);
				
			};
		};
		onOffIconInitialClustering.setImagePath(ON_ICON_PATH);
		initialClusteringDeamon.start();
		lblStateInitialClustering.setText("Ruleaza");
		pbInitialClustering.setIndeterminate(true);
		btnStartInitialClustering.setText(STOP);
	}
	
	private void enableFileSystemWatcher(){
		lblStateFsw.setText("Asteptare");
		btnFsw.setEnabled(true);
	}
	
	private void enableServerQuery(){
		btnStartServer.setEnabled(true);
		lblServerState.setText("Asteptare");
	}
	
	private void setListeners(){
		btnStartInitialClustering.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(btnStartInitialClustering.getText().equals(START)){
					startInitialClustering();
				}else{
					initialClusteringDeamon.stopD();
					lblStateInitialClustering.setText("Oprire...");
				}
			}
		});
		btnFsw.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(btnFsw.getText().equals(START)){
					FileSystemWatcher.getInstance().start();
					btnFsw.setText(STOP);
					imgFsw.setImagePath(ON_ICON_PATH);
					lblStateFsw.setText("Ruleaza");
				}else{
					FileSystemWatcher.getInstance().stopD();
					btnFsw.setText(START);
					imgFsw.setImagePath(OFF_ICON_PATH);
					lblStateFsw.setText("Oprit");
				}
			}
		});
		btnStartServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(btnStartServer.getText().equals(START)){
					if(queryServer!=null){
						return ;
					}
					queryServer = new Server();
					new Thread(queryServer).start();
					imgServer.setImagePath(ON_ICON_PATH);
					lblServerState.setText("Ruleaza");
					btnStartServer.setText(STOP);
				}else{
					queryServer.stop();
					queryServer = null;
					imgServer.setImagePath(OFF_ICON_PATH);
					lblServerState.setText("Oprit");
					btnStartServer.setText(START);
				}
			}
		});
	}
}
