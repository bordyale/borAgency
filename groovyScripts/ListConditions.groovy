/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.ofbiz.entity.condition.EntityExpr
import org.apache.ofbiz.entity.condition.EntityFunction
import org.apache.ofbiz.entity.condition.EntityOperator
import org.apache.ofbiz.entity.condition.EntityFieldValue
import org.apache.ofbiz.entity.condition.EntityConditionList
import org.apache.ofbiz.entity.condition.EntityCondition
import org.apache.ofbiz.entity.GenericValue
import org.apache.ofbiz.entity.util.EntityUtil
import java.sql.Timestamp
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

filcontractorId = parameters.filcontractorId
filproductId = parameters.filproductId
filclientId = parameters.filclientId
filclientType = parameters.filclientType
filactiv = parameters.filactiv
filisProductBought = parameters.filisProductBought
filshowConditions = parameters.filshowConditions

List searchCond = []
if (filcontractorId) {
	searchCond.add(EntityCondition.makeCondition("contractorId", EntityOperator.EQUALS, filcontractorId))
}
if (filproductId) {
	searchCond.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, filproductId))
}
if (filclientId) {
	searchCond.add(EntityCondition.makeCondition("clientId", EntityOperator.EQUALS, filclientId))
}
if (filclientType) {
	searchCond.add(EntityCondition.makeCondition("clientType", EntityOperator.EQUALS, filclientType))
}
if (filactiv.equals("Y")) {
	searchCond.add(EntityCondition.makeCondition("validTo",EntityOperator.EQUALS, null) )
}
if (filisProductBought) {
	searchCond.add(EntityCondition.makeCondition("isProductBought",EntityOperator.EQUALS, filisProductBought) )
}


conditionsList = from("ConditionView").where(searchCond).orderBy("productName","productId").cache(false).queryList()

//conditionsList = EntityUtil.orderBy(conditionsList,  ["productName"])

List<HashMap<String,Object>> hashMaps = new ArrayList<HashMap<String,Object>>()
if(filshowConditions.equals("Y")){
	for (GenericValue entry: conditionsList){
		Map<String,Object> e = new HashMap<String,Object>()
		e.put("conditionId",entry.get("conditionId"))
		e.put("contractId",entry.get("contractId"))
		e.put("contractId2",entry.get("contractId2"))
		e.put("contractorName",entry.get("contractorName"))
		e.put("clientName",entry.get("clientName"))
		e.put("pricelistName",entry.get("pricelistName"))
		e.put("productName",entry.get("productName"))
		e.put("prodCode",entry.get("prodCode"))
		e.put("note",entry.get("note"))
		BigDecimal listprice =entry.get("price")
		BigDecimal price = BigDecimal.ZERO
		if (listprice != null) {
			price = listprice.setScale(3,RoundingMode.HALF_UP)
		}
		Locale locale  = new Locale("it", "IT");
		String pattern = "0.000"
		DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale)
		decimalFormat.applyPattern(pattern)
		String valueString = decimalFormat.format(price);

		e.put("price",valueString)
		e.put("isProductBought",entry.get("isProductBought"))
		e.put("validFrom",entry.get("validFrom"))
		e.put("validTo",entry.get("validTo"))
		BigDecimal startingPrice = entry.get("startingPrice")
		e.put("startingPrice",startingPrice)
		BigDecimal resultPrice = startingPrice ==null ? price : startingPrice
		if (resultPrice == null) {
			resultPrice = BigDecimal.ZERO
		}
		BigDecimal sc1 = entry.get("sc1")==null ? BigDecimal.ZERO : entry.get("sc1")
		BigDecimal sc2 = entry.get("sc2")==null ? BigDecimal.ZERO : entry.get("sc2")
		BigDecimal sc3 = entry.get("sc3")==null ? BigDecimal.ZERO : entry.get("sc3")
		BigDecimal sc4 = entry.get("sc4")==null ? BigDecimal.ZERO : entry.get("sc4")
		BigDecimal sc5 = entry.get("sc5")==null ? BigDecimal.ZERO : entry.get("sc5")
		BigDecimal contractValue1 = entry.get("totalValue")==null ? BigDecimal.ZERO : entry.get("totalValue")
		BigDecimal contractValue2 = entry.get("totalValue2")==null ? BigDecimal.ZERO : entry.get("totalValue2")
		BigDecimal contractValue = contractValue1.add(contractValue2)
		e.put("sc1",sc1)
		e.put("sc2",sc2)
		e.put("sc3",sc3)
		e.put("sc4",sc4)
		e.put("sc5",sc5)
		e.put("contractValue",contractValue)
		resultPrice = resultPrice.multiply(new BigDecimal(1).subtract(sc1.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : sc1.divide(new BigDecimal(100))))
		resultPrice = resultPrice.multiply(new BigDecimal(1).subtract(sc2.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : sc2.divide(new BigDecimal(100))))
		resultPrice = resultPrice.multiply(new BigDecimal(1).subtract(sc3.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : sc3.divide(new BigDecimal(100))))
		resultPrice = resultPrice.multiply(new BigDecimal(1).subtract(sc4.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : sc4.divide(new BigDecimal(100))))
		resultPrice = resultPrice.multiply(new BigDecimal(1).subtract(sc5.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : sc5.divide(new BigDecimal(100))))
		//BigDecimal invoicePrice = resultPrice.setScale(3,RoundingMode.HALF_UP)
		String invoicePriceStr = decimalFormat.format(resultPrice);
		e.put("invoicePrice",invoicePriceStr)
		resultPrice = resultPrice.multiply(new BigDecimal(1).subtract(contractValue.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : contractValue.divide(new BigDecimal(100)))).setScale(3,RoundingMode.HALF_UP)
		e.put("resultPrice",resultPrice)


		String productId = entry.get("productId")
		String clientId = entry.get("clientId")

		exprBldr = new org.apache.ofbiz.entity.condition.EntityConditionBuilder()
		expr = exprBldr.AND() {
			EQUALS(productId: productId)
			EQUALS(clientId: clientId)
		}
		pricecheckList = from("BorPriceCheckView").where(expr).orderBy("date DESC").queryFirst()
		if (pricecheckList){
			BigDecimal priceCheckPrice = pricecheckList.get("price")
			e.put("priceCheckPrice",priceCheckPrice)
			BigDecimal perc = priceCheckPrice.subtract(resultPrice).divide(priceCheckPrice,3,RoundingMode.HALF_UP).multiply(new BigDecimal(100))
			//BigDecimal perc = priceCheckPrice.divide(resultPrice,2,RoundingMode.HALF_UP).multiply(new BigDecimal(100))
			e.put("perc",perc)
		}

		//price Kg
		BigDecimal weight = entry.get("weight")
		if (weight != null){
			BigDecimal resultPriceKg = new BigDecimal(1).divide(weight,3,RoundingMode.HALF_UP).multiply(resultPrice).setScale(3,RoundingMode.HALF_UP)
			e.put("resultPriceKg",resultPriceKg)
		}


		hashMaps.add(e)
	}
}



context.listConditions = hashMaps
