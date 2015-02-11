angular.module('webLog')
		.factory('Charts',function() {

					return {
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
								title : {
									text : 'Tests run'
								}
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
									type : "areaspline",
									backgroundColor : '#ecf0f1',
									events : {
										selection : function(event) {
											console.log(event);
											var text, label;
											if (event.xAxis) {
												text = 'min: '
														+ Highcharts
																.numberFormat(
																		event.xAxis[0].min,
																		2)
														+ ', max: '
														+ Highcharts
																.numberFormat(
																		event.xAxis[0].max,
																		2);
											} else {
												text = 'Selection reset';
											}
											label = this.renderer
													.label(text, 100, 120)
													.attr(
															{
																fill : Highcharts
																		.getOptions().colors[0],
																padding : 10,
																r : 5,
																zIndex : 8
															}).css({
														color : '#FFFFFF'
													}).add();

											setTimeout(function() {
												label.fadeOut();
											}, 1000);
										}
									},
									zoomType : 'x'
								},
								tooltip : {},
								plotOptions : {
									series : {
										stacking : "percent",
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
							},
							series : [ {
								data : [],
								name : 'Pass',
								color : '#D4D9DD',
								id : "mainPass"
							}, {
								data : [],
								name : 'Fail',
								color : '#FF0000',
								id : "mainFail"
							} ],
							title : {
							// text: "Pass / Fail for the last " + data.length +
							// " results"
							},
							credits : {
								enabled : false
							},
							loading : false,
							size : {},
							useHighStocks : false,
						},

						// HOME PAGE CHART
						// TIME------------------------------------------------------------------------------
						mainTime : {
							options : {
								chart : {
									backgroundColor : '#ecf0f1',
									type : "line"
								},
								plotOptions : {
									series : {
										stacking : ""
									}
								}
							},
							series : [ {
								data : [ 13, 6, 10, 13, 20, 5, 6, 7, 1, 1 ],
								id : "series-4",
								color : '#FF0000'
							} ],
							title : {
								text : "Time to run"
							},
							credits:{
								enabled: false
							},
							size : {}
						}
					}

				});