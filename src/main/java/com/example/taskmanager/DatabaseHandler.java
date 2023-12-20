package com.example.taskmanager;

import com.example.taskmanager.models.Project;
import com.example.taskmanager.models.ProjectTask;
import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private Connection connection;
    private DatePicker datePicker;
    private TextArea taskTextArea;
    private ListView<String> taskListView;
    private User user;

    public Connection getDbConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
            return connection;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void createUserTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS `User` ("
                + "user_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(255), "
                + "password VARCHAR(255), "
                + "email VARCHAR(255) "
                + ") CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;";

        try (Statement statement = getDbConnection().createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createTaskTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Task ("
                + "task_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(255), "
                + "description TEXT, "
                + "date DATE, "
                + "status VARCHAR(255),"
                + "user_id INT,"
                + "FOREIGN KEY (user_id) REFERENCES User(user_id)"
                + ")";

        try (Statement statement = getDbConnection().createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createProjectTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Project ("
                + "project_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(255), "
                + "description TEXT, "
                + "user_id INT,"
                + "FOREIGN KEY (user_id) REFERENCES User(user_id)"
                + ")";

        try (Statement statement = getDbConnection().createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createProjectWorkTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS ProjectTask ("
                + "ptask_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(255), "
                + "description TEXT, "
                + "date_start Date,"
                + "date_end Date,"
                + "status VARCHAR(255),"
                + "project_id int,"
                + "FOREIGN KEY (project_id) REFERENCES Project(project_id)"
                + ")";

        try (Statement statement = getDbConnection().createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User loginUser(String email,String password){
        String selectQuery = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_EMAIL + " = ? AND " + Const.USERS_PASSWORD + " = ?";

        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(selectQuery)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(Const.USERS_ID));
                user.setName(resultSet.getString(Const.USERS_NAME));
                user.setEmail(resultSet.getString(Const.USERS_EMAIL));
                user.setPassword(resultSet.getString(Const.USERS_PASSWORD));
                return user;
            } else {
                return null; // Если вход не выполнен
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Если произошла ошибка
        }
    }
    public void signUpUser(String name,String password,String email) throws RegistrationException{
        String insert= "INSERT INTO "+ Const.USERS_TABLE + "("+Const.USERS_NAME + "," + Const.USERS_EMAIL+ "," +Const.USERS_PASSWORD+ ")" + "VALUES(?,?,?)";

        try {
            PreparedStatement prSt=getDbConnection().prepareStatement(insert);
            prSt.setString(1,name);
            prSt.setString(2,password);
            prSt.setString(3,email);
            int rowsAffected = prSt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RegistrationException("Registration failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void saveProjectToDatabase(String title,String description,User user){
        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement("INSERT INTO Project (title,description,user_id) VALUES (?,?,?)")) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    void saveProjectTaskToDatabase(String title, String description, Date date_start, Date date_end, String status, Project project) {
//        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement("INSERT INTO Task (title,description,date_start,date_end,status,project_id) VALUES (?,?,?,?,?,?)")) {
//            preparedStatement.setString(1, title);
//            preparedStatement.setString(2, description);
//            preparedStatement.setDate(3, date_start);
//            preparedStatement.setDate(4, date_end);
//            preparedStatement.setString(5, status);
//            preparedStatement.setInt(6, project.getProject_id());
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    void saveProjectTaskToDatabase(ProjectTask projectTask) {
        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement("INSERT INTO ProjectTask (title, description, date_start, date_end, status, project_id) VALUES (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, projectTask.getTitle());
            preparedStatement.setString(2, projectTask.getDescription());
            preparedStatement.setDate(3, projectTask.getDate_start());
            preparedStatement.setDate(4, projectTask.getDate_end());
            preparedStatement.setString(5, projectTask.getStatus());
            preparedStatement.setInt(6, projectTask.getProject().getProject_id());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void saveTaskToDatabase(String title, String description,Date date,String status,User user) {
        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement("INSERT INTO Task (title,description,date,status,user_id) VALUES (?, ?,?,?,?)")) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setDate(3, date);
            preparedStatement.setString(4, status);
            preparedStatement.setInt(5, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public List<Project> loadProjectsFromDatabase(User user) {
//        List<Project> projects = new ArrayList<>();
//
//        try (Statement statement = getDbConnection().createStatement()) {
//            String query = "SELECT title FROM Project WHERE user_id = ?";
//            try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(query)) {
//                preparedStatement.setInt(1, user.getId());
//
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    while (resultSet.next()) {
//                        String title = resultSet.getString("title");
//                        Project project = new Project(title);
//                        projects.add(project);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return projects;
//    }
public List<Project> loadProjectsFromDatabase(User user) {
    List<Project> projects = new ArrayList<>();

    try (Connection connection = getDbConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT p.project_id, p.title, t.ptask_id, t.description, t.status " +
                         "FROM Project p " +
                         "LEFT JOIN ProjectTask t ON p.project_id = t.project_id " +
                         "WHERE p.user_id = ?")) {
        preparedStatement.setInt(1, user.getId());

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int projectId = resultSet.getInt("project_id");
                String title = resultSet.getString("title");

                // Check if the project already exists in the list
                Project project = projects.stream()
                        .filter(p -> p.getProject_id() == projectId)
                        .findFirst()
                        .orElseGet(() -> {
                            Project newProject = new Project(projectId, title);
                            projects.add(newProject);
                            return newProject;
                        });

                // Load tasks for the project
                if (resultSet.getInt("ptask_id") > 0) {
                    int taskId = resultSet.getInt("ptask_id");
                    String title1=resultSet.getString("title");
                    String description = resultSet.getString("description");
                    String status = resultSet.getString("status");
                    ProjectTask task = new ProjectTask(taskId,title1,status,description);
                    project.addTask(task);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return projects;
}

//    private List<ProjectTask> loadTasksForProject(int projectId) {
//        List<ProjectTask> tasks = new ArrayList<>();
//
//        try (Connection connection = getDbConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement("SELECT title, description, status,date_start,date_end FROM ProjectTask WHERE project_id = ?")) {
//            preparedStatement.setInt(1, projectId);
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
////                    int taskId = resultSet.getInt("ptask_id");
//                    String title=resultSet.getString("title");
//                    String description = resultSet.getString("description");
//                    String status = resultSet.getString("status");
//                    Date date_start = Date.valueOf(resultSet.getString("date_start"));
//                    Date date_end = Date.valueOf(resultSet.getString("date_end"));
//                    ProjectTask task = new ProjectTask(title, description, status,date_start,date_end);
//                    tasks.add(task);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return tasks;
//    }
    public List<Task> loadTasksFromDatabase(Date selectedDate, User user) {
        List<Task> tasks = new ArrayList<>();

        // Здесь напишите код для выполнения запроса к базе данных
        // и выборки задач по выбранной дате
        // Пример (не забудьте адаптировать под вашу структуру данных):
        try (Statement statement = getDbConnection().createStatement()) {
            String query = "SELECT title, status, description,date FROM Task WHERE date = ? AND user_id = ?";
            try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(query)) {
                preparedStatement.setDate(1, selectedDate);
                preparedStatement.setInt(2, user.getId());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String title = resultSet.getString("title");
                        String status = resultSet.getString("status");
                        String description = resultSet.getString("description");
                        Date date = Date.valueOf(resultSet.getString("date"));
                        Task task = new Task(title, status, description,date);
                        tasks.add(task);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    //    List<String> loadTasksFromDatabase() {
//        try (Statement statement = getDbConnection().createStatement()) {
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM Task");
//            while (resultSet.next()) {
//                String date = resultSet.getString("date");
//                String task = resultSet.getString("title");
//                String taskEntry = date + ": " + task;
//                taskListView.getItems().add(taskEntry);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public class RegistrationException extends Exception {
        public RegistrationException(String message) {
            super(message);
        }
    }

    public void closeDbConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void executeQuery(String sql) throws SQLException {
        try (Connection connection = getDbConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw e;
        }
    }
    public static void main(String[] args) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.createTaskTable();
        dbHandler.createUserTable();
        dbHandler.createProjectTable();
        dbHandler.createProjectWorkTable();

    }

}

