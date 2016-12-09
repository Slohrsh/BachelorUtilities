package frontend.topic;

import backend.database.dbClasses.Topic;
import backend.database.dbQueries.DeleteQueries;
import backend.database.dbQueries.InsertQueries;
import backend.database.dbQueries.SearchQueries;
import frontend.LoginPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListTopicsWindow extends Stage {

	private ListView<ListTopicEntry> list;
	private Stage primaryStage;
	private DeleteQueries deleteQueries;

	public ListTopicsWindow(Stage primaryStage, SearchQueries searchQueries, InsertQueries insertQueries, DeleteQueries deleteQueries) {
		this.primaryStage = primaryStage;
		this.deleteQueries = deleteQueries;
		
		setTitle("Übersicht Projektarbeiten");
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setWidth(600);
		setHeight(500);
		setX(primaryStage.getX() + 250);
		setY(primaryStage.getY() + 100);

		list = initComponents(searchQueries, insertQueries);
		setLayout();

	}

	private void setLayout() {
		Scene scene = new Scene(list, getWidth(), getHeight());
		scene.getStylesheets().add(LoginPane.cssFile);
		setScene(scene);
		show();
	}

	private ListView<ListTopicEntry> initComponents(SearchQueries searchQueries, InsertQueries insertQueries) {
		list = new ListView<ListTopicEntry>();
		ObservableList<ListTopicEntry> items = FXCollections.observableArrayList();
	      
		for (Topic topic : searchQueries.searchAllTopics()) {
			items.add(new ListTopicEntry(topic, initEventHandlerForDetailView(topic, insertQueries), deleteQueries));
		}
		list.setItems(items);
		return list;
	}

	private EventHandler<ActionEvent> initEventHandlerForDetailView(Topic topic, InsertQueries insertQueries) {
		EventHandler<ActionEvent> detailHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				new TopicWindow(primaryStage, insertQueries, deleteQueries, topic);
			}
		};
		return detailHandler;
	}
}