package admin;
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;
import java.util.*;
public class NewStu extends JPanel implements ActionListener
{
    private String host;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    private Map<String,String> map_team = new HashMap<String,String>();//���鼯��
    private JLabel[] jlArray = {new JLabel("ѧ��"),new JLabel("����"),new JLabel("�Ա�"),new JLabel("����"),new JLabel("����")};
    private JTextField[] jtfArray = {new JTextField(),new JTextField(),new JTextField()};
    String[] str_gender = {"��","Ů "};
    private JComboBox[] jcb = {new JComboBox(str_gender),new JComboBox()};//�Ա�����鸴ѡ������
    JButton[] jbArray = {new JButton("ȷ��"),new JButton("����")};
    public NewStu(String host)
    {
    	this.host= host;
    	this.initialData();
    	this.initialFrame();
    	this.addListener();
    }
    public void initialData()
    {
    	try
    	{
    		this.initialConnection();
    		String sql  = "select team_id,team_name from team";
    	    rs = stmt.executeQuery(sql);
    	    int i =0 ;
    	    while(rs.next())
    	    {
    	    	String team_id	= rs.getString(1).trim();
    	    	String name = rs.getString(2).trim();
    	    	if(i==0)
    	    	{
    	    		String initial_name = name;
    	    		jcb[1].setSelectedItem(initial_name);
    	    	}
    	    	jcb[1].addItem(name);
    	    	map_team.put(name, team_id);
    	    	i++;
    	    }
    	    this.closeConn();
     	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    }
    public void initialFrame()
    {
    	this.setLayout(null);
    	jlArray[0].setBounds(30,50,100,30);
    	this.add(jlArray[0]);
    	jlArray[1].setBounds(30,90,100,30);
    	this.add(jlArray[1]);
    	jlArray[2].setBounds(30,130,100,30);
    	this.add(jlArray[2]);
    	jlArray[3].setBounds(30,170,100,30);
    	this.add(jlArray[3]);
    	jlArray[4].setBounds(30,210,100,30);
    	this.add(jlArray[4]);
    	
        jtfArray[0].setBounds(150,50,200,30);
        this.add(jtfArray[0]);
        jtfArray[1].setBounds(150,90,200,30);
        this.add(jtfArray[1]);
        
        jcb[0].setBounds(150,130,70,30);
        this.add(jcb[0]);
        
        jtfArray[2].setBounds(150,170,70,30);
        this.add(jtfArray[2]);
        
        jcb[1].setBounds(150,210,115,30);
        this.add(jcb[1]);
        
        jbArray[0].setBounds(100,270,80,30);
        jbArray[1].setBounds(200,270,80,30);
        this.add(jbArray[0]);
        this.add(jbArray[1]);
    }
    public void addListener()
    {
    	jbArray[0].addActionListener(this);
    	jbArray[1].addActionListener(this);
    }
    //��д
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getSource()==jtfArray[0])
		{
			jtfArray[1].requestFocus();
		}
		else if(e.getSource()==jtfArray[1])
		{
		   jcb[0].requestFocus();	
		}
		else if(e.getSource()==jcb[0])
		{
			jtfArray[2].requestFocus();
		}
		else if(e.getSource()==jtfArray[2])
		{
			jcb[1].requestFocus();
		}
		else if(e.getSource()==jcb[1])
		{
			jbArray[0].requestFocus();
		}
		else if(e.getSource()==jbArray[1])
		{
			jtfArray[0].setText("");
			jtfArray[1].setText("");
			jtfArray[2].setText("");
		}
		else if(e.getSource()==jbArray[0])//ȷ�����ѧ��
		{
			this.submitStu(); 
		}	
	}
	//ע��ѧ��������
	public void submitStu()
	{
		 String stu_id = jtfArray[0].getText().trim();
         if(stu_id.equals(""))
         {
        	  JOptionPane.showMessageDialog(this, "������ѧ��","����",JOptionPane.ERROR_MESSAGE);
              return ;
         }
         try
         {
        	 this.initialConnection();
        	 String sql = "select * from student where stu_id = '"+stu_id+"'";
        	 rs = stmt.executeQuery(sql);
             if(rs.next())
             {
            	 JOptionPane.showMessageDialog(this, "��ѧ���Ѵ���","����",JOptionPane.ERROR_MESSAGE);
            	 this.closeConn();
            	 return ;
             }
         }
         catch(SQLException e)
         {
        	 e.printStackTrace();
         }
         String stu_name = jtfArray[1].getText().trim();
         if(stu_name.equals(""))
         {
       	    JOptionPane.showMessageDialog(this,"����������","����",JOptionPane.ERROR_MESSAGE);
            return ;	  
        }
        String stu_age = jtfArray[2].getText().trim();
        String stu_gender = ((String)jcb[0].getSelectedItem()).trim();
        String team_name = ((String)jcb[1].getSelectedItem()).trim();
        String team_id = map_team.get(team_name);
        try
        {
        	String sql  = "insert into student values('"+stu_id+"','"+stu_id+"','"+stu_name+"','"+stu_gender+"','"+stu_age+"','"+team_id+"')";
            int  i =  stmt.executeUpdate(sql);
            if(i==1)
            {
            	JOptionPane.showMessageDialog(this,"��ӳɹ�","��ȷ",JOptionPane.INFORMATION_MESSAGE);	
            }
            else
            {
            	JOptionPane.showMessageDialog(this, "���ʧ��","����",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        } 
        this.closeConn();
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

