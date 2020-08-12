package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner (System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		try {
		System.out.println("Enter rental data:");
		System.out.print("Car model: ");
		String model = sc.nextLine();
		Vehicle vehicle = new Vehicle(model);
		
		System.out.print("Pickup (dd/MM/yyyy hh:mm): ");
		Date initialInstant = sdf.parse(sc.nextLine());
		
		System.out.print("Return (dd/MM/yyyy hh:mm): ");
		Date finalInstant = sdf.parse(sc.nextLine());
		
		System.out.print("Enter price per hour: ");
		double pricePerHour = sc.nextDouble();
		
		System.out.print("Enter price per day: ");
		double pricePerDay = sc.nextDouble();
		
		CarRental carRental = new CarRental(initialInstant, finalInstant, vehicle);
		
		
		RentalService rentalService = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());
		rentalService.processInvoice(carRental);
		
		System.out.println("INVOICE: \nBasic payment: "+ String.format("%.2f", carRental.getInvoice().getBasicPayment())
				+"\nTax: "+ String.format("%.2f", carRental.getInvoice().getTax())+"\nTotal payment: "+ 
				String.format("%.2f", carRental.getInvoice().totalValue()));
		
		
		
		} catch (ParseException e) {
			System.out.println("Error: invalid data value");
		}
		sc.close();
	}
}