package cn.seu.Impl;

import cn.seu.dao.BaseDao;
import cn.seu.dao.StudentDao;
import java.util.*;
import java.text.*;
import java.sql.*;

public class CourseImpl implements StudentDao, BaseDao {
  private HashMap<String, String> status = new HashMap<String, String>();
  Connection connection;
  private static String database = "mysql";
  private static String password = "123456";
  private static String CourseTableName = "tblcourse";

  public CourseImpl() {
    status.put("必修", "1");
    status.put("选修", "2");
  }

  /**
   * 
   * @param dateString 输入的日期，格式为"YYYY-MM-DD"
   * @return 返回mysql特定格式的日期
   * @throws ParseException
   */
  public java.sql.Date stringToSqlDate(String dateString) throws ParseException {
    if (dateString.equals("null"))
      return null;
    String str = dateString;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date d = null;
    try {
      d = format.parse(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    java.sql.Date date = new java.sql.Date(d.getTime());
    return date;
  }

  public String getDate(Integer year, Integer month, Integer day) {
    String y = Integer.toString(year);
    String m = Integer.toString(month);
    String d = Integer.toString(day);
    return y + "-" + m + "-" + d;
  }

  /**
   * 创建和数据库的连接
   */
  public void getConnection() {
    try {
      // 加载及注册JDBC驱动程序
      Class.forName("com.mysql.cj.jdbc.Driver");

      // 创建JDBC连接
      String dbURL = "jdbc:mysql://localhost:3306/" + database + "?user=root&password=" + password;
      connection = DriverManager.getConnection(dbURL);

    } catch (ClassNotFoundException e) {
      System.out.println("无法找到驱动类");

    } catch (Exception e) {
      e.printStackTrace();

    }
  }

  /**
   * @param args 课程的所有信息(0：课程id，1：课程名称，2：老师名字，3：星期几，4：起始时间，5：结束时间，6：状态，7：现选人数，8：最大人数)；
   *             其中，在状态中，1代表必修，2代表选修；
   * 
   * @return 若返回1则代表添加用户信息成功，若返回-1代表添加信息失败
   */
  public Integer addUser(String[] args) {
    for (String i : args) {
      System.out.print(i);
      System.out.print(",");
    }
    System.out.println(" ");

    String course_id, course_name, teacher_name, status;
    int time, start_time, end_time, now_num, max_num;

    course_id = args[0];
    course_name = args[1];
    teacher_name = args[2];
    time = Integer.parseInt(args[3]);
    start_time = Integer.parseInt(args[4]);
    end_time = Integer.parseInt(args[5]);
    status = args[6];
    now_num = Integer.parseInt(args[7]);
    max_num = Integer.parseInt(args[8]);

    PreparedStatement pstmt = null;
    try {
      getConnection();
      String sql = "INSERT INTO " + CourseTableName
          + "(course_id,course_name,teacher_name,time,start_time,end_time,status,now_num,max_num) VALUES(?,?,?,?,?,?,?,?,?)";
      pstmt = connection.prepareStatement(sql);

      pstmt.setString(1, course_id);
      pstmt.setString(2, course_name);
      pstmt.setString(3, teacher_name);
      pstmt.setInt(4, time);
      pstmt.setInt(5, start_time);
      pstmt.setInt(6, end_time);
      pstmt.setString(7, status);
      pstmt.setInt(8, now_num);
      pstmt.setInt(9, max_num);

      pstmt.addBatch();
      pstmt.clearParameters();

      pstmt.executeBatch();
      pstmt.clearBatch();
      return 1;
    } catch (BatchUpdateException e) {
      System.out.println("This record is existing in the table!");
      return -1;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    } finally {
      try {
        pstmt.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 根据索引信息删除指定用户
   * 
   * @param condition_name 需要删除的索引
   * @param condition      索引值
   * @return 若返回1则代表删除该用户成功，若返回-1则代表删除该用户失败
   */
  public Integer deleteUser(String condition_name, String condition) {
    Statement statement = null;
    try {
      getConnection();

      boolean isInt = false;
      if (condition_name.equals("time") || condition_name.equals("max_num") || condition_name.equals("start_time")
          || condition_name.equals("end_time")) {
        isInt = true;
      }

      statement = connection.createStatement();
      String sql = null;
      if (isInt)
        sql = "DELETE FROM " + CourseTableName + " WHERE " + condition_name + "=" + condition;
      else
        sql = "DELETE FROM " + CourseTableName + " WHERE " + condition_name + "=" + "'" + condition + "'";
      statement.executeUpdate(sql);
      return 1;
    } catch (SQLException e) {
      System.out.println("输入数据格式错误");
      return -1;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    } finally {
      try {
        statement.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于修改用户再数据库中的信息
   * 
   * @param args 课程的所有信息(0：课程id，1：课程名称，2：老师名字，3：星期几，4：起始时间，5：结束时间，6：状态，7：现选人数，8：最大人数)；
   *             其中，在状态中，1代表必修，2代表选修；
   * 
   * @return 若返回1则代表修改用户信息成功，若返回-1代表修改信息失败
   */
  public Integer updateUser(String[] args) {
    for (String i : args) {
      System.out.print(i);
      System.out.print(",");
    }
    System.out.println(" ");

    String course_id, course_name, teacher_name, status;
    int time, start_time, end_time, now_num, max_num;

    course_id = args[0];
    course_name = args[1];
    teacher_name = args[2];
    time = Integer.parseInt(args[3]);
    start_time = Integer.parseInt(args[4]);
    end_time = Integer.parseInt(args[5]);
    status = args[6];
    now_num = Integer.parseInt(args[7]);
    max_num = Integer.parseInt(args[8]);

    PreparedStatement pstmt = null;
    try {
      getConnection();
      String sql = "UPDATE " + CourseTableName
          + " SET course_id=?,course_name=?,teacher_name=?,time=?,start_time=?,end_time=?,status=?,now_num=?,max_num=?"
          + " WHERE course_id=" + course_id;
      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, course_id);
      pstmt.setString(2, course_name);
      pstmt.setString(3, teacher_name);
      pstmt.setInt(4, time);
      pstmt.setInt(5, start_time);
      pstmt.setInt(6, end_time);
      pstmt.setString(7, status);
      pstmt.setInt(8, now_num);
      pstmt.setInt(9, max_num);

      pstmt.executeUpdate();
      return 1;
    } catch (SQLException e) {
      System.out.println("该用户不存在");
      return -1;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    } finally {
      try {
        pstmt.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于查找符合要求的所有信息
   * 
   * @param condition_name 需要查询的索引
   * @param condition      索引值
   * @return 返回为符合该索引值的所有信息，包括课程id，课程名字等等， 服务端根据客户端的需求自行选择信息返回(注意：下标从0开始！！)；
   *         每一行的信息为（0：课程id，1：课程名称，2：老师名字，3：星期几，4：起始时间，5：结束时间，6：状态，7：现选人数，8：最大人数）
   */
  public ArrayList<ArrayList<String>> searchOneUser(String condition_name, String condition) {
    Statement statement = null;
    ResultSet rs = null;
    ResultSetMetaData rsmd = null;
    ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
    ArrayList<String> temp = null;
    boolean isInt = false;

    try {
      getConnection();
      String sql = null;
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      if (condition_name.equals("null") && condition.equals("null")) {
        sql = "SELECT* FROM " + CourseTableName;
      } else { // 如果两个参数不为空
        if (condition_name.equals("authority") || condition_name.equals("age") || condition_name.equals("id")
            || condition_name.equals("sex")) {
          isInt = true;
        }

        if (isInt)
          sql = "SELECT* FROM " + CourseTableName + " WHERE " + condition_name + "=" + condition;
        else
          sql = "SELECT* FROM " + CourseTableName + " WHERE " + condition_name + " LIKE" + "'%" + condition + "%'";
      }
      rs = statement.executeQuery(sql);
      rsmd = rs.getMetaData();
      while (rs.next()) {
        temp = new ArrayList<String>();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
          if (i == 7)
            temp.add(status.get(rs.getString(i)));
          else
            temp.add(rs.getString(i));
        }
        al.add(temp);
      }
      return al;
    } catch (Exception e) {
      e.printStackTrace();
      return al;
    } finally {
      try {
        statement.close();
        rs.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于查找指定列的所有课程信息
   * 
   * @param column 用于指定列
   * @return 返回所有用户的指定列信息(注意：下标从0开始!!)
   */
  public ArrayList<String> searchAllUser(String column) {
    System.out.println(column);
    Statement statement = null;
    ResultSet rs = null;
    ArrayList<String> al = new ArrayList<String>();
    try {
      getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT* FROM " + CourseTableName;
      rs = statement.executeQuery(sql);
      while (rs.next()) {
        al.add(rs.getString(column));
      }
      return al;
    } catch (SQLException e) {
      System.out.println("该列不存在");
      e.printStackTrace();
      return al;
    } catch (Exception e) {
      e.printStackTrace();
      return al;
    } finally {
      try {
        statement.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于学生或者老师添加课程
   * 
   * @param stu_id 学生或老师一卡通
   * @param cou_id 课程id
   * 
   * @return 如果添加成功则返回1，否则返回-1
   */
  public synchronized Integer addCourse(String stu_id, String cou_id) {
    String id, course_id;
    id = stu_id;
    course_id = cou_id;

    ArrayList<String> result = null;
    PreparedStatement pstmt = null;
    try {
      getConnection();

      result = searchOneUser("course_id", course_id).get(0);
      Integer now_temp = Integer.parseInt(result.get(7)) + 1;
      Integer max_temp = Integer.parseInt(result.get(8));
      if (now_temp.intValue() >= max_temp.intValue() + 1) {
        return -1;
      }

      else {

        String[] temp = new String[9];
        for (int i = 0; i < 9; i++) {
          temp[i] = result.get(i);
        }
        temp[7] = now_temp.toString();
        updateUser(temp);

        String sql = "INSERT INTO tblprivate_course" + "(id,course_id) VALUES(?,?)";
        pstmt = connection.prepareStatement(sql);

        pstmt.setString(1, id);
        pstmt.setString(2, course_id);

        pstmt.addBatch();
        pstmt.clearParameters();

        pstmt.executeBatch();
        pstmt.clearBatch();

        return 1;
      }
    } catch (BatchUpdateException e) {
      System.out.println("This record is existing in the table!");
      return -1;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    } finally {
      try {
        pstmt.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于学生或者老师删除课程
   * 
   * @param stu_id 学生或老师的一卡通号
   * @param cou_id 课程id
   * @return 若删除成功则返回1，否则返回-1
   */
  public Integer deleteCourse(String stu_id, String cou_id) {
    String id, course_id;
    id = stu_id;
    course_id = cou_id;

    ArrayList<String> result = null;
    PreparedStatement pstmt = null;
    try {
      getConnection();

      result = searchOneUser("course_id", course_id).get(0);
      Integer now_temp = Integer.parseInt(result.get(7)) - 1;

      String[] temp = new String[9];
      for (int i = 0; i < 9; i++) {
        temp[i] = result.get(i);
      }
      temp[7] = now_temp.toString();
      updateUser(temp);

      String sql = "DELETE FROM tblprivate_course WHERE id=" + id + " AND course_id=" + "'" + course_id + "'";
      pstmt = connection.prepareStatement(sql);

      pstmt.executeUpdate();

      return 1;
    } catch (BatchUpdateException e) {
      System.out.println("This record is existing in the table!");
      return -1;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    } finally {
      try {
        pstmt.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 用于搜索一个学生所选的所有课程
   * 
   * @param n 学生所选所有课程的id号，不定参数
   * @return 该学生所有课程的二维数组
   */
  public ArrayList<ArrayList<String>> searchCourse(String... n) {
    Statement statement = null;
    ResultSet rs = null;
    ResultSetMetaData rsmd = null;
    ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
    ArrayList<String> temp = null;

    try {
      getConnection();
      String subsql = null, sql = null;
      for (String data : n) {
        subsql += "'" + data + "',";
      }
      subsql = subsql.substring(4, subsql.length() - 1);
      System.out.println(subsql);

      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      if (subsql != null)
        sql = "SELECT* FROM " + CourseTableName + " WHERE course_id IN(" + sql + ")";
      else
        return null;
      rs = statement.executeQuery(sql);
      rsmd = rs.getMetaData();
      while (rs.next()) {
        temp = new ArrayList<String>();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
          if (i == 7)
            temp.add(status.get(rs.getString(i)));
          else
            temp.add(rs.getString(i));
        }
        al.add(temp);
      }
      return al;
    } catch (Exception e) {
      e.printStackTrace();
      return al;
    } finally {
      try {
        statement.close();
        rs.close();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  public static void main(String[] args) {
    CourseImpl cl = new CourseImpl();
    // cl.searchCourse("woaini", "?????", "woainima?");
    String[] temp = { "asdd", "fafaf", "dsad" };
    for (String a : temp) {
      System.out.println(a);
    }
  }
}
