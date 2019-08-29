<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

<#escape x as x?xml>
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <fo:layout-master-set>
      <fo:simple-page-master master-name="main" page-height="8.5in" page-width="11in"
        margin-top="0.1in" margin-bottom="1in" margin-left=".5in" margin-right="1in">
          <fo:region-body margin-top="0.2in"/>
          <fo:region-before extent="1in"/>
          <fo:region-after extent="1in"/>
      </fo:simple-page-master>
    </fo:layout-master-set>


  
  <fo:page-sequence master-reference="main">
  	
	
	<fo:flow flow-name="xsl-region-body" font-family="NotoSans">
            <fo:block>
              <#if listConditions?has_content>
			  <fo:block font-size="13pt" font-weight="bold" text-align="center">
					${uiLabelMap.Conditions} 
			  </fo:block>
              <fo:table width="100%" table-layout="fixed"> 
                  <fo:table-column column-width="7%" border-width="1px" border-style="solid" />
                  <fo:table-column column-width="7%" border-width="1px" border-style="solid" />
                  <fo:table-column column-width="10%" border-width="1px" border-style="solid" />        
				  <fo:table-column column-width="5%" border-width="1px" border-style="solid" />
				  <fo:table-column column-width="5%" border-width="1px" border-style="solid" />
				  <fo:table-column column-width="3%" border-width="1px" border-style="solid" />
				  <fo:table-column column-width="3%" border-width="1px" border-style="solid" />
				  <fo:table-column column-width="3%" border-width="1px" border-style="solid" />
				  <fo:table-column column-width="3%" border-width="1px" border-style="solid" />
              	  <fo:table-column column-width="3%" border-width="1px" border-style="solid" />
                  <fo:table-column column-width="5%" border-width="1px" border-style="solid" />        
				  <fo:table-column column-width="5%" border-width="1px" border-style="solid" />
				  <fo:table-column column-width="5%" border-width="1px" border-style="solid" />
				  <fo:table-column column-width="5%" border-width="1px" border-style="solid" />
				  <fo:table-column column-width="7%" border-width="1px" border-style="solid" />
				  <fo:table-column column-width="7%" border-width="1px" border-style="solid" />
				  <fo:table-column column-width="5%" border-width="1px" border-style="solid" />
               <fo:table-header text-align="center" background-color="silver">
					<fo:table-row>
						
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.ClientName}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.PriceListName}</fo:block>
						</fo:table-cell>					
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.ProductName}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.PriceListPrice}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.PriceNet}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.sc1}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.sc2}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.sc3}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.sc4}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.sc5}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="6pt">
							<fo:block font-weight="bold">${uiLabelMap.ContractValue}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="6pt">
							<fo:block font-weight="bold">${uiLabelMap.ResultPrice}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="6pt">
							<fo:block font-weight="bold">${uiLabelMap.PriceCheck}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="6pt">
							<fo:block font-weight="bold">${uiLabelMap.PercRic}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="6pt">
							<fo:block font-weight="bold">${uiLabelMap.ValidFrom}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="6pt">
							<fo:block font-weight="bold">${uiLabelMap.ValidTo}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="6pt">
							<fo:block font-weight="bold">${uiLabelMap.IsProductBought}</fo:block>
						</fo:table-cell>
						
					</fo:table-row>
				</fo:table-header>
                <fo:table-body>
                
	                <#list listConditions as item>
		                
		                  <fo:table-row>
		                  			            
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        ${item.clientName}
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        <#if item.pricelistName?has_content>
		                        	 ${item.pricelistName}
		                        <#else>
		                      		
		                        </#if>
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        ${item.productName}
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                      	<#if item.price?has_content>
		                        	 ${item.price}
		                        <#else>
		                      		
		                        </#if>
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        <#if item.startingPrice?has_content>
		                        	 ${item.startingPrice}
		                        <#else>
		                      		
		                        </#if>
		                        
		                      </fo:block>
		                    </fo:table-cell>
		                    
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        ${item.sc1}
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        ${item.sc2}
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        ${item.sc3}
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        ${item.sc4}
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        ${item.sc5}
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        ${item.contractValue}
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        ${item.resultPrice}
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                      	<#if item.priceCheckPrice?has_content>
		                        	 ${item.priceCheckPrice}
		                        <#else>
		                      		
		                        </#if>
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        <#if item.perc?has_content>
		                        	 ${item.perc}
		                        <#else>
		                      		
		                        </#if>
		                       
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                         <#if item.validFrom?has_content>
		                        	 ${item.validFrom?if_exists?string("yyyy.MM.dd")}
		                        <#else>
		                      		
		                        </#if>
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        <#if item.validTo?has_content>
		                        	 ${item.validTo?if_exists?string("yyyy.MM.dd")}
		                        <#else>
		                      		
		                        </#if>
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                      	<#if item.isProductBought?has_content>
		                        	${item.isProductBought}
		                        <#else>
		                      		
		                        </#if>
	                  		</fo:block>
		                    </fo:table-cell>
		                    </fo:table-row>
		                    
		                    
		                    
		    	         
	                 </#list>
     
                </fo:table-body>
              </fo:table>
              </#if>
              
                        
            </fo:block>
	</fo:flow>

  </fo:page-sequence>


</fo:root>
</#escape>
