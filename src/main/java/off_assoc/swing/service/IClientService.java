package off_assoc.swing.service;

import off_assoc.swing.model.Client;

import java.util.List;

public interface IClientService {
    public List<Client> allClients();
    public Client findClientById(Integer idClient);
    public void createClient(Client client);
    public void deleteClient(Integer idClient);
}
