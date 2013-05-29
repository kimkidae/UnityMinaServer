package main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class ServerRuncher extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JTextField txtPort;
	private Server server;
	private StringBuilder sb = new StringBuilder();

	public ServerRuncher() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(new Rectangle(0, 0, 600, 500));
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		final JButton btnStart = new JButton("Start");
		final JButton btnStop = new JButton("Stop");
		btnStart.setPreferredSize(new Dimension(80, 23));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port = 9090;
				if(!txtPort.getText().isEmpty()){
					try{
						port = Integer.parseInt(txtPort.getText());
					}catch(Exception ex){}
				}
				txtPort.setText(port+"");
				
				server = new Server();
				server.start(port);
				
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
			}
		});
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblPort = new JLabel("PORT");
		lblPort.setFont(new Font("Gulim", Font.PLAIN, 12));
		lblPort.setPreferredSize(new Dimension(50, 15));
		panel.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setEditable(false);
		txtPort.setText("9090");
		txtPort.setPreferredSize(new Dimension(80, 21));
		panel.add(txtPort);
		txtPort.setColumns(6);
		
		panel.add(btnStart);
		
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.stop();
				server = null;
				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
			}
		});
		btnStop.setPreferredSize(new Dimension(80, 23));
		panel.add(btnStop);
		
		final JTextArea txtAConsole = new JTextArea();
		txtAConsole.setEditable(false);
		txtAConsole.setWrapStyleWord(true);
		txtAConsole.setLineWrap(true);
		getContentPane().add(txtAConsole, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(txtAConsole);
		getContentPane().add(scrollPane);

		OutputStream os = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				if (b == '\r') return;
			      if (b == '\n') {
			         final String text = sb.toString() + "\n";
			         SwingUtilities.invokeLater(new Runnable() {
			            public void run() {
			            	txtAConsole.append(text);
			            }
			         });
			         sb.setLength(0);
			         return;
			      }
			      sb.append((char) b);
			}
		};

		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
	}

	public static void main(String args[]){
		ServerRuncher frame = new ServerRuncher();
		frame.setVisible(true);
	}
}
