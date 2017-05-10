package com.junald.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.junald.dao.StudentDAO;
import com.junald.dao.StudentDAOImplemantation;
import com.junald.model.Student;

@WebServlet("/StudentController")
public class StudentController extends HttpServlet {

	private StudentDAO dao;
	private static final long serialVersionUID = 1L;
	public static final String LIST_STUDENT = "/listStudent.jsp";
	public static final String INSERT_OR_EDIT = "/student.jsp";

	public StudentController() {
		dao = new StudentDAOImplemantation();
	}

	/**
	 * 画面を開く
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String forward = "";
		// クエリーストリングから値を取得（delete edit insert ）
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("delete")) {
			forward = LIST_STUDENT;
			// リクエストからIDを取得
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			// 削除実行
			dao.deleteStudent(studentId);
			// 一覧を取得して、リクエストにセットする
			request.setAttribute("students", dao.getAllStudents());

		} else if (action.equalsIgnoreCase("edit")) {
			forward = INSERT_OR_EDIT;
			// リクエストからIDを取得
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			// 1件取得
			Student student = dao.getStudentById(studentId);
			// 一覧を取得して、リクエストにセットする
			request.setAttribute("student", student);

		} else if (action.equalsIgnoreCase("insert")) {
			forward = INSERT_OR_EDIT;

		} else {
			forward = LIST_STUDENT;
			// 一覧を取得して、リクエストにセットする
			request.setAttribute("students", dao.getAllStudents());
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = new Student();
		student.setFirstName(request.getParameter("firstName"));
		student.setLastName(request.getParameter("lastName"));
		student.setCourse(request.getParameter("course"));
		student.setYear(Integer.parseInt(request.getParameter("year")));
		String studentId = request.getParameter("studentId");

		if (studentId == null || studentId.isEmpty()) {
			// 新規登録
			dao.addStudent(student);
		} else {
			// 上書き
			student.setStudentId(Integer.parseInt(studentId));
			dao.updateStudent(student);
		}
		RequestDispatcher view = request.getRequestDispatcher(LIST_STUDENT);
		request.setAttribute("students", dao.getAllStudents());
		view.forward(request, response);
	}

}
