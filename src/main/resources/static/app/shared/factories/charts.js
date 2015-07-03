(function(){
	'use strict';
	
	angular
		.module('webLog')
		.factory('Charts', Charts);
			
	Charts.$inject = ['$http','CurrentSuite'];
	
	function Charts ($http, CurrentSuite) {		
	
		var service = {
				// CHART DATA
				getTooltipPercentageString: getTooltipPercentageString,
				getTooltipDeviceList: getTooltipDeviceList,
				
				data : [],
				homeChartBlueprint: {
					options : {
						chart : {
							type : "areaspline",
							backgroundColor : '#ecf0f1'
						},
				        legend: {
				            itemHoverStyle: {
				                color: '#FF0000'
				            },
				            layout: 'vertical',
				            align: 'right',
				            verticalAlign: 'middle',
				            borderWidth: 0
				        },
					},
					title:{
						text: "Suite name"
						},
					subtitle:{
						text: "Last 50 test runs"
					},
					series : [ {
						data : [],
						id : "pass",
						name : "Passed",
						type : "column",
						color : "green",
						dashStyle : "Solid",
						connectNulls : false
					},{
						data : [],
						id : "skip",
						name : "Skipped",
						type : "column",
						color : "#EEDB00",
						dashStyle : "Solid",
						connectNulls : false
					},{
						data : [],
						id : "fail",
						name : "Failed",
						type : "column",
						color : '#DD072B',
						dashStyle : "Solid",
						connectNulls : false
					} ],
					xAxis : {
						tickInterval: 0.9,
						labels : {
							rotation : 45
						}
					},	
					yAxis : {
						title : {
							text : 'Number of tests'
						},
					},
					useHighStocks : false,
					loading:true,
					size : {
						height : 400
					},
					func : function(chart) {
					}
				},
				// HOME PAGE CHART
				chartHomeConfig: [],
			
				// MAIN PAGE CHART NORM
				mainChart : {
					options : {
						chart : {
							backgroundColor : '#ecf0f1',
							height : 420,
							zoomType : 'x'
						},
				        legend: {
				            itemHoverStyle: {
				                color: '#FF0000'
				            },
				            layout: 'vertical',
				            align: 'right',
				            verticalAlign: 'middle',
				            borderWidth: 0,
				            maxHeight:270
				        },
				        tooltip : {
						    shared: true,
						    borderRadius: 0,
						    borderWidth: 0,
						    shadow: false,
						    enabled: true,
						    backgroundColor: 'none',
				            useHTML: true,
				            followPointer: true,
				            hideDelay: 3,
				            showDelay: 0,
				            positioner: function(boxWidth, boxHeight, point) {
				                return {
				                    x: point.plotX + 100,
				                    y: point.plotY - point.plotY/2
				                };
				            },
				            formatter: function(){
				            	var points = this;
				            	var test;
				            	var tooltip;
				            	
								test = $http({
						            url   : '/api/stats/devicerange/'+CurrentSuite.currentSuiteInfo.id+'/'+CurrentSuite.timestampRaw[CurrentSuite.currentSuiteInfo.id][points.points[0].point.index],
						            method: 'GET',
						            cache: true,
						        });
				            	
				            	tooltip = getTooltipPercentageString(points.points);
				            	
				            	test.success(function(dataObj, status, headers, config){
						        	
						        	var deviceObj = getTooltipDeviceList(dataObj);
						        	
						        	tooltip += "<div>";
						        	for(var platform in deviceObj){
						        		tooltip += "<br>";
						        		tooltip += "<b>"+platform+"</b><br>";
						        		for(var i = 0; i < deviceObj[platform].devices.length; i++){
						        			tooltip += deviceObj[platform].devices[i]+"<br>";
						        		}
						        	}
						        	
						        	tooltip +="</div></div>";
						        	points.points[0].series.chart.tooltip.label.textSetter(tooltip);
						        }).error(function(data, status, headers, config){
						        	addErrorMessage(config, "change");
						        });
				            	return tooltip+'<br><b>Loading Devices...</b></div></div>';
				            },
						},
						plotOptions : {
							series : {
								cursor : 'pointer',
								point : {
								},
								marker : {
									enabled: false,
									lineWidth : 1,
								}
							}
						}
					},
					title : {
						text : ""
					},
					xAxis : {
						minTickInterval : 5,
						labels : {
							rotation : 45
						},
						  plotLines: [{
						    color: '#2c3e50', // Color value
						    width: 2 // Width of the line    
						  }]
					},
					yAxis : {
						title : {
							text : 'Percentage'
						},
					},
					series : [ {
						data : [],
						name : 'total Pass',
						color : '#FF0000',
						id : "mainPass"
					} ],
					credits : {
						enabled : false
					},
					loading : false,
					size : {},
					useHighStocks : false,
					subtitle: {
						text: "lots of tests"
					}
				}
		};
		
		function getTooltipPercentageString(points){
			var tooltip ="<div class='tooltipContainer'><strong style='display:block'>"+points[0].point.category+"</strong><br><table class='tooltipTable'>";
			tooltip += "<tr>"+
						    "<th>Serie</th>" +
						    "<th>Amt</th>";
			
			if(points[0].percentage !== undefined){
				tooltip += "<th>Pct</th>";
				
			}
			
			tooltip += "</tr>";
			for(var i = 0; i < points.length; i++){
				tooltip +=		"<tr>" +
									"<td style='color: "+points[i].series.color+"'>"+
										points[i].series.name +
									"</td>"+
									"<td>"+
										"<b>"+Math.round(points[i].y)+"</b>"+
									"</td>";
				
				if(points[i].percentage !== undefined){
						tooltip += "<td>"+
										"<b>"+Math.round(points[i].percentage)+"%</b>"+
									"</td>";
				}
				
					tooltip += "</tr>";
			}
			tooltip += "</table>";
			return tooltip;
		}
		
		function getTooltipDeviceList(list){
			var deviceKeeper = {};
			var listItem;
			
			for(var i = 0; i < list.length; i++){
				listItem = list[i];
				if (deviceKeeper[listItem.osname] === undefined) {
					deviceKeeper[listItem.osname] = {devices:[]};
					deviceKeeper[listItem.osname].devices.push(listItem.devicename);
				} else {
					deviceKeeper[listItem.osname].devices.push(listItem.devicename);
				}
			}
			
			return deviceKeeper;
		}
		
		return service;
	}
})();