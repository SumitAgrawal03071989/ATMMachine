package com.bank.transaction;

import com.google.protobuf.Timestamp;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

public class RandomTransactionGenerator {
	
	private String atm_machine_id;
	private String customer_id;
	private int amount;
	private Timestamp event_timestamp;


	public RandomTransactionGenerator() {

		Random randIntGenerator = new Random();

		// ATM limit per transaction is 40,000
		// Also, the amount should be multiple of 100.
		int amount = new Integer(randIntGenerator.nextInt(40000));
		amount = amount - amount % 100;
		this.setAmount(amount);

		// 5 ATMS Operational as of now.
		this.setAtm_machine_id(new Integer(randIntGenerator.nextInt(5)).toString());

		// Only 100 accounts in a bank, Hey this bank just openened a month ago.
		this.setCustomer_id(new Integer(randIntGenerator.nextInt(100)).toString());

		long millis = System.currentTimeMillis();

		Timestamp event_timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
				.setNanos((int) ((millis % 1000) * 1000000)).build();

		this.setEvent_timestamp(event_timestamp);

	}

	public Timestamp getEvent_timestamp() {
		return event_timestamp;
	}

	public void setEvent_timestamp(Timestamp event_timestamp) {
		this.event_timestamp = event_timestamp;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setAtm_machine_id(String atm_machine_id) {
		this.atm_machine_id = atm_machine_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getAtm_machine_id() {
		return atm_machine_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}







	public static void main(String args[]){
		RandomTransactionGenerator rtg = new RandomTransactionGenerator();

		Random randIntGenerator = new Random();

		int amount = new Integer(randIntGenerator.nextInt(40000));
		amount = amount - amount % 100;


		System.out.println("Amount" + amount);
	}
	
	
	@Override
	public String toString() {
		return "[ ATM Machine Id: " + atm_machine_id + " Customer ID: " + customer_id + " Amount: " + amount + " Event timestamp: "+
				LocalDateTime.ofEpochSecond(event_timestamp.getSeconds(),event_timestamp.getNanos(), ZoneOffset.UTC).toLocalDate() + " " +
				LocalDateTime.ofEpochSecond(event_timestamp.getSeconds(),event_timestamp.getNanos(), ZoneOffset.UTC).toLocalTime()
//				event_timestamp.to +
                + "]";
	}
}
