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

            <fo:block>
              <#if listPromotions?has_content>
			  <fo:block font-size="13pt" font-weight="bold" text-align="center">
					${uiLabelMap.Promotions} 
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
              	  
               <fo:table-header text-align="center" background-color="silver">
					<fo:table-row>
						
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.ClientName}</fo:block>
						</fo:table-cell>											
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.ProductName}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.Discount}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.SellInFrom}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.SellInTo}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.SellOutFrom}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.SellOutTo}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.DateIns}</fo:block>
						</fo:table-cell>
						<fo:table-cell padding="1mm" border-width="0.3mm" border-style="solid" font-size="8pt">
							<fo:block font-weight="bold">${uiLabelMap.IsValid}</fo:block>
						</fo:table-cell>
						
						
					</fo:table-row>
				</fo:table-header>
                <fo:table-body>
                
	                <#list listPromotions as item>
		                
		                  <fo:table-row>
		                  			            
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        ${item.clientName}
		                      </fo:block>
		                    </fo:table-cell>		        
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        ${item.productName}
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                      	<#if item.discount?has_content>
		                        	 ${item.discount}
		                        <#else>
		                      		
		                        </#if>
		                      </fo:block>
		                    </fo:table-cell>		          
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                         <#if item.sellinFrom?has_content>
		                        	 ${item.sellinFrom?if_exists?string("yyyy.MM.dd")}
		                        <#else>
		                      		
		                        </#if>
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        <#if item.sellinTo?has_content>
		                        	 ${item.sellinTo?if_exists?string("yyyy.MM.dd")}
		                        <#else>
		                      		
		                        </#if>
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        <#if item.selloutFrom?has_content>
		                        	 ${item.selloutFrom?if_exists?string("yyyy.MM.dd")}
		                        <#else>
		                      		
		                        </#if>
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        <#if item.selloutTo?has_content>
		                        	 ${item.selloutTo?if_exists?string("yyyy.MM.dd")}
		                        <#else>
		                      		
		                        </#if>
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                        <#if item.dateIns?has_content>
		                        	 ${item.dateIns?if_exists?string("yyyy.MM.dd")}
		                        <#else>
		                      		
		                        </#if>
		                      </fo:block>
		                    </fo:table-cell>
		                    <fo:table-cell border-style="solid" border-width="0.3mm" font-size="8pt">
		                      <fo:block>
		                      	<#if item.isValid?has_content>
		                        	${item.isValid}
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

</#escape>
