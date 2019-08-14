package admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;
import java.util.*;
public class KeySelect extends JPanel implements ActionListener
{
	private String host;
	private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    private JLabel jl=new JLabel("���������ؼ���վ������");
    private JTextField jtf= new JTextField(); 
	private JButton jb1 = new JButton("����"); 
    private JTable jt;
    private JScrollPane jsp;
    private Vector<String> v_head = new Vector<String>();
    private Vector<Vector> v_data = new Vector<Vector>();
	public KeySelect(String host)
	{
    	this.host = host;
    	this.initialData();
    	this.initialFrame();
    	this.addListener();
	}
	public void initialData()
	{
		v_head.add("��Ŀ����");
		v_head.add("ѧ��");
		v_head.add("����");
		v_head.add("����");
		v_head.add("�Ա�");
	}
    public void initialFrame()
    {
    	this.setLayout(null);
    	jl.setBounds(20,20,200,30);
    	this.add(jl);
    	jtf.setBounds(50,50,200,30);
        this.add(jtf);
        jb1.setBounds(250,50,150,30);
        this.add(jb1);    
    }
    public void addListener()
    {
    	jb1.addActionListener(this);
    }
    //��д����
	public void actionPerformed(ActionEvent e) 
	{
	    if(e.getSource()==jb1)
	    {
	    	String key = jtf.getText().trim();   
	    	if(key.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(this,"���������Ĺؼ���","����",JOptionPane.ERROR_MESSAGE);
	    		return ;
	    	}
	    	try
	    	{
                v_data.clear();
		    	this.initialConnection();
	    		String sql = "select distinct item.item_name,student.stu_id,student.stu_name,student.age,student.sex " +
	    					 "from  grade,item,student " +
	    					 "where  " +
	    					 "( " +
	    					 	"student.stu_id=grade.stu_id and item.item_id=grade.item_id" +
	    					 ") and " +
	    					 "(" +
	    					 	"locate('"+key+"',stu_name)>0"+
	    					 ")";
                rs = stmt.executeQuery(sql);	    		
	    	    while(rs.next())
	    	    {
	    	    	 Vector<String> v = new Vector<String>();
	                 String item_name = rs.getString(1).trim();
	                 String stu_id = rs.getString(2).trim();
	                 String stu_name  = rs.getString(3).trim();
	                 String age = rs.getString(4).trim();
	                 String sex  = rs.getString(5).trim();
	                 v.add(item_name);
	                 v.add(stu_id);
	                 v.add(stu_name);
	                 v.add(age);
	                 v.add(sex);
	                 v_data.add(v);
	                 DefaultTableModel dtm = new DefaultTableModel(v_data,v_head);
	    	    	 jt = new JTable(dtm);
	    	    	 jsp = new JScrollPane(jt);
	    	    	 jsp.setBounds(20,100,650,500);
	    	    	 this.add(jsp);
	    	    }	 
                if(v_data.isEmpty())
                {
                	JOptionPane.showMessageDialog(this, "�޲�ѯ���","��ʾ",JOptionPane.ERROR_MESSAGE);
                	this.repaint();
                	return ;
                }	
                this.repaint();
	    	}
	    	catch(SQLException ea)
	    	{
	    		ea.printStackTrace();
	    	}
	    	catch(Exception ea)
	    	{
	    		ea.printStackTrace();
	    	}
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
