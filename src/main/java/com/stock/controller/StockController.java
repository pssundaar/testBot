package com.stock.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.stock.beans.PurchasedStock;
import com.stock.beans.Stocks;



import com.stock.service.StockService;
import com.stocks.cb.model.Cb;
import com.stocks.cb.model.User;
import com.stocks.javaApp.Stocks.MarketTimer;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;


@Path("/json/chat")
@RestController
public class StockController {
	public StockController() {}
	Logger logger = LoggerFactory.getLogger(StockController.class);
	@Autowired
	private StockService stockService;
	int balance = 0;
	String speech = "";
	List<Stocks> stocks;
	public static Logger LOG = LoggerFactory.getLogger(StockController.class);
	
	
	
	
////////////////////////////-------------*************************************----------------------//////////////////////////////////////////

	
	
	
	  @POST
	  @Consumes({"application/json"})
	 @RequestMapping(value = "/get")
	public String createTrackInJSON(@RequestBody Cb request) throws JsonGenerationException, JsonMappingException, IOException, SQLException {
		
		String action =  request.getQueryResult().getAction();
	    ObjectMapper mapper = new ObjectMapper();
	    
	    
	  
	    	logger.error("request: error: " + mapper.writeValueAsString(request));
	        logger.debug("request: debug: " + mapper.writeValueAsString(request));
	        logger.info("request: info: " + mapper.writeValueAsString(request));
	     
	      String speech = "";
	     
	    	  com.stocks.cb.model.Parameters params = request.getQueryResult().getParameters();
	  
	        final Map<String, Object> properties = params.getAdditionalProperties();
	    
	       
	        System.out.println("-------------------"+action);
	        BotResponse response = new BotResponse();
	        String jsonInString=null;
	        
	        if (action.equalsIgnoreCase("getStocks"))
	        {
	          speech = getStocks(properties);
	          response.setFulfillmentText(speech);
	          jsonInString = mapper.writeValueAsString(response);
	        }
	         
	      
	     mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
	      Response.status(201).entity(jsonInString).build();
	      
	      System.out.println("final output"+jsonInString);
	      System.out.println("final output1"+Response.status(201).entity(jsonInString).build());
	      return jsonInString;
	   
	    
	  }
	  
	 
	 @Consumes({"application/json"})
	@PostMapping( value = "/process")
		public String process(@RequestBody Cb pa) 
		    throws Exception {
		  String speech = "hello";
			 BotResponse response = new BotResponse();
		        String jsonInString=null;
	         response.setFulfillmentText(speech);
	         ObjectMapper mapper = new ObjectMapper();
	         jsonInString = mapper.writeValueAsString(response);
	      //   Response.status(201).entity(jsonInString).build();
		return jsonInString;
		    //System.out.println();

	}
	 
	 
	 
	 private String getStocks( Map<String, Object> properties) throws SQLException, IOException {
                    
StringBuffer sb = new StringBuffer();
YahooFinance yahoofinance = new YahooFinance();
Stock  stock=yahoofinance.get("BABA");
StockQuote stockQuote = stock.getQuote();
sb.append(stockQuote.getPrice().intValue()+"");


     return " "+sb;
     }

	
	
	
	
	
	
	
	
	
	
	
	
////////////////////////////-------------*************************************----------------------//////////////////////////////////////////

	/*
	 * This method is for Stock Information every 30 mints it will update the stocks
	 * information.
	 *  method getStockInfromation 
	 *  return void
	 * @Scheduled- Repeated interval . no-arg
	 */
	@Scheduled(fixedRate = 300000)
	public void getStockInfromation() {
		stocks = stockService.getListOfStockInformation();
		LOG.info("Stock Information...");
		System.out.println("Stock Name \t Price");
		System.out.println("--------------------------");
		for (Stocks s : stocks) {
			System.out.println(s.getStockName() + "\t" + s.getStckprice());
		}
	}

	/*
	 * This method is for get stock by url . 
	 * method getStockInfio 
	 * return List of
	 * stock Info.
	 * @Scheduled- Repeated interval . no-arg
	 */
	
	
	
	
	
	
	@RequestMapping(value = "getStocks")
	public List<Stocks> getStockInfio() {
		
		
		
		
		
		
		return stocks;
	}

	/*
	 * This method is for buying a stock. 
	 * method getStockInfio 
	 * return List
	 */
	@RequestMapping(value = "buyStock")
	public PurchasedStock buyStock() {
		/* openOrNotMarket is a timmer it will check the time - market opened or closed.
		  */
		Scanner sc = new Scanner(System.in);
		boolean openOrNotMarket = MarketTimer.marketTime();

		if (!openOrNotMarket) {
			if (balance == 0) {

				LOG.info("Please Enter initialAmount: ");
				balance = sc.nextInt();
			}
			LOG.info("Please Enter stock name: ");
			String stockName = sc.next();
			LOG.info("Your Balance is  "+ balance);
			PurchasedStock buy = stockService.buyStock(balance, stockName);
			if (buy.getNoOfShares() != 0)
				balance = buy.getRemainingBalance();

			return buy;
		}
		sc.close();
		return null;
	}

	/*
	 * This method is for Selling a stock. 
	 * method sellStock 
	 * return String
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "sellStock")
	public String sellStock() {
		Scanner sc = new Scanner(System.in);
		List<PurchasedStock> purchasedStocks = stockService.getAllBuyInfo();

		purchasedStocks.stream().filter(b -> b.getNoOfShares()!=0).forEach(System.out::println);

		try {
			LOG.info("Please Enter stock name for sell: ");
			String stockName = sc.next();
			
			if (purchasedStocks.stream().filter(s -> stockName.equalsIgnoreCase(s.getStockName())).count() > 0) {
				PurchasedStock buy = stockService.sellStock(stockName);
				if (buy.getNoOfShares() == 0) {
					balance = buy.getRemainingBalance();
				}
				return "Remaining balance: " + balance + " \n Stock sells has been completed :" + buy.getStockName();
			} else {
				return "Choosen stock not available for sell.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sc.close();
		return null;
	}
	/*
	 * This method is for List of purchased shares . 
	 * method listBS 
	 * return list
	 */
	@RequestMapping(value = "listBS")
	public List<PurchasedStock> listBS() {
		return stockService.getAllBuyInfo();
	}
	
	
	/*
	 * This method is for Live/ Current price of stock . 
	 * method Status 
	 * return string
	 */
	@RequestMapping(value = "Status")
	public String Status()  {
		Scanner sc = new Scanner(System.in);
		LOG.info("Please Enter stock name for Live Price: ");
		String stockName = sc.next();
		sc.close();
		return stockService.liveStatus(stockName);
	
	}

}
