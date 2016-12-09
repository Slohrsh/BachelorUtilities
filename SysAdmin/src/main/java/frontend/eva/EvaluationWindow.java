package frontend.eva;

import backend.database.dbClasses.Author;
import backend.database.dbClasses.ExpertOpinion;
import backend.database.dbClasses.SecondOpinion;
import backend.database.dbClasses.Topic;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EvaluationWindow extends Stage {

	private GridPane grid = new GridPane();

	private RadioButton rbProject = new RadioButton("Projektarbeit");
	private RadioButton rbBachelor = new RadioButton("Bachelorarbeit");
	private RadioButton rbMaster = new RadioButton("Masterarbeit");

	private TextArea taProjectTitle = new TextArea();

	private TextField tfAuthorLastName = new TextField("");
	private TextField tfAuthorFirstName = new TextField("");
	private TextField tfAuthorMatrNr = new TextField("");
	private TextField tfAuthorCourse = new TextField("");

	private TextField tfSupervisorLastName = new TextField("");
	private TextField tfSupervisorFirstName = new TextField("");
	private TextArea taSupervisorEva = new TextArea();

	private TextField tfProofReaderFirstName = new TextField("");
	private TextField tfProofReaderLastName = new TextField("");
	private TextArea taProofReaderEva = new TextArea();

	private TextField tfSignings = new TextField("");

	private Button buttonOK = new Button("Generate");
	private Button buttonCancel = new Button("Abbrechen");

	private Topic topic;

	public EvaluationWindow(Topic topic, Stage primaryStage) {
		this.topic = topic;

		setTitle("Projektarbeit erstellen...");
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setWidth(670);
		setHeight(670);
		setX(primaryStage.getX() + 250);
		setY(primaryStage.getY() + 100);

		initComponents();
		setLayout();

		insertInformation();
	}

	private void initComponents() {

		grid.setPadding(new Insets(10));
		grid.setHgap(10);
		grid.setVgap(10);

		taProjectTitle.setMaxWidth(getWidth() - 130);

		buttonCancel.setOnAction(e -> close());
		buttonOK.setOnAction(e -> generateEva());
	}

	private void setLayout() {
		grid.setAlignment(Pos.CENTER);
		Label label = new Label("Gutachten");
		label.setFont(new Font("NewTimesRoman", 18));
		HBox box = new HBox(label);
		box.setAlignment(Pos.CENTER);
		grid.add(box, 0, 0, 4, 1);

		grid.add(new Label("Art der wiss. Arbeit"), 0, 1);
		grid.add(rbProject, 1, 1);
		grid.add(rbBachelor, 2, 1);
		grid.add(rbMaster, 3, 1);

		grid.add(new Label("Thema der Arbeit"), 0, 2);
		grid.add(taProjectTitle, 1, 2, 3, 3);

		grid.add(new Label("Verfasser"), 0, 5);
		grid.add(tfAuthorLastName, 1, 5);
		grid.add(tfAuthorFirstName, 2, 5);
		grid.add(tfAuthorMatrNr, 3, 5);

		grid.add(new Label("Studiengang"), 0, 6);
		grid.add(tfAuthorCourse, 1, 6, 3, 1);

		grid.add(new Label("Betreuer"), 0, 7);
		grid.add(tfSupervisorLastName, 1, 7);
		grid.add(tfSupervisorFirstName, 2, 7);

		grid.add(new Label("Beurteilung"), 0, 8);
		grid.add(taSupervisorEva, 1, 8, 3, 3);

		grid.add(new Label("Zweitkorrektor"), 0, 11);
		grid.add(tfProofReaderLastName, 1, 11);
		grid.add(tfProofReaderFirstName, 2, 11);

		grid.add(new Label("Zweitkorrektur"), 0, 12);
		grid.add(taProofReaderEva, 1, 12, 3, 3);

		grid.add(new Label("Ort, Datum, Unterschrift"), 0, 15);
		grid.add(tfSignings, 1, 15, 4, 1);

		HBox hBox = new HBox(10, buttonOK, buttonCancel);
		hBox.setAlignment(Pos.CENTER_RIGHT);
		grid.add(hBox, 0, 16, 5, 1);
		setScene(new Scene(grid, getWidth(), getHeight()));
		show();
	}

	private void insertInformation() {
		extractTopicInformation(topic);
		extractAuthorInfos(topic.getAuthor());
		extractExpertInfos(topic.getExpertOpinion());
		extractProofReaderInfos(topic.getSecondOpinion());

		buttonOK.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				generateEva();
			}
		});
	}

	private void extractTopicInformation(Topic topic) {
		String title = topic.getTitle();
		String description = topic.getDescription();
		String result = "";

		if (!title.isEmpty())
			result = result.concat(title + " \n");
		if (!description.isEmpty())
			result = result.concat(description);

		taProjectTitle.setText((title.isEmpty()) ? "Kein Titel angegeben" : result);
	}

	private void extractAuthorInfos(Author author) {
		if (null == author)
			return;

		String foreName = author.getForename();
		tfAuthorFirstName.setText((foreName.isEmpty()) ? "Kein Vorname angegeben" : foreName);

		String lastName = author.getName();
		tfAuthorLastName.setText((lastName.isEmpty()) ? "Kein Nachname angegeben" : lastName);

		Integer matrNr;
		try {
			matrNr = Integer.valueOf(author.getMatriculationNumber());
		} catch (NumberFormatException e) {
			matrNr = Integer.valueOf(0);
		}
		tfAuthorMatrNr.setText(matrNr.toString());
	}

	private void extractProofReaderInfos(SecondOpinion secondOpinion) {
		if (null == secondOpinion)
			return;

		String foreName = secondOpinion.getForename();
		tfProofReaderFirstName.setText((foreName.isEmpty()) ? "Kein Vorname angegeben" : foreName);

		String lastName = secondOpinion.getName();
		tfProofReaderLastName.setText(lastName.isEmpty() ? "Kein Nachname angegeben" : lastName);

		String opinionText = secondOpinion.getOpinion();
		taProofReaderEva.setText(lastName.isEmpty() ? "Keine Beurteilung angegeben" : opinionText);
	}

	private void extractExpertInfos(ExpertOpinion expertOpinion) {
		if (null == expertOpinion)
			return;

		String foreName = expertOpinion.getForename();
		tfSupervisorFirstName.setText((foreName.isEmpty()) ? "Kein Vorname angegeben" : foreName);

		String lastName = expertOpinion.getName();
		tfSupervisorLastName.setText(lastName.isEmpty() ? "Kein Nachname angegeben" : lastName);

		String opinionText = expertOpinion.getOpinion();
		taSupervisorEva.setText(lastName.isEmpty() ? "Keine Beurteilung angegeben" : opinionText);
	}

	private void generateEva() {
		Topic topic = validateTopicInput();
		topic.setAuthor(validateAuthorInput());
		topic.setExpertOpinion(validateExpertOpinionInput());
		topic.setSecondOpinion(validateProofReaderInput());
		topic.setFinished(1);

		// TODO Generate LibreOffice Document generateDocument(Topic)
		close();
	}

	private SecondOpinion validateProofReaderInput() {
		SecondOpinion opinion = new SecondOpinion();

		String firstName = tfProofReaderFirstName.getText();
		String lastName = tfProofReaderLastName.getText();
		String opinionText = taProofReaderEva.getText();

		opinion.setForename((firstName.isEmpty() ? "Kein Vorname angegeben" : firstName));
		opinion.setName((lastName.isEmpty() ? "Kein Nachname angegeben" : lastName));
		opinion.setOpinion(opinionText.isEmpty() ? "Keine Beurteilung angegeben" : opinionText);
		return opinion;
	}

	private ExpertOpinion validateExpertOpinionInput() {
		ExpertOpinion opinion = new ExpertOpinion();

		String firstName = tfSupervisorFirstName.getText();
		String lastName = tfSupervisorLastName.getText();
		String opinionText = taSupervisorEva.getText();

		opinion.setForename((firstName.isEmpty() ? "Kein Vorname angegeben" : firstName));
		opinion.setName((lastName.isEmpty() ? "Kein Nachname angegeben" : lastName));
		opinion.setOpinion(opinionText.isEmpty() ? "Keine Beurteilung angegeben" : opinionText);

		return opinion;
	}

	private Topic validateTopicInput() {
		String descriptionText = taProjectTitle.getText();

		topic.setDescription((descriptionText.isEmpty()) ? "Keine Beschreibung angegeben" : descriptionText);
		return topic;
	}

	private Author validateAuthorInput() {
		String firstName = tfAuthorFirstName.getText();
		String lastName = tfAuthorLastName.getText();
		int matrNr = 0;
		try {
			matrNr = Integer.parseInt(tfAuthorMatrNr.getText());
		} catch (NumberFormatException e) {

		}

		Author newAuthor = new Author();
		newAuthor.setForename((firstName.isEmpty()) ? "" : firstName);
		newAuthor.setName((lastName.isEmpty() ? "" : lastName));
		newAuthor.setMatriculationNumber(matrNr);
		return newAuthor;
	}
}