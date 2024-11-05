package lk.ijse.controller;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dto.UserDto;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;
import lk.ijse.view.tdm.UserTm;

import java.util.List;

public class UserFormController {

    public AnchorPane rootNode;
    public TextField user_password;
    public TableColumn<UserTm, String> userPassword_col;

    @FXML
    private TableColumn<UserTm, String> userId_col;

    @FXML
    private TableColumn<UserTm, String> userName_col;

    @FXML
    private TableColumn<UserTm, String> userEmail_col;

    @FXML
    private TableColumn<UserTm, String> userRole_col;

    @FXML
    private Button userClearBtn;

    @FXML
    private Button userDeleteBtn;

    @FXML
    private Button userAddBtn;

    @FXML
    private Button userUpdateBtn;

    @FXML
    private TextField user_id;

    @FXML
    private TextField user_name;

    @FXML
    private TextField user_email;

    @FXML
    private ComboBox<String> user_role;

    @FXML
    private TableView<UserTm> user_table;

    UserBO userBO = new UserBOImpl();

    public void initialize() {
        setCellValueFactory();
        loadAllUsers(); // Call this method to load users initially

        user_table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
            }
        });

        user_id.setText(userBO.generateUserId());

        user_role.getItems().addAll("Admin", "Manager","Developer","HR Specialist", "Finance Officer", "IT Support");
    }

    private void setCellValueFactory() {
        userId_col.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userName_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        userPassword_col.setCellValueFactory(new PropertyValueFactory<>("password"));
        userRole_col.setCellValueFactory(new PropertyValueFactory<>("role"));
        userEmail_col.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadAllUsers() {
        ObservableList<UserTm> obList = FXCollections.observableArrayList();

        try {
            List<UserDto> userList = userBO.getAllUsers();

            for (UserDto userDto : userList) {
                UserTm userTm = new UserTm(
                        userDto.getUserId(),
                        userDto.getUsername(),
                        userDto.getPassword(),
                        userDto.getRole(),
                        userDto.getEmail()  // Added email here
                );

                obList.add(userTm);
            }

            user_table.setItems(obList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading users: " + e.getMessage(), ButtonType.OK).show();
        }
    }

    private void populateFields(UserTm userTm) {
        user_id.setText(userTm.getUserId());
        user_name.setText(userTm.getUsername());
        user_password.setText(userTm.getPassword());
        user_role.setValue(userTm.getRole());
        user_email.setText(userTm.getEmail());
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        // Validate each field using Regex
        boolean isUserIDValid = Regex.isTextFieldValid(TextFields.UserID, user_id.getText());
        boolean isUserNameValid = Regex.isTextFieldValid(TextFields.UserName, user_name.getText());
        boolean isUserEmailValid = Regex.isTextFieldValid(TextFields.UserEmail, user_email.getText());
        boolean isUserPasswordValid = Regex.isTextFieldValid(TextFields.UserPassword, user_password.getText());

        // Check if all fields are valid
        if (isUserIDValid && isUserNameValid && isUserEmailValid && isUserPasswordValid) {
            boolean isSaved = userBO.save(new UserDto(
                    user_id.getText(),
                    user_name.getText(),
                    user_password.getText(),
                    user_role.getValue(),
                    user_email.getText() // Added email here
            ));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Saved").show();
                loadAllUsers(); // Refresh the table
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "User Not Saved").show();
            }
        } else {
            // Show an error alert for invalid fields
            new Alert(Alert.AlertType.ERROR, "Please check your input. All fields must be valid.").show();
        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean isUpdated = userBO.update(new UserDto(
                user_id.getText(),
                user_name.getText(),
                user_password.getText(),
                user_role.getValue(),
                user_email.getText() // Added email here
        ));
        if(isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION, "User Updated").show();
            loadAllUsers(); // Refresh the table
            clearFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "User Not Updated").show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
        user_id.setText(userBO.generateUserId());
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        boolean isDeleted = userBO.delete(new UserDto(
                user_id.getText(),
                user_name.getText(),
                user_password.getText(),
                user_role.getValue(),
                user_email.getText() // Added email here
        ));
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "User Deleted").show();
            loadAllUsers(); // Refresh the table
            clearFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "User Not Deleted").show();
        }
    }

    private void clearFields() {
        user_id.setText("");
        user_name.setText("");
        user_email.setText("");
        user_role.getSelectionModel().clearSelection(); // Clear selected item from ComboBox
    }

    // Event handlers for TextField actions
    public void txtIdOnAction(ActionEvent actionEvent) {
        user_name.requestFocus();
    }

    public void txtNameOnAction(ActionEvent actionEvent) {
        user_email.requestFocus();
    }

    public void txtEmailOnAction(ActionEvent actionEvent) {
        user_role.requestFocus();
    }

    public void txtRoleOnAction(ActionEvent actionEvent) {
        user_role.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
    }
}
