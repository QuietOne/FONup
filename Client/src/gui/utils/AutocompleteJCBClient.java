package gui.utils;

import domain.Client;
import game.logic.Controller;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 *
 * JComboBox with an autocomplete drop down menu. This class is hard-coded for
 * Client objects, but can be altered into a generic form to allow for any
 * searchable item.
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public class AutocompleteJCBClient extends JComboBox<Client> {

    private final Client client = new Client();
    /**
     * Constructs a new object based upon the parameter searchable
     */
    public AutocompleteJCBClient() {
        super();
        setEditable(true);
        Component c = getEditor().getEditorComponent();
        if (c instanceof JTextComponent) {
            final JTextComponent tc = (JTextComponent) c;
            tc.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent arg0) {
                }

                @Override
                public void insertUpdate(DocumentEvent arg0) {
                    update();
                }

                @Override
                public void removeUpdate(DocumentEvent arg0) {
                    update();
                }

                public void update() {
                    //perform separately, as listener conflicts between the editing component
                    //and JComboBox will result in an IllegalStateException due to editing 
                    //the component when it is locked. 
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            List<Client> founds = Controller.getInstance().autocompleteClient(tc.getText());;
                            setEditable(false);
                            removeAllItems();

                            boolean found = false;
                            for (Client client : founds) {
                                if (tc.getText().equals(client.getUsername())) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                client.setUsername(tc.getText());
                                addItem(client);
                            }

                            
                            for (Client s : founds) {
                                addItem(s);
                            }
                            setEditable(true);
                            setPopupVisible(true);
                            requestFocus();
                        }
                    });
                }
            });
            //When the text component changes, focus is gained 
            //and the menu disappears. To account for this, whenever the focus
            //is gained by the JTextComponent and it has searchable values, we show the popup.
            tc.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent arg0) {
                    if (tc.getText().length() > 0) {
                        setPopupVisible(true);
                    }
                }

                @Override
                public void focusLost(FocusEvent arg0) {
                }
            });

        } else {
            throw new IllegalStateException("Editing component is not a JTextComponent!");
        }
    }
}
