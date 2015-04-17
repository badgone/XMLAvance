package main;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import classes.generees.CustomerType;
import classes.generees.ObjectFactory;
import classes.generees.OrderType;
import classes.generees.Root;
import classes.generees.ShipInfoType;
import classes.generees.Root.Customers;
import classes.generees.Root.Orders;

public class Demo {

	public static void main(String[] args) throws JAXBException {
		demoMarshall();

	}

	/**
	 * Unmarshalling
	 * 
	 * @throws JAXBException
	 */
	public static void demoUnmarshall() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);

		Unmarshaller unmarshaller = context.createUnmarshaller();
		Root racine = (Root) unmarshaller.unmarshal(new File("xml/demo.xml"));

		Customers clients = racine.getCustomers();
		List<CustomerType> customers = clients.getCustomer();

		for (CustomerType client : customers) {
			String nomClient = client.getContactName();
			System.out.println(String.format("Nom client : %s", nomClient));

			String societeClient = client.getCompanyName();
			System.out.println(String.format("Société : %s", societeClient));

		}
	}

	/**
	 * Marshalling
	 * 
	 * @throws JAXBException
	 */
	public static void demoMarshall() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		Root racine = new Root();
		
		Customers clients = new Customers();
		Orders livraisons = new Orders();
		
		CustomerType client = new CustomerType();
		client.setContactName("Toto");
		client.setCompanyName("Toto Company");
		clients.getCustomer().add(client);
		
		OrderType livraison = new OrderType();
		livraison.setCustomerID("TOTO2015");
		
		ShipInfoType infosLivraison = new ShipInfoType();
		infosLivraison.setShipName("Livraison à la Toto Company");
		infosLivraison.setShipAddress("1 rue de toto");
		infosLivraison.setShipCity("Toto City");
		livraison.setShipInfo(infosLivraison);
		livraisons.getOrder().add(livraison);
		
		racine.setCustomers(clients);
		racine.setOrders(livraisons);
		
		Marshaller marshaller = context.createMarshaller();
		
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		 
		marshaller.marshal(racine, new File("xml/demo.xml"));
		
	}

}
