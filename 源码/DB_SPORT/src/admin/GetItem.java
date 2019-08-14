package admin;
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.*;
public class GetItem extends JPanel implements ActionListener
{
    private String host;

    private Connection conn;
    private Statement stmt;
	private ResultSet rs;
	
	private JTextField[] jtfArray = {new JTextField(),new JTextField()};
	private JComboBox jcb = new JComboBox();

	private JTable jt;
    private JScrollPane jsp;
    private JTable jt2;
    private JScrollPane jsp2;
    private Vector<String> v_head = new Vector<String>();//��ͷ����
    private Vector<Vector> v_data = new Vector<Vector>();//����������
    private Vector<String> v_itemid = new Vector<String>();//��Ŀ������
    private Map<String,String> map_cate = new HashMap<String,String>();//��Ŀ�༯��
    private String selectCate_id = "21";
    public GetItem(String host)
    {
    	this.host = host;
        this.initialFrame();
        this.addListener();
    }
    public void initialData()
    {
    	v_head.clear();
    	v_data.clear();
    	v_itemid.clear();
    	v_head.add("��Ŀ��");
    	v_head.add("��Ŀ����");
    	v_head.add("����ʱ��");
    	v_head.add("����");
    	v_head.add("������һ������");
    	v_head.add("�ص�");
    	try
    	{
    		String sql  = "select * from item where cate_id = '"+map_cate.get(jcb.getSelectedItem())+"' order by item_id";
    		rs = stmt.executeQuery(sql);
    	    while(rs.next())
    	    {
    	    	String item_id =  rs.getString(1).trim();
    	        String item_name = rs.getString(2).trim();
    	        String item_time  = rs.getString(3);
    	        String rule = rs.getString(4);
    	        String promotion_num = rs.getString(5);
    	        String place = rs.getString(6);
    	        	
    	        Vector<String> v = new Vector<String>();
    	        	v.add(item_id);
    	        	v.add(item_name);
    	        	v.add(item_time);
    	        	v.add(rule);
    	        	v.add(promotion_num);
    	        	v.add(place);
    	        v_data.add(v);
    	        v_itemid.add(item_id);
    	    }
    	    rs.close();
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	catch(NullPointerException e)
    	{
    		e.printStackTrace();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	this.closeConn();
    }
    public void initialFrame()
    {
    	 this.setLayout(null);
    	 this.initialConnection();
    	 try
    	 {
    		 String  sql = "select * from  category ";
    	     rs  =  stmt.executeQuery(sql);
    	     jcb.removeAllItems();
    	     while(rs.next())
    	     {
    	    	 String	cate_id = rs.getString(1).trim();
    	    	 String cate_name = rs.getString(2).trim();
    	    	 map_cate.put(cate_name, cate_id);
    	         jcb.addItem(cate_name);        
    	     }
    	     jcb.setSelectedItem(selectCate_id);//ѡ����Ŀ����
    	     this.initialData();
    	 }
    	 catch(SQLException e)
    	 {
    		 e.printStackTrace();
    	 }
    	 catch(Exception e)
    	 {
    		 e.printStackTrace();
    	 }
    	 jcb.setBounds(20,13,100,30);
    	 this.add(jcb);
    	 DefaultTableModel dtm = new DefaultTableModel(v_data,v_head);
    	 jt = new JTable(dtm);
    	 jsp = new JScrollPane(jt);
    	 jsp.setBounds(20,60,650,600);
    	 this.add(jsp);
    	

    }
    public void addListener()
    {
    	jcb.addActionListener(this);
    }
	//��дactionPerformed()����
	public void actionPerformed(ActionEvent e) 
	{
		//ѡ����Ŀ�����
		if(e.getSource()==jcb)
		{
			selectCate_id = (String)jcb.getSelectedItem();
			this.removeAll();
			this.initialFrame();
			this.repaint();
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
