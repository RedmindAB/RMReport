angular.module('webLog')
		.factory('Charts',function() {

					return {
						// CHART DATA
						// ------------------------------------------------------------------------------
						data : [],
						// HOME PAGE CHART
						// ------------------------------------------------------------------------------
						homeChart : {
							options : {
								chart : {
									type : "areaspline",
									backgroundColor : '#ecf0f1'
								},
								plotOptions : {
									series : {
										stacking : "normal"
									}
								}
							},
							series : [ {
								data : [],
								id : "pass",
								name : "Pass",
								type : "column",
								color : "green",
								dashStyle : "Solid",
								connectNulls : false
							}, {
								data : [],
								id : "fail",
								name : "Fail",
								type : "column",
								color : '#FF0000',
								dashStyle : "Solid",
								connectNulls : false
							} ],
							xAxis : {
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
							size : {
								height : 400
							},
							// function (optional)
							func : function(chart) {
								// setup some logic for the chart
							}
						},

						// MAIN PAGE CHART NORM
						// ------------------------------------------------------------------------------
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
						            } 
//						            layout: 'vertical',
//						            align: 'right',
//						            verticalAlign: 'middle',
//						            borderWidth: 0
						        },
								tooltip : {
									crosshairs: true,
						            shared: true,
						            useHTML: true,
						            headerFormat: '<small>{point.key}</small><table>',
						            pointFormat: '<tr><td style="color: {series.color}">{series.name}: </td>' +
						            '<td style="text-align: right"><b>{point.y}</b></td></tr>',
						            footerFormat: '</table>',
								},
								plotOptions : {
									series : {
										cursor : 'pointer',
										point : {
											events : {
												click : function(e) {
												}
											}
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
						},

					// HOME PAGE CHART
					// TIME------------------------------------------------------------------------------

					}

				});