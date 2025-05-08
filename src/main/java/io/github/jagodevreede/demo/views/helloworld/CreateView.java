package io.github.jagodevreede.demo.views.helloworld;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.jagodevreede.demo.nohassle.image.MemeImage;
import io.github.jagodevreede.demo.nohassle.image.MemeImageRepository;
import io.github.jagodevreede.demo.nohassle.text.MemeText;
import io.github.jagodevreede.demo.nohassle.text.MemeTextRepository;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@PageTitle("Create")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class CreateView extends VerticalLayout {

    public CreateView(MemeTextRepository memeTextRepository, MemeImageRepository memeImageRepository) {
        TextField upperTextField = new TextField("Upper Text");
        TextField lowerTextField = new TextField("Lower Text");
        ComboBox<String> imageIdComboBox = new ComboBox<>("Select Image");
        List<MemeImage> memeImages = memeImageRepository.findAll();
        List<String> imageIds = memeImages.stream().map(MemeImage::id).collect(Collectors.toList());
        imageIdComboBox.setItems(imageIds);

        Button createMemeButton = new Button("Create Meme");

        createMemeButton.addClickListener(event -> {
            String upperText = upperTextField.getValue();
            String lowerText = lowerTextField.getValue();
            String imageId = imageIdComboBox.getValue();

            memeTextRepository.save(new MemeText(UUID.randomUUID(), upperText, lowerText, imageId));

            Notification.show("Meme created!");
        });

        add(upperTextField, lowerTextField, imageIdComboBox, createMemeButton);
    }

}
