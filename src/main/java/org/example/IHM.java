package org.example;

import org.example.utils.DatabaseManager;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class IHM {
    private  static Scanner scanner = new Scanner(System.in);
    public IHM() throws SQLException, ParseException {


        int choix = 0;
        do {
            System.out.println("1. Ajouter un étudiant");
            System.out.println("2. Afficher la totalité des étudiants");
            System.out.println("3. Afficher les étudiants d'une classe");
            System.out.println("4. Supprimer un étudiant");
            System.out.println("0. Quitter");
            choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    createStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    viewStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
            }
        }while(choix != 0);

    }

    public static void createStudent() throws SQLException {
        Connection connection = DatabaseManager.getPostgreSQLConnection();
        try {
            scanner.nextLine();
            System.out.println("Merci de saisir le nom :");
            String lastname = scanner.nextLine();
            System.out.println("Merci de saisir le prénom :");
            String firstname = scanner.nextLine();
            System.out.println("Merci de saisir votre numéro de class :");
            int nbclass = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Merci de saisir votre date de diplôme :");
            String str = scanner.nextLine();
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(str);
            java.sql.Date dateSql = new java.sql.Date(date.getTime());
            Student student = new Student(lastname, firstname, nbclass, dateSql);
            String request = "INSERT INTO student (first_name, last_name, nb_class, date_of_birth) VALUES (?, ?, ?, ?)";



            PreparedStatement preparedStatement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, student.getLastname());
            preparedStatement.setString(2, student.getFirstname());
            preparedStatement.setInt(3, student.getNbclass());
            preparedStatement.setDate(4,student.getDate());
            int nbRows = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                System.out.println("ID de l'étudiant est :" + resultSet.getInt(1));
            }


            if (nbRows > 0) {
                System.out.println("Des données renvoyées par la requête");
            } else {
                System.out.println("Aucune données renvoyées par la requête");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    public static void viewStudents () throws SQLException {
        Connection connection = DatabaseManager.getPostgreSQLConnection();
        String request = "SELECT * FROM student";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(request);
        while (resultSet.next()) {
            Student student = new Student(resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getInt("nb_class"), resultSet.getDate("date_of_birth")  );
            System.out.println(student.getFirstname() + " , " + student.getLastname() +
                    " , " + student.getNbclass() + " , " + student.getDate());
        }
        System.out.println();
    }

    public static void viewStudent () throws SQLException {
        Connection connection = DatabaseManager.getPostgreSQLConnection();
        System.out.println("Entrez l'identifiant :");
        int id = scanner.nextInt();
        System.out.println();
        String requestStudent = "SELECT * FROM student WHERE student.id = '" + id + "'";
        Statement statementStudent = connection.createStatement();
        ResultSet resultSetStudent = statementStudent.executeQuery(requestStudent);
        while (resultSetStudent.next()) {
            Student student = new Student(resultSetStudent.getString("first_name"), resultSetStudent.getString("last_name"), resultSetStudent.getInt("nb_class"), resultSetStudent.getDate("date_of_birth")  );
            System.out.println(student.getFirstname() + " , " + student.getLastname() +
                    " , " + student.getNbclass() + " , " + student.getDate());
            System.out.println(resultSetStudent.getInt("id") + " , " + resultSetStudent.getString("first_name") +
                    " , " + resultSetStudent.getString("last_name"));
        }
        System.out.println();
    };

    public static void deleteStudent () throws SQLException {
        Connection connection = DatabaseManager.getPostgreSQLConnection();
        try {


            System.out.println("Entrez l'identifiant :");
            int idDelete = scanner.nextInt();
            System.out.println();
            String requestUpdate = "SELECT * FROM student WHERE student.id = '" + idDelete + "'";
            Statement statementStudentUpdate = connection.createStatement();
            int resultSetUpdate = statementStudentUpdate.executeUpdate(requestUpdate);

            if (resultSetUpdate > 0) {
                System.out.println("Des données renvoyées par la requête");
            } else {
                System.out.println("Aucune données renvoyées par la requête");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    }
