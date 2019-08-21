/*******************************************************************************
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
 *******************************************************************************/
package org.apache.ofbiz.boragency;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.ObjectType;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilFormatOut;
import org.apache.ofbiz.base.util.UtilGenerics;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilNumber;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericPK;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.order.shoppingcart.product.ProductPromoWorker;
import org.apache.ofbiz.product.catalog.CatalogWorker;
import org.apache.ofbiz.product.config.ProductConfigWorker;
import org.apache.ofbiz.product.config.ProductConfigWrapper;
import org.apache.ofbiz.product.product.ProductWorker;
import org.apache.ofbiz.product.store.ProductStoreSurveyWrapper;
import org.apache.ofbiz.product.store.ProductStoreWorker;
import org.apache.ofbiz.security.Security;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ModelService;
import org.apache.ofbiz.service.ServiceUtil;
import org.apache.ofbiz.webapp.control.RequestHandler;

/**
 * Vforder events.
 */
public class BorAgencyEvents {

	public static String module = BorAgencyEvents.class.getName();

	public static final String resource = "OrderUiLabels";
	public static final String resource_error = "OrderErrorUiLabels";

	private static final String NO_ERROR = "noerror";
	private static final String NON_CRITICAL_ERROR = "noncritical";
	private static final String ERROR = "error";

	public static final MathContext generalRounding = new MathContext(10);

	public static String updateContractValue(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		GenericValue userLogin = (GenericValue) session.getAttribute("userLogin");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

		String controlDirective = null;
		Map<String, Object> result = null;

		Map<String, Object> paramMap = UtilHttp.getParameterMap(request);

		// The number of multi form rows is retrieved

		String contractId = null;

		// get the params
		if (paramMap.containsKey("contractId")) {
			contractId = (String) paramMap.get("contractId");
		}
		if (contractId == null) {
			return "error";
		}
		try {
			List<GenericValue> contractdetails = EntityQuery.use(delegator).from("BorContractDetail").where(EntityCondition.makeCondition("contractId", EntityOperator.EQUALS, contractId))
					.orderBy("contractdetailId").queryList();
			BigDecimal totalValue = BigDecimal.ZERO;
			if (contractdetails != null) {
				for (GenericValue entry : contractdetails) {
					String contractDetailType = (String) entry.get("contractDetailType");
					BigDecimal value = (BigDecimal) entry.get("value");
					BigDecimal refRevenue = (BigDecimal) entry.get("refRevenue");
					BigDecimal perc = BigDecimal.ZERO;
					if (contractDetailType.equals("CONR_TYPE_PERC")) {
						perc = value;
					} else if (contractDetailType.equals("CONR_TYPE_FISSO")) {
						perc = value.divide(refRevenue);
					}
					totalValue = totalValue.add(perc);
				}
				try {
					Map<String, Object> tmpResult = dispatcher.runSync("updateContract", UtilMisc.<String, Object> toMap("userLogin", userLogin, "contractId", contractId, "totalValue", totalValue));

				} catch (GenericServiceException e) {
					Debug.logError(e, module);
				}

			}
		} catch (GenericEntityException e) {
			Debug.logError(e, module);

		}

		return "success";

	}

	/**
	 * This should be called to translate the error messages of the
	 * <code>ShoppingCartHelper</code> to an appropriately formatted
	 * <code>String</code> in the request object and indicate whether the result
	 * was an error or not and whether the errors were critical or not
	 *
	 * @param result
	 *            The result returned from the <code>ShoppingCartHelper</code>
	 * @param request
	 *            The servlet request instance to set the error messages in
	 * @return one of NON_CRITICAL_ERROR, ERROR or NO_ERROR.
	 */
	private static String processResult(Map<String, Object> result, HttpServletRequest request) {
		// Check for errors
		StringBuilder errMsg = new StringBuilder();
		if (result.containsKey(ModelService.ERROR_MESSAGE_LIST)) {
			List<String> errorMsgs = UtilGenerics.checkList(result.get(ModelService.ERROR_MESSAGE_LIST));
			Iterator<String> iterator = errorMsgs.iterator();
			errMsg.append("<ul>");
			while (iterator.hasNext()) {
				errMsg.append("<li>");
				errMsg.append(iterator.next());
				errMsg.append("</li>");
			}
			errMsg.append("</ul>");
		} else if (result.containsKey(ModelService.ERROR_MESSAGE)) {
			errMsg.append(result.get(ModelService.ERROR_MESSAGE));
			request.setAttribute("_ERROR_MESSAGE_", errMsg.toString());
		}

		// See whether there was an error
		if (errMsg.length() > 0) {
			request.setAttribute("_ERROR_MESSAGE_", errMsg.toString());
			if (result.get(ModelService.RESPONSE_MESSAGE).equals(ModelService.RESPOND_SUCCESS)) {
				return NON_CRITICAL_ERROR;
			} else {
				return ERROR;
			}
		} else {
			return NO_ERROR;
		}
	}

}
