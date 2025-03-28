package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class DeleteUser implements EventHandler<ActionEvent> {

    TextField tF;
    int id;

    public DeleteUser(TextField tF) {
        this.tF = tF;
    }
    public DeleteUser(int id) {
        this.id = id;
    }

    public void handle(ActionEvent event) {
        if (tF != null) {
            id = Integer.parseInt(tF.getText());
        }

        deleteUser(id,true);

    }

    public void deleteUser(int id, boolean confirmation) {

        DNode<User> user = Main.getUserFromID(id);
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The user with id "+id+" does not exists");
            alert.showAndWait();
            return;
        }

        boolean ok = true;
        if (confirmation) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the user with id "+id+
                    " ?, this will delete all his posts.");
            ok = alert.showAndWait().get() == ButtonType.OK;
        }
        if (ok) {

            //To delete all his friends and posts from and to them
            DNode<User> curr = user.getData().getFriends().getHead().getNext();
            while (curr != user.getData().getFriends().getHead()){
                DeleteFriend.deleteFriend(id,curr.getData().getId(),false);
                curr = curr.getNext();
            }

            //To delete the user from the list
            Main.userList.delete(user.getData());

        }

    }
}
