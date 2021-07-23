package cn.seu.dao;

import java.util.*;
import java.sql.*;

public interface StudentDao {
  public Integer addUser(String [] args);

  public Integer deleteUser(String condition_name, String condition);

  public Integer updateUser(String [] args);

  public ArrayList<ArrayList<String>> searchOneUser(String condition_name, String condition);

  public ArrayList<String> searchAllUser(String column);
}