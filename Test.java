import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;

public class Test extends JFrame {

	private JPanel contentPane;
	private final Action action = new SwingAction();
	private JScrollPane scrollPane_1;
	JTable table;
	private final Action action_1 = new SwingAction_1();
	JTextArea textArea;
	JTextArea textArea_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 668, 577);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setAction(action);
		btnNewButton.setBounds(538, 95, 93, 33);
		contentPane.add(btnNewButton);
		
	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 50, 244, 286);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.add(textArea);
		scrollPane.setViewportView(textArea);
		
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(297, 50, 212, 286);
		contentPane.add(scrollPane_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(26, 371, 612, 147);
		contentPane.add(scrollPane_2);
		
		textArea_1 = new JTextArea();
		scrollPane_2.add(textArea_1);
		scrollPane_2.setViewportView(textArea_1);
		
		JLabel label = new JLabel("源代码：");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(29, 23, 93, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("词法分析：");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(305, 23, 93, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("语法分析：");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(26, 346, 93, 15);
		contentPane.add(label_2);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setAction(action_1);
		btnNewButton_1.setBounds(538, 186, 93, 33);
		contentPane.add(btnNewButton_1);
		
		
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "词法分析");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			String s=textArea.getText();
			String mm[]=getStr(s);
			VerbAnalysis VA=new VerbAnalysis();
			VA.Analysis(mm);
			ArrayList TokenList=VA.returnTokenList();
			Vector Vtable=new Vector();
			for(int i=0;i<TokenList.size();i++){
				Vector V=new Vector();
				token T=(token) TokenList.get(i);
				V.add(T.getType());
				V.add(T.getValue());
				Vtable.add(V);
			}
			Vector Vname=new Vector();
			Vname.add("类型");
			Vname.add("值");
			table=new JTable(Vtable,Vname){
			    public boolean isCellEditable(int rowIndex, int ColIndex){
			    	 return false;
		            } 
		    };
		    
		    scrollPane_1.add(table);
		    scrollPane_1.setViewportView(table);  
			
			
	}
		
	}
	
	public String[]getStr(String str){
	      String sss[]=str.split("\n");
	      return sss;
}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "语法分析");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			textArea_1.setText("");
			String s=textArea.getText();
			String mm[]=getStr(s);
			grammerAnalysis ga=new grammerAnalysis();
			if(ga.Analysis(mm)) {
				textArea_1.setText("编译错误！");
			}
			else{
				List <String>error=ga.getErrorMessage();
				if(error.size()==0) {
					textArea_1.setText("Compile finished!");
				}
				else {
				for(int i=0;i<error.size();i++) {
					textArea_1.append(i+":"+error.get(i)+"\n");
				}
				textArea_1.append("Compile false!");
				}
			};
		}
	}
}
