angular.module('webLog')
	.factory('Charts', function(){
		
		return {
			
			//HOME PAGE CHART ------------------------------------
			homeChart: {
	        		  options: {
	        			    chart: {
	        			      type: "areaspline",
	        			      backgroundColor: '#ecf0f1'
	        			    },
	        			    plotOptions: {
	        			      series: {
	        			        stacking: "normal"
	        			      }
	        			    }
	        			  }, 
	        		  series: [{
	        			  data: [],
	        		      id: "pass",
	        		      name: "Pass",
	        		      type: "column",
	        		      color: "green",
	        		      dashStyle: "Solid",
	        		      connectNulls: false
	        		  },{
	        			  data: [],
	      	   		      id: "fail",
	      	   		      name: "Fail",
	      	   		      type: "column",
	      	   		      color: "red",
	      	   		      dashStyle: "Solid",
	      	   		      connectNulls: false
	        		  }],
	        		  title: {
	        		     text: 'Pass / Fail - Last 50 Runs'
	        		  },
	        		  loading: false,
	        		  xAxis: {
	        		  title: {text: 'Tests run'}
	        		  },
	        		  useHighStocks: false,
	        		  size: {
	        		   height: 400
	        		  },
	        		  //function (optional)
	        		  func: function (chart) {
	        		   //setup some logic for the chart
	        		  }
	        		},
	        		
	        		
	        		
	        //MAIN CHART ------------------------------------
			mainChart: {
	        			  options: {
	      				    chart: {
	      				      type: "areaspline",
	      				      backgroundColor: '#ecf0f1',
	      			            events: {
	      			                selection: function (event) {
	      			                	console.log(event);
	      			                    var text,
	      			                        label;
	      			                    if (event.xAxis) {
	      			                        text = 'min: ' + Highcharts.numberFormat(event.xAxis[0].min, 2) + ', max: ' + Highcharts.numberFormat(event.xAxis[0].max, 2);
	      			                    } else {
	      			                        text = 'Selection reset';
	      			                    }
	      			                    label = this.renderer.label(text, 100, 120)
	      			                        .attr({
	      			                            fill: Highcharts.getOptions().colors[0],
	      			                            padding: 10,
	      			                            r: 5,
	      			                            zIndex: 8
	      			                        })
	      			                        .css({
	      			                            color: '#FFFFFF'
	      			                        })
	      			                        .add();

	      			                    setTimeout(function () {
	      			                        label.fadeOut();
	      			                    }, 1000);
	      			                }
	      			            },
	      			            zoomType: 'x'
	      				    },
	  			            tooltip: {
	  			            },
	      				    plotOptions: {
	      				      series: {
	      				    	  stacking: "percent",
	      		                    cursor: 'pointer',
	      		                    point: {
	      		                        events: {
	      		                            click: function (e) {
	      		                            	console.log(this.category);
	      		                            }
	      		                        }
	      		                    },
	      		                    marker: {
	      		                        lineWidth: 1
	      		                    }
	      				      }
	      				    }
	      				  },
	      				  xAxis:{
	      					  categories: "",
	      					  minTickInterval: 5,
	      					  labels:{
	      						  rotation: 45
	      					  }
	      				  },
	  						yAxis: {
	  							title: {
	  								text: 'Percentage'
	  								
	  							},
	  							style: {
	  								
	  							}
	      				  },
	      				  series: [
	      				    {
	      				      data: [],
	      				      name:'Pass',
	      				      color: '#D4D9DD',
	      				      id: "mainPass"
	      				    },
	      				    {
	      				      data: [],
	      				      name: 'Fail',
	      				      color: '#FF0000',
	      				      id: "mainFail"
	      				    }
	      				  ],
	      				  title: {
	      				    text: "" 
	      				  },
	      				  credits: {
	      				    enabled: false
	      				  },
	      				  loading: false,
	      				  size: {},
	      				  useHighStocks: false,
	      				}
		};
		
	});