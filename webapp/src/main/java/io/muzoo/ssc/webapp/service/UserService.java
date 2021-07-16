package io.muzoo.ssc.webapp.service;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final String INSERT_USER_SQL = "INSERT INTO tbl_user (username, password, display_name) VALUES (?,?,?);";
    private static final String SELECT_USER_SQL = "SELECT * FROM tbl_user WHERE username = ?;";
    private static final String SELECT_ALL_USER_SQL = "SELECT * FROM tbl_user;";


    private DatabaseConnectionService databaseConnectionService;

    private static UserService service;

    private UserService(){

    }

    public static UserService getInstance() {
        if (service == null){
            service = new UserService();
            service.setDatabaseConnectionService(DatabaseConnectionService.getInstance());
        }
        return service;
    }

    public void setDatabaseConnectionService(DatabaseConnectionService databaseConnectionService) {
        this.databaseConnectionService = databaseConnectionService;
    }

    public void createUser(String username, String password, String displayName) throws UserServiceException{
        try (Connection connection = databaseConnectionService.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL);){

            ps.setString(1, username);
            ps.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setString(3, displayName);
            ps.executeUpdate();
            connection.commit();

        } catch (SQLIntegrityConstraintViolationException e){
            throw new UsernameNotUniqueExpection(String.format("Username %s has already been taken.",username));
        } catch (SQLException throwables){
            throw new UserServiceException(throwables.getMessage());
        }
    }

    public User findByUsername(String username){
        try (
                Connection connection = databaseConnectionService.getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);

                ){

            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("displayName")
            );
        } catch (SQLException throwables){
            return null;
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = databaseConnectionService.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USER_SQL);){

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("displayName"))
                );
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return users;
    }

    public void deleteUserByUsername(){}


    /**
     * Users can inky change their display name when updating profile
     * @param id
     * @param displayName
     */
    public void updateUserByID(long id, String displayName){throw new UnsupportedOperationException("not implemented");}

    public void changePassword(String newPassword){throw new UnsupportedOperationException("not implemented");}

    public static void main(String[] args) {
        UserService userService = UserService.getInstance();
        try {
            userService.createUser("admin", "123456", "admin");
        }catch (UserServiceException e){
            e.printStackTrace();
        }
    }

}
