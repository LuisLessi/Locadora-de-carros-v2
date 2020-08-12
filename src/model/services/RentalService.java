package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	private Double pricePerHour;
	private Double pricePerDay;
	
	private BrazilTaxService brazilTax;

	public RentalService() {
	}

	public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService brazilTax) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.brazilTax = brazilTax;
	}

	public BrazilTaxService getBrazilTax() {
		return brazilTax;
	}

	public void setBrazilTax(BrazilTaxService brazilTax) {
		this.brazilTax = brazilTax;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}
	
	public void processInvoice(CarRental carRental) {
		long dt = (carRental.getFinish().getTime() - carRental.getStart().getTime() ) + 3600000;
		long days = (dt / 86400000L);
		long finish = carRental.getFinish().getTime();
		long start = carRental.getStart().getTime();
		double hours = (double)(finish - start)/1000/60/60;
		
		double basicPayment;
		if(hours > 24) {
			 basicPayment = pricePerDay * Math.ceil(days);
		} else {
			 basicPayment = pricePerHour *  Math.ceil(hours);
		}
		
		double tax = brazilTax.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}

	
	}

	
	
	

	
