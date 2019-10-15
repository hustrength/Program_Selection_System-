package com.pss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pss.conn.Conn;
import com.pss.user.GPS;
import com.pss.user.Student;
import com.pss.dao.DaoStu;

public class DaoGPS {
	
	/**
	 * 获取所有gps
	 * @return
	 */
	public List<GPS> listAllGPS(){
		Connection conn = null;
		GPS gps=null;
		List<GPS> list  = new ArrayList<GPS>();
		try{
			
			String sql = "select* from gps";
			conn = new Conn().getConn();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				int gno=rs.getInt("GNo");
				int pno = rs.getInt("PNo");
				String gname = rs.getString("Gname");
				String pname = rs.getString("Pname");
				String sno1 = rs.getString("SNo1");
				String sno2 = rs.getString("SNo1");
				String sno3 = rs.getString("SNo1");
				int gsnum = rs.getInt("Gsnum");
				int gleftnum = rs.getInt("Gleftnum");
				Student stu1,stu2,stu3;
				DaoStu checkid = new DaoStu();
				stu1 = checkid.checkbyid(sno1);
				if(sno2!=null||sno2!=""){
					stu2 = checkid.checkbyid(sno2);
				}
				else stu2=null;
				if(sno3!=null||sno3!=""){
					stu3 = checkid.checkbyid(sno3);
				}
				else stu3=null;
				gps = new GPS(gno,pno,gname,pname,stu1,stu2,stu3,gsnum,gleftnum);
				
				list.add(gps);
				System.out.println("selected course is over!!");
			}
			
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 插入一个队伍课题学生信息（GPS）
	 * @param gps
	 * @return
	 */
	public int insertGPS(GPS gps){
		int rs = 0;
		Connection conn=null;
		try{
			String sno2,sno3;
			if(gps.getStu2()==null){
				sno2="";
			}
			else{
				sno2=gps.getStu2().getSNo();
			}
			if(gps.getStu3()==null){
				sno3="";
			}
			else{
				sno3=gps.getStu3().getSNo();
			}
			String sql_insert = "insert into gps(Gname,PNo,Pname,SNo1,SNo2,SNo2,Gsnum,Gleftnum) values(?,?,?,?,?,?,?,?);";
			conn = new Conn().getConn();
			PreparedStatement pst = conn.prepareStatement(sql_insert);
			pst.setString(1, gps.getGname());
			pst.setInt(2, gps.getPNo());
			pst.setString(3, gps.getPname());
			pst.setString(4, gps.getStu1().getSNo());
			pst.setString(5, sno2);
			pst.setString(6, sno3);
			pst.setInt(7, gps.getGsnum());
			pst.setInt(8, gps.getGleftnum());
			rs = pst.executeUpdate();
			if(rs!=0){
				System.out.println("GPS_Gname:"+gps.getGname()+"insert over!");
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return rs;
	}
	
}
