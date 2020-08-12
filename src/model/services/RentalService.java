package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	private Double pricePerHour;
	private Double pricePerDay;
	
	private TaxService taxService;

	public RentalService() {
	}

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}

	public TaxService getTaxService() {
		return taxService;
	}

	public void setTaxService(TaxService taxService) {
		this.taxService = taxService;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}
	
	public void processInvoice(CarRental carRental) {
		long finish = carRental.getFinish().getTime();
		long start = carRental.getStart().getTime();
		double hours = (double)(finish - start)/1000/60/60;
		
		double basicPayment;
		if(hours > 24) {
			 basicPayment = pricePerDay * Math.ceil(hours / 24);
		} else {
			 basicPayment = pricePerHour *  Math.ceil(hours);
		}
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}

	
	}

	
	
	

	
