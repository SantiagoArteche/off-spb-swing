package off_assoc.swing.gui;


import off_assoc.swing.model.Client;
import off_assoc.swing.service.ClientService;
import off_assoc.swing.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Component
public class OffForm extends JFrame {
    private JPanel principalPanel;
    private JTable clientsTable;
    private JTextField textFieldName;
    private JTextField textFieldLastName;
    private JTextField textFieldMembership;
    private JButton saveButton;
    private JButton cleanButton;
    private JButton deleteButton;
    IClientService clientService;
    private DefaultTableModel tableClientModel;
    private Integer idClient;

    @Autowired
    public OffForm(ClientService clientService){
        this.clientService = clientService;
        init();
        deleteButton.addActionListener(e -> {
            deleteClient();
        });
        saveButton.addActionListener(e -> {
            createClient();
        });
        cleanButton.addActionListener(e -> {
            cleanForm();
        });
        clientsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadClient();
            }
        });
    }

    private void init(){
        setContentPane(principalPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        this.tableClientModel = new DefaultTableModel(0,4);
        String[] heads = {"Id", "Name", "Last Name", "Membership"};
        this.tableClientModel.setColumnIdentifiers(heads);
        this.clientsTable = new JTable(this.tableClientModel);

        listClients();
    }

    private void listClients(){
        this.tableClientModel.setRowCount(0);
        List<Client> clients = this.clientService.allClients();
        clients.forEach( client -> {
            Object[] row = {client.getId(), client.getName(), client.getLastName(), client.getMembership()};
            this.tableClientModel.addRow(row);
        });
    };

    private void createClient(){
        if(textFieldName.getText().equals("")){
            showMessage("Name is required");
            return;
        }
        if (textFieldLastName.getText().equals("")) {
            showMessage("Last name is required");
            return;
        }
        if (textFieldMembership.getText().equals("")) {
            showMessage("Membership is required");
            return;
        }

        String name = textFieldName.getText();
        String lastName = textFieldLastName.getText();
        int membership = Integer.parseInt(textFieldMembership.getText());
        Client client = new Client(this.idClient, name, lastName, membership);
        client.setName(name);
        client.setLastName(lastName);
        client.setMembership(membership);
        this.clientService.createClient(client);
        cleanForm();
        JOptionPane.showMessageDialog(this, "Client added!");
        listClients();
    }

    private void cleanForm(){
        textFieldLastName.setText("");
        textFieldName.setText("");
        textFieldMembership.setText("");
        this.idClient = null;
        this.clientsTable.getSelectionModel().clearSelection();
    }

    private void loadClient(){
        var line = clientsTable.getSelectedRow();
        if(line != -1){
            var id = clientsTable.getModel().getValueAt(line, 0).toString();
            this.idClient = Integer.parseInt(id);
            String name = clientsTable.getModel().getValueAt(line, 1).toString();
            this.textFieldName.setText(name);
            String lastName = clientsTable.getModel().getValueAt(line, 2).toString();
            this.textFieldLastName.setText(lastName);
            String membership = clientsTable.getModel().getValueAt(line, 3).toString();
            this.textFieldMembership.setText(membership);
        }
    }

    private void deleteClient(){
        if(this.idClient != null){
            this.clientService.deleteClient(this.idClient);
            cleanForm();
            JOptionPane.showMessageDialog(this, "Client was deleted!");
            listClients();
        }else{
            JOptionPane.showMessageDialog(this, "Select one client before delete");
        }
    }
    private void showMessage(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }
}
