package com.serverless;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.Rate;


public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = Logger.getLogger(Handler.class);

	 @Override
	 public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
	 	Map<String, String> headers = new HashMap<>();
	 	headers.put("Content-Type", "application/json");

	 	if (input != null && input.containsKey("queryStringParameters")) {
	 		Map<String, Object> queryStringParams = (Map<String, Object>) input.get("queryStringParameters");

	 		String source = queryStringParams.get("source").toString();
	 		String target = queryStringParams.get("target").toString();
	 		String date = queryStringParams.get("date").toString();

             Rate rate = null;
             try {
                 rate = RateService.getRate(source, target, date);
             } catch (ParseException e) {
                 throw new RuntimeException(e);
             }

             return ApiGatewayResponse.builder()
	 				.setStatusCode(200)
	 				.setObjectBody(rate)
	 				.setHeaders(headers)
	 				.build();
	 	} else {
	 		return ApiGatewayResponse.builder()
	 				.setStatusCode(400)
	 				.setHeaders(headers)
	 				.build();
	 	}

	 }

}
