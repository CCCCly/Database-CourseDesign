package admin;
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;
public class NewTeam extends JPanel implements ActionListener
{
    private String host;
	private Connection conn; 
    private Statement stmt;
    private ResultSet rs;
    
	private JLabel[] jlArray = {new JLabel("Ժϵ���"),new JLabel("Ժϵ����")};
    private JTextField[] jtfArray = {new JTextField(),new JTextField()};
    private JButton[] jbArray = {new JButton("ȷ��"),new JButton("����")};
	public NewTeam(String host)
	{
	     this.host = host;
	    this.initialFrame();
	}
	public void initialFrame()
	{
		this.setLayout(null);
		jlArray[0].setBounds(100,20,100,30);
		this.add(jlArray[0]);
        jlArray[1].setBounds(100,70,100,30);
		this.add(jlArray[1]);
		
	    jtfArray[0].setBounds(200,20,150,30);
        this.add(jtfArray[0]);
        jtfArray[1].setBounds(200,70,150,30);
        this.add(jtfArray[1]);
        
	    jbArray[0].setBounds(150,120,70,30);
	    this.add(jbArray[0]);
	    jbArray[1].setBounds(250,120,70,30);
	    this.add(jbArray[1]);
	    
	    jbArray[0].addActionListener(this);
	    jbArray[1].addActionListener(this);
	}
	//���·���
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jbArray[1])
		{
			jtfArray[0].setText("");
			jtfArray[1].setText("");
		}
		if(e.getSource()==jbArray[0])
		{
			String pattern = "[0-9]{2}";
			String team_id = jtfArray[0].getText().trim();
			if(team_id.equals(""))
			{
				JOptionPane.showMessageDialog(this,"��Ų���Ϊ��","����",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			if(!team_id.matches(pattern))
			{
				JOptionPane.showMessageDialog(this,"���Ϊ2λ����","����",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			String team_name  = jtfArray[1].getText().trim(); 
			if(team_name.equals(""))
			{
				JOptionPane.showMessageDialog(this,"���Ʋ���Ϊ��","����",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			this.initialConnection();
			try
			{
				String sql  = "select * from team where team_id =  '"+team_id+"'";
                rs = stmt.executeQuery(sql); 				
				if(rs.next())
				{
					JOptionPane.showMessageDialog(this,"��Ժϵ����Ѵ���","����",JOptionPane.ERROR_MESSAGE);
					this.closeConn();
					return ;
				}
			    rs.close();
			    String sql2  = "select * from team where team_name =  '"+team_name+"'";
                rs = stmt.executeQuery(sql2); 				
				if(rs.next())
				{
					JOptionPane.showMessageDialog(this,"Ժϵ���ƴ���","����",JOptionPane.ERROR_MESSAGE);
					this.closeConn();
					return ;
				}
			   rs.close();
			   String  sql3 = "insert into team values('"+team_id+"','"+team_name+"')";
			   int i = stmt.executeUpdate(sql3);
			   if(i==1)
			   {
				   JOptionPane.showMessageDialog(this,"��ӳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);	
			   }
			   else
			   {
				   JOptionPane.showMessageDialog(this,"���ʧ��","����",JOptionPane.ERROR_MESSAGE);   
			   }
			
			}
			catch(SQLException ea)
			{
				ea.printStackTrace();
			}
			this.closeConn();
		}
	}
	//�������ݿ�
	public void initialConnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sportmanager", "root" , "root");;
			stmt = conn.createStatement();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this,"����ʧ�ܣ����������Ƿ���ȷ","����",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	//�ر����ݿ�����
	public void closeConn()
	{  
		   try
		   {
			   if(rs!=null){rs.close();}
			   if(stmt!=null){stmt.close();}
			   if(conn!=null){conn.close();}
		   }
		   catch(SQLException e)
		   {
			   e.printStackTrace();
		   }
	}
}
