package off_assoc.swing.service;

import off_assoc.swing.model.Client;
import off_assoc.swing.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> allClients() {
        return this.clientRepository.findAll();
    }

    @Override
    public Client findClientById(Integer idClient) {
        return this.clientRepository.findById(idClient).orElse(null);
    }

    @Override
    public void createClient(Client client) {
         this.clientRepository.save(client);
    }

    @Override
    public void deleteClient(Integer idClient) {
        this.clientRepository.deleteById(idClient);
    }
}
