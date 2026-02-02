package in.co.rays.proj4.test;

import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.model.TicketModel;

public class TestTicket {
	static TicketModel model = new TicketModel();

	public static void main(String[] args) {
		testNextpk();
	}

	public static void testNextpk() {
		try {
			System.out.println(model.nextPk());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
