package student;
import pub.Welcome;
import javax.swing.*;
import javax.swing.tree.*;

import com.mysql.jdbc.Connection;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.Statement;
public class StuClient extends JFrame
{
     private String host;
     private String stu_id;
     //���ṹ����ѡ��
     private DefaultMutableTreeNode dmtnRoot = new DefaultMutableTreeNode(new MyNode("����ѡ��","0"));
     private DefaultMutableTreeNode dmtn1    = new DefaultMutableTreeNode(new MyNode("�˶�Ա��Ϣ����","1"));
     private DefaultMutableTreeNode dmtn4    = new DefaultMutableTreeNode(new MyNode("ϵͳѡ��","4"));
     private DefaultMutableTreeNode dmtn11   = new DefaultMutableTreeNode(new MyNode("�˶�Ա��Ϣ��ѯ","11"));
     private DefaultMutableTreeNode dmtn14   = new DefaultMutableTreeNode(new MyNode("�˶�Ա����","14"));     
     private DefaultMutableTreeNode dmtn41   = new DefaultMutableTreeNode(new MyNode("�����޸�","41"));
     private DefaultMutableTreeNode dmtn42   = new DefaultMutableTreeNode(new MyNode("�˳�","42"));
     private DefaultTreeModel dtm = new DefaultTreeModel(dmtnRoot);
     private JTree jt = new JTree(dtm);
     
     private JScrollPane jsp = new JScrollPane(jt);
     private JPanel jpy = new JPanel();
     private JSplitPane jspl = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jsp,jpy);//�ָ����
     CardLayout cl;//���ֹ�����
     
     private ChangePwdStu changepwdstu;//�޸����������
     private GetStu getstu;//��ȡѧ����Ϣ�����
     private SignUp signup;//���������
     private Welcome welcome;//��ӭ�����
     //���캯��
     public StuClient(String host,String stu_id)
     {
    	 this.host = host;
    	 this.stu_id = stu_id;
         this.initialTree();
    	 this.initialPanel();
         this.initialJpy();
         this.initialFrame();
         this.addListener();
     }
     //��ʼ��������
     public void initialTree()
     {
    	 dmtnRoot.add(dmtn1);
    	 dmtnRoot.add(dmtn4);
    	 dmtn1.add(dmtn11);
    	 dmtn1.add(dmtn14);
    	 dmtn4.add(dmtn41);
    	 dmtn4.add(dmtn42); 
     }
     //��ʼ��������
     public void initialPanel()
     {
    	 changepwdstu = new ChangePwdStu(host,stu_id,this);
    	 signup = new SignUp(host,stu_id);
    	 welcome = new Welcome();
     }
    //��ʼ�����
     public void initialJpy()
     {
    	   jpy.setLayout(new CardLayout());
    	   cl = (CardLayout)jpy.getLayout();
    	   jpy.add(welcome,"welcome");
    	   jpy.add(changepwdstu,"changepwdstu");
    	   jpy.add(signup,"signup");
     }
     //��ʼ������
     public void initialFrame()
     {
    	 this.add(jspl);
    	 jspl.setDividerLocation(200);
    	 jspl.setDividerSize(4);
    	 this.setTitle("ѧ���ͻ���");
    	 Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    	 int centerx = screensize.width/2;
    	 int centery =screensize.height/2;
    	 int w = 900;
    	 int h = 650;
    	 this.setBounds(centerx-w/2,centery-h/2-30,w,h);
    	 this.setVisible(true);
    	 this.setExtendedState(JFrame.MAXIMIZED_BOTH);
     }
     //������
     public void addListener()
     {
    	 
    	 //����������������
    	 jt.addMouseListener
    	 (
    			 new MouseAdapter()
    			 {
					public void mouseClicked(MouseEvent e) 
					{
				        DefaultMutableTreeNode dmtntemp  =(DefaultMutableTreeNode)jt.getLastSelectedPathComponent();
				        MyNode myNode = (MyNode)dmtntemp.getUserObject();
				        String id = myNode.getId();
					    if(id.equals("0"))
					    {
	                        cl.show(jpy, "welcome");				       
					    }
					    else if(id.equals("42"))
					    {
		                      int i = JOptionPane.showConfirmDialog(jpy,"��ȷ���˳�?","ѯ��",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);			    	
					          if(i==0)
					          {
					        	  System.exit(0);
					          }  
					    }
					    else if(id.equals("41"))
					    {
					         cl.show(jpy,"changepwdstu");
		                }
					    else if(id.equals("11"))
					    {
		                	  getstu =  new GetStu(host,stu_id);
		                  	  jpy.add(getstu,"getstu");
		                      cl.show(jpy, "getstu");
					    }
					    else if(id.equals("14"))
					    {
					    	  cl.show(jpy, "signup");
					    }
				   }
    				 
    			 }
    	 );
     }
     //�������ڵ�
     class MyNode
     {
		 private String id;
		 private String values;
		 public MyNode(String values,String id)
		 {
			 this.values = values;
			 this.id = id;
		 }
		 public String toString()
		 {
			 return this.values;
		 }
		 public String getId()
		 {
			 return this.id;
		 }
     }
}
