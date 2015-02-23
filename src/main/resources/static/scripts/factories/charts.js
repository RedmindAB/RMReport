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
								minTickInterval : 5,
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
								tooltip : {
									crosshairs: true,
						            shared: true,
						            useHTML: true,
						            headerFormat: '<small>{point.key}</small><table>',
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
											lineWidth : 1
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
								}
							},
							yAxis : {
								title : {
									text : 'Percentage'
								},
								min : 0
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
						},

					// HOME PAGE CHART
					// TIME------------------------------------------------------------------------------

					}

				});