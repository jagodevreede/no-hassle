package io.github.jagodevreede.demo.views.masterdetail;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import io.github.jagodevreede.demo.nohassle.generator.MemeGenerator;
import io.github.jagodevreede.demo.nohassle.image.MemeImageRepository;
import io.github.jagodevreede.demo.nohassle.text.MemeText;
import io.github.jagodevreede.demo.nohassle.text.MemeTextRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import javax.imageio.ImageIO;

@PageTitle("Meme text")
@Route("master-detail/:MemeTextID?/:action?(edit)")
@Menu(order = 1, icon = LineAwesomeIconUrl.COLUMNS_SOLID)
@Uses(Icon.class)
public class MasterDetailView extends Div implements BeforeEnterObserver {

    private final String MemeText_ID = "MemeTextID";
    private final String MemeText_EDIT_ROUTE_TEMPLATE = "master-detail/%s/edit";

    private final Grid<MemeText> grid = new Grid<>(MemeText.class, false);

    private TextField upperText;
    private TextField lowerText;
    private TextField imageId;

    private MemeText memeText;

    private final MemeTextRepository memeTextRepository;

    public MasterDetailView(MemeTextRepository memeTextRepository) {
        this.memeTextRepository = memeTextRepository;
        addClassNames("master-detail-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("upperText").setAutoWidth(true);
        grid.addColumn("lowerText").setAutoWidth(true);
        grid.addColumn("imageId").setAutoWidth(true);

        grid.setItems(query -> {
            var pageRequest = VaadinSpringDataHelpers.toSpringPageRequest(query);
            return memeTextRepository.findAll().stream();
        });
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(MemeText_EDIT_ROUTE_TEMPLATE, event.getValue().id()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MasterDetailView.class);
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> memeTextId = event.getRouteParameters().get(MemeText_ID);
        if (memeTextId.isPresent()) {
            Optional<MemeText> memeTextFromBackend = Optional.ofNullable(memeTextRepository.findById(UUID.fromString(memeTextId.get())));
            if (memeTextFromBackend.isPresent()) {
                populateForm(memeTextFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested MemeText was not found, ID = %s", memeTextId.get()), 3000,
                        Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(MasterDetailView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        // Create an Image component to display the generated meme
        com.vaadin.flow.component.html.Image memeImage = new com.vaadin.flow.component.html.Image();
        memeImage.setAlt("Generated Meme");
        memeImage.setWidth("100%");

        // Add the Image component to the editor
        editorDiv.add(memeImage);

        splitLayout.addToSecondary(editorLayoutDiv);

        // Update the image when a new MemeText is selected
        grid.asSingleSelect().addValueChangeListener(event -> {
            MemeText selectedMemeText = event.getValue();
            if (selectedMemeText != null) {
                try {
                    // Generate the meme image
                    MemeGenerator memeGenerator = new MemeGenerator();
                    MemeImageRepository memeImageRepository = new MemeImageRepository();
                    BufferedImage image = memeImageRepository.findById(selectedMemeText.imageId()).imageData();
                    BufferedImage generatedMeme = memeGenerator.generateMeme(image, selectedMemeText.upperText(), selectedMemeText.lowerText());

                    // Convert the BufferedImage to a Base64 string
                    String base64Image = convertBufferedImageToBase64(generatedMeme);

                    // Set the Base64 image as the source for the Image component
                    memeImage.setSrc("data:image/png;base64," + base64Image);
                } catch (Exception e) {
                    Notification.show("Failed to generate meme: " + e.getMessage(), 3000, Position.MIDDLE);
                }
            } else {
                memeImage.setSrc(""); // Clear the image if no MemeText is selected
            }
        });
    }

    // Utility method to convert BufferedImage to Base64
    private String convertBufferedImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(MemeText value) {
        this.memeText = value;
    }
}
