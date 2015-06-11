(function(){
	'use strict';
	
	angular
		.module('webLog')
		.service('ChartMaker', ChartMaker);
	
	ChartMaker.$inject =  ['$http','$state', 'RestLoader', 'CurrentSuite', 'Utilities', 'Charts'];
	
	function ChartMaker($http,$state, RestLoader, CurrentSuite, Utilities, Charts){
			
		var chartMaker = this;
			
		chartMaker.loadMainChart = function(suiteID,newLine,name){
			RestLoader.loadMainChart(suiteID, newLine, createMainChart, name);
		};
		
		chartMaker.loadHomeChart = function(suite){
			RestLoader.createHomeChartFromID(suite,createHomeChart);
		};
		
		chartMaker.addCaseToGraph = function(osName, osVersion, deviceName, browserName, browserVer){
			RestLoader.addCaseToGraph(osName, osVersion, deviceName, browserName, browserVer, createMainChart);
		};
		
	    chartMaker.highlightPoint = function(timestamp){
	    	for (var i = 0, seriesLength = Charts.mainChart.series.length; i < seriesLength; i++) {
	    		for (var j = 0, dataLength = Charts.mainChart.series[i].data.length; j < dataLength; j++) {
	  			   if (Charts.mainChart.xAxis.categories[j] === timestamp){
	  				   Charts.mainChart.xAxis.plotLines[0].value = j;
	  				   return;
	  			   }
	  		   }
	    	}
	    };
			
	    function createMainChart(data, newLine){
	    	if (data.length === 0) {
	    		alert("There is no data for that combination");
	    		Charts.mainChart.loading = false;
				return;
			}
			CurrentSuite.currentTimeStampArray = [];
			for (var i = 0, stampLength = data[0].data.length; i < stampLength; i++) {
				CurrentSuite.currentTimeStampArray.push(data[0].data[i].timestamp);
			}
			
			if (CurrentSuite.currentTimeStamp === '') {
				CurrentSuite.currentTimeStamp = data[0].data[data[0].data.length-1].timestamp;
			}
			Utilities.descTimestamps = reverseArray(CurrentSuite.currentTimeStampArray);
			var graphDataArray = [];
			var dataObj = [];
			for (var i = 0, dataLength = data.length; i < dataLength; i++) {
				var graphDataObj = {
						runTime: [],
						totalPass: [],
						totalFail: [],
						totalSkipped: [],
						passPercentage: []
				};
				var graphName = data[i].name;
				for (var j = 0, innderLength = data[i].data.length ; j < innderLength; j++) {
					if (isSkipped(data[i].data[j])) {
						graphDataObj.runTime.push(null);
						graphDataObj.totalPass.push(null);
						graphDataObj.totalFail.push(null);
						graphDataObj.passPercentage.push(null);
					} else {
						dataObj = data[i].data[j];
						graphDataObj.runTime.push(dataObj.time);
						graphDataObj.totalPass.push(dataObj.pass);
						graphDataObj.totalFail.push(dataObj.fail + dataObj.error);
						graphDataObj.totalSkipped.push(dataObj.skipped);
						graphDataObj.passPercentage.push(Math.round(getPassPercentage(dataObj.pass, dataObj.fail, dataObj.error)));
					}
				}
				graphDataObj.name = graphName;
				graphDataArray.push(graphDataObj);
			}
			if (newLine) {
				Charts.data = [];
				for (i = 0; i < graphDataArray.length; i++) {
					Charts.data.push(graphDataArray[i]);
				}
			} else {
				Charts.data = graphDataArray;
			}
			
			Charts.mainChart.xAxis.categories = CurrentSuite.currentTimeStampArray;
			
			Charts.mainChart.options.plotOptions.series.point = {
					events : {
						click : function(e) {
							RestLoader.loadTimestamp(this.category);
							chartMaker.highlightPoint(this.category);
							$('#mainChart').highcharts().zoomOut();
						}
					}
			};
			
			Charts.mainChart.subtitle.text = "Showing " + CurrentSuite.currentTimeStampArray.length + " results";
				
			for (var i = 0, chartsLength = Charts.data.length; i < chartsLength; i++) {
		    	Charts.mainChart.series.push({
					data : Charts.data[i].passPercentage,
					name : Charts.data[i].name,
				});
			}
			if(CurrentSuite.currentTimeStampArray.length <= 50){
				Charts.mainChart.xAxis.tickInterval = 0.4;
			} else if(CurrentSuite.currentTimeStampArray.length > 50 && CurrentSuite.currentTimeStampArray.length <= 100){
	        	Charts.mainChart.xAxis.tickInterval = 2;
	    	} else{
	    		Charts.mainChart.xAxis.tickInterval = 5;
	    	}
			chartMaker.changeChartVariant(Utilities.graphView);
			Charts.mainChart.loading = false;
			chartMaker.highlightPoint(CurrentSuite.currentTimeStamp);
	    }
		
		chartMaker.changeChartVariant = function(input){
			Utilities.graphView = input;
			
			switch (input) {
			case "Pass/Fail":
				passFailChart();
				break;
				
			case "Run Time":
				runTimeChart();
				break;
				
			case "Total Pass":
				totalPassChart();
				break;
				
			case "Total Fail":
				totalFailChart();
				break;
				
			case "Total Skipped":
				totalSkippedChart();
				break;
				
			default:
				Utilities.graphView = "Pass/Fail";
				passFailChart();
				break;
			}
		};
		
		function getSerieColor(i){
			if (i > Utilities.colors.length -1) {
				i = i - (Utilities.colors.length - 1);
			}
			return Utilities.colors[i];
		}
		
		function passFailChart() {
			var chart = Charts.mainChart;
			
			chart.series = [];
			for (var i = 0, dataLength = Charts.data.length; i < dataLength; i++) {
				chart.series.push({
					data : Charts.data[i].passPercentage,
					name : Charts.data[i].name,
					color: getSerieColor(i),
					type: "line"
				});
			}
			chart.yAxis.title.text = 'Pass percentage';
			chart.title.text = "Percentage of passed tests";
			delete Charts.mainChart.options.tooltip.valueDecimals;
		}
		
		function totalFailChart() {
			var chart = Charts.mainChart;
			
			chart.options.chart.type = "";
			chart.series = [];
			chart.yAxis.max = undefined;
			for (var i = 0, dataLength = Charts.data.length; i < dataLength; i++) {
				chart.series.push({
					data : Charts.data[i].totalFail,
					name : Charts.data[i].name,
					color: getSerieColor(i+1),
					type : "column",
					dashStyle : "Solid",
					connectNulls : false
				});
			}
			chart.yAxis.title.text = 'Failed tests';
			chart.options.plotOptions.series.stacking = '';
			chart.title.text = "Failed tests";
			delete Charts.mainChart.options.tooltip.valueDecimals;
		}
		
		function totalPassChart() {
			var chart = Charts.mainChart;
			
			chart.options.chart.type = "";
			chart.series = [];
			chart.yAxis.max = undefined;
			for (var i = 0, dataLength = Charts.data.length; i < dataLength; i++) {
				chart.series.push({
					data : Charts.data[i].totalPass,
					name : Charts.data[i].name,
					color: getSerieColor(i),
					type : "column",
					dashStyle : "Solid",
					connectNulls : false
				});
			}
			chart.yAxis.title.text = 'Passed tests';
			chart.options.plotOptions.series.stacking = '';
			chart.title.text = "Passed tests";
			delete Charts.mainChart.options.tooltip.valueDecimals;
		}
		
		function totalSkippedChart() {
			var chart = Charts.mainChart;
			
			chart.options.chart.type = "";
			chart.series = [];
			chart.yAxis.max = undefined;
			for (var i = 0, dataLength = Charts.data.length; i < dataLength; i++) {
				chart.series.push({
					data : Charts.data[i].totalSkipped,
					name : Charts.data[i].name,
					color: getSerieColor(i),
					type : "column",
					dashStyle : "Solid",
					connectNulls : false
				});
			}
			chart.yAxis.title.text = 'Skipped tests';
			chart.options.plotOptions.series.stacking = '';
			chart.title.text = "Skipped tests";
			delete Charts.mainChart.options.tooltip.valueDecimals;
		}
		
		function runTimeChart() {
			
			var chart = Charts.mainChart;
			chart.options.chart.type = "line";
			chart.series = [];
			chart.yAxis.max = undefined;
			for (var i = 0, dataLength = Charts.data.length; i < dataLength; i++) {
				chart.series.push({
							data : Charts.data[i].runTime,
							name : Charts.data[i].name,
							color: getSerieColor(i),
				});
			}
			
			chart.yAxis.title.text = 'Seconds';
			chart.options.plotOptions.series.stacking = '';
			chart.title.text = "Time to run in seconds";
			Charts.mainChart.options.tooltip.valueDecimals = 2;
		}
		
		function reverseArray(array){
			var length = array.length;
			var reverse = [];
			for (var i = length-1; i >= 0; i--) {
				reverse.push(array[i]);
			}
			return reverse;
		}
		
		function isSkipped(dataObj){
			var passed = dataObj.pass> 0;
			var failed = dataObj.fail > 0;
			var error = dataObj.error > 0;
			var skipped = dataObj.skipped;
			if (!passed && !failed && !error && skipped > 0) {
				return true;
			} else {
				return false;
			}
		}
		
		function getPassPercentage(pass, fail, error){
			var totalFail = fail + error;
			var total = pass + totalFail;
			var percentage = (pass/total)*100;
			return percentage;
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
		
		function getTooltipPercentageString(points){
			var tooltip ="<div class='tooltipContainer'><small><strong>"+points[0].point.category+"</strong></small><table>";
			for(var i = 0; i < points.length; i++){
				tooltip += "<tr>" +
									"<td style='color: "+points[i].series.color+"'>"+
										points[i].series.name +
									"</td>"+
									"<td style='text-align: right'>"+
										"<b>"+Math.round(points[i].percentage)+" %</b>"+
									"</td>"+
								"</tr>";
				
			}
			tooltip += "</table><br>";
			return tooltip;
		}
		
		function createHomeChart(data, suite) {
			
			var timeStamps = [];
			var timestampObj = data[0].data;
			for (var index = 0, timeLength = data[0].data.length; index < timeLength; index++) {
				timeStamps.push(timestampObj[index].timestamp);
			}
			
			suite.lastTimeStamp = timeStamps[timeStamps.length-1];
			
		    var chartHomeConfigObject = {
					options : {
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
				            formatter: function(){
				            	var points = this;
				            	var test = $http({
						            url   : '/api/stats/devicerange/'+suite.id+'/'+points.x,
						            method: 'GET',
						            cache: true,
						        }).success(function(dataObj, status, headers, config){
						        	var tooltip = getTooltipPercentageString(points.points);
						        	
						        	var deviceObj = getTooltipDeviceList(dataObj);
						        	
						        	tooltip += "<div>";
						        	for(var platform in deviceObj){
						        		tooltip += "<b>"+platform+"</b><br>";
						        		for(var i = 0; i < deviceObj[platform].devices.length; i++){
						        			tooltip += deviceObj[platform].devices[i]+"<br>";
						        		}
						        		tooltip += "<br>";
						        	}
						        	
						        	tooltip +="</div></div>";
						        	points.points[0].series.chart.tooltip.label.textSetter(tooltip);
						        }).error(function(data, status, headers, config){
						        	addErrorMessage(config, "change");
						        });
				            	return "loading..";
				            },
						},
						chart : {
							type : "areaspline",
							backgroundColor : '#ecf0f1'
						},
						plotOptions : {
							series : {
								cursor : 'pointer',
								stacking : "normal",
								point: {
									events: {
										click: function(e){
											Utilities.clearData();
											CurrentSuite.currentSuiteInfo = suite;
											RestLoader.loadTimestamp(this.category, true);
											chartMaker.loadMainChart(suite.id, true);
											$state.transitionTo('reports.classes');
										}
									}
								}
							}
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
						text: suite.name
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
				};
		    
		    chartHomeConfigObject.series[0].data = [];
		    chartHomeConfigObject.series[1].data = [];
		    
		    var passData = chartHomeConfigObject.series[0].data;
		    var skipData = chartHomeConfigObject.series[1].data;
		    var failData = chartHomeConfigObject.series[2].data;
		    var dataKeeper = {};
			for (var j = 0; j < data[0].data.length; j++) {
				dataKeeper = data[0].data[j];
				passData.push(dataKeeper.pass);
				skipData.push(dataKeeper.skipped);
				failData.push(dataKeeper.fail + dataKeeper.error);
			}
			chartHomeConfigObject.xAxis.categories = timeStamps;
			Charts.chartHomeConfig[suite.id] = chartHomeConfigObject;
			Charts.chartHomeConfig[suite.id].loading = false;
		}
	}
})();