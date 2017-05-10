package com.junald.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.junald.model.Student;
import com.junald.util.DBUtil;

public class StudentDAOImplemantation implements StudentDAO {
	
	private Connection conn;
	
	public StudentDAOImplemantation() {
		conn = DBUtil.getConnection();
	}
	
	

	@Override
	public void addStudent(Student student) {
		
		String query = "insert into student (firstName, lastName, course, year) values (?, ?, ?, ?) ";
		try {
			PreparedStatement preparedStatemant = conn.prepareStatement(query);
			preparedStatemant.setString(1, student.getFirstName());
			preparedStatemant.setString(2, student.getLastName());
			preparedStatemant.setString(3, student.getCourse());
			preparedStatemant.setInt(4, student.getYear());
			preparedStatemant.executeUpdate();
			preparedStatemant.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteStudent(int studentId) {
		
		String query = "delete from student where studentId = ?";
		try {
			PreparedStatement preparedStatemant = conn.prepareStatement(query);

			preparedStatemant.setInt(1, studentId);
			preparedStatemant.executeUpdate();
			preparedStatemant.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateStudent(Student student) {
		String query = "update student set firstName = ?, lastName = ?, course = ?, year = ? where studentId = ?";
		try {
			PreparedStatement preparedStatemant = conn.prepareStatement(query);
			preparedStatemant.setString(1, student.getFirstName());
			preparedStatemant.setString(2, student.getLastName());
			preparedStatemant.setString(3, student.getCourse());
			preparedStatemant.setInt(4, student.getYear());
			preparedStatemant.setInt(5, student.getStudentId());
			preparedStatemant.executeUpdate();
			preparedStatemant.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<Student>();
		
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from student");
			
			while (resultSet.next()) {
				Student student = new Student();
				student.setStudentId(resultSet.getInt("studentId"));
				student.setFirstName(resultSet.getString("firstName"));
				student.setLastName(resultSet.getString("lastName"));
				student.setCourse(resultSet.getString("course"));
				student.setYear(resultSet.getInt("year"));
				students.add(student);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	@Override
	public Student getStudentById(int studentId) {
		Student student = new Student();
		
		try {
			String query = "select * from student where studentId = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, studentId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				student.setStudentId(resultSet.getInt("studentId"));
				student.setFirstName(resultSet.getString("firstName"));
				student.setLastName(resultSet.getString("lastName"));
				student.setCourse(resultSet.getString("course"));
				student.setYear(resultSet.getInt("year"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

}
